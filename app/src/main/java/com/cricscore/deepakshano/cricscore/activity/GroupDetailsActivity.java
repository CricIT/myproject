package com.cricscore.deepakshano.cricscore.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.adapter.AllGroupListAdapter;
import com.cricscore.deepakshano.cricscore.helper.CustomMessageHelper;
import com.cricscore.deepakshano.cricscore.helper.GlobalClass;
import com.cricscore.deepakshano.cricscore.pojo.GetAllGroupsListPojoClass;
import com.cricscore.deepakshano.cricscore.pojo.GroupDetailsPojo;
import com.cricscore.deepakshano.cricscore.retrofit.ClientServiceGenerator;
import com.cricscore.deepakshano.cricscore.services.APIMethods;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupDetailsActivity extends AppCompatActivity {

    private TextView tv_group_details_name, tv_group_details_location, tv_group_details_description,
            tv_group_details_total_members, tv_group_details_availale_members, tv_group_details_unavailable_members,
            tv_group_details_team1_name, tv_group_details_team1_count, tv_group_details_team2_name, tv_group_details_team2_count,
            tv_group_details_more_teams, tv_group_details_join_request_count;
    private Button btn_group_details_add_memebers, btn_group_details_join_requests;
    private RecyclerView recycler_group_details_member_list;
    private Context context;
    private ProgressDialog dialog;
    private String groupid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = this;
            dialog = new ProgressDialog(context);
            setContentView(R.layout.activity_group_details);
            Intent intent = getIntent();
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
            btn_group_details_add_memebers = findViewById(R.id.btn_group_details_add_memebers);
            btn_group_details_join_requests = findViewById(R.id.btn_group_details_join_requests);
            recycler_group_details_member_list = findViewById(R.id.recycler_group_details_member_list);
            getgroupdetails();

            btn_group_details_add_memebers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

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
            map.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJDb2RlV29yayIsInN1YiI6IjVjZTUyMDg3Nzk5ZjZmMDAxNzg5OWJmNSIsImlhdCI6MTU1OTczMDU0NDgzNX0.oDTK_vWcmW4cVIMneq2wnZwjbg4glsh9MChXoh2N-3c");
            Call<GroupDetailsPojo> call = api.getgroupdetails("13548df3-cbf8-4729-8190-a5c9296a58c8", map);
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







                                   /* recycler_group_details_member_list.setHasFixedSize(true);
                                    recycler_group_details_member_list.setLayoutManager(new GridLayoutManager(context, 2));
                                    recycler_group_details_member_list.setAdapter(allGroupListAdapter);
                                    recycler_group_details_member_list.setVisibility(View.VISIBLE);*/
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
