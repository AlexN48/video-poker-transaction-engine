import java.util.ArrayList;

public class Player {

    private ArrayList<Card> hand;
    private double bankroll;
    private double bet;

    public Player() {
        bankroll = 50.0;
        bet = 0.0;
        hand = new ArrayList<Card>();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void addCard(Card c) {
        hand.add(c);
    }

    public void removeCard(Card c) {
        hand.remove(c);
    }

    public void bets(double amt) {
        bet = amt;
        bankroll = bankroll - amt;
    }

    public void winnings(double multiplier) {
        bankroll = bankroll + (bet * multiplier);
    }

    public double getBankroll() {
        return bankroll;
    }
}
