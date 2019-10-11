package com.fairmontsinternational.charlie.fairmontsinternational.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fairmontsinternational.charlie.fairmontsinternational.Classes.NotificationClass;
import com.fairmontsinternational.charlie.fairmontsinternational.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationActivityViewHolder> {
    private Context context;
    private List<NotificationClass>notificationClasses;

    public NotificationAdapter(Context context, List<NotificationClass>notificationClasses){
        this.context = context;
        this.notificationClasses = notificationClasses;
    }
    @NonNull
    @Override
    public NotificationActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_notification_list, null);
        return new NotificationAdapter.NotificationActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationActivityViewHolder holder, int location){
        final NotificationClass NotificationClass =  notificationClasses.get(location);
        holder.NoteDate.setText(NotificationClass.getDate());
        holder.NoteTitle.setText(NotificationClass.getTitle());
        holder.NoteDesc.setText(NotificationClass.getDescription());
    }

    @Override
    public int getItemCount(){
        return notificationClasses.size();
    }
    class NotificationActivityViewHolder extends RecyclerView.ViewHolder{
        TextView NoteDate, NoteTitle, NoteDesc;

        public NotificationActivityViewHolder(View view){
            super(view);
            NoteDate = view.findViewById(R.id.note_date);
            NoteTitle = view.findViewById(R.id.note_title);
            NoteDesc = view.findViewById(R.id.note_description);
        }
    }

    }


