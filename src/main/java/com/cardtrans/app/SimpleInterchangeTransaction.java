package com.cardtrans.app;

import com.cardtrans.datatype.BitmapDataFields;
import com.cardtrans.datatype.LLVar;
import com.cardtrans.datatype.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HexFormat;


public class SimpleInterchangeTransaction implements ITransaction{

    public static final int s_version = 1;

    public static final int MESSAGE_TYPE_LEN = 4;
    public static final int HEX_FIELDS_LEN = 2;
    public static final int PAN_FIELD_LEN = 2;
    public static final int MONTH_FIELD_LEN = 2;
    public static final int YEAR_FIELD_LEN = 2;
    public static final int AMOUNT_FIELD_LEN = 10;

    public static final int CARDHOLDER_FIELD_LEN = 2;

    public static final int ZIP_FIELD_LEN = 5;

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

        String stMessageType = m_reader.readString(MESSAGE_TYPE_LEN);
        m_messageType = MessageType.parse(stMessageType);

        String stHexDataFields = m_reader.readString(HEX_FIELDS_LEN);
        m_dataBitmap = new BitmapDataFields(stHexDataFields);
        parse();
    }

    public void parse() throws Exception{
        Logger logger = LoggerFactory.getLogger(SimpleInterchangeTransaction.class.getName());

        if(m_dataBitmap.hasPAN()){
            Numeric panLen = new Numeric(m_reader.readString(PAN_FIELD_LEN));
            m_PAN = new LLVar(panLen, m_reader.readString(panLen.intValue()));
            logger.debug("PAN " + m_PAN.getValue());
        }

        if(m_dataBitmap.hasExpiryDate()) {
            m_expMonth = new Numeric(m_reader.readString(MONTH_FIELD_LEN));
            logger.debug("ExpMonth " + m_expMonth.intValue());
            m_expYear = new Numeric(m_reader.readString(YEAR_FIELD_LEN));
            logger.debug("ExpYear " + m_expYear.intValue());
        }

        if(m_dataBitmap.hasTransactionAmount()) {
            m_amount = new Numeric(m_reader.readString(AMOUNT_FIELD_LEN));
            logger.debug("Amount " + m_amount.intValue());
        }

        if(m_dataBitmap.hasCardHolderName()) {
            Numeric cardHolderLen = new Numeric(m_reader.readString(CARDHOLDER_FIELD_LEN));
            m_cardholderName = new LLVar(cardHolderLen, m_reader.readString(cardHolderLen.intValue()));
            logger.debug("CardHolder " + m_cardholderName);
        }

        if(m_dataBitmap.hasZipCode()) {
            m_zipCode = new Numeric(m_reader.readString(ZIP_FIELD_LEN));
            logger.debug("Zip " + m_zipCode.intValue());
        }

    }

    private String constructResult(){
        Logger logger = LoggerFactory.getLogger(SimpleInterchangeTransaction.class.getName());
        StringBuilder sb = new StringBuilder();
        sb.append(MessageType.AUTH_RESPONSE_MESSAGE);

        m_dataBitmap.getBitSet().set(4);
        String stHex = HexFormat.of().formatHex(m_dataBitmap.getBitSet().toByteArray());
        sb.append(stHex);
        logger.info("Result Bitset Hex " + stHex);

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
