package com.cardtrans.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TransactionProcessorTest {

    static TransactionProcessor m_tp;
    @BeforeAll
    static void setupApp() {
        m_tp = new TransactionProcessor();
    }

    @ParameterizedTest
    @ValueSource(longs = {0,500,10000})
    @Tag("UnitTest")
    void approveEligibleAmountWithOutZip(long amount) {
        Assertions.assertTrue(m_tp.approveEligibleAmount(amount,-1));
    }
    @ParameterizedTest
    @ValueSource(longs = {10100, 500000})
    @Tag("UnitTest")
    void upApproveEligibleAmountWithoutZip(long amount) {
        Assertions.assertFalse(m_tp.approveEligibleAmount(amount,-1));
    }
    @ParameterizedTest
    @ValueSource(longs = {0,500,10000,10100, 20000})
    @Tag("UnitTest")
    void approveEligibleAmountWithZip(long amount) {
        Assertions.assertTrue(m_tp.approveEligibleAmount(amount,1));
    }
    @ParameterizedTest
    @ValueSource(longs = {20100, 500000})
    @Tag("UnitTest")
    void upApproveEligibleAmountWithZip(long amount) {
        Assertions.assertFalse(m_tp.approveEligibleAmount(amount,1));
    }

}