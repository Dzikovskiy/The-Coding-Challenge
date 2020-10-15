package by.dzikovskiy;

import by.dzikovskiy.entity.TransactionRecord;
import by.dzikovskiy.service.CSVReader;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        CSVReader reader = new CSVReader();

        ArrayList<TransactionRecord> list = reader.readSCVFormFile();
        for (TransactionRecord record : list) {
            System.out.println(record);
        }
    }
}
