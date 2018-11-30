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
    /*
    public void writeToFile(String filename, String key, Context ctx)
    {
        FileOutputStream outputstream;
        try
        {
            outputstream = ctx.openFileOutput("key-" + filename, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(outputstream);
            oos.write(key.getBytes());
            oos.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public String readFromFile(String filename, Context ctx)
    {
        FileInputStream filestream;
        try
        {
            filestream = ctx.openFileInput("key-" + filename);

        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    */

    private void writeToFile(String filename, String data,Context context)
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

    private String readFromFile(String filename, Context context)
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

    private String[] getKeyList(Context ctx)
    { return ctx.fileList(); }
}
