package com.cardtrans.app;

 public enum MessageType {
     RequestMsg,ResponseMsg;

    static String AUTH_REQUEST_MESSAGE = "0100";
    static String AUTH_RESPONSE_MESSAGE = "0110";

    public static MessageType parse(String stMsgType) {
        switch (stMsgType) {
            case "0100":
                return RequestMsg;

            case "0110":
                return ResponseMsg;

            default:
                throw new IllegalArgumentException("Invalid MessageType " + stMsgType);
        }
    }

        public String toString(MessageType MsgType){

            switch(MsgType){
                case RequestMsg:
                    return "0100";

                case ResponseMsg:
                    return "0110";

                default:
                    throw new IllegalArgumentException("Invalid MessageType " + MsgType);
            }

        }



}
