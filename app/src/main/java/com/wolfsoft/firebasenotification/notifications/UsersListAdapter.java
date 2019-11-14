package com.wolfsoft.firebasenotification.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wolfsoft.firebasenotification.MySharedPref;
import com.wolfsoft.firebasenotification.R;

import java.util.ArrayList;


/**
 * Created by wolfsoft on 6/5/19.
 */

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<UserDetails> modelArrayList;

    public UsersListAdapter(Context context, ArrayList<UserDetails> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public UsersListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final UsersListAdapter.ViewHolder holder, final int position) {
        final UserDetails model = modelArrayList.get(position);

        holder.tvStoreName.setText(model.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySharedPref.getInstance(context).saveData("hisUid",model.getUid());
                MySharedPref.getInstance(context).saveData("name",model.getName());
                Intent intent = new Intent(context,ChatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvStoreName, tvAddress;

        ViewHolder(View itemView) {
            super(itemView);

            tvStoreName = itemView.findViewById(R.id.tvStoreName);
        }
    }
}
