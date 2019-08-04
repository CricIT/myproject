package com.cricscore.deepakshano.cricscore.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.adapter.GroupMemberListAdapter;
import com.cricscore.deepakshano.cricscore.adapter.InviteUserListAdapter;
import com.cricscore.deepakshano.cricscore.helper.CustomMessageHelper;
import com.cricscore.deepakshano.cricscore.helper.GlobalClass;
import com.cricscore.deepakshano.cricscore.pojo.GroupDetailsPojo;
import com.cricscore.deepakshano.cricscore.pojo.GroupMembersList;
import com.cricscore.deepakshano.cricscore.pojo.MemberInfo;
import com.cricscore.deepakshano.cricscore.pojo.UserList;
import com.cricscore.deepakshano.cricscore.retrofit.ClientServiceGenerator;
import com.cricscore.deepakshano.cricscore.services.APIMethods;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InviteUserActivity extends AppCompatActivity {


    private Context context;
    private ProgressDialog dialog;
    private InviteUserListAdapter adapter;
    List<MemberInfo> memberInfo;
    TextView title_name,btn_back;

    RecyclerView recycle_invite_member_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_invite_user);
            context = this;
            dialog = new ProgressDialog(context);
            title_name=findViewById(R.id.title_name);
            btn_back=findViewById(R.id.btn_back);
            recycle_invite_member_list = findViewById(R.id.recycle_invite_member_list);
            title_name.setText("Invite Users");

            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            getUserList();
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }
    }
    private void getUserList() {
        try {
            dialog.setMessage("please wait.");
            dialog.setCancelable(false);
            dialog.show();
            APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
            Map<String, String> map = new HashMap<>();
            map.put("Authorization","Bearer " + GlobalClass.usertoken);
            map.put("Content-Type","application/json");
            Call<UserList> call = api.inviteuser(map);
            call.enqueue(new Callback<UserList>() {
                @Override
                public void onResponse(Call<UserList> call, Response<UserList> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getRequestStatus() == 1) {
                                try {
                                    dismissDialog();
                                    memberInfo=response.body().getUserDetails();
                                    adapter = new InviteUserListAdapter(context,memberInfo);
                                    recycle_invite_member_list.setHasFixedSize(true);
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                                    recycle_invite_member_list.setLayoutManager(layoutManager);
                                    recycle_invite_member_list.setAdapter(adapter);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                dismissDialog();
                                CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.ERROR), false, false);
                            }
                        } else {
                            dismissDialog();
                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", response.message(), false, false);
                        }
                    } catch (Exception e) {
                        dismissDialog();
                        e.printStackTrace();
                        e.getMessage();
                    }
                }

                @Override
                public void onFailure(Call<UserList> call, Throwable t) {
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
                        dismissDialog();
                        e.printStackTrace();

                    }
                }
            });
        } catch (Exception e) {
            dismissDialog();
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
