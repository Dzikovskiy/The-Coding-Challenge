package by.dzikovskiy.service;


import by.dzikovskiy.entity.TransactionOutputInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

class TransactionRecordsServiceTest {

    @Test
    void getTransactionInfo() {
        //given
        String csvFileName = "testInputCSV.csv";
        try (InputStream input = CSVReaderTest.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            //load a properties file from class path
            prop.load(input);
            csvFileName = prop.getProperty("csvFileName");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        TransactionRecordsService service = new TransactionRecordsService(csvFileName);
        TransactionOutputInfo info1 = new TransactionOutputInfo();
        TransactionOutputInfo info2 = new TransactionOutputInfo();
        TransactionOutputInfo info3 = new TransactionOutputInfo();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        //when
        try {
            //reversal case
            info1 = service.getTransactionInfo(format.parse("20/08/2018 12:45:33"),
                    format.parse("20/08/2018 12:49:17"), "Kwik-E-Mart");
            //without reversal
            info2 = service.getTransactionInfo(format.parse("20/08/2018 12:45:33"),
                    format.parse("20/08/2018 12:52:17"), "MacLaren");
            //empty
            info3 = service.getTransactionInfo(format.parse("20/08/2018 12:45:33"),
                    format.parse("20/08/2018 14:07:10"), "EMPTY");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //then

        Assert.assertEquals(1, info1.getNumberOfTransactions());
        Assert.assertEquals(59.99, info1.getAverageTransactionValue(), 0.0);
        Assert.assertEquals(1, info2.getNumberOfTransactions());
        Assert.assertEquals(5.00, info2.getAverageTransactionValue(), 0.0);
        Assert.assertEquals(0, info3.getNumberOfTransactions());
        Assert.assertTrue(Double.isNaN(info3.getAverageTransactionValue()));
    }
}