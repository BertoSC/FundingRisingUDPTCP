package org.example;

public class Founds {
    private double totalMoney;

    public Founds() {
        this.totalMoney=0;
    }

    public synchronized double getTotalMoney() {
        return totalMoney;
    }

    public synchronized void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    @Override
    public String toString() {
        return "Total money = " + totalMoney;
    }
}
