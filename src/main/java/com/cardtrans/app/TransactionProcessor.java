package com.cardtrans.app;

import java.time.YearMonth;

public class TransactionProcessor {

    ITransaction m_transaction;

    public TransactionProcessor(ITransaction transaction){
        m_transaction = transaction;
    }

    public ResponseCode process() {
        //Check for mandatory fields
        if(!m_transaction.hasMandatoryDataFields()){
            return ResponseCode.ER;
        }
        //Check for expiry date
        boolean fDateExpired = validateExpiryDate();
        //Check for valid amount
        boolean fValidAmount = approveEligibleAmount();

        if(!fDateExpired && fValidAmount){
            return ResponseCode.OK;
        }
        return ResponseCode.DE;
    }

    private boolean validateExpiryDate(){
        YearMonth yearMonthExpiry = YearMonth.of(2000+m_transaction.getExpYear().intValue(),m_transaction.getExpMonth().intValue());
        return yearMonthExpiry.isBefore(YearMonth.now());
    }

    private boolean approveEligibleAmount(){
        boolean fValidAmount = false;
        if ((m_transaction.getZipCode() != null && m_transaction.getAmount().intValue() < 20000)
                || (m_transaction.getZipCode() == null && m_transaction.getAmount().intValue() < 10000)) {
            fValidAmount = true;
            System.out.println("Card amount is valid!");
        } else {
            System.out.println("Card amount is invalid!");
        }
        return fValidAmount;
    }


}
