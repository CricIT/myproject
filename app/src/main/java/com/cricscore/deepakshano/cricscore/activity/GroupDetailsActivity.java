package com.cricscore.deepakshano.cricscore.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.adapter.GroupMemberListAdapter;
import com.cricscore.deepakshano.cricscore.helper.CustomMessageHelper;
import com.cricscore.deepakshano.cricscore.helper.GlobalClass;
import com.cricscore.deepakshano.cricscore.model.ToggleAdmin;
import com.cricscore.deepakshano.cricscore.pojo.GeneralPojoClass;
import com.cricscore.deepakshano.cricscore.pojo.GroupDetailsPojo;
import com.cricscore.deepakshano.cricscore.pojo.GroupMembersList;
import com.cricscore.deepakshano.cricscore.retrofit.ClientServiceGenerator;
import com.cricscore.deepakshano.cricscore.services.APIMethods;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupDetailsActivity extends AppCompatActivity {

    public Button btn_promote;
    List<GroupMembersList> membersLists;
    private TextView tv_group_details_name, tv_group_details_location, tv_group_details_description,
            tv_group_details_total_members, tv_group_details_availale_members, tv_group_details_unavailable_members,
            tv_group_details_team1_name, tv_group_details_team1_count, tv_group_details_team2_name, tv_group_details_team2_count,
            tv_group_details_more_teams, tv_group_details_join_request_count;
    private TextView btn_group_details_add_memebers, btn_group_details_join_requests;
    private int member_position;
    private TextView btn_group_details_invite_memebers;
    private RecyclerView recycler_group_details_member_list;
    private Context context;
    private ProgressDialog dialog;
    private String groupid = "";
    private ToggleAdmin toggleAdmin;
    private String userid;
    private AlertDialog alertDialogToggleAdmin;
    private GroupMemberListAdapter adapter;
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

            toggleAdmin = new ToggleAdmin();
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
            map.put("Authorization", "Bearer " + GlobalClass.usertoken);
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
                                    membersLists = response.body().getGroupDetails().getMembers();
                                    adapter = new GroupMemberListAdapter(context, membersLists, new GroupMemberListAdapter.GroupMemberListAdapterListener() {
                                        @Override
                                        public void onItemClickListener(int position) {
                                            userid = membersLists.get(position).getUserId();
                                            member_position = membersLists.get(position).getRole();
                                            if (member_position == 1) {
                                                Toast.makeText(GroupDetailsActivity.this, "You are the admin!", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                showPopUpPromote();
                                            }

                                        }
                                    });
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

    private void showPopUpPromote() {
        try {
            final TextView btn_promote_co_admin, btn_demote, btn_remove, btn_make_admin;

            //before inflating the custom alert dialog layout, we will get the current activity viewgroup
            ViewGroup viewGroup = findViewById(android.R.id.content);

            //then we will inflate the custom alert dialog xml that we created
            View dialogView = LayoutInflater.from(this).inflate(R.layout.group_member_toggle, viewGroup, false);


            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            //setting the view of the builder to our custom view that we already inflated
            builder.setView(dialogView);

            //finally creating the alert dialog and displaying it
            alertDialogToggleAdmin = builder.create();
            btn_demote = dialogView.findViewById(R.id.tv_demote_co_admin);
            btn_make_admin = dialogView.findViewById(R.id.tv_promote_admin);
            btn_promote_co_admin = dialogView.findViewById(R.id.tv_promote_co_admin);
            btn_remove = dialogView.findViewById(R.id.tv_remove);

            if (member_position == 2) {
                GlobalClass.hideView(btn_promote_co_admin);
                GlobalClass.showView(btn_make_admin);
                GlobalClass.showView(btn_remove);
                GlobalClass.showView(btn_demote);
            } if (member_position==3){
                GlobalClass.showView(btn_promote_co_admin);
                GlobalClass.showView(btn_remove);
                GlobalClass.hideView(btn_demote);
                GlobalClass.hideView(btn_make_admin);

            }


            btn_promote_co_admin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    makeCoAdmin(1);
                }
            });

            btn_demote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeCoAdmin(0);

                }
            });

            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeMember();

                }
            });

            btn_make_admin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            alertDialogToggleAdmin.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

    }


    private void removeMember() {
        try {
            dialog.setMessage("please wait.");
            dialog.setCancelable(false);
            dialog.show();
            APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
            Map<String, String> map = new HashMap<>();
            map.put("Authorization", "Bearer " + GlobalClass.usertoken);

            Call<GeneralPojoClass> call = api.removeUser(groupid, userid, map);
            call.enqueue(new Callback<GeneralPojoClass>() {
                @Override
                public void onResponse(Call<GeneralPojoClass> call, Response<GeneralPojoClass> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getRequestStatus() == 1) {
                                try {
                                    dismissDialog();
                                    Toast.makeText(GroupDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

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

    private void makeCoAdmin(int bool) {
        try {
            dialog.setMessage("please wait.");
            dialog.setCancelable(false);
            dialog.show();
            APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
            Map<String, String> map = new HashMap<>();
            map.put("Authorization", "Bearer " + GlobalClass.usertoken);
            toggleAdmin.setGroupid(groupid);
            toggleAdmin.setBool(bool);

            Call<GeneralPojoClass> call = api.promoteUser(userid, map, toggleAdmin);
            call.enqueue(new Callback<GeneralPojoClass>() {
                @Override
                public void onResponse(Call<GeneralPojoClass> call, Response<GeneralPojoClass> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getRequestStatus() == 1) {
                                try {
                                    dismissDialog();
                                    Toast.makeText(GroupDetailsActivity.this, userid + "has been promoted!", Toast.LENGTH_SHORT).show();
                                    alertDialogToggleAdmin.dismiss();

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
