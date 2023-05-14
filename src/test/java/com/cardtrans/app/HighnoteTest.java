package com.cardtrans.app;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class HighnoteTest {

    static Highnote m_App;
    @BeforeAll
    static void setupApp() {
        m_App = new Highnote();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/transaction.file", numLinesToSkip = 1)
    @Tag("IntegrationTest")
    void processOneTransaction(String stTransaciton, String stOutput) throws Exception{
        System.out.println(String.format("Processing transaction %s, result %s",stTransaciton,stOutput));
        Assertions.assertEquals(m_App.processOneTransaction(stTransaciton),stOutput);
    }
}