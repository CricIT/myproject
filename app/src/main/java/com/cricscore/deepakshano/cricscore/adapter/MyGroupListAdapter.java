package com.cricscore.deepakshano.cricscore.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import com.cricscore.deepakshano.cricscore.pojo.GroupListData;

import java.util.List;

/**
 * Created by Deepak Shano on 6/5/2019.
 */

public class MyGroupListAdapter extends RecyclerView.Adapter<MyGroupListAdapter.ViewHolder> {

    Context context;
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



            holder.my_groups_more_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        showPopupMenu(holder.my_groups_more_icon, position);
                    }catch (Exception e){
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

    private void showPopupMenu(ImageView iv_btn_menu, int position) {
        PopupMenu popup = new PopupMenu(iv_btn_menu.getContext(), iv_btn_menu);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.my_group_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }
    @Override
    public int getItemCount() {
        return getAllGroupsListPojoClass.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView groupImageView;
        public TextView group_name,group_members_count;
        public CardView lyt_card_group_content;
        public ImageView my_groups_more_icon;
        public ViewHolder(View view) {
            super(view);
            groupImageView = itemView.findViewById(R.id.groupImageView);
            group_name = itemView.findViewById(R.id.group_name);
            group_members_count = itemView.findViewById(R.id.group_members_count);
            lyt_card_group_content = itemView.findViewById(R.id.lyt_card_group_content);
            my_groups_more_icon= itemView.findViewById(R.id.my_groups_more_icon);
        }
    }



    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int position;

        public MyMenuItemClickListener(int positon) {
            this.position = positon;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            try {
                switch (menuItem.getItemId()) {
                    case R.id.group_exit:

                        return true;
                    case R.id.group_delete:

                        return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }


}

