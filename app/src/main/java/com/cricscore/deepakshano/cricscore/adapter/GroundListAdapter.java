package com.cricscore.deepakshano.cricscore.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cricscore.deepakshano.cricscore.R;

import com.cricscore.deepakshano.cricscore.activity.HostingTournament;
import com.cricscore.deepakshano.cricscore.pojo.HostingGroundList;

import java.util.List;

public class GroundListAdapter extends RecyclerView.Adapter<GroundListAdapter.ViewHolder> {

    Context context;
    List<HostingGroundList> hostingGroundList;
    GroundListAdapterAdapterListener groundListAdapterAdapterListener;


    public GroundListAdapter(Context context, List<HostingGroundList> hostingGroundList, GroundListAdapterAdapterListener groundListAdapterAdapterListener) {
        this.context = context;
        this.hostingGroundList = hostingGroundList;
        this.groundListAdapterAdapterListener = groundListAdapterAdapterListener;
    }

    public interface GroundListAdapterAdapterListener { // create an interface
        void onItemClickListener(int position); // create callback function
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ground_listing_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            holder.tv_ground_name.setText(hostingGroundList.get(position).getGroundName());
            holder.tv_ground_loc.setText(hostingGroundList.get(position).getArea());
            holder.tv_ratings.setText(String.valueOf(hostingGroundList.get(position).getRating()));
            holder.sel_gnd_lyt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    groundListAdapterAdapterListener.onItemClickListener(position);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }


    @Override
    public int getItemCount() {
        return hostingGroundList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_ground_name, tv_ground_loc, tv_ratings;
        public LinearLayout sel_gnd_lyt;

        public ViewHolder(View view) {
            super(view);
            tv_ground_name = itemView.findViewById(R.id.tv_ground_name);
            tv_ground_loc = itemView.findViewById(R.id.tv_ground_loc);
            tv_ratings = itemView.findViewById(R.id.tv_ratings);
            sel_gnd_lyt = itemView.findViewById(R.id.sel_gnd_lyt);
        }
    }
}
