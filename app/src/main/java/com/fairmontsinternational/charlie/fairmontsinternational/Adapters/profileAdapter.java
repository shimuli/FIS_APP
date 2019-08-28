package com.fairmontsinternational.charlie.fairmontsinternational.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fairmontsinternational.charlie.fairmontsinternational.Classes.profiles;
import com.fairmontsinternational.charlie.fairmontsinternational.MainHomepage;
import com.fairmontsinternational.charlie.fairmontsinternational.R;
import com.fairmontsinternational.charlie.fairmontsinternational.children_profiles;

import java.util.List;

public class profileAdapter extends RecyclerView.Adapter<profileAdapter.profileViewHolder>{

    private Context mCtx;
    private List<profiles> profilesList;


    public profileAdapter(Context mCtx, List<profiles> profilesList) {
        this.mCtx = mCtx;
        this.profilesList = profilesList;
    }

    @NonNull
    @Override
    public profileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view= inflater.inflate(R.layout.profile_list,null);
        return new profileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull profileViewHolder holder, int position) {
        final profiles profiles= profilesList.get(position);
        holder.studentName.setText(profiles.getChildname());
        holder.studentLevel.setText(profiles.getLevel());
        holder.educationSystem.setText("Admission No: "+profiles.getAdmissionNo());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String admission_no= profiles.getAdmissionNo();
                Intent intent=new Intent(mCtx, MainHomepage.class);
                intent.putExtra("admission_no",admission_no);
                mCtx.startActivity(intent);
                ((children_profiles)mCtx).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return profilesList.size();
    }

    class profileViewHolder extends RecyclerView.ViewHolder{

        TextView studentName,studentLevel,educationSystem;
        RelativeLayout constraintLayout;

        public profileViewHolder(View itemView) {
            super(itemView);
            studentName=itemView.findViewById(R.id.profile_studentName);
            studentLevel=itemView.findViewById(R.id.profile_studentGrade);
            educationSystem=itemView.findViewById(R.id.profile_studentSystem);
            constraintLayout=itemView.findViewById(R.id.select_child_profile);
        }
    }

}
