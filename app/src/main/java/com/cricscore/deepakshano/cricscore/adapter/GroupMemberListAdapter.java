package com.cricscore.deepakshano.cricscore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.pojo.GroupMembersList;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class GroupMemberListAdapter extends RecyclerView.Adapter<GroupMemberListAdapter.ViewHolder> {

    Context context;
    List<GroupMembersList> membersLists;
    GroupMemberListAdapter.GroupMemberListAdapterListener groupMemberListAdapterListener;


    public GroupMemberListAdapter(Context context, List<GroupMembersList> membersLists,GroupMemberListAdapterListener groupMemberListAdapterListener) {
        this.context = context;
        this.membersLists = membersLists;
        this.groupMemberListAdapterListener=groupMemberListAdapterListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_member_list_recycler_content, parent, false);
        return new ViewHolder(view);
    }

    public interface GroupMemberListAdapterListener { // create an interface
        void onItemClickListener(int position); // create callback function
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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

            holder.btn_expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!holder.toggleDown){
                        holder.toggleDown=true;
                        holder.btn_expand.setBackgroundResource(R.drawable.expand_less);
                    }
                    else {
                         holder.toggleDown=false;

                        holder.btn_expand.setBackgroundResource(R.drawable.expand_more);
                    }

                }
            });

            holder.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    groupMemberListAdapterListener.onItemClickListener(position);
                }
            });



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
        public ImageView btn_expand;

        public boolean toggleDown=false;
        ConstraintLayout main;



        public ViewHolder(View view) {
            super(view);
            tv_group_member_name = view.findViewById(R.id.tv_group_member_name);
            img_group_member = view.findViewById(R.id.img_group_member);
            tv_group_member_role = view.findViewById(R.id.tv_group_member_role);
            btn_expand=view.findViewById(R.id.btn_expand);
            main=view.findViewById(R.id.all_parent);


        }
    }
}
