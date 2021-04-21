package com.jitterted.ebp.blackjack;

public enum Suit {
    HEARTS("♥", true),
    SPADES("♠", false),
    CLUBS("♣", false),
    DIAMONDS("♦", true);

    private final boolean isRed;
    private final String symbol;

    Suit(String symbol, boolean isRed) {
        this.symbol = symbol;
        this.isRed = isRed;
    }

    public String symbol() {
        return symbol;
    }

    boolean isRed() {
        return isRed;
    }
}
