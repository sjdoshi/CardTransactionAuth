package com.cardtrans.datatype;

public class Alpha {
    int m_iLen;
    String m_stVal;

    public Alpha(){
    }

    public Alpha(int len, String stVal){
        m_iLen = len;
        m_stVal = stVal;

        if(m_iLen!=m_stVal.length() || !m_stVal.matches("^[ a-zA-Z0-9\\s]+$")){
            throw new IllegalArgumentException("Invalid LLVar len=" + m_iLen + " val " + m_stVal);
        }
    }

    public int getLength(){
        return m_iLen;
    }

    @Override
    public String toString(){
        return m_stVal;
    }
}
