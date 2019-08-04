package com.cricscore.deepakshano.cricscore.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.helper.CustomMessageHelper;
import com.cricscore.deepakshano.cricscore.helper.GlobalClass;
import com.cricscore.deepakshano.cricscore.helper.PreferenceManager;
import com.cricscore.deepakshano.cricscore.model.LoginMobileModelClass;
import com.cricscore.deepakshano.cricscore.model.VerifyOtpModelClass;
import com.cricscore.deepakshano.cricscore.pojo.GeneralPojoClass;
import com.cricscore.deepakshano.cricscore.pojo.VerifyOtpPojo;
import com.cricscore.deepakshano.cricscore.retrofit.ClientServiceGenerator;
import com.cricscore.deepakshano.cricscore.services.APIMethods;
import com.goodiebag.pinview.Pinview;
import com.rilixtech.CountryCodePicker;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cricscore.deepakshano.cricscore.helper.GlobalClass.hideView;

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
    private LoginMobileModelClass loginMobile;
    private ProgressDialog progressDialog;
    private Context context;
    private String countryCode;
    private String mobileNumber;
    private VerifyOtpModelClass verifyOtp;
    private PreferenceManager prefManager;
    private ProgressBar progressBar;
    private Drawable customErrorDrawable;
    private InputMethodManager inputManager;
    private  View focusedView;
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

            verifyOtp = new VerifyOtpModelClass();

            progressDialog = new ProgressDialog(LoginActivity.this);

            loginMobile = new LoginMobileModelClass();
            inputManager = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            focusedView= getCurrentFocus();

            GlobalClass.showView(verifyLayout); //show the main layout
            hideView(inputCodeLayout); //hide the otp layout
            //hideView(loadingProgress); //hide the progress loading layout

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (TextUtils.isEmpty(phoneNumber.getText())) {
                            phoneNumber.setError("Please enter your mobile number", customErrorDrawable);
                        } else if (GlobalClass.isValidMobile(phoneNumber.getText().toString())) {
                            GlobalClass.showView(progressBar);
                            phoneNumber.setEnabled(false);
                            ccp.setClickable(false);
                            loginButton.setEnabled(false);
                            attemptLogin();
                        }else {
                            phoneNumber.setError("Not a Valid Number", customErrorDrawable);
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
                        GlobalClass.hideKeyboard(inputManager,focusedView);
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

                    GlobalClass.hideKeyboard(inputManager,focusedView);

                }
            });

        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }


    }
    @Override
    protected void onStart() {
        super.onStart();
        updateUI(prefManager.isLoginSuccessful());
    }
    private void attemptLogin() {
        try {
            GlobalClass.hideKeyboard(inputManager,focusedView);
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
                                GlobalClass.showView(inputCodeLayout);
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

    private void verifyOtp() {
        try {
            APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);

            Call<VerifyOtpPojo> call = api.verifyOtp(verifyOtp);
            call.enqueue(new Callback<VerifyOtpPojo>() {

                @Override
                public void onResponse(Call<VerifyOtpPojo> call, Response<VerifyOtpPojo> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getRequestStatus() == 1) {

                                GlobalClass.mobileNumber=mobileNumber;
                                saveCredentilas(GlobalClass.usertoken=response.body().getToken(),mobileNumber);
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
                public void onFailure(Call<VerifyOtpPojo> call, Throwable t) {
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
    @Override
    public void onBackPressed() {
        try{
        if (verifyLayout.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
        } else {
            GlobalClass.showView(verifyLayout);
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
    private void saveCredentilas(String usertoken, String mobileNumber) {
        try {
            GlobalClass.sharedPreferences = getSharedPreferences("User", 0);
            GlobalClass.edit = GlobalClass.sharedPreferences.edit();
            GlobalClass.edit.putString("UserToken", usertoken);
            GlobalClass.edit.putString("MobileNumber", mobileNumber);
            GlobalClass.edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

}
