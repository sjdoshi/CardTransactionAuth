package com.cardtrans.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.YearMonth;

public class TransactionProcessor {

    ITransaction m_transaction;

    public TransactionProcessor(){}

    public TransactionProcessor(ITransaction transaction){
        m_transaction = transaction;
    }

    public ResponseCode process() throws Exception{
        if(m_transaction==null){
            throw new Exception("Transaction not set!");
        }

        Logger logger = LoggerFactory.getLogger(TransactionProcessor.class.getName());

        //Check for mandatory fields
        if(!m_transaction.hasMandatoryDataFields()){
            return ResponseCode.ER;
        }
        //Check for expiry date
        boolean fDateExpired = validateExpiryDateIn2000Decade(m_transaction.getExpYear().intValue(),m_transaction.getExpMonth().intValue());
        //Check for valid amount
        int iZipCode = m_transaction.getZipCode()!=null?m_transaction.getZipCode().intValue():-1;
        boolean fValidAmount = approveEligibleAmount(m_transaction.getAmount().longValue(),iZipCode);

        if(!fDateExpired && fValidAmount){
            return ResponseCode.OK;
        }

        return ResponseCode.DE;
    }

    public boolean validateExpiryDateIn2000Decade(int iExpiryYear, int iExpiryMonth){
        YearMonth yearMonthExpiry = YearMonth.of(2000+iExpiryYear,iExpiryMonth);
        return yearMonthExpiry.isBefore(YearMonth.now());
    }

    public boolean approveEligibleAmount(long lAmount, int iZipCode){
        Logger logger = LoggerFactory.getLogger(TransactionProcessor.class.getName());
        logger.trace(String.format("Criteria amount %s zip %s",lAmount,iZipCode));
        boolean fValidAmount = false;
        if ((iZipCode != -1 && lAmount <= 20000)
                || (iZipCode == -1 && lAmount <= 10000)) {
            fValidAmount = true;
            logger.debug("Card amount is valid!");
        } else {
            logger.debug("Card amount is invalid!");
        }
        return fValidAmount;
    }

    public ITransaction getTransaction(){
        return m_transaction;
    }

    public void setTransaction(ITransaction transaction) throws Exception{
        if (getTransaction()!=null){
            throw new Exception("Transaction already set!");
        }
        m_transaction = transaction;
    }


}
