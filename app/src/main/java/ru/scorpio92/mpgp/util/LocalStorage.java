package ru.scorpio92.mpgp.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;

public class LocalStorage {

    public final static String AUTH_TOKEN_STORAGE = ".token";

    private static volatile LocalStorage localStorageInstance;

    private WeakReference<Context> contextWeakReference;

    private LocalStorage(@NonNull Context context) {
        this.contextWeakReference = new WeakReference<>(context);
    }

    public static void initLocalStorage(Context context) {
        if (localStorageInstance == null && context != null)
            localStorageInstance = new LocalStorage(context.getApplicationContext());
    }

    @Nullable
    public static LocalStorage getLocalStorageInstance() {
        return localStorageInstance;
    }

    public void close() {
        if (contextWeakReference != null)
            contextWeakReference.clear();
        contextWeakReference = null;
        localStorageInstance = null;
    }

    public void setDataInFile(String fileName, String data) throws Exception {
        FileOutputStream outputStream = contextWeakReference.get().openFileOutput(fileName, Context.MODE_PRIVATE);
        outputStream.write(data.getBytes());
        outputStream.close();
    }

    public String getDataFromFile(String fileName) throws Exception {
        FileInputStream fileInputStream = contextWeakReference.get().openFileInput(fileName);
        int ch;
        StringBuilder temp = new StringBuilder();
        while ((ch = fileInputStream.read()) != -1) {
            temp.append(Character.toString((char) ch));
        }
        fileInputStream.close();
        return temp.toString();
    }

    public boolean deleteFile(String fileName) throws Exception {
        return this.contextWeakReference.get().deleteFile(fileName);
    }

    public boolean fileExist(String fileName) {
        String[] arrFileName = contextWeakReference.get().fileList();
        for (String item : arrFileName) {
            if (item.equals(fileName)) {
                return true;
            }
        }
        return false;
    }
}
