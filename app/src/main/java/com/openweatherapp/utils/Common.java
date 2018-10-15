package com.openweatherapp.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.openweatherapp.R;

/**
 * @author Vishal
 * Use for manage common methods for application
 */
public class Common {

    private static Common mSelf;
    private Context context = null;

    private Common() {
    }

    public static Common getInstance() {
        if (mSelf == null) {
            mSelf = new Common();
        }

        return mSelf;
    }


    public void init(Context context) {
        this.context = context;
    }

    /**
     * <b>Description: </b> use to check internet newtwork connection if network
     * connection not available than alert for open network
     * settings
     *
     * @return
     */
    public final boolean isOnline(boolean message) {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
            if (netInfo != null) {
                if (netInfo.isConnectedOrConnecting())
                    return true;
                else
                    Toast.makeText(context, context.getString(R.string.toast_no_internet), Toast.LENGTH_LONG).show();
            }

            if (message) {
                Toast.makeText(context, context.getString(R.string.toast_no_internet), Toast.LENGTH_LONG).show();
                return false;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Hide soft keyboard
     *
     * @param activity
     */
    public final void hideKeyboard(Activity activity) {

        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * <b>Description</b> - hide soft keyboard
     *
     * @param context
     * @param view
     */
    public final void showKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * @param str
     * @return String
     */
    public final String uppercaseFirstLetters(String str) {
        boolean prevWasWhiteSp = true;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i])) {
                if (prevWasWhiteSp) {
                    chars[i] = Character.toUpperCase(chars[i]);
                }
                prevWasWhiteSp = false;
            } else {
                prevWasWhiteSp = Character.isWhitespace(chars[i]);
            }
        }
        return new String(chars);
    }

    public void showToast(final String MESSAGE) {
        Toast.makeText(context, MESSAGE, Toast.LENGTH_SHORT).show();

    }

    /**
     * check string is empty or not
     *
     * @param value
     * @return
     */
    public boolean isStringEmpty(String value) {
        if (value == null || value.equals("null") || value.trim().isEmpty())
            return true;
        else
            return false;
    }

    /**
     * Use for hide keyboard
     *
     * @param context
     */
    public static void hideSoftKeyboard(Context context) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isAcceptingText())
                inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
