package com.cricscore.deepakshano.cricscore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricscore.deepakshano.cricscore.R;
import com.cricscore.deepakshano.cricscore.pojo.GroupMembersList;
import com.cricscore.deepakshano.cricscore.pojo.MemberInfo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InviteUserListAdapter extends RecyclerView.Adapter<InviteUserListAdapter.ViewHolder> {

    Context context;
    List<MemberInfo> memberInfo;


    public InviteUserListAdapter(Context context, List<MemberInfo> memberInfo) {
        this.context = context;
        this.memberInfo = memberInfo;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_invite_recycler_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
            holder.tv_user_name.setText(memberInfo.get(position).getFName());

            holder.image_invite_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        holder.image_invite_btn.setImageResource(R.drawable.cancel_btn);
                    }catch (Exception e){
                        e.getMessage();

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
        return memberInfo.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_user_name;
        public CircleImageView img_profile_img;
        ImageView image_invite_btn;

        public ViewHolder(View view) {
            super(view);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            img_profile_img = itemView.findViewById(R.id.img_profile_img);
            image_invite_btn = itemView.findViewById(R.id.image_invite_btn);
        }
    }
}
