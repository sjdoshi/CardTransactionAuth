package com.cardtrans.app;

import com.cardtrans.datatype.LLVar;
import com.cardtrans.datatype.Numeric;

import java.io.InputStreamReader;
import java.io.PipedReader;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.nio.LongBuffer;
import java.time.Year;
import java.time.YearMonth;
import java.util.BitSet;
import java.util.Calendar;
import java.util.HexFormat;

public class Highnote {

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
            String stMsgType = null;
            ResponseCode responseCode = null;

            int ptr = 0;
            MessageType MsgType = MessageType.parse(stTransaction.substring(ptr, ptr += 4));
            long lDataFields = HexFormat.fromHexDigitsToLong(stTransaction, ptr, ptr += 2);
            BitSet bitSetData = BitSet.valueOf(new long[]{lDataFields});
            long mandatoryVal = 224;
            BitSet bitSetMandatory = BitSet.valueOf(new long[]{mandatoryVal});
            bitSetMandatory.andNot(bitSetData);
            if (bitSetMandatory.cardinality() > 0) {
                System.out.println("BitSet cardiniality " + bitSetMandatory.cardinality());
                responseCode = ResponseCode.ER;
            }

            Numeric expMonth = null;
            Numeric expYear = null;

            Numeric zipCode = null;
            Numeric amount = null;
            LLVar PAN = null;
            LLVar cardHolder = null;

            for (int i = 7; i > 1; i--) {
                if (bitSetData.get(i)) {
                    switch (i) {
                        case 7:
                            Numeric panLen = new Numeric(2, stTransaction.substring(ptr, ptr += 2));
                            PAN = new LLVar(panLen, stTransaction.substring(ptr, ptr += panLen.intValue()));
                            System.out.println("PAN " + PAN.getValue());
                            break;

                        case 6:
                            expMonth = new Numeric(2, stTransaction.substring(ptr, ptr += 2));
                            System.out.println("ExpMonth " + expMonth.intValue());
                            expYear = new Numeric(2, stTransaction.substring(ptr, ptr += 2));
                            System.out.println("ExpYear " + expYear.intValue());
                            break;

                        case 5:
                            amount = new Numeric(10, stTransaction.substring(ptr, ptr += 10));
                            System.out.println("Amount " + amount.intValue());
                            break;

                        case 3:
                            Numeric cardHolderLen = new Numeric(2, stTransaction.substring(ptr, ptr += 2));
                            cardHolder = new LLVar(cardHolderLen, stTransaction.substring(ptr, ptr += cardHolderLen.intValue()));
                            System.out.println("CardHolder " + cardHolder);
                            break;

                        case 2:
                            zipCode = new Numeric(5, stTransaction.substring(ptr, ptr += 5));
                            System.out.println("Zip " + zipCode.intValue());
                            break;

                        default:
                            throw new Exception("Invalid data");
                    }
                } else {
                    System.out.println("Ignoring bit " + i);
                }
            }

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
            bitSetData.set(4);
            String stHex = HexFormat.of().formatHex(bitSetData.toByteArray());
            System.out.println("Result Bitset Hex " + stHex);

            sbResponse.append(MessageType.AUTH_RESPONSE_MESSAGE).append(stHex);
            for (int i = 7; i > 1; i--) {
                if (bitSetData.get(i)) {
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
