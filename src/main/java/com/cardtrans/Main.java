package com.cardtrans;

import com.cardtrans.app.Highnote;

// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press ⌥⏎ with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");


        try{
            //Initialize the application
            Highnote highnote = new Highnote();
            highnote.processTransactions(args);
        }catch(Exception e){
            System.out.println("Ignoring exception " + e.getMessage());
        }


        System.out.println("Goodbye!");
    }
}