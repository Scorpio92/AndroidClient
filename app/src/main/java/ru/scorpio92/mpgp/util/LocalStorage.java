package ru.scorpio92.mpgp.util;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by scorpio92 on 16.01.2018.
 */

public class LocalStorage {

    public final static String AUTH_TOKEN_STORAGE = ".token";
    public final static String SESSION_KEY_STORAGE = ".skey";

    private Context context;

    private LocalStorage(Context context) {
        this.context = context;
    }

    public static LocalStorage getInstance(Context context) {
        return new LocalStorage(context);
    }

    public void setDataInFile(String fileName, String data) throws Exception {
        FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        outputStream.write(data.getBytes());
        outputStream.close();
    }

    public String getDataFromFile(String fileName) throws Exception {
        FileInputStream fileInputStream = context.openFileInput(fileName);
        int ch;
        StringBuilder temp = new StringBuilder();
        while ((ch = fileInputStream.read()) != -1) {
            temp.append(Character.toString((char) ch));
        }
        fileInputStream.close();
        return temp.toString();
    }

    public boolean fileExist(String fileName) {
        String[] arrFileName = context.fileList();
        for (String item : arrFileName) {
            if (item.equals(fileName)) {
                return true;
            }
        }
        return false;
    }
}
