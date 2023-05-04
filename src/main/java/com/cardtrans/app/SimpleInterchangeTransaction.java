package com.cardtrans.app;

import com.cardtrans.datatype.BitmapDataFields;
import com.cardtrans.datatype.LLVar;
import com.cardtrans.datatype.Numeric;

import java.util.HexFormat;


public class SimpleInterchangeTransaction implements ITransaction{

    MessageType m_messageType;
    BitmapDataFields m_dataBitmap;
    LLVar m_PAN;
    Numeric m_expMonth,m_expYear,m_amount;

    LLVar m_cardholderName;
    Numeric m_zipCode;

    StringTransactionReader m_reader;

    ResponseCode m_response;

    public SimpleInterchangeTransaction(String stTransaction) throws Exception{

        m_reader = new StringTransactionReader(stTransaction);

        String stMessageType = m_reader.readString(4);
        m_messageType = MessageType.parse(stMessageType);

        String stHexDataFields = m_reader.readString(2);
        m_dataBitmap = new BitmapDataFields(stHexDataFields);
        parse();
    }

    public void parse() throws Exception{

        if(m_dataBitmap.hasPAN()){
            Numeric panLen = new Numeric(m_reader.readString(2));
            m_PAN = new LLVar(panLen, m_reader.readString(panLen.intValue()));
            System.out.println("PAN " + m_PAN.getValue());
        }

        if(m_dataBitmap.hasExpiryDate()) {
            m_expMonth = new Numeric(m_reader.readString(2));
            System.out.println("ExpMonth " + m_expMonth.intValue());
            m_expYear = new Numeric(m_reader.readString(2));
            System.out.println("ExpYear " + m_expYear.intValue());
        }

        if(m_dataBitmap.hasTransactionAmount()) {
            m_amount = new Numeric(m_reader.readString(10));
            System.out.println("Amount " + m_amount.intValue());
        }

        if(m_dataBitmap.hasCardHolderName()) {
            Numeric cardHolderLen = new Numeric(m_reader.readString(2));
            m_cardholderName = new LLVar(cardHolderLen, m_reader.readString(cardHolderLen.intValue()));
            System.out.println("CardHolder " + m_cardholderName);
        }

        if(m_dataBitmap.hasZipCode()) {
            m_zipCode = new Numeric(m_reader.readString(5));
            System.out.println("Zip " + m_zipCode.intValue());
        }

    }

    private String constructResult(){
        StringBuilder sb = new StringBuilder();
        sb.append(MessageType.AUTH_RESPONSE_MESSAGE);

        m_dataBitmap.getBitSet().set(4);
        String stHex = HexFormat.of().formatHex(m_dataBitmap.getBitSet().toByteArray());
        sb.append(stHex);
        System.out.println("Result Bitset Hex " + stHex);

        if(m_dataBitmap.hasPAN()) {
            sb.append(m_PAN.toString());
        }

        if(m_dataBitmap.hasExpiryDate()) {
            sb.append(m_expMonth).append(m_expYear);
        }

        if(m_dataBitmap.hasTransactionAmount()) {
            sb.append(m_amount);
        }

        if(m_dataBitmap.hasResponseCode()) {
            sb.append(m_response);
        }

        if(m_dataBitmap.hasCardHolderName()){
            sb.append(m_cardholderName);
        }

        if(m_dataBitmap.hasZipCode()){
            sb.append(m_zipCode);
        }

        return sb.toString();
    }

    public boolean hasMandatoryDataFields() {
        return m_dataBitmap.hasMandatoryFields();
    }

    public BitmapDataFields getBitMapDataFields() {
        return m_dataBitmap;
    }

    public StringTransactionReader getReader() {
        return m_reader;
    }

    public MessageType getMessageType() {
        return m_messageType;
    }

    public LLVar getPAN() {
        return m_PAN;
    }

    public Numeric getExpMonth() {
        return m_expMonth;
    }

    public Numeric getExpYear() {
        return m_expYear;
    }

    public Numeric getAmount() {
        return m_amount;
    }

    public LLVar getCardholderName() {
        return m_cardholderName;
    }

    public Numeric getZipCode() {
        return m_zipCode;
    }

    public void setResponse(ResponseCode responseCode) throws Exception{
        if(m_response!=null){
            throw new Exception("Response" + m_response + " already set!");
        }
        m_response = responseCode;
    }

    public ResponseCode getResponse(){
        return m_response;
    }

    public String getResult() throws Exception{
        if(m_response==null){
            throw new Exception("Response not set!");
        }
        return constructResult();
    }
}
