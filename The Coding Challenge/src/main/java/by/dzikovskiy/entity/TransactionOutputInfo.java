package by.dzikovskiy.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity for storage number of transactions and each transaction value
 */
@Getter
@Setter
public class TransactionOutputInfo {
    private int numberOfTransactions = 0;
    private List<Double> transactionValues;

    public TransactionOutputInfo() {
        this.transactionValues = new ArrayList<>();
    }

    /**
     * Method for getting average value of all transactions
     *
     * @return average value of all transactions in double
     */
    public double getAverageTransactionValue() {
        return transactionValues.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
    }
}
