package com.cricscore.deepakshano.cricscore.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.helper.CustomMessageHelper;
import com.cricscore.deepakshano.cricscore.pojo.GeneralPojoClass;
import com.cricscore.deepakshano.cricscore.pojo.LoginMobile;
import com.cricscore.deepakshano.cricscore.pojo.VerifyOtp;
import com.cricscore.deepakshano.cricscore.retrofit.ClientServiceGenerator;
import com.cricscore.deepakshano.cricscore.services.APIMethods;
import com.goodiebag.pinview.Pinview;
import com.rilixtech.CountryCodePicker;

import java.net.SocketTimeoutException;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    //private LinearLayout loadingProgress;
    private CountryCodePicker ccp;
    private Button loginButton;
    private EditText phoneNumber;
    private LinearLayout verifyLayout;
    private LinearLayout inputCodeLayout;
    private TextView timer;
    private Button resendCode;
    private Pinview smsCode;
    //private LottieAnimationView animationView;
    private LoginMobile loginMobile;
    private ProgressDialog progressDialog;
    private Context context;
    private String countryCode;
    private String mobileNumber;
    private VerifyOtp verifyOtp;
    private PreferenceManager prefManager;
    private ProgressBar progressBar;
    private Drawable customErrorDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            prefManager = new PreferenceManager(this);

            setContentView(R.layout.activity_login);

            //define views here
            inputCodeLayout = findViewById(R.id.inputCodeLayout);
            //loadingProgress = findViewById(R.id.loadingProgress);
            //loadingProgress.setVisibility(View.INVISIBLE);
            verifyLayout = findViewById(R.id.verifyLayout);
            ccp = findViewById(R.id.ccp);
            loginButton = findViewById(R.id.loginButton);
            phoneNumber = findViewById(R.id.phone_number);
            timer = findViewById(R.id.timer);
            resendCode = findViewById(R.id.resend_code);
            smsCode = findViewById(R.id.sms_code);
            progressBar = findViewById(R.id.progressBar);
            customErrorDrawable = getResources().getDrawable(R.drawable.error);
            customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
            //animationView=findViewById(R.id.animation_success);

            context = LoginActivity.this;

            verifyOtp = new VerifyOtp();

            progressDialog = new ProgressDialog(LoginActivity.this);

            loginMobile = new LoginMobile();


            showView(verifyLayout); //show the main layout
            hideView(inputCodeLayout); //hide the otp layout
            //hideView(loadingProgress); //hide the progress loading layout

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (TextUtils.isEmpty(phoneNumber.getText())) {
                            phoneNumber.setError("Please enter your mobile number", customErrorDrawable);
                        } else if (isValidMobile(phoneNumber.getText().toString())) {
                            showView(progressBar);
                            phoneNumber.setEnabled(false);
                            ccp.setClickable(false);
                            loginButton.setEnabled(false);
                            attemptLogin();
                        }
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }

                }
            });

            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        progressDialog.show();
                        progressDialog.setMessage("Please wait..");
                        progressDialog.setCancelable(false);
                        progressDialog.setCanceledOnTouchOutside(false);
                        hideKeyboard();
                        //trigger this when the OTP code has finished typing
                        final String verifyCode = smsCode.getValue();

                        verifyOtp.setMobile(mobileNumber);
                        verifyOtp.setOtp(verifyCode);
                        verifyOtp();
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });

            smsCode.setPinViewEventListener(new Pinview.PinViewEventListener() {
                @Override
                public void onDataEntered(Pinview pinview, boolean b) {

                    hideKeyboard();

                }
            });

        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }


    }

    private boolean isValidMobile(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < 10) {
                // if(phone.length() != 10) {
                phoneNumber.setError("Not a Valid Number", customErrorDrawable);
            } else {
                check = true;
            }
        }
        return check;
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI(prefManager.isLoginSuccessful());
    }

    public void hideKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            View focusedView = getCurrentFocus();
        /*
         * If no view is focused, an NPE will be thrown
         *
         * Maxim Dmitriev
         */
            if (focusedView != null) {
                if (inputManager != null) {
                    inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    private void updateUI(boolean loginSuccessful) {
        try {
            if (loginSuccessful) {
                launchHomeScreen();
                finish();
            }
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    private void launchHomeScreen() {
        try {
            prefManager.setFirstTimeLaunch(true);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            progressDialog.dismiss();
            finish();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }


    private void verifyOtp() {
        try {
            APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);

            Call<GeneralPojoClass> call = api.verifyOtp(verifyOtp);
            call.enqueue(new Callback<GeneralPojoClass>() {

                @Override
                public void onResponse(Call<GeneralPojoClass> call, Response<GeneralPojoClass> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getRequestStatus() == 1) {
                                launchHomeScreen();
                            } else {
                                CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.ERROR), false, false);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getMessage();
                    }
                }

                @Override
                public void onFailure(Call<GeneralPojoClass> call, Throwable t) {
                    try {
                        Log.d("INSIDE FAILURE", "****");
                        if (t instanceof SocketTimeoutException) {
                            hideView(progressBar);
                            phoneNumber.setEnabled(true);
                            ccp.setClickable(true);
                            loginButton.setEnabled(true);

                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.SOCKET_ISSUE), false, false);
                        } else {
                            hideView(progressBar);
                            phoneNumber.setEnabled(true);
                            ccp.setClickable(true);
                            loginButton.setEnabled(true);
                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.NETWORK_ISSUE), false, false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

    }


    private void attemptLogin() {
        try {
            hideKeyboard();
            countryCode = ccp.getSelectedCountryCode();
            mobileNumber = phoneNumber.getText().toString();

            loginMobile.setCountryCode(countryCode);
            loginMobile.setMobile(mobileNumber);

            firstAttemptLogin();

        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }


    private void firstAttemptLogin() {
        try {
            APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);

            Call<GeneralPojoClass> call = api.attemptLogin(loginMobile);
            call.enqueue(new Callback<GeneralPojoClass>() {
                @Override
                public void onResponse(Call<GeneralPojoClass> call, Response<GeneralPojoClass> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getRequestStatus() == 1) {

                                //show loading screen
                                progressDialog.dismiss();
                                hideView(verifyLayout);
                                showView(inputCodeLayout);
                            } else {
                                CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.ERROR), false, false);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getMessage();
                    }
                }

                @Override
                public void onFailure(Call<GeneralPojoClass> call, Throwable t) {
                    try {
                        Log.d("INSIDE FAILURE", "****");
                        if (t instanceof SocketTimeoutException) {
                            progressDialog.dismiss();

                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.SOCKET_ISSUE), false, false);
                        } else {
                            progressDialog.dismiss();

                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.NETWORK_ISSUE), false, false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }


    private void showView(View... views) {
        try{
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
    }

    private void hideView(View... views) {
        try{
        for (View v : views) {
            v.setVisibility(View.INVISIBLE);
        }
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        try{
        if (verifyLayout.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
        } else {
            showView(verifyLayout);
            hideView(inputCodeLayout);
            hideView(progressBar);
            ccp.setEnabled(true);
            phoneNumber.setEnabled(true);
            loginButton.setEnabled(true);
        }
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
    }
}
