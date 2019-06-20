package com.cricscore.deepakshano.cricscore.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.helper.CustomMessageHelper;
import com.cricscore.deepakshano.cricscore.helper.GlobalClass;
import com.cricscore.deepakshano.cricscore.model.CreateGroupModelClass;
import com.cricscore.deepakshano.cricscore.pojo.GeneralPojoClass;
import com.cricscore.deepakshano.cricscore.retrofit.ClientServiceGenerator;
import com.cricscore.deepakshano.cricscore.services.APIMethods;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Deepak Shano on 6/5/2019.
 */

public class CreateGroupActivity  extends AppCompatActivity {

    //google api sed lat long and area name in the location section

    ImageView iv_group_banner;
    Context context;
    TextView tv_location;
    EditText et_group_desc;
    EditText group_name;
    Button btn_view_members,btn_add_members,btn_publish_group;
    private ProgressDialog dialog;
    CreateGroupModelClass createGroupModelClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        try {
            context = this;
            dialog = new ProgressDialog(context);
            iv_group_banner = findViewById(R.id.iv_group_banner);
            tv_location=findViewById(R.id.tv_location);
            group_name=findViewById(R.id.group_name);
            et_group_desc=findViewById(R.id.et_group_desc);
            btn_view_members=findViewById(R.id.btn_view_members);
            btn_add_members=findViewById(R.id.btn_add_members);
            btn_publish_group=findViewById(R.id.btn_publish_group);
            createGroupModelClass=new CreateGroupModelClass();
            btn_add_members.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            btn_view_members.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            btn_publish_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createGroupModelClass.setGroupName(group_name.getText().toString());
                    createGroupModelClass.setDescription("Respect All no Quarrels");
                    createGroupModelClass.setAreaName("Btm layout");
                    createGroupModelClass.setLat(12.9166);
                    createGroupModelClass.setLng(77.6101);
                    createGroupModelClass.setGrouptype(1);
                    Creategroup();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }
    }

    private boolean checkdeatils() {
        createGroupModelClass.setDescription(et_group_desc.getText().toString());
        createGroupModelClass.setGroupName(group_name.getText().toString());


        return true;
    }

    private void Creategroup() {
        try {
            Map<String, String> map = new HashMap<>();
            dialog.setMessage("please wait.");
            dialog.setCancelable(false);
            dialog.show();
            APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
            map.clear();
            map.put("Authorization","Bearer "+ GlobalClass.usertoken);
            Call<GeneralPojoClass> call = api.creategroup(map,createGroupModelClass);
            call.enqueue(new Callback<GeneralPojoClass>() {
                @Override
                public void onResponse(Call<GeneralPojoClass> call, Response<GeneralPojoClass> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getRequestStatus() == 1) {
                                try {
                                    dismissDialog();
                                    CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                    showDialog.showCustomMessage((Activity) context, "Alert!!", "Group Created Sucessfully", false, false);
                                }catch (Exception e){
                                    dismissDialog();
                                    e.printStackTrace();
                                }
                            } else {
                                dismissDialog();
                                CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.ERROR), false, false);
                            }
                        }else{
                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!",response.message(), false, false);
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
                            dismissDialog();
                           /* hideView(progressBar);
                            phoneNumber.setEnabled(true);
                            ccp.setClickable(true);
                            loginButton.setEnabled(true);*/
                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.SOCKET_ISSUE), false, false);
                        } else {
                            dismissDialog();
                           /* hideView(progressBar);
                            phoneNumber.setEnabled(true);
                            ccp.setClickable(true);
                            loginButton.setEnabled(true);*/
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
    public void dismissDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        } else {
            return;
        }
    }
}
