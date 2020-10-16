package by.dzikovskiy.service;

import by.dzikovskiy.entity.TransactionRecord;
import by.dzikovskiy.entity.TransactionType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

class CSVReaderTest {

    /**
     * Test is the TransactionRecord from file parses correctly
     */
    @Test
    void readSCVFormFile() {
        //given
        String csvFile = "testInputCSV.csv";
        try (InputStream input = CSVReaderTest.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            //load a properties file from class path
            prop.load(input);
            csvFile = prop.getProperty("csvFileName");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        CSVReader csvReader = new CSVReader();
        TransactionRecord record = new TransactionRecord();
        record.setId("WLMFRDGD");
        try {
            record.setDate(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse("20/08/2018 12:45:33"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        record.setAmount(59.99);
        record.setMerchant("Kwik-E-Mart");
        record.setType(TransactionType.PAYMENT);

        //when
        ArrayList<TransactionRecord> records = csvReader.readCSVFormFile(csvFile);

        //then
        Assert.assertEquals(records.get(0), record);
    }
}