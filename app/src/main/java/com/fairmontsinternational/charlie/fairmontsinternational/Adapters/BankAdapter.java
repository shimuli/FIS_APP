package com.fairmontsinternational.charlie.fairmontsinternational.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fairmontsinternational.charlie.fairmontsinternational.Classes.BankInfoClass;
import com.fairmontsinternational.charlie.fairmontsinternational.R;

import java.util.List;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.BankViewHolder> {
    private Context context;
    private List<BankInfoClass>BankList;

    public BankAdapter(Context context, List<BankInfoClass>BankList){
        this.context = context;
        this.BankList = BankList;
    }
    @NonNull
    @Override
    public BankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bank_list, null);
        return  new BankAdapter.BankViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull BankViewHolder holder, int location){
        final BankInfoClass BankInfoClass = BankList.get(location);
        holder.BankName.setText(BankInfoClass.getBankName());
        holder.AccName.setText(BankInfoClass.getAcName());
        holder.AccNumber.setText(BankInfoClass.getAcNumber());
        holder.BankDesc.setText(BankInfoClass.getBankDesc());

    }
    @Override
    public int getItemCount(){
        return BankList.size();
    }
    class BankViewHolder extends RecyclerView.ViewHolder{
        TextView BankName, AccName, AccNumber, BankDesc;

        public BankViewHolder(View view){
            super(view);
            BankName = view.findViewById(R.id.bank_name);
            AccName = view.findViewById(R.id.ac_name);
            AccNumber = view.findViewById(R.id.ac_number);
            BankDesc =view.findViewById(R.id.bank_desc);
        }
    }
}
