package com.cardtrans.app;

import com.cardtrans.datatype.BitmapDataFields;
import com.cardtrans.datatype.LLVar;
import com.cardtrans.datatype.Numeric;

import java.time.YearMonth;
import java.util.HexFormat;

public class Highnote {

    public Highnote(){

    }

    public void processTransactions(String[] aStTransactions) throws Exception{
        for (String stTransaction : aStTransactions) {
            String stResult = processOneTransaction(stTransaction);
            System.out.println("Response of " + stTransaction + " is " + stResult);
        }
    }

    public String processOneTransaction(String stTransaction) throws Exception{
        //Construct transaction
        ITransaction transaction = new SimpleInterchangeTransaction(stTransaction);

        //run it through processor
        TransactionProcessor processor = new TransactionProcessor(transaction);
        ResponseCode responseCode = processor.process();

        //Set the result in the transaction and create a response
        transaction.setResponse(responseCode);
        String stResult = transaction.getResult();

        return stResult;
    }





}
