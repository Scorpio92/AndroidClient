package ru.scorpio92.mpgp.util;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class ViewUtils {

    /**
     * Скрываем клавиатуру
     */
    public static void hideSoftKeyboard(Context context, View focusedView) {
        if(context != null && focusedView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            IBinder windowToken = focusedView.getWindowToken();
            if (inputMethodManager != null)
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
        }
    }

    /**
     * Показываем тост
     */
    public static void showToast(Context context, String text) {
        if (context != null && text != null && !text.isEmpty())
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
