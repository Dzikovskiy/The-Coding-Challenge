package by.dzikovskiy.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TransactionRecord {
    private String id;
    private Date date;
    private double amount;
    private String Merchant;
    private Enum<TransactionType> type;
    private String relatedTransaction;


}
