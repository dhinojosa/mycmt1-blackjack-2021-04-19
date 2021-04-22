package com.jitterted.ebp.blackjack;

public class Wallet {
    private long balance;

    public boolean isEmpty() {
        return balance == 0;
    }

    public void addMoney(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Money should be positive");
        this.balance += amount;
    }

    public long balance() {
        return balance;
    }
}
