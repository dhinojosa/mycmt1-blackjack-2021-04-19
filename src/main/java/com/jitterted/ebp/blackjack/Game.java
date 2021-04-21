package com.jitterted.ebp.blackjack;

import org.fusesource.jansi.Ansi;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

    private final Deck deck;

    //Primitive Obsession
    private final Hand dealerHand = new Hand();
    private final Hand playerHand = new Hand();

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
        playerHand.dealCardFrom(deck);
        dealerHand.dealCardFrom(deck);
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
                playerHand.dealCardFrom(deck);
                if (playerHand.isBusted()) {
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
            while (dealerHand.dealerShouldHit()) { //should we call this
                // dealer busted
                dealerHand.dealCardFrom(deck);
            }
        }
    }

    private void displayGameResult(boolean playerBusted) {
        //display result
        if (playerBusted) {
            System.out.println("You Busted, so you lose.  💸");
        } else if (dealerHand.isBusted()) {
            System.out.println("Dealer went BUST, Player wins! Yay for you!! " +
                "💵");
        } else if (playerHand.beats(dealerHand)) {
            System.out.println("You beat the Dealer! 💵");
        } else if (playerHand.pushes(dealerHand)) {
            System.out.println("Push: You tie with the Dealer. 💸");
        } else {
            System.out.println("You lost to the Dealer. 💸");
        }
    }

    //Query
    private boolean playerSelectsHit(String playerChoice) {
        return playerChoice.startsWith("h");
    }

    //Query
    private boolean playerSelectStand(String playerChoice) {
        return playerChoice.startsWith("s");
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
        System.out.println(dealerHand.getTopCard().display()); // first card is
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

    //Long method
    private void displayFinalGameState() {
        displayFinalDealerHand();
        displayPlayerHand();

    }

    private void displayPlayerHand() {
        System.out.println();
        System.out.println("Player has: ");
        playerHand.displayHand();
    }

    private void displayFinalDealerHand() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        dealerHand.displayHand();
    }
}
