package com.cardtrans.app;

import com.cardtrans.datatype.BitmapDataFields;
import com.cardtrans.datatype.LLVar;
import com.cardtrans.datatype.Numeric;

import java.time.YearMonth;
import java.util.HexFormat;

public class Highnote {

    public Highnote(){

    }

    public void processTransactions(String[] aStTransactions) throws Exception{
        //TODO: implement
        // 1. Parse a transaction
        // 2. Ensure mandatory fields
        // 3. Run processing rules
        // 4. Construct response message

        //0100e016411111111111111112250000001000
        //0110f016411111111111111112250000001000OK
        //0110f016411111111111111112250000001000OK

        //0100e016401288888888188112250000011000
        //0110f016401288888888188112250000011000DE
        //0110f016401288888888188112250000011000DE

        //0100ec1651051051051051001225000001100011MASTER YODA90089
        //0110fc16510510510510510012250000011000OK11MASTER YODA90089
        //0110fc16510510510510510012250000011000OK11MASTER YODA90089

        //0100e016411111111111111112180000001000
        //0110f016411111111111111112180000001000DE
        //0110f016411111111111111112180000001000DE

        //01006012250000001000
        //01107012250000001000ER
        //01107012250000001000ER

        for (String stTransaction : aStTransactions) {
            ResponseCode responseCode = null;

            ITransaction transaction = new SimpleInterchangeTransaction(stTransaction);

            if(!transaction.hasMandatoryDataFields()){
                responseCode = ResponseCode.ER;
            }

            Numeric expMonth = transaction.getExpMonth();
            Numeric expYear = transaction.getExpYear();

            Numeric zipCode = transaction.getZipCode();
            Numeric amount = transaction.getAmount();
            LLVar PAN = transaction.getPAN();
            LLVar cardHolder = transaction.getCardholderName();


            if (responseCode == null) {
                YearMonth yearMonthExpiry = YearMonth.of(2000+expYear.intValue(),expMonth.intValue());
                boolean fDateExpired = yearMonthExpiry.isBefore(YearMonth.now());

                boolean fValidAmount = false;
                if ((zipCode != null && amount.intValue() < 20000)
                        || (zipCode == null && amount.intValue() < 10000)) {
                    fValidAmount = true;
                    System.out.println("Card amount is valid!");
                } else {
                    System.out.println("Card amount is invalid!");
                }

                if(!fDateExpired && fValidAmount){
                    responseCode = ResponseCode.OK;
                }else{
                    responseCode = ResponseCode.DE;
                }

            }




            StringBuilder sbResponse = new StringBuilder();
            transaction.getBitMapDataFields().getBitSet().set(4);
            String stHex = HexFormat.of().formatHex(transaction.getBitMapDataFields().getBitSet().toByteArray());
            System.out.println("Result Bitset Hex " + stHex);

            sbResponse.append(MessageType.AUTH_RESPONSE_MESSAGE).append(stHex);
            for (int i = 7; i > 1; i--) {
                if (transaction.getBitMapDataFields().getBitSet().get(i)) {
                    switch (i) {
                        case 7:
                            sbResponse.append(PAN.toString());
                            break;

                        case 6:
                            sbResponse.append(expMonth).append(expYear);
                            break;

                        case 5:
                            sbResponse.append(amount);
                            break;

                        case 4:
                            sbResponse.append(responseCode);
                            break;

                        case 3:
                            sbResponse.append(cardHolder);
                            break;

                        case 2:
                            sbResponse.append(zipCode);
                            break;

                        default:
                            throw new Exception("Invalid data");
                    }
                }

            }
            String stResponse = sbResponse.toString();
            System.out.println("Response of " + stTransaction + " is " + stResponse);
        }




    }



}
