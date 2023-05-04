package com.cardtrans.app;

import java.util.BitSet;

public class BitmapDataFields {

    public static BitSet s_bsMandatoryFields;
    static {
        s_bsMandatoryFields.set(0);
        s_bsMandatoryFields.set(1);
        s_bsMandatoryFields.set(2);
    }

    BitSet m_bsFields;
    public BitmapDataFields(String stVal){
        byte[] abVal = stVal.getBytes();
        if(stVal.length()!=2 || abVal.length!=2){
            throw new IllegalArgumentException("Invalid input for Bitmap" + stVal);
        }
        long l = Long.parseUnsignedLong(stVal);
        m_bsFields = BitSet.valueOf(stVal.getBytes());
    }

    public boolean hasMandatoryFields(){
        BitSet bsCopy = BitSet.valueOf(m_bsFields.toByteArray());
        bsCopy.and(s_bsMandatoryFields);
        return bsCopy.equals(s_bsMandatoryFields);
    }

    public boolean hasCardHolderName(){
        return m_bsFields.get(4);
    }

    public boolean hasZipCode(){
        return m_bsFields.get(5);
    }



}
