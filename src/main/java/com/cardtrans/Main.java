package com.cardtrans;

import com.cardtrans.app.Highnote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press ⌥⏎ with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        Logger logger = LoggerFactory.getLogger(Main.class.getName());


        System.out.println("Hello and welcome!");



        try{
            //Initialize the application
            Highnote highnote = new Highnote();
            highnote.processTransactions(args);
        }catch(Exception e){
            logger.error("Ignoring exception " + e.getMessage());
        }


        System.out.println("Goodbye!");
    }
}