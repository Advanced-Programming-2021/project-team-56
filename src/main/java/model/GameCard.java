package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameCard extends Image {

    public Card card;

    public GameCard(Card card) {
        super(card.getImageURL());
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}
