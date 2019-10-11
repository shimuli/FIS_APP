package com.fairmontsinternational.charlie.fairmontsinternational.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fairmontsinternational.charlie.fairmontsinternational.Classes.InvoiceFee;
import com.fairmontsinternational.charlie.fairmontsinternational.FeeInvoice;
import com.fairmontsinternational.charlie.fairmontsinternational.R;

import java.util.List;

public class FeeInvoiceAdapter extends RecyclerView.Adapter<FeeInvoiceAdapter.invoiceViewHolder> {
    private Context context;
    private List<InvoiceFee>invoiceFeeList;

    public FeeInvoiceAdapter(Context context, List<InvoiceFee>invoiceFeeList){
        this.context = context;
        this.invoiceFeeList = invoiceFeeList;
    }

    @NonNull
    @Override
    public invoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fee_invoice_list, null);
        return new FeeInvoiceAdapter.invoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull invoiceViewHolder holder, int position){
        final  InvoiceFee invoiceFee =invoiceFeeList.get(position);
        holder.FeesDate.setText(invoiceFee.getDate());
        holder.FeesAmount.setText(invoiceFee.getAmount());
        holder.FeesDebit.setText(invoiceFee.getAmount_debit());
        holder.FeesPeriod.setText(invoiceFee.getPeriod());
        holder.FeesDesc.setText(invoiceFee.getDescription());

    }
    @Override
    public  int getItemCount() {
        return  invoiceFeeList.size();
    }
    class invoiceViewHolder extends RecyclerView.ViewHolder{

        TextView FeesDate,FeesAmount,FeesDebit,FeesPeriod,FeesDesc;

        public invoiceViewHolder(View itemView) {
            super(itemView);
            FeesDate=itemView.findViewById(R.id.fees_date);
            FeesAmount=itemView.findViewById(R.id.fees_amount);
            FeesDebit=itemView.findViewById(R.id.fees_debit);
            FeesPeriod=itemView.findViewById(R.id.fees_academic_period);
            FeesDesc=itemView.findViewById(R.id.fees_description);
        }
    }
}
