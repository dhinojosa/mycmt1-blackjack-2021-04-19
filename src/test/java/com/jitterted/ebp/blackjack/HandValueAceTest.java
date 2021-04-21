package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HandValueAceTest {
    private static Suit whocares = Suit.SPADES;

    @Test
    public void handWithOneAceTwoCardsIsValuedAt11() throws Exception {
        Game game = new Game();
        Hand hand = new Hand(List.of(new Card(whocares, "A"),
            new Card(whocares, "5")));
        assertThat(hand.value())
            .isEqualTo(11 + 5);
    }

    @Test
    public void handWithOneAceAndOtherCardsEqualTo11IsValuedAt1() throws Exception {
        Game game = new Game();
        Hand hand = new Hand(List.of(new Card(whocares, "A"),
            new Card(whocares, "8"),
            new Card(whocares, "3")));
        assertThat(hand.value())
            .isEqualTo(1 + 8 + 3);
    }

}
