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

import com.fairmontsinternational.charlie.fairmontsinternational.Classes.ExtraDiaryClass;
import com.fairmontsinternational.charlie.fairmontsinternational.DiaryComment;
import com.fairmontsinternational.charlie.fairmontsinternational.Extra_Diary;
import com.fairmontsinternational.charlie.fairmontsinternational.R;

import java.util.List;

public class ExtraDiaryAdapter extends RecyclerView.Adapter<ExtraDiaryAdapter.DiaryViewHolder> {
    private Context context;
    private List<ExtraDiaryClass>myclass;

    public ExtraDiaryAdapter(Context context, List<ExtraDiaryClass>myclass){
        this.context = context;
        this.myclass = myclass;
    }

    @NonNull
    @Override

    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.extra_diary_list, null);
        return new ExtraDiaryAdapter.DiaryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int pstn){
        final ExtraDiaryClass extraDiaryClass = myclass.get(pstn);
        holder.DiaryTitle.setText(extraDiaryClass.getDiarytitle());
        holder.DiaryDate.setText(extraDiaryClass.getDiarydate());

        holder.selectedDiary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String diaryId =extraDiaryClass.getstudentId();
                String DiaryDate = extraDiaryClass.getDiarydate();
                String DiaryTitle = extraDiaryClass.getDiarytitle();
                String TeacherComment= extraDiaryClass.getteacherComment();
                String ParentComment = extraDiaryClass.getparentComment();

                Intent intent =new Intent(context,  DiaryComment.class );
                intent.putExtra("diaryId", diaryId);
                intent.putExtra("date",DiaryDate);
                intent.putExtra("Title", DiaryTitle);
                intent.putExtra("Teacher Comment", TeacherComment);
                intent.putExtra("Parent Comment", ParentComment);
                context.startActivity(intent);
                ((Extra_Diary)context).finish();


            }
        });

    }

    @Override
    public int getItemCount(){
        return myclass.size();
    }

    class DiaryViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout selectedDiary;
        TextView DiaryDate, DiaryTitle;

        public DiaryViewHolder(View myView){
            super(myView);
            DiaryTitle = myView.findViewById(R.id.diary_title);
            DiaryDate = myView.findViewById(R.id.diary_date);
            selectedDiary = myView.findViewById(R.id.rLayout_extra);

        }
    }

}
