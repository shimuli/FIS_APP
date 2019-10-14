package com.fairmontsinternational.charlie.fairmontsinternational.Classes;

public class BankInfoClass {
    public BankInfoClass(String bankName, String acName, String acNumber, String bankDesc) {
        this.bankName = bankName;
        this.acName = acName;
        this.acNumber = acNumber;
        this.bankDesc = bankDesc;
    }

    public String getBankName() {
        return bankName;
    }

    public String getAcName() {
        return acName;
    }

    public String getAcNumber() {
        return acNumber;
    }
    public String getBankDesc(){
        return bankDesc;
    }

    private String bankName, acName, acNumber, bankDesc;
}
