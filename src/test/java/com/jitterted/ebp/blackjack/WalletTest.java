package com.jitterted.ebp.blackjack;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WalletTest {

    //ZOMBIES - TDD
    //Z - Zero
    //O - One

    @Test
    void testWalletIsEmpty() {
        Wallet wallet = new Wallet();
        assertThat(wallet.isEmpty()).isTrue();
    }

    @Test
    void testWalletAddMoneyIsNotEmpty() {
        Wallet wallet = new Wallet();
        wallet.addMoney(10);
        assertThat(wallet.isEmpty()).isFalse();
    }

    @Test
    void testWalletAddMoneyOfZeroIsStillEmpty() {
        Wallet wallet = new Wallet();
        wallet.addMoney(0);
        assertThat(wallet.isEmpty()).isTrue();
    }

    @Test
    void testWalletHasAZeroBalance() {
        Wallet wallet = new Wallet();
        long balance = wallet.balance();
        assertThat(balance).isZero();
    }

    @Test
    void testAddMoneyWithNegative() {
        //E - Exception
        Wallet wallet = new Wallet();
        assertThatThrownBy(() -> wallet.addMoney(-1))
            .hasMessage("Money should be positive")
            .isInstanceOf(IllegalArgumentException.class);
    }
}
