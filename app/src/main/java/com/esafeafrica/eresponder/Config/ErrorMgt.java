package com.esafeafrica.eresponder.Config;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esafeafrica.eresponder.R;


public class ErrorMgt {
    private static TextView error, tagerr;
    private static LinearLayout custom;
    private static ImageView mage;

    private static void InitView(View view) {
        error = view.findViewById(R.id.err);
        tagerr = view.findViewById(R.id.errtag);
        mage = view.findViewById(R.id.errimage);
        custom = view.findViewById(R.id.cust_lay);
    }

    public static void DataError(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("Information Error");
        tagerr.setText("Please check your details");
        mage.setImageResource(R.drawable.ic_error_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void NoInternet(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("Check your Internet Connection");
        tagerr.setText("Please contact Your Internet Provider!");
        mage.setImageResource(R.drawable.ic_warning_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void Aboutapp(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        custom.setBackground(context.getResources().getDrawable(R.drawable.about_border));
        //custom.setBackground(R.drawable.side_nav_bar);
        error.setTextColor(context.getResources().getColor(R.color.white));
        tagerr.setTextColor(context.getResources().getColor(R.color.white));
        error.setText("Official Safe Responder App");
        tagerr.setText("Version 1.0");
        mage.setImageResource(R.drawable.web_hi);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void Developer(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("Developed by Wakaima Labs");
        tagerr.setText("info@wakaimalabs.com\n+256 751 073507 / 785 438035");
        mage.setImageResource(R.drawable.waka);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void NotActive(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("No Active");
        tagerr.setText("Item not yet enabled.\n Thanks!");
        mage.setImageResource(R.drawable.ic_warning_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void ServerError(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("Server Encountered Error");
        tagerr.setText("Please Contact Us or Try again");
        mage.setImageResource(R.drawable.ic_error_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void IncorrectDetails(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("Wrong Email or Password");
        tagerr.setText("Please check your details");
        mage.setImageResource(R.drawable.ic_error_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void SuccessAccount(Context context, LayoutInflater inflater, String email) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("Account Opened:" + email);
        tagerr.setText("Please proceed to Login");
        mage.setImageResource(R.drawable.ic_account_circle_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void ActivateEmail(Context context, LayoutInflater inflater, String email) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("Check this email:" + email);
        tagerr.setText("Activation Link Sent");
        mage.setImageResource(R.drawable.ic_email_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void CheckerTerms(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("Must Agree to terms & Condition");
        tagerr.setText("Please see bottom left of screen");
        mage.setImageResource(R.drawable.ic_error_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void ZeroReturn(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("No Results");
        tagerr.setText("There is no data to be shown");
        mage.setImageResource(R.drawable.ic_warning_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}
