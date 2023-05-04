package com.cardtrans.app;

public class StringTransactionReader {

    String m_stTransaction;
    int m_iPtr;
    int m_iLen;

    public StringTransactionReader(String stTransaction) {
        m_stTransaction = stTransaction;
        m_iPtr = 0;
        m_iLen = stTransaction.length();
    }

    public String readString(int len) throws Exception{
        //checkLength(len);
        return m_stTransaction.substring(m_iPtr,m_iPtr+=len);
    }

    public void checkLength(int offset) throws Exception{
        if(m_iPtr+offset>m_iLen){
            throw new Exception("Transaction length " + m_iLen + " reached ptr " + m_iPtr + " offset " + offset);
        }
    }

}
