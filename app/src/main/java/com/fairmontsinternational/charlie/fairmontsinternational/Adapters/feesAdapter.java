package com.fairmontsinternational.charlie.fairmontsinternational.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fairmontsinternational.charlie.fairmontsinternational.Classes.feesClass;
import com.fairmontsinternational.charlie.fairmontsinternational.R;

import java.util.List;

public class    feesAdapter extends RecyclerView.Adapter<feesAdapter.feesViewHolder>{
    private Context mCtx;
    private List<feesClass> feesClassList;

    public feesAdapter(Context mCtx, List<feesClass> feesClassList) {
        this.mCtx = mCtx;
        this.feesClassList = feesClassList;
    }

    @NonNull
    @Override
    public feesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view= inflater.inflate(R.layout.fee_logs,null);
        return new feesAdapter.feesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull feesViewHolder holder, int position) {
        final feesClass feesClass=feesClassList.get(position);
        holder.Feesdate.setText(feesClass.getDate());
        holder.Feesamount.setText(feesClass.getAmount());
        holder.FeesDebit.setText(feesClass.getAmount_debit());
        holder.FeesPeriod.setText(feesClass.getPeriod());
        holder.FeesDesc.setText(feesClass.getDescription());
    }

    @Override
    public int getItemCount() {
        return feesClassList.size();
    }

    class feesViewHolder extends RecyclerView.ViewHolder{

        TextView Feesdate,Feesamount,FeesDebit,FeesPeriod,FeesDesc;

        public feesViewHolder(View itemView) {
            super(itemView);
            Feesdate=itemView.findViewById(R.id.fees_date);
            Feesamount=itemView.findViewById(R.id.fees_amount);
            FeesDebit=itemView.findViewById(R.id.fees_debit);
            FeesPeriod=itemView.findViewById(R.id.fees_academic_period);
            FeesDesc=itemView.findViewById(R.id.fees_description);
        }
    }

}
