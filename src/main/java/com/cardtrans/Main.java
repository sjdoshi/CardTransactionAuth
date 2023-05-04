package com.cardtrans;

import com.cardtrans.app.Highnote;

// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press ⌥⏎ with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!" + "\r\n");

        //0100e016411111111111111112250000001000
        int i =0;
        for(String st : args){
            System.out.printf("Trans " + i++ + " " + st + "\r\n");
        }

        try{
            //Initialize the application
            Highnote highnote = new Highnote();
            highnote.processTransactions(args);
        }catch(Exception e){
            System.out.println("Ignoring exception " + e.getMessage());
        }


        System.out.printf("Hello and welcome!" + "\r\n");
    }
}