package com.cardtrans.app;

import com.cardtrans.datatype.Alpha;
import com.cardtrans.datatype.LLVar;
import com.cardtrans.datatype.Numeric;

public class SimpleInterchangeTransaction {

    MessageType MessageType;
    BitmapDataFields m_dataBitmap;
    LLVar m_PAN;
    Numeric m_expMonth,m_expYear,m_amount;
    Alpha m_responseCode;
    LLVar m_cardholderName;
    Numeric m_zipCode;

    public SimpleInterchangeTransaction(MessageType MessageType, BitmapDataFields dataBitmap){

    }

    public SimpleInterchangeTransaction(){

    }

    public SimpleInterchangeTransaction(String stTrans){

    }

    public void parse(){

    }

    public boolean isValid(){

        return true;
    }

    public String createResponse(boolean fValid, boolean fCanAuthorize){

        return null;
    }


}
