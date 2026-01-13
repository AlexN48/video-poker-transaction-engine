import java.util.Random;

public class Deck {

    private Card[] cards;
    private int top;
    private Random rng = new Random();

    public Deck() {
        cards = new Card[52];
        int index = 0;

        for (int suit = 1; suit <= 4; suit++) {
            for (int rank = 1; rank <= 13; rank++) {
                cards[index++] = new Card(suit, rank);
            }
        }

        top = 0;
    }

    public void shuffle() {
        for (int i = cards.length - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            Card temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }
        top = 0;
    }

    public Card deal() {
        if (top >= cards.length) {
            shuffle();
        }
        return cards[top++];
    }
}
