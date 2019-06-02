package com.cricscore.deepakshano.cricscore.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.adapter.GroundListAdapter;
import com.cricscore.deepakshano.cricscore.helper.CustomMessageHelper;
import com.cricscore.deepakshano.cricscore.helper.GlobalClass;
import com.cricscore.deepakshano.cricscore.pojo.HostingGroundList;
import com.cricscore.deepakshano.cricscore.pojo.MessageEvent;
import com.cricscore.deepakshano.cricscore.pojo.PaginatedGroundListPoJo;
import com.cricscore.deepakshano.cricscore.retrofit.ClientServiceGenerator;
import com.cricscore.deepakshano.cricscore.services.APIMethods;

import org.greenrobot.eventbus.EventBus;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroundListActivity extends AppCompatActivity {

    Double lat;
    Double lng;
    Integer page;
    List<HostingGroundList> hostingGroundList;
    GroundListAdapter adapter;
    RecyclerView gnd_lst_recycler_view;
    TextView loading_tv;
    private ProgressDialog dialog;
    private Context context;
    private LinearLayout parent;
    private String groundName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ground_listing);
        try {
            gnd_lst_recycler_view = findViewById(R.id.gnd_lst_recycler_view);
            parent = findViewById(R.id.parent_ground);
            dialog = new ProgressDialog(GroundListActivity.this);
            dialog.show();
            dialog.setMessage("Please Wait..");
            context = this;
            getGroundList();
            hostingGroundList = new ArrayList<>();
            TextView back_btn = findViewById(R.id.btn_back);
            TextView tv_title = findViewById(R.id.title_name);
            loading_tv = findViewById(R.id.error_message);

            tv_title.setText("Grounds near you");

            back_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().postSticky(new MessageEvent(groundName));
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }


    private void getGroundList() {
        try {
            /*dialog = new ProgressDialog(this, R.style.theme_mydialog);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setMessage("Searching for grounds near you!");
            dialog.setCancelable(true);
            dialog.show();*/
            lat = 12.9166;
            lng = 77.6101;
            page = 0;
            parent.setVisibility(View.GONE);
            APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
            Call<PaginatedGroundListPoJo> call = api.getGroundList(lat, lng, page);
            call.enqueue(new Callback<PaginatedGroundListPoJo>() {
                @Override
                public void onResponse(Call<PaginatedGroundListPoJo> call, final Response<PaginatedGroundListPoJo> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getRequestStatus() == 1) {
                                parent.setVisibility(View.VISIBLE);
                                dismissDialog();
                                parent.animate().alpha(1.0f).setDuration(1000);
                                hostingGroundList = response.body().getHostingGroundList();
                                adapter = new GroundListAdapter(context, hostingGroundList, new GroundListAdapter.GroundListAdapterAdapterListener() {
                                    @Override
                                    public void onItemClickListener(int position) {
                                        groundName = hostingGroundList.get(position).getGroundName();
                                        EventBus.getDefault().postSticky(new MessageEvent(groundName));
                                        finish();
                                    }
                                });
                                gnd_lst_recycler_view.setHasFixedSize(true);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                                gnd_lst_recycler_view.setLayoutManager(layoutManager);
                                gnd_lst_recycler_view.setAdapter(adapter);


                            }
                        }
                    } catch (Exception e) {
                        dismissDialog();
                        e.printStackTrace();
                        e.getMessage();
                    }
                }

                @Override
                public void onFailure(Call<PaginatedGroundListPoJo> call, Throwable t) {
                    try {
                        Log.d("INSIDE FAILURE", "****");
                        if (t instanceof SocketTimeoutException) {
                            dismissDialog();
                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.SOCKET_ISSUE), false, true);
                        } else {
                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.NETWORK_ISSUE), false, true);
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
        }
    }
}
