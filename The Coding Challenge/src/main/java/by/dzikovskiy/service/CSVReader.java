package by.dzikovskiy.service;

import by.dzikovskiy.entity.TransactionRecord;
import by.dzikovskiy.entity.TransactionType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CSVReader {
    String csvFile = "inputCSV.csv";
    String line = "";
    String cvsSplitBy = ",";


    public ArrayList<TransactionRecord> readSCVFormFile() {
        ArrayList<TransactionRecord> recordList = new ArrayList<>();
        TransactionRecord record;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        CSVReader app = new CSVReader();
        InputStream is = app.getFileFromResourceAsStream(csvFile);


        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            while ((line = br.readLine()) != null) {
                record = new TransactionRecord();

                // use comma as separator
                String[] recordStringFromFile = line.split(cvsSplitBy);
                record.setId(recordStringFromFile[0]);
                try {
                    record.setDate(format.parse(recordStringFromFile[1]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                record.setAmount(Double.parseDouble(recordStringFromFile[2]));
                record.setMerchant(recordStringFromFile[3]);
                record.setType(TransactionType.valueOf(recordStringFromFile[4].trim()));
                if (recordStringFromFile.length > 5) {
                    record.setRelatedTransaction(recordStringFromFile[5]);
                }
                recordList.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recordList;
    }

    // get a file from the resources folder
    // works everywhere, IDEA, unit test and JAR file.
    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }
}
