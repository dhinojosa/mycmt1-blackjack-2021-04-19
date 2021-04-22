package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class DisplayAllCardsTest {

    @Test
    void testDisplayOfCards() {
        Hand hand = new Hand(new ArrayList<>());
        String result  = hand.displayAllCards();
        assertThat(result).isEqualTo("");
    }
}
