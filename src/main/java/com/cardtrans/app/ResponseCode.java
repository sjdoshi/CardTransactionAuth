package com.cardtrans.app;

public enum ResponseCode {

    OK,DE,ER;

    public static ResponseCode parse(String stResponseCode) {
        switch (stResponseCode) {
            case "OK":
                return OK;

            case "DE":
                return DE;

            case "ER":
                return ER;

            default:
                throw new IllegalArgumentException("Invalid ResponseCode " + stResponseCode);
        }
    }

    public String toString(ResponseCode ResponseCode){

        switch(ResponseCode){
            case OK:
                return "OK";

            case DE:
                return "DE";

            case ER:
                return "ER";

            default:
                throw new IllegalArgumentException("Invalid ResponseCode " + ResponseCode);
        }

    }

}
