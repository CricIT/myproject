package com.cricscore.deepakshano.cricscore.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.activity.CreateGroupActivity;
import com.cricscore.deepakshano.cricscore.adapter.AllGroupListAdapter;
import com.cricscore.deepakshano.cricscore.helper.CustomMessageHelper;
import com.cricscore.deepakshano.cricscore.helper.GlobalClass;
import com.cricscore.deepakshano.cricscore.pojo.GetAllGroupsListPojoClass;
import com.cricscore.deepakshano.cricscore.pojo.GroupListData;
import com.cricscore.deepakshano.cricscore.pojo.VerifyOtpPojo;
import com.cricscore.deepakshano.cricscore.retrofit.ClientServiceGenerator;
import com.cricscore.deepakshano.cricscore.services.APIMethods;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cricscore.deepakshano.cricscore.helper.GlobalClass.hideView;

/**
 * Created by Deepak Shano on 3/4/2019.
 */

public class GroupsFragment extends Fragment {

    private List<GroupListData> getAllGroupsListPojoClass = new ArrayList<>();;
    private Context context;
    private ProgressDialog dialog;
    RecyclerView group_lst_recycler_view;
    AllGroupListAdapter allGroupListAdapter;
    Button create_group_btn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.group_fragment, null);
        try {
           context = getActivity();
           group_lst_recycler_view=view.findViewById(R.id.group_lst_recycler_view);
            create_group_btn=view.findViewById(R.id.create_group_btn);
           dialog = new ProgressDialog(context);
           getallGroupsList();
            create_group_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(context, CreateGroupActivity.class);
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                        e.getMessage();
                    }

                }
            });

       }catch (Exception e){
           e.printStackTrace();
       }
        return view;
    }

    private void getallGroupsList() {
        try {
            getAllGroupsListPojoClass.clear();
            dialog.setMessage("please wait.");
            dialog.setCancelable(false);
            dialog.show();
            APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
            Map<String, String> map = new HashMap<>();
            map.put("Authorization","Bearer "+GlobalClass.usertoken);
            Call<GetAllGroupsListPojoClass> call = api.getGroupList(map);
            call.enqueue(new Callback<GetAllGroupsListPojoClass>() {
                @Override
                public void onResponse(Call<GetAllGroupsListPojoClass> call, Response<GetAllGroupsListPojoClass> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getRequestStatus() == 1) {
                                try {
                                    getAllGroupsListPojoClass = response.body().getData();
                                    dismissDialog();
                                    allGroupListAdapter = new AllGroupListAdapter(getActivity(), getAllGroupsListPojoClass, new AllGroupListAdapter.GrouplistAdapterAdapterListener() {
                                        @Override
                                        public void onItemClickListener(int position) {

                                        }
                                    });
                                    group_lst_recycler_view.setHasFixedSize(true);
                                    group_lst_recycler_view.setLayoutManager(new GridLayoutManager(context, 2));
                                    group_lst_recycler_view.setAdapter(allGroupListAdapter);
                                    group_lst_recycler_view.setVisibility(View.VISIBLE);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            } else {
                                dismissDialog();
                                CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.ERROR), false, false);
                            }
                        }else{
                            dismissDialog();
                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!",response.message(), false, false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getMessage();
                    }
                }

                @Override
                public void onFailure(Call<GetAllGroupsListPojoClass> call, Throwable t) {
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