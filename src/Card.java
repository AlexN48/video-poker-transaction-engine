public class Card implements Comparable<Card> {

    private int suit; 
    private int rank; 

    public Card(int s, int r) {
        suit = s;
        rank = r;
    }

    public int getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    public int compareTo(Card c) {
        if (rank != c.rank) return rank - c.rank;
        return suit - c.suit;
    }

    public String toString() {
        String rankStr;
        switch (rank) {
            case 1: rankStr = "Ace"; break;
            case 11: rankStr = "Jack"; break;
            case 12: rankStr = "Queen"; break;
            case 13: rankStr = "King"; break;
            default: rankStr = String.valueOf(rank); break;
        }

        String suitStr = "Unknown";
        if (suit == 1) suitStr = "Clubs";
        else if (suit == 2) suitStr = "Diamonds";
        else if (suit == 3) suitStr = "Hearts";
        else if (suit == 4) suitStr = "Spades";

        return rankStr + " of " + suitStr;
    }
}
