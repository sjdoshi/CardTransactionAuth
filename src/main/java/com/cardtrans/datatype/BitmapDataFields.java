package com.cardtrans.datatype;

import com.cardtrans.app.SimpleInterchangeTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.BitSet;
import java.util.HexFormat;

public class BitmapDataFields {

    public static final int INDEX_PAN = 7;
    public static final int INDEX_EXPIRY_DATE = 6;
    public static final int INDEX_TRANSACTION_AMOUNT = 5;
    public static final int INDEX_RESPONSE_CODE = 4;
    public static final int INDEX_CARDHOLDER_NAME = 3;
    public static final int INDEX_ZIP_CODE = 2;


    public static BitSet s_bsMandatoryFields = new BitSet();
    static {
        s_bsMandatoryFields.set(7);
        s_bsMandatoryFields.set(6);
        s_bsMandatoryFields.set(5);
    }

    BitSet m_bsFields;
    public BitmapDataFields(String stHexVal){
        long lDataFields = HexFormat.fromHexDigitsToLong(stHexVal);
        m_bsFields = BitSet.valueOf(new long[]{lDataFields});
    }

    public boolean hasMandatoryFields(){
        Logger logger = LoggerFactory.getLogger(BitmapDataFields.class.getName());
        BitSet bsMandatoryCopy = BitSet.valueOf(s_bsMandatoryFields.toByteArray());
        bsMandatoryCopy.andNot(m_bsFields);
        if (bsMandatoryCopy.cardinality() > 0) {
            logger.trace("BitSet cardiniality " + bsMandatoryCopy.cardinality());
            return false;
        }
        return true;
    }

    public BitSet getBitSet(){return m_bsFields;}

    public boolean hasPAN(){return m_bsFields.get(INDEX_PAN);}

    public boolean hasExpiryDate(){return m_bsFields.get(INDEX_EXPIRY_DATE);}

    public boolean hasTransactionAmount(){return m_bsFields.get(INDEX_TRANSACTION_AMOUNT);}

    public boolean hasResponseCode(){return m_bsFields.get(INDEX_RESPONSE_CODE);}

    public boolean hasCardHolderName(){return m_bsFields.get(INDEX_CARDHOLDER_NAME);}

    public boolean hasZipCode(){return m_bsFields.get(INDEX_ZIP_CODE);}
}
