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
    String line = "";
    String csvSplitBy = ",";

    /**
     * Method for parsing csv file into ArrayList<TransactionRecord>
     * @param csvFileName given csv file name
     * @return ArrayList of TransactionRecords
     */
    public ArrayList<TransactionRecord> readCSVFormFile(String csvFileName) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        CSVReader app = new CSVReader();
        InputStream is = app.getFileFromResourceAsStream(csvFileName);
        ArrayList<TransactionRecord> recordList = new ArrayList<>();
        TransactionRecord record;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            while ((line = br.readLine()) != null) {
                record = new TransactionRecord();

                // use comma as separator
                String[] recordStringFromFile = line.split(csvSplitBy);

                record.setId(recordStringFromFile[0]);
                try {
                    record.setDate(format.parse(recordStringFromFile[1]));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                record.setAmount(Double.parseDouble(recordStringFromFile[2]));
                record.setMerchant(recordStringFromFile[3].trim());
                record.setType(TransactionType.valueOf(recordStringFromFile[4].trim()));
                if (recordStringFromFile.length > 5) {
                    record.setRelatedTransaction(recordStringFromFile[5].trim());
                }
                recordList.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recordList;
    }

    /**
     * Method for getting a file from the resources folder
     *
     * @param fileName given file name
     * @return InputStream of file
     */
    private InputStream getFileFromResourceAsStream(String fileName) {
        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // The stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }
}
