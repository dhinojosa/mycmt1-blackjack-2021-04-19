package com.jitterted.ebp.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

public class Hand {
    private final List<Card> cardList;

    public Hand(List<Card> cards) {
        this.cardList = cards;
    }

    public Hand() {
        this.cardList = new ArrayList<Card>();
    }

    void dealCardFrom(Deck deck) {
        cardList.add(deck.draw());
    }

    Card getTopCard() {
        return cardList.get(0);
    }

    void displayHand() {
        System.out.println(
            cardList.stream()
                    .map(Card::display)
                    .collect(Collectors.joining(
                        ansi().cursorUp(6).cursorRight(1).toString())));
        System.out.println(" (" + value() + ")");
    }

    //Everyone should use this
    private int value() {
        int handValue = calculateHandValue();
        boolean hasAce = containsAtLeastOneAce();
        handValue = calculateAppropriateAceValue(handValue, hasAce);
        return handValue;
    }

    private int calculateAppropriateAceValue(int handValue, boolean hasAce) {
        if (hasAce && handValue < 11) {
            handValue += 10;
        }
        return handValue;
    }

    private boolean containsAtLeastOneAce() {
        return cardList
            .stream()
            .anyMatch(card -> card.rankValue() == 1);
    }

    private int calculateHandValue() {
        return cardList
            .stream()
            .mapToInt(Card::rankValue)
            .sum();
    }

    boolean dealerShouldHit() {
        return value() <= 16;
    }

    //Query
    boolean isBusted() {
        return value() > 21;
    }

    boolean beats(Hand other) {
        return other.value() < value();
    }

    boolean pushes(Hand other) {
        return other.value() == value();
    }

    public boolean containsValue(int value) {
        return value() == value;
    }
}
