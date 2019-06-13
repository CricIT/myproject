package com.cricscore.deepakshano.cricscore.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.activity.GroupDetailsActivity;
import com.cricscore.deepakshano.cricscore.pojo.GroupListData;
import com.cricscore.deepakshano.cricscore.pojo.HostingGroundList;

import java.util.List;

/**
 * Created by Deepak Shano on 6/5/2019.
 */

public class AllGroupListAdapter extends RecyclerView.Adapter<AllGroupListAdapter.ViewHolder> {

    Context context;
    private List<GroupListData> getAllGroupsListPojoClass;
    GrouplistAdapterAdapterListener grouplistapadpterlistner;


    public AllGroupListAdapter(Context context, List<GroupListData> getAllGroupsListPojoClass, GrouplistAdapterAdapterListener grouplistapadpterlistner) {
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            holder.group_name.setText(getAllGroupsListPojoClass.get(position).getGroupName());
            holder.group_members_count.setText(getAllGroupsListPojoClass.get(position).getMembers().size()+" Members");
            holder.lyt_card_group_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(context, GroupDetailsActivity.class);
                        intent.putExtra("group_id", getAllGroupsListPojoClass.get(position).getId());
                        context.startActivity(intent);
                    }catch (Exception e){
                        e.getMessage();
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }


    @Override
    public int getItemCount() {
        return getAllGroupsListPojoClass.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView groupImageView;
        public TextView group_name,group_members_count;
        public CardView lyt_card_group_content;
        public ViewHolder(View view) {
            super(view);
            groupImageView = itemView.findViewById(R.id.groupImageView);
            group_name = itemView.findViewById(R.id.group_name);
            group_members_count = itemView.findViewById(R.id.group_members_count);
            lyt_card_group_content = itemView.findViewById(R.id.lyt_card_group_content);
        }
    }
}

