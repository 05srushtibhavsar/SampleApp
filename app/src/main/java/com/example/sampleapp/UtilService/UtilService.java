package com.example.sampleapp.UtilService;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class UtilService {

    public void hideKeyboard(View view, Activity activity)
    {
        try {
            InputMethodManager inputMethodManager=(InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
