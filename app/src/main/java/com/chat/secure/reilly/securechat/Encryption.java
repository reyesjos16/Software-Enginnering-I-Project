package com.chat.secure.reilly.securechat;

import android.util.Log;

import javax.crypto.spec.*;
import javax.crypto.Cipher;

import java.security.spec.*;
import java.security.MessageDigest;
import android.util.Base64;


public class Encryption {

    private final Cipher cipher;
    private final SecretKeySpec key;
    private AlgorithmParameterSpec spec;


    public Encryption(String password) throws Exception {
        // hash password with SHA-256 and crop the output to 128-bit for key
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(password.getBytes("UTF-8"));
        byte[] keyBytes = new byte[32];
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);

        cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        key = new SecretKeySpec(keyBytes, "AES");
        spec = getIV();
    }

    public AlgorithmParameterSpec getIV() {
        //NOTE: this iv shouldnt be static
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);

        return ivParameterSpec;
    }

    public String encrypt(String plainText) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        String encryptedText = new String(Base64.encodeToString(encrypted, Base64.DEFAULT));

        return encryptedText;
    }

    public String decrypt(String cryptedText) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] bytes = Base64.decode(cryptedText, Base64.DEFAULT);
        byte[] decrypted = cipher.doFinal(bytes);
        String decryptedText = new String(decrypted, "UTF-8");

        return decryptedText;
    }

    public Message encryptMessage(Message m){
        try {
            return new Message(encrypt(m.getText()), m.getName(), m.getPhotoUrl(), m.getImageUrl(), m.getId());
        }catch (Exception e){
            return null;
        }
    }

    public Message decryptMessage(Message m){
        try {
            return new Message(decrypt(m.getText()), m.getName(), m.getPhotoUrl(), m.getImageUrl(), m.getId());
        }catch (Exception e){
            return null;
        }
    }
    

    public static void test(String pw){
        try{
            Encryption e = new Encryption(pw);
            Encryption e2 = new Encryption(pw + "hey");

            String m = "i am a cool cow";
            Log.v("E:- ", m);
            String cypherText = e.encrypt(m);
            Log.v("E: ", cypherText);
            String clear = e.decrypt(cypherText);
            Log.v("E:!", clear);

            String clear2 = e2.decrypt(cypherText);
            Log.v("E2:!", clear2);


            m = "sup famedy\nfam i am a cool cow i am a cool cow i am a cool cow !!!!!!!!!!!!!!!!!!!!!!!!!!!";
            Log.v("E:- ", m);
            cypherText = e.encrypt(m);
            Log.v("E: ", cypherText);
            clear = e.decrypt(cypherText);
            Log.v("E:!", clear);

        }catch (Exception e){
            Log.v("E: ", "whoops");
        }
    }

}