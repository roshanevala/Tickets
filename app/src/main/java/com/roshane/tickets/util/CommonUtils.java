package com.roshane.tickets.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by roshanedesilva on 7/7/17.
 */

public class CommonUtils {
    private static CommonUtils commonUtils;

    private ProgressDialog mProgressDialog;

    /**
     * Singleton method for CommonUtils
     *
     * @return CommonUtils
     */
    public static CommonUtils getInstance()
    {
        if (commonUtils == null)
        {
            commonUtils = new CommonUtils();
        }
        return commonUtils;
    }

    public DateFormat getDateFormat()
    {
        return new SimpleDateFormat("hh:mm a", Locale.US);
    }

    public DateFormat getDayFormat()
    {
        return new SimpleDateFormat("dd MMM hh:mm a", Locale.US);
    }




    /**
     * Display Toast Message in Application
     *
     * @param message Display Toast message
     */
    public void showToastMessage(int message)
    {
        Toast.makeText(RootApplication.getAppContext(),
                RootApplication.getAppContext().getResources().getString(message),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Store String value in Application Shared Pref
     * @param key hash key
     * @param value hash value
     */
    public void storeSharedPrefString(String key, String value)
    {
        SharedPreferences sharedPref = RootApplication.getAppContext()
                .getSharedPreferences(TicketsGlobal.SHARED_PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Store Integer value in Application Shared Pref
     * @param key hash key
     * @param value hash value
     */
    public void storeSharedPrefInt(String key, int value)
    {
        SharedPreferences sharedPref = RootApplication.getAppContext()
                .getSharedPreferences(TicketsGlobal.SHARED_PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Store Boolean value in Application Shared Pref
     * @param key hash key
     * @param value hash value
     */
    public void storeSharedPrefBoolean(String key, boolean value)
    {
        SharedPreferences sharedPref = RootApplication.getAppContext()
                .getSharedPreferences(TicketsGlobal.SHARED_PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Get the String value from Shared Preference
     * Using Key
     *
     * @param key hash key
     * @return
     */
    public String getSharedPrefString(String key)
    {
        SharedPreferences sharedPref = RootApplication.getAppContext()
                .getSharedPreferences(TicketsGlobal.SHARED_PREF_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }

    /**
     * Get the Integer value from Shared Preference
     * Using Key
     *
     * @param key
     * @return
     */
    public int getSharedPrefInt(String key)
    {
        SharedPreferences sharedPref = RootApplication.getAppContext()
                .getSharedPreferences(TicketsGlobal.SHARED_PREF_FILE, Context.MODE_PRIVATE);
        return sharedPref.getInt(key, 0);
    }

    /**
     * Get the Boolean value from Shared Preference
     * Using Key
     *
     * @param key
     * @return
     */
    public boolean getSharedPrefBoolean(String key)
    {
        SharedPreferences sharedPref = RootApplication.getAppContext()
                .getSharedPreferences(TicketsGlobal.SHARED_PREF_FILE, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false);
    }

    /**
     * Store Array inside the Shared Preference
     * @param array
     * @param arrayName
     * @param mContext
     * @return
     */
    public boolean saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(TicketsGlobal.SHARED_PREF_FILE, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName +"_size", array.length);
        for(int i=0;i<array.length;i++)
        {
            editor.putString(arrayName + "_" + i, array[i]);
        }
        return editor.commit();
    }

    /**
     * Display Progress Dialog, while doing any AsyncTask work
     * @param activity
     * @param message
     */
    public void showProgressDialog(Activity activity, int message)
    {
        // If already progress dialog is loading
        // dismiss the dialog
        hideProgressDialog();

        // Create a new progress dialog
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setMessage(RootApplication.getAppContext().getString(message));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    /**
     * Hide Progress Dialog, After finish the AsyncTAsk Work
     */
    public void hideProgressDialog()
    {
        if (mProgressDialog != null && mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss();
        }
    }

    /**
     * Check Internet Connection is Available or not
     *
     * @return isInternetAvailable
     */
    public boolean isInternetAvailable()
    {
        ConnectivityManager cm =
                (ConnectivityManager) RootApplication.getAppContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();


        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    /**
     * A 64-bit number (as a hex string) that is randomly generated
     * when the user first sets up the device and
     * should remain constant for the lifetime of the user's device.
     *
     * @return deviceId - Android device ID
     */
    public String getDeviceID()
    {
        return Settings.Secure.getString(RootApplication.getAppContext().getContentResolver()
                , Settings.Secure.ANDROID_ID);
    }

    public String getDeviceName()
    {
        return Build.MODEL;
    }

    public String getDeviceType()
    {
        return "Phone";
    }

    /**
     * This provides a convenient way to create an intent that is
     * intended to execute and navigate a new class
     *
     * @param packageContext A Context of the application package implementing
     *                       this class.
     * @param cls            The component class that is to be used for the intent.
     */
    public void navigateTo(Context packageContext, Class<?> cls)
    {
        navigateTo(packageContext, cls, null);
    }

    /**
     * This provides a convenient way to create an intent that is
     * intended to execute and navigate a new class
     *
     * @param packageContext A Context of the application package implementing
     *                       this class.
     * @param cls            The component class that is to be used for the intent.
     * @param bundle Bundle Value
     */
    public void navigateTo(Context packageContext, Class<?> cls, Bundle bundle)
    {
        Intent intent = new Intent(packageContext, cls);
        if (bundle != null)
        {
            intent.putExtras(bundle);
        }
        packageContext.startActivity(intent);
    }

    /**
     * Show Soft Keyboard in UI
     * @param view
     */
    public void showKeyBoard(View view)
    {
        InputMethodManager imm = (InputMethodManager) RootApplication.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Hide Soft Keyboard in UI
     * @param view
     */
    public void hideKeyboard(View view)
    {
        if (view != null)
        {
            InputMethodManager inputManager = (InputMethodManager) RootApplication.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
