import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Game {

    private Player p;
    private Deck cards;

    public Game(String[] testHand) {
        p = new Player();
        cards = new Deck();

        for (String code : testHand) {
            char suitChar = code.charAt(0);
            int suit = 0;

            if (suitChar == 'c') suit = 1;
            else if (suitChar == 'd') suit = 2;
            else if (suitChar == 'h') suit = 3;
            else if (suitChar == 's') suit = 4;

            int rank = Integer.parseInt(code.substring(1));
            Card c = new Card(suit, rank);
            p.addCard(c);
        }
    }

    public Game() {
        p = new Player();
        cards = new Deck();
        cards.shuffle();
    }

    public void play() {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Video Poker! ");

        while (true) {
            if (p.getBankroll() <= 0) {
                System.out.println("Sorry, you are out of tokens. :( ");
                System.out.print("Thank you for playing Video Poker!");
                return;
            }

            System.out.println("YOUR TOKENS: " + p.getBankroll());
            System.out.println("Would you like to play a round? (y/n): ");
            String answer = input.nextLine();

            if (!answer.equalsIgnoreCase("y")) {
                System.out.print("Thank you for playing Video Poker!");
                return;
            }

            System.out.print("How many tokens to bet this hand? (1 to 5): ");
            int bet = Integer.parseInt(input.nextLine());

            if (bet < 1) bet = 1;
            if (bet > 5) bet = 5;

            if (bet > p.getBankroll()) {
                bet = (int) p.getBankroll();
                if (bet <= 0) bet = 1;
            }

            p.bets(bet);

            cards = new Deck();
            cards.shuffle();

            p.getHand().clear();
            for (int i = 0; i < 5; i++) {
                p.addCard(cards.deal());
            }

            System.out.print("The hand is: ");
            printHand(p.getHand());
            System.out.println();

            System.out.print("How many cards (0-5) would you like to exchange? ");
            int replace = Integer.parseInt(input.nextLine());
            if (replace < 0) replace = 0;
            if (replace > 5) replace = 5;

            for (int i = 0; i < replace; i++) {
                System.out.print("Which card (1-5) would you like to exchange? ");
                int index = Integer.parseInt(input.nextLine()) - 1;

                ArrayList<Card> handList = p.getHand();
                if (index >= 0 && index < handList.size()) {
                    Card removed = handList.get(index);
                    p.removeCard(removed);
                    p.addCard(cards.deal());
                }
            }

            System.out.print("The hand is: ");
            printHand(p.getHand());
            System.out.println();

            String handName = checkHand(p.getHand());
            System.out.println("You got a " + handName + "!");

            int multiplier = getMultiplier(handName);
            double payout = bet * multiplier;

            p.winnings(multiplier);

            System.out.println("PAYOUT: " + (int) payout + " tokens");
        }
    }

    public void printHand(ArrayList<Card> hand) {
        for (Card c : hand) {
            System.out.print(c + " ");
        }
    }

    public String checkHand(ArrayList<Card> hand) {
        Collections.sort(hand);

        boolean flush = isFlush(hand);
        boolean straight = isStraight(hand);
        int[] counts = rankCounts(hand);

        int pairs = 0;
        boolean three = false;
        boolean four = false;

        for (int x : counts) {
            if (x == 2) pairs++;
            if (x == 3) three = true;
            if (x == 4) four = true;
        }

        int firstRank = hand.get(0).getRank();

        if (flush && straight && firstRank == 10) return "Royal Flush";
        if (flush && straight) return "Straight Flush";
        if (four) return "Four of a Kind";
        if (three && pairs == 1) return "Full House";
        if (flush) return "Flush";
        if (straight) return "Straight";
        if (three) return "Three of a Kind";
        if (pairs == 2) return "Two Pair";
        if (pairs == 1) return "One Pair";
        return "No Pair";
    }

    private boolean isFlush(ArrayList<Card> hand) {
        int suit = hand.get(0).getSuit();
        for (Card c : hand) {
            if (c.getSuit() != suit) return false;
        }
        return true;
    }

    private boolean isStraight(ArrayList<Card> hand) {
        ArrayList<Integer> ranks = new ArrayList<>();
        for (Card c : hand) ranks.add(c.getRank());
        Collections.sort(ranks);

        if (ranks.get(0) == 1 && ranks.get(1) == 2 && ranks.get(2) == 3 && ranks.get(3) == 4 && ranks.get(4) == 5) {
            return true;
        }

        if (ranks.get(0) == 1 && ranks.get(1) == 10 && ranks.get(2) == 11 && ranks.get(3) == 12 && ranks.get(4) == 13) {
            return true;
        }

        for (int i = 1; i < ranks.size(); i++) {
            if (ranks.get(i) != ranks.get(i - 1) + 1) return false;
        }
        return true;
    }

    private int[] rankCounts(ArrayList<Card> hand) {
        int[] counts = new int[14];
        for (Card c : hand) {
            counts[c.getRank()]++;
        }
        return counts;
    }

    private int getMultiplier(String handName) {
        switch (handName) {
            case "No Pair": return 0;
            case "One Pair": return 1;
            case "Two Pair": return 2;
            case "Three of a Kind": return 3;
            case "Straight": return 4;
            case "Flush": return 5;
            case "Full House": return 6;
            case "Four of a Kind": return 25;
            case "Straight Flush": return 50;
            case "Royal Flush": return 250;
            default: return 0;
        }
    }
}
