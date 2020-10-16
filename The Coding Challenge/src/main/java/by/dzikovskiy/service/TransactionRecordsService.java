package by.dzikovskiy.service;

import by.dzikovskiy.entity.TransactionOutputInfo;
import by.dzikovskiy.entity.TransactionRecord;
import by.dzikovskiy.entity.TransactionType;

import java.util.ArrayList;
import java.util.Date;

public class TransactionRecordsService {
    String csvFileName;
    CSVReader csvReader;

    public TransactionRecordsService(String csvFileName) {
        this.csvFileName = csvFileName;
        this.csvReader = new CSVReader();
    }

    /**
     * Method for getting the total number of transactions
     * and the average transaction value for
     * a specific merchant in a specific date range
     *
     * @param fromDate
     * @param toDate
     * @param merchant name
     * @return TransactionOutputInfo object that contain number of records that match the condition for transactions
     * and values of each transaction
     */
    public TransactionOutputInfo getTransactionInfo(Date fromDate, Date toDate, String merchant) {
        TransactionOutputInfo outputInfo = new TransactionOutputInfo();
        ArrayList<TransactionRecord> records = csvReader.readCSVFormFile(csvFileName);

        deleteTransactionsWithReversal(records);

        for (TransactionRecord record : records) {

            //if a date is between two given dates
            if (fromDate.compareTo(record.getDate()) * record.getDate().compareTo(toDate) >= 0
                    && merchant.equals(record.getMerchant())) {

                if (record.getType() == TransactionType.PAYMENT) {
                    outputInfo.setNumberOfTransactions(outputInfo.getNumberOfTransactions() + 1);
                    outputInfo.getTransactionValues().add(record.getAmount());
                }
            }

        }
        return outputInfo;
    }

    /**
     * Method helper for deleting transaction with reversal related transaction
     *
     * @param records given ArrayList of TransactionRecords
     */
    private void deleteTransactionsWithReversal(ArrayList<TransactionRecord> records) {
        for (TransactionRecord reversalRecord : records) {
            if (reversalRecord.getType() == TransactionType.REVERSAL) {
                records.removeIf(paymentRecord -> paymentRecord.getId().equals(reversalRecord.getRelatedTransaction()));
            }
        }
    }
}
