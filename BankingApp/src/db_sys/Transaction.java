package db_sys;

// Transaction data.

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private final int USER_ID;
    private final String TRANSACTION_TYPE;
    private final Date TRANSACTION_DATE;
    private final BigDecimal TRANSACTION_AMOUNT;

    public Transaction(int USER_ID, String TRANSACTION_TYPE, BigDecimal TRANSACTION_AMOUNT, Date TRANSACTION_DATE ) {
        this.USER_ID = USER_ID;
        this.TRANSACTION_TYPE = TRANSACTION_TYPE;
        this.TRANSACTION_DATE = TRANSACTION_DATE;
        this.TRANSACTION_AMOUNT = TRANSACTION_AMOUNT;
    }

    public int getUSER_ID() {
        return USER_ID;
    }

    public String getTRANSACTION_TYPE() {
        return TRANSACTION_TYPE;
    }

    public Date getTRANSACTION_DATE() {
        return TRANSACTION_DATE;
    }

    public BigDecimal getTRANSACTION_AMOUNT() {
        return TRANSACTION_AMOUNT;
    }
}
