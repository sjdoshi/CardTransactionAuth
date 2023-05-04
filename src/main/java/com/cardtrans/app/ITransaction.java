package com.cardtrans.app;

import com.cardtrans.datatype.BitmapDataFields;
import com.cardtrans.datatype.LLVar;
import com.cardtrans.datatype.Numeric;

public interface ITransaction {
    public boolean hasMandatoryDataFields();

    public BitmapDataFields getBitMapDataFields();

    public MessageType getMessageType();

    public LLVar getPAN();

    public Numeric getExpMonth();

    public Numeric getExpYear();

    public Numeric getAmount();

    public LLVar getCardholderName();

    public Numeric getZipCode();

    public void setResponse(ResponseCode responseCode) throws Exception;

    public ResponseCode getResponse();

    public String getResult() throws Exception;



}
