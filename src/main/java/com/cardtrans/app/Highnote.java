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
        //TODO: implement
        // 1. Parse a transaction
        // 2. Ensure mandatory fields
        // 3. Run processing rules
        // 4. Construct response message

        //0100e016411111111111111112250000001000
        //0110f016411111111111111112250000001000OK
        //0110f016411111111111111112250000001000OK

        //0100e016401288888888188112250000011000
        //0110f016401288888888188112250000011000DE
        //0110f016401288888888188112250000011000DE

        //0100ec1651051051051051001225000001100011MASTER YODA90089
        //0110fc16510510510510510012250000011000OK11MASTER YODA90089
        //0110fc16510510510510510012250000011000OK11MASTER YODA90089

        //0100e016411111111111111112180000001000
        //0110f016411111111111111112180000001000DE
        //0110f016411111111111111112180000001000DE

        //01006012250000001000
        //01107012250000001000ER
        //01107012250000001000ER

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
