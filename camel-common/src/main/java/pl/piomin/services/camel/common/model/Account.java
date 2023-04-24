package pl.piomin.services.camel.common.model;

public class Account {

    private Integer id;
    private String number;
    private int amount;
    private Integer customerId;

    public Account() {
    }

    public Account(Integer id, String number, int amount, Integer customerId) {
        this.id = id;
        this.number = number;
        this.amount = amount;
        this.customerId = customerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
