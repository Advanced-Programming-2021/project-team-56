package model;

import javafx.scene.image.Image;
import server.Card;

public class GameCard extends Image {

    public Card card;

    public GameCard(Card card) {
        super(card.getImageURL());
        this.card = card;
    }

    public GameCard(Card card, String url) {
        super("/images/Duel/Back-card.jpg");
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}
