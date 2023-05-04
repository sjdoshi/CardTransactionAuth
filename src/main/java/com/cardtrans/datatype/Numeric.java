package com.cardtrans.datatype;

public class Numeric extends Number{

    int m_len;
    String m_stVal;
    public Numeric(int len){
        m_len = len;
    }

    public Numeric(int len, String stVal){
        m_len = len;
        m_stVal = stVal;
    }


    public int getLength(){
        return m_len;
    }


    public void ensureVal(){
        if(m_stVal==null){
            throw new IllegalArgumentException("Value for numeric not set");
        }
        if(m_len<0 || m_len>10 || m_stVal.length()!=m_len){
            throw new IllegalArgumentException("Invalid numeric len=" + m_len + " val " + m_stVal);
        }
    }
    /**
     * Returns the value of the specified number as an {@code int}.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code int}.
     */
    @Override
    public int intValue() {
        ensureVal();
        return Integer.parseInt(m_stVal);
    }

    /**
     * Returns the value of the specified number as a {@code long}.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code long}.
     */
    @Override
    public long longValue() {
        ensureVal();
        return Long.parseLong(m_stVal);
    }

    /**
     * Returns the value of the specified number as a {@code float}.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code float}.
     */
    @Override
    public float floatValue() {
        ensureVal();
        return Float.valueOf(m_stVal);
    }

    /**
     * Returns the value of the specified number as a {@code double}.
     *
     * @return the numeric value represented by this object after conversion
     * to type {@code double}.
     */
    @Override
    public double doubleValue() {
        ensureVal();
        return Double.valueOf(m_stVal);
    }

    @Override
    public String toString(){
        ensureVal();
        return m_stVal;
    }


}
