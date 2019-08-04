package com.cricscore.deepakshano.cricscore.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.adapter.GroupMemberListAdapter;
import com.cricscore.deepakshano.cricscore.helper.CustomMessageHelper;
import com.cricscore.deepakshano.cricscore.helper.GlobalClass;
import com.cricscore.deepakshano.cricscore.pojo.GroupDetailsPojo;
import com.cricscore.deepakshano.cricscore.pojo.GroupMembersList;
import com.cricscore.deepakshano.cricscore.retrofit.ClientServiceGenerator;
import com.cricscore.deepakshano.cricscore.services.APIMethods;

import java.net.InterfaceAddress;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupDetailsActivity extends AppCompatActivity {

    private TextView tv_group_details_name, tv_group_details_location, tv_group_details_description,
            tv_group_details_total_members, tv_group_details_availale_members, tv_group_details_unavailable_members,
            tv_group_details_team1_name, tv_group_details_team1_count, tv_group_details_team2_name, tv_group_details_team2_count,
            tv_group_details_more_teams, tv_group_details_join_request_count;
    private TextView btn_group_details_invite_memebers, btn_group_details_join_requests;
    private RecyclerView recycler_group_details_member_list;
    private Context context;
    private ProgressDialog dialog;
    private String groupid = "";

    private GroupMemberListAdapter adapter;
    List<GroupMembersList> membersLists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = this;
            dialog = new ProgressDialog(context);
            setContentView(R.layout.activity_group_details);
            final Intent intent = getIntent();
            groupid = intent.getStringExtra("group_id");
            tv_group_details_name = findViewById(R.id.tv_group_details_name);
            tv_group_details_location = findViewById(R.id.tv_group_details_location);
            tv_group_details_description = findViewById(R.id.tv_group_details_description);
            tv_group_details_total_members = findViewById(R.id.tv_group_details_total_members);
            tv_group_details_availale_members = findViewById(R.id.tv_group_details_availale_members);
            tv_group_details_unavailable_members = findViewById(R.id.tv_group_details_unavailable_members);
            tv_group_details_team1_name = findViewById(R.id.tv_group_details_team1_name);
            tv_group_details_team1_count = findViewById(R.id.tv_group_details_team1_count);
            tv_group_details_team2_name = findViewById(R.id.tv_group_details_team2_name);
            tv_group_details_team2_count = findViewById(R.id.tv_group_details_team2_count);
            tv_group_details_more_teams = findViewById(R.id.tv_group_details_more_teams);
            tv_group_details_join_request_count = findViewById(R.id.tv_group_details_join_request_count);
            btn_group_details_invite_memebers = findViewById(R.id.btn_group_details_invite_memebers);
            btn_group_details_join_requests = findViewById(R.id.btn_group_details_join_requests);
            recycler_group_details_member_list = findViewById(R.id.recycler_group_details_member_list);
            getgroupdetails();

            btn_group_details_invite_memebers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent1=new Intent(context,InviteUserActivity.class);
                        startActivity(intent1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getMessage();
                    }
                }
            });


            btn_group_details_join_requests.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getMessage();
                    }
                }
            });


        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    private void getgroupdetails() {
        try {
            dialog.setMessage("please wait.");
            dialog.setCancelable(false);
            dialog.show();
            APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
            Map<String, String> map = new HashMap<>();
            map.put("Authorization","Bearer " + GlobalClass.usertoken);
            Call<GroupDetailsPojo> call = api.getgroupdetails(groupid, map);
            call.enqueue(new Callback<GroupDetailsPojo>() {
                @Override
                public void onResponse(Call<GroupDetailsPojo> call, Response<GroupDetailsPojo> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getRequestStatus() == 1) {
                                try {
                                    dismissDialog();
                                    tv_group_details_name.setText(response.body().getGroupDetails().getGroupName());
                                    tv_group_details_location.setText(response.body().getGroupDetails().getAreaName());
                                    tv_group_details_description.setText(response.body().getGroupDetails().getDescription());
                                    tv_group_details_total_members.setText(String.valueOf(response.body().getGroupDetails().getMembers().size()));
                                    tv_group_details_availale_members.setText("10");
                                    tv_group_details_unavailable_members.setText("12");
                                    tv_group_details_team1_name.setText("rockers");
                                    tv_group_details_team1_count.setText("5 members");
                                    tv_group_details_team2_name.setText("all stars");
                                    tv_group_details_team2_count.setText("2 members");
                                    tv_group_details_more_teams.setText("New");
                                    tv_group_details_join_request_count.setText("15");
                                    membersLists=response.body().getGroupDetails().getMembers();
                                    adapter = new GroupMemberListAdapter(context,membersLists);
                                    recycler_group_details_member_list.setHasFixedSize(true);
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                                    recycler_group_details_member_list.setLayoutManager(layoutManager);
                                    recycler_group_details_member_list.setAdapter(adapter);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                dismissDialog();
                                CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.ERROR), false, false);
                            }
                        } else {
                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", response.message(), false, false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getMessage();
                    }
                }

                @Override
                public void onFailure(Call<GroupDetailsPojo> call, Throwable t) {
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
