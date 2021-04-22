package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HandValueAceTest {
    private static final Suit whoCares = Suit.SPADES;

    @Test
    public void handWithOneAceTwoCardsIsValuedAt11() throws Exception {
        Hand hand = new Hand(
            List.of(new Card(whoCares, "A"),
                new Card(whoCares, "5")));
        assertThat(hand.containsValue((11 + 5))).isTrue();
    }

    @Test
    public void handWithOneAceAndOtherCardsEqualTo11IsValuedAt1() throws Exception {
        Hand hand = new Hand(List.of(new Card(whoCares, "A"),
            new Card(whoCares, "8"),
            new Card(whoCares, "3")));
        assertThat(hand.containsValue(1 + 8 + 3)).isTrue();
    }

    //Ace + 10 or Face Card (J/Q/K) should result in a total of 21 (Ace counts as 11)
    @Test
    public void handWithOneAceAndFaceCardEqualTo11() throws Exception {
        Hand hand = new Hand(List.of(new Card(whoCares, "A"),
            new Card(whoCares, "J")));
        assertThat(hand.containsValue(11 + 10)).isTrue();
    }

    @Test
    void testAceOtherCardsAddingTo12ShouldBe13() {
        Hand hand = new Hand(List.of(new Card(whoCares, "A"),
            new Card(whoCares, "J"), new Card(whoCares, "2")));
        assertThat(hand.containsValue(10 + 2 + 1)).isTrue();
    }
}
