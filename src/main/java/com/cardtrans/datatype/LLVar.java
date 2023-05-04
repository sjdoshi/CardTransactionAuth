package com.cardtrans.datatype;

import javax.crypto.NullCipher;
import java.util.regex.PatternSyntaxException;

public class LLVar extends Alpha{
    Numeric m_len;

    public LLVar(){
        m_len = new Numeric(2);
    }
    public LLVar(Numeric numericLen, String stVal){
        super(numericLen.intValue(),stVal);
        m_len = numericLen;
    }

    public String getValue(){
        return super.toString();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(m_len).append(m_stVal);
        return sb.toString();
    }

}
