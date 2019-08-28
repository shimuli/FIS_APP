package com.fairmontsinternational.charlie.fairmontsinternational.Classes;

public class feesClass {
    private String date,amount,amount_debit,period,description;

    public feesClass(String date, String amount, String amount_debit, String period, String description) {
        this.date = date;
        this.amount = amount;
        this.amount_debit = amount_debit;
        this.period = period;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }

    public String getAmount_debit() {
        return amount_debit;
    }

    public String getPeriod() {
        return period;
    }

    public String getDescription() {
        return description;
    }
}
