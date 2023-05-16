package com.cardtrans.app;

import com.cardtrans.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Highnote {

    public Highnote(){

    }

    public void processTransactions(String[] aStTransactions) throws Exception{
        Logger logger = LoggerFactory.getLogger(Highnote.class.getName());
        for (String stTransaction : aStTransactions) {
            String stResult = processOneTransaction(stTransaction);
            logger.info("Response of " + stTransaction + " is " + stResult);
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
