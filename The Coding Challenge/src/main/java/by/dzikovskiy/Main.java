package by.dzikovskiy;

import by.dzikovskiy.entity.TransactionOutputInfo;
import by.dzikovskiy.service.TransactionRecordsService;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        String csvFileName = "inputCSV.csv";
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            //load a properties file from class path
            prop.load(input);
            csvFileName = prop.getProperty("csvFileName");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.print("\nPlease enter from date: ");
        Date fromDate = format.parse(scanner.nextLine());

        System.out.print("\nPlease enter from date: ");
        Date toDate = format.parse(scanner.nextLine());

        System.out.print("\nEnter merchant: ");
        String merchant = scanner.nextLine();

        TransactionRecordsService service = new TransactionRecordsService(csvFileName);
        TransactionOutputInfo records = service.getTransactionInfo(fromDate, toDate, merchant);

        System.out.println("Number of transactions: " + records.getNumberOfTransactions());
        System.out.println("Average transaction value: " + records.getAverageTransactionValue());
    }
}
