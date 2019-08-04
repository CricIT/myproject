package com.cricscore.deepakshano.cricscore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.pojo.GroupMembersList;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Deepak Shano on 6/14/2019.
 */

public class GroupMemberListAdapter extends RecyclerView.Adapter<GroupMemberListAdapter.ViewHolder> {

    Context context;
    List<GroupMembersList> membersLists;


    public GroupMemberListAdapter(Context context, List<GroupMembersList> membersLists) {
        this.context = context;
        this.membersLists = membersLists;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_member_list_recycler_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {


            switch (membersLists.get(position).getRole()) {
                case 1:
                    holder.tv_group_member_role.setText("Admin");
                    break;
                case 2:
                    holder.tv_group_member_role.setText("Co-Leader");
                    break;
                default:
                    holder.tv_group_member_role.setText("Member");
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }


    @Override
    public int getItemCount() {
        return membersLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_group_member_name, tv_group_member_role;
        public CircleImageView img_group_member;

        public ViewHolder(View view) {
            super(view);
            tv_group_member_name = itemView.findViewById(R.id.tv_group_member_name);
            img_group_member = itemView.findViewById(R.id.img_group_member);
            tv_group_member_role = itemView.findViewById(R.id.tv_group_member_role);
        }
    }
}
