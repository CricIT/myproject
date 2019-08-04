package com.cricscore.deepakshano.cricscore.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.activity.GroupDetailsActivity;
import com.cricscore.deepakshano.cricscore.helper.CustomMessageHelper;
import com.cricscore.deepakshano.cricscore.helper.GlobalClass;
import com.cricscore.deepakshano.cricscore.pojo.GeneralPojoClass;
import com.cricscore.deepakshano.cricscore.pojo.GroupDetailsPojo;
import com.cricscore.deepakshano.cricscore.pojo.GroupListData;
import com.cricscore.deepakshano.cricscore.retrofit.ClientServiceGenerator;
import com.cricscore.deepakshano.cricscore.services.APIMethods;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Deepak Shano on 6/5/2019.
 */

public class MyGroupListAdapter extends RecyclerView.Adapter<MyGroupListAdapter.ViewHolder> {

    Context context;
    private ProgressDialog dialog;
    private List<GroupListData> getAllGroupsListPojoClass;
    GrouplistAdapterAdapterListener grouplistapadpterlistner;


    public MyGroupListAdapter(Context context, List<GroupListData> getAllGroupsListPojoClass, GrouplistAdapterAdapterListener grouplistapadpterlistner) {
        this.context = context;
        this.getAllGroupsListPojoClass = getAllGroupsListPojoClass;
        this.grouplistapadpterlistner = grouplistapadpterlistner;
    }

    public interface GrouplistAdapterAdapterListener { // create an interface
        void onItemClickListener(int position); // create callback function
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mygroups_list_recycler_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
            holder.group_name.setText(getAllGroupsListPojoClass.get(position).getGroupName());
            holder.group_members_count.setText(getAllGroupsListPojoClass.get(position).getMembers().size() + " Members");
            holder.lyt_card_group_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(context, GroupDetailsActivity.class);
                        intent.putExtra("group_id", getAllGroupsListPojoClass.get(position).getId());
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });
            holder.my_groups_more_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        showPopupMenu(holder.my_groups_more_icon, position, getAllGroupsListPojoClass.get(position).getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getMessage();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    private void showPopupMenu(ImageView iv_btn_menu, int position, String groupid) {
        PopupMenu popup = new PopupMenu(iv_btn_menu.getContext(), iv_btn_menu);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.my_group_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position, groupid));
        popup.show();
    }

    @Override
    public int getItemCount() {
        return getAllGroupsListPojoClass.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView groupImageView;
        public TextView group_name, group_members_count;
        public CardView lyt_card_group_content;
        public ImageView my_groups_more_icon;

        public ViewHolder(View view) {
            super(view);
            groupImageView = itemView.findViewById(R.id.groupImageView);
            group_name = itemView.findViewById(R.id.group_name);
            group_members_count = itemView.findViewById(R.id.group_members_count);
            lyt_card_group_content = itemView.findViewById(R.id.lyt_card_group_content);
            my_groups_more_icon = itemView.findViewById(R.id.my_groups_more_icon);
        }
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        private int position;
        private String groupid;

        public MyMenuItemClickListener(int positon, String groupid) {
            this.position = positon;
            this.groupid = groupid;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            try {
                switch (menuItem.getItemId()) {
                    case R.id.group_exit:
                        exitgroup(groupid);
                        return true;
                    case R.id.group_delete:
                        deletegroup(groupid);
                        return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }


    }
    private void exitgroup(String groupid) {
        try {
            dialog = new ProgressDialog(context);
            dialog.setMessage("please wait.");
            dialog.setCancelable(false);
            dialog.show();
            APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
            Map<String, String> map = new HashMap<>();
            map.put("Authorization", "Bearer " + GlobalClass.usertoken);
            map.put("Content-Type","application/json");
            Call<GeneralPojoClass> call = api.exitgroup(groupid, map);
            call.enqueue(new Callback<GeneralPojoClass>() {
                @Override
                public void onResponse(Call<GeneralPojoClass> call, Response<GeneralPojoClass> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getRequestStatus() == 1) {
                                try {
                                    dismissDialog();
                                    CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                    showDialog.showCustomMessage((Activity) context, "Success!", "Group Deleted", false, false);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                dismissDialog();
                                CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                showDialog.showCustomMessage((Activity) context, "Alert!!", context.getString(R.string.ERROR), false, false);
                            }
                        } else {
                            dismissDialog();
                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", response.message(), false, false);
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
                            showDialog.showCustomMessage((Activity) context, "Alert!!", context.getString(R.string.SOCKET_ISSUE), false, false);
                        } else {
                            dismissDialog();
                           /* hideView(progressBar);
                            phoneNumber.setEnabled(true);
                            ccp.setClickable(true);
                            loginButton.setEnabled(true);*/
                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", context.getString(R.string.NETWORK_ISSUE), false, false);
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
    private void deletegroup(String groupid) {
        try {
            dialog = new ProgressDialog(context);
            dialog.setMessage("please wait.");
            dialog.setCancelable(false);
            dialog.show();
            APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
            Map<String, String> map = new HashMap<>();
            map.put("Authorization", "Bearer " + GlobalClass.usertoken);
            Call<GeneralPojoClass> call = api.deletegroup(groupid, map);
            call.enqueue(new Callback<GeneralPojoClass>() {
                @Override
                public void onResponse(Call<GeneralPojoClass> call, Response<GeneralPojoClass> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getRequestStatus() == 1) {
                                try {
                                    dismissDialog();
                                    CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                    showDialog.showCustomMessage((Activity) context, "Success!", "Group Deleted", false, false);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                dismissDialog();
                                CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                showDialog.showCustomMessage((Activity) context, "Alert!!", context.getString(R.string.ERROR), false, false);
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
                            showDialog.showCustomMessage((Activity) context, "Alert!!", context.getString(R.string.SOCKET_ISSUE), false, false);
                        } else {
                            dismissDialog();
                           /* hideView(progressBar);
                            phoneNumber.setEnabled(true);
                            ccp.setClickable(true);
                            loginButton.setEnabled(true);*/
                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", context.getString(R.string.NETWORK_ISSUE), false, false);
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

