package com.chat.secure.reilly.securechat;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

public class LocalKey
{


    //Example input:
    // writeKey("cat-dog", "password", getBaseContext())
    //Example creates a a file with the key password inside file cat-dog
    public static void writeKey(String filename, String data, Context context)
    {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("key-" + filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    //Will return an empty string if file is not found
    public static String readKey(String filename, Context context)
    {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("key-" + filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("key activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("key activity", "Can not read file: " + e.toString());
        }
        return ret;
    }

    //True if key successfully removed, false otherwise
    public static boolean removeKey(String filename, Context ctx)
    {
        return ctx.deleteFile("key-" + filename);
    }

    private static String[] getKeyList(Context ctx)
    { return ctx.fileList(); }
    /*
    public static void printKeys(Context ctx)
    {
        String[] filenames = getKeyList(ctx);
        for(int i = 0; i < filenames.length; i++)
        {
            Log.v("File [" + i + "] = ", filenames[i]);
        }
    }
    */
}
