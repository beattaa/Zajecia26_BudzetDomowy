package budzetDomowy;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction {
    private Long id;
    private int type;
    private String description;
    private double amount;
    private LocalDate date;

    public Transaction(int type, String description, double amount, LocalDate date) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public Transaction(Long id, int type, String description, double amount, LocalDate date) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override public String toString()
    {
        return "Id=" + id + ", typ=" + type + ", opis= " + description + ", wysokość=" + amount
                + ", data=" + date;
    }
}
