package com.fourthstatelab.pinpointhomelesspeople;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by sid on 4/15/17.
 */

public class Utility {

    public static Typeface fredoka, nunito_bold,nunito_Extrabold,nunito_reg;

    public static void prepareFonts(Context context){
        fredoka= Typeface.createFromAsset(context.getAssets(),"fonts/FredokaOne-Regular.ttf");
        nunito_bold=Typeface.createFromAsset(context.getAssets(),"fonts/Nunito-Bold.ttf");
        nunito_Extrabold=Typeface.createFromAsset(context.getAssets(),"fonts/Nunito-ExtraBold.ttf");
        nunito_reg = Typeface.createFromAsset(context.getAssets(), "fonts/Nunito-Regular.ttf");
    }

    public static void setStatusBar(Window window, Context context, int color) {
        if(Build.VERSION.SDK_INT>=21){
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(context,color));
        }
    }  //CHANGE THE COLOR OF THE STATUS BAR
}
