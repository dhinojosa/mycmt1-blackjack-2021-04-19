package com.jitterted.ebp.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
  private final List<Card> cards = new ArrayList<>();

  //Long Method? Too much going on in constructor,
  public Deck() {
    List<String> cardValues = List.of("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K");
    List<String> suits = List.of("♠", "♦", "♥", "♣");
    for (String suit : suits) {
      for (String cardValue : cardValues) {
        cards.add(new Card(suit, cardValue));
      }
    }
    Collections.shuffle(cards);
  }

  public int size() {
    return cards.size();
  }

  //Issue: What if the cards are exhausted I don't think would work
  public Card draw() {
    return cards.remove(0);
  }
}
