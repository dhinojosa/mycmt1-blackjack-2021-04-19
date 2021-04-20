package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

    private final Deck deck;

    private final List<Card> dealerHand = new ArrayList<>();
    private final List<Card> playerHand = new ArrayList<>();

    public static void main(String[] args) {
        showWelcomeScreen();
        playGame();
        resetScreen();
    }

    private static void resetScreen() {
        System.out.println(ansi().reset());
    }

    private static void playGame() {
        Game game = new Game();
        game.initialDeal();
        game.play();
    }

    private static void showWelcomeScreen() {
        System.out.println(ansi()
            .bgBright(Ansi.Color.WHITE)
            .eraseScreen()
            .cursor(1, 1)
            .fgGreen().a("Welcome to")
            .fgRed().a(" Jitterted's")
            .fgBlack().a(" BlackJack"));
    }

    public Game() {
        deck = new Deck();
    }

    //Long Method
    public void initialDeal() {
        dealRound();
        dealRound();
    }

    private void dealRound() {
        dealCardToPlayer();
        dealCardToDealer();
    }

    //Command
    private void dealCardToDealer() {
        dealerHand.add(deck.draw());
    }

    //Command
    private void dealCardToPlayer() {
        playerHand.add(deck.draw());
    }

    public void play() {
        //player plays hand
        // get Player's decision: hit until they stand, then they're done (or
        // they go bust)
        boolean playerBusted = playerTurn();
        dealerTurn(playerBusted);
        displayFinalGameState();
        displayGameResult(playerBusted);
    }

    private boolean playerTurn() {
        boolean playerBusted = false;
        while (!playerBusted) {
            displayGameState();
            String playerChoice = inputFromPlayer().toLowerCase();
            if (playerSelectStand(playerChoice)) {
                break;
            }
            if (playerSelectsHit(playerChoice)) {
                dealCardToPlayer();
                if (playerBusts()) {
                    playerBusted = true;
                }
            } else {
                System.out.println("You need to [H]it or [S]tand");
            }
        }
        return playerBusted;
    }

    private void dealerTurn(boolean playerBusted) {
        // Dealer makes its choice automatically based on a simple heuristic
        // (<=16, hit, 17>=stand)
        if (!playerBusted) {
            while (handValueOf(dealerHand) <= 16) { //should we call this
                // dealer busted
                dealCardToDealer();
            }
        }
    }

    private void displayGameResult(boolean playerBusted) {
        //display result
        if (playerBusted) {
            System.out.println("You Busted, so you lose.  💸");
        } else if (handValueOf(dealerHand) > 21) {
            System.out.println("Dealer went BUST, Player wins! Yay for you!! " +
                "💵");
        } else if (handValueOf(dealerHand) < handValueOf(playerHand)) {
            System.out.println("You beat the Dealer! 💵");
        } else if (handValueOf(dealerHand) == handValueOf(playerHand)) {
            System.out.println("Push: You tie with the Dealer. 💸");
        } else {
            System.out.println("You lost to the Dealer. 💸");
        }
    }

    //Query
    private boolean playerBusts() {
        return handValueOf(playerHand) > 21;
    }

    //Query
    private boolean playerSelectsHit(String playerChoice) {
        return playerChoice.startsWith("h");
    }

    //Query
    private boolean playerSelectStand(String playerChoice) {
        return playerChoice.startsWith("s");
    }

    //Is too long?
    public int handValueOf(List<Card> hand) {

        //calculating hand value
        int handValue = hand
            .stream()
            .mapToInt(Card::rankValue)
            .sum();

        // does the hand contain at least 1 Ace?
        boolean hasAce = hand
            .stream()
            .anyMatch(card -> card.rankValue() == 1);

        // if the total hand value <= 11, then count the Ace as 11 by adding 10
        if (hasAce && handValue < 11) {
            handValue += 10;
        }

        return handValue;
    }

    //Command
    private String inputFromPlayer() {
        System.out.println("[H]it or [S]tand?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void displayGameState() {
        displayInGameDealerHand();
        displayPlayerHand();
    }

    private void displayInGameDealerHand() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        System.out.println(dealerHand.get(0).display()); // first card is
        displayBackOfCard();
    }

    private void displayBackOfCard() {
        System.out.print(
            ansi()
                .cursorUp(7)
                .cursorRight(12)
                .a("┌─────────┐").cursorDown(1).cursorLeft(11)
                .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
                .a("│░ J I T ░│").cursorDown(1).cursorLeft(11)
                .a("│░ T E R ░│").cursorDown(1).cursorLeft(11)
                .a("│░ T E D ░│").cursorDown(1).cursorLeft(11)
                .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
                .a("└─────────┘"));
    }

    private void displayHand(List<Card> hand) {
        System.out.println(hand.stream()
                               .map(Card::display)
                               .collect(Collectors.joining(
                                   ansi().cursorUp(6).cursorRight(1).toString())));
    }

    //Long method
    private void displayFinalGameState() {
        displayFinalDealerHand();
        displayPlayerHand();

    }

    private void displayPlayerHand() {
        //Player hand is always displayed
        System.out.println();
        System.out.println("Player has: ");
        displayHand(playerHand);
        System.out.println(" (" + handValueOf(playerHand) + ")");
    }

    private void displayFinalDealerHand() {
        //show final dealer
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        displayHand(dealerHand);
        System.out.println(" (" + handValueOf(dealerHand) + ")");
    }
}
