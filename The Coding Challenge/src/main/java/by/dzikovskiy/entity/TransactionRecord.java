package by.dzikovskiy.entity;

import lombok.*;

import java.util.Date;

/**
 * Entity for transaction record
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TransactionRecord {
    private String id;
    private Date date;
    private double amount;
    private String Merchant;
    private Enum<TransactionType> type;
    private String relatedTransaction;
}
