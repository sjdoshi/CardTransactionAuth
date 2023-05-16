package com.cardtrans.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.YearMonth;

public class TransactionProcessor {

    ITransaction m_transaction;

    public TransactionProcessor(ITransaction transaction){
        m_transaction = transaction;
    }

    public ResponseCode process() {
        Logger logger = LoggerFactory.getLogger(TransactionProcessor.class.getName());

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
        Logger logger = LoggerFactory.getLogger(TransactionProcessor.class.getName());
        logger.trace(String.format("Criteria amount %s zip %s",m_transaction.getAmount().longValue(),m_transaction.getZipCode()));
        boolean fValidAmount = false;
        if ((m_transaction.getZipCode() != null && m_transaction.getAmount().longValue() < 20000)
                || (m_transaction.getZipCode() == null && m_transaction.getAmount().longValue() < 10000)) {
            fValidAmount = true;
            logger.debug("Card amount is valid!");
        } else {
            logger.debug("Card amount is invalid!");
        }
        return fValidAmount;
    }


}
