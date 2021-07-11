package view.duel;

import controller.SoundPlayer;
import controller.duel.DuelWithUser;
import controller.duel.phases.DrawPhaseController;
import controller.duel.phases.MainPhase1Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextFlow;
import model.Card;
import javafx.util.Duration;
import model.*;
import model.enums.DuelInfo;
import model.enums.MenuURL;
import model.enums.SoundURL;
import view.FxmlController;
import view.components.NodeEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static model.enums.DuelInfo.*;

public class DuelView {

    private static User firstPlayer;
    private static User secondPlayer;
    private static int numberOfRounds;

    public static ArrayList<ImageView> myHandImageViews;
    public static ArrayList<ImageView> opponentHandImageViews;
    public static ArrayList<ImageView> opponentSpellAndTrapTerritoryImageViews;
    public static ArrayList<ImageView> opponentMonsterTerritoryImageViews;
    public static ArrayList<ImageView> myMonsterTerritoryImageViews;
    public static ArrayList<ImageView> mySpellAndTrapTerritoryImageViews;

    private DuelInfo currentPhase;

    public AnchorPane fieldAnchorPane;
    public Label opponentLPLabel;
    public Pane opponentLPBar;
    public ImageView onMouseEnteredCardImageView;
    public Label myLPLabel;
    public Pane myLPBar;
    public Circle opponentMagicCircle;
    public Circle opponentAvatar;
    public Label opponentUsernameLabel;
    public Label opponentNicknameLabel;
    public Circle myMagicCircle;
    public Circle myAvatarCircle;
    public Label myUsernameLabel;
    public Label myNicknameLabel;
    public TextFlow textFlow;
    public HBox settingHBox;
    public Button backToMainMenuButton;
    public Button continueButton;
    public VBox settingVBox;
    public VBox drawPhaseVBox;
    public VBox standbyPhaseVBox;
    public VBox mainPhase1VBox;
    public VBox battlePhaseVBox;
    public VBox mainPhase2VBox;
    public VBox endPhaseVBox;
    public ScrollPane graveYardScrollPane;
    public GridPane graveYardGridPane;

    //TODO
    //TODO
    //TODO
    //TODO

    public HBox myHandHBox;
    public ImageView myHandImageView1;
    public ImageView myHandImageView2;
    public ImageView myHandImageView3;
    public ImageView myHandImageView4;
    public ImageView myHandImageView5;
    public ImageView myHandImageView6;
    public ImageView myHandImageView7;
    public ImageView myHandImageView8;
    public ImageView myHandImageView9;

    public HBox opponentHandHBox;
    public ImageView opponentHandImageView9;
    public ImageView opponentHandImageView7;
    public ImageView opponentHandImageView5;
    public ImageView opponentHandImageView3;
    public ImageView opponentHandImageView1;
    public ImageView opponentHandImageView2;
    public ImageView opponentHandImageView4;
    public ImageView opponentHandImageView6;
    public ImageView opponentHandImageView8;

    public ImageView myDeckCardsImageView;
    public Label myDeckCardsNumberLabel;
    public ImageView opponentDeckCardsImageView;
    public Label opponentDeckCardsNumberLabel;

    public ImageView opponentGraveyardImageView;
    public ImageView myGraveyardImageView;

    public ImageView opponentSpellTerritoryImageView4;
    public ImageView opponentSpellTerritoryImageView2;
    public ImageView opponentSpellTerritoryImageView1;
    public ImageView opponentSpellTerritoryImageView3;
    public ImageView opponentSpellTerritoryImageView5;
    public ImageView opponentMonsterTerritoryImageView4;
    public ImageView opponentMonsterTerritoryImageView2;
    public ImageView opponentMonsterTerritoryImageView1;
    public ImageView opponentMonsterTerritoryImageView3;
    public ImageView opponentMonsterTerritoryImageView5;

    public ImageView myMonsterTerritoryImageView5;
    public ImageView myMonsterTerritoryImageView3;
    public ImageView myMonsterTerritoryImageView1;
    public ImageView myMonsterTerritoryImageView2;
    public ImageView myMonsterTerritoryImageView4;
    public ImageView mySpellTerritoryCardImageView5;
    public ImageView mySpellTerritoryCardImageView3;
    public ImageView mySpellTerritoryCardImageView1;
    public ImageView mySpellTerritoryCardImageView2;
    public ImageView mySpellTerritoryCardImageView4;

    public ImageView opponentFieldSpellImageView;
    public ImageView myFieldSpellImageView;

    public Label duelInfoLabel;

    //TODO
    //TODO
    //TODO
    //TODO

    //TODO
    //TODO
    //TODO
    Board firstPlayerBoard;
    Board secondPlayerBoard;
    int turnCounter;
    int startTurn;
    //TODO
    //TODO
    //TODO

    public static void setPlayers(String firstPlayerName, String secondPlayerName) {
        firstPlayer = User.getUserByUsername(firstPlayerName);
        secondPlayer = User.getUserByUsername(secondPlayerName);
    }

    public static void setNumberOfRounds(int numberOfRounds) {
        DuelView.numberOfRounds = numberOfRounds;
    }

    @FXML
    public void initialize() {
        settingVBox.setVisible(false);
        initializeFieldComponents();
//        playerHandImageView.setImage(new GameCard(firstPlayer.getActiveDeck().getMainDeck().get(5)));
//        myHandImageView1.setImage(new GameCard(firstPlayer.getActiveDeck().getMainDeck().get(5)));
//        GameCard gameCard = (GameCard) myHandImageView1.getImage();
//        System.out.println(gameCard.getCard().getName());
        initializePlayersInformation();
        editSettingHBox();
        //changeFieldImage(null);
        //TODO
        //TODO
        //TODO
        //TODO
        //TODO
        //TODO
        //TODO
        editPhaseVBoxes();
        initializeImageViews();

        new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            startRound();
        })).play();

        //TODO
        //TODO
        //TODO
        //TODO
        //TODO
        //TODO
        //TODO

        graveYardScrollPane.setFitToHeight(true);
        for (int i = 0; i < 20; i++) {
            Image image = new Image(Card.getCardByName("Battle OX").getImageURL());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(96);
            imageView.setFitHeight(140);
            graveYardGridPane.add(imageView, i, 0);
            GridPane.setMargin(imageView, new Insets(5));
        }
    }

    private void startRound() {
        editImageViews();
        currentPhase = PHASE_DRAW;
        //TODO Last round result?
        DuelWithUser.getInstance().setUpGame(firstPlayer.getUsername(), secondPlayer.getUsername(), 0);
        updateFromDrawPhase();
        showDuelInfoLabel(PHASE_DRAW.value);
        new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            showDuelInfoLabel(DrawPhaseController.getInstance().run());
            updateFromDrawPhase();
        })).play();
    }

    private void processPhase() {
        showDuelInfoLabel(currentPhase.value);
        new Timeline(new KeyFrame(Duration.seconds(2), event -> {
//            switch (currentPhase) {
//                case PHASE_DRAW:
//                    DuelWithUser.getInstance().phaseCaller(firstPlayer.getUsername(), "1");
//                    break;
//                case PHASE_STANDBY:
//                    DuelWithUser.getInstance().phaseCaller(firstPlayer.getUsername(), "2");
//                    break;
//                case PHASE_MAIN1:
//                    DuelWithUser.getInstance().phaseCaller(firstPlayer.getUsername(), "3");
//                    break;
//                case PHASE_BATTLE:
//                    DuelWithUser.getInstance().phaseCaller(firstPlayer.getUsername(), "4");
//                    break;
//                case PHASE_MAIN2:
//                    DuelWithUser.getInstance().phaseCaller(firstPlayer.getUsername(), "5");
//                    break;
//                default:
//                    DuelWithUser.getInstance().phaseCaller(firstPlayer.getUsername(), "6");
//            }
        })).play();
    }

    private void initializePlayersInformation() {
        opponentMagicCircle.setFill(new ImagePattern(new Image("/images/Magic-Circle.png")));
        opponentAvatar.setFill(new ImagePattern(new Image(secondPlayer.getAvatarURL())));
        opponentUsernameLabel.setText(secondPlayer.getUsername());
        opponentNicknameLabel.setText(secondPlayer.getNickname());
        myMagicCircle.setFill(new ImagePattern(new Image("/images/Magic-Circle.png")));
        myAvatarCircle.setFill(new ImagePattern(new Image(firstPlayer.getAvatarURL())));
        myUsernameLabel.setText(firstPlayer.getUsername());
        myNicknameLabel.setText(firstPlayer.getNickname());
    }

    private void initializeFieldComponents() {
        graveYardScrollPane.setVisible(false);
        duelInfoLabel.setVisible(false);

    }

    //TODO
    //TODO
    //TODO
    //TODO
    //TODO
    //TODO
    //TODO

    public void updateFromDrawPhase() {
        updateMyHandCards();
        updateOpponentHandCards();
        updateMyDeckCardsNumber();
        updateOpponentDeckCardsNumber();
    }

    public void updateFromSummon() {
        updateMyHandCards();
        updateMySpellAndTrapTerritory();
        updateMyMonsterTerritory();
    }

    public void updateMyHandCards() {
        myHandImageViews.forEach(imageView -> imageView.setImage(null));
        ArrayList<Card> myHandCards = DuelWithUser.getInstance().getMyBoard().getPlayerHand();
        for (int i = 0; i < myHandCards.size(); i++) {
            myHandImageViews.get(i).setImage(new GameCard(myHandCards.get(i)));
        }
    }

    public void updateOpponentHandCards() {
        opponentHandImageViews.forEach(imageView -> imageView.setImage(null));
        ArrayList<Card> enemyHandCards = DuelWithUser.getInstance().getEnemyBoard().getPlayerHand();
        for (int i = 0; i < enemyHandCards.size(); i++) {
            opponentHandImageViews.get(i).setImage(new GameCard(enemyHandCards.get(i), ""));
        }
    }

    public void updateMyMonsterTerritory() {
        myMonsterTerritoryImageViews.forEach(imageView -> {
            imageView.setImage(null);
            imageView.setRotate(0);
        });
        ArrayList<Card> myMonsterTerritory = DuelWithUser.getInstance().getMyBoard().getMonsterTerritoryArrayList();
        for (int i = 0; i < myMonsterTerritory.size(); i++) {
            if (myMonsterTerritory.get(i) != null) {
                MonsterCard card = (MonsterCard) myMonsterTerritory.get(i);
                if (!card.getIsInAttackPosition()) {
                    myMonsterTerritoryImageViews.get(i).setRotate(90);
                }
                if (card.getIsFacedUp()) {
                    myMonsterTerritoryImageViews.get(i).setImage(new GameCard(card));
                } else {
                    myMonsterTerritoryImageViews.get(i).setImage(new GameCard(card, ""));
                }
            }
        }
    }

    public void updateOpponentMonsterTerritory() {
        opponentMonsterTerritoryImageViews.forEach(imageView -> {
            imageView.setImage(null);
            imageView.setRotate(0);
        });
        ArrayList<Card> opponentMonsterTerritory = DuelWithUser.getInstance().getEnemyBoard().getMonsterTerritoryArrayList();
        for (int i = 0; i < opponentMonsterTerritory.size(); i++) {
            if (opponentMonsterTerritory.get(i) != null) {
                MonsterCard card = (MonsterCard) opponentMonsterTerritory.get(i);
                if (!card.getIsInAttackPosition()) {
                    opponentMonsterTerritoryImageViews.get(i).setRotate(90);
                }
                if (card.getIsFacedUp()) {
                    opponentMonsterTerritoryImageViews.get(i).setImage(new GameCard(card));
                } else {
                    opponentMonsterTerritoryImageViews.get(i).setImage(new GameCard(card, ""));
                }
            }
        }
    }

    public void updateMySpellAndTrapTerritory() {
        mySpellAndTrapTerritoryImageViews.forEach(imageView -> imageView.setImage(null));
        ArrayList<Card> mySpellAndTrapTerritory = DuelWithUser.getInstance().getMyBoard().getSpellAndTrapTerritoryArrayList();
        for (int i = 0; i < mySpellAndTrapTerritory.size(); i++) {
            if (mySpellAndTrapTerritory.get(i) != null) {
                Card card = mySpellAndTrapTerritory.get(i);
                if (card.getIsFacedUp()) {
                    mySpellAndTrapTerritoryImageViews.get(i).setImage(new GameCard(card));
                } else {
                    mySpellAndTrapTerritoryImageViews.get(i).setImage(new GameCard(card, ""));
                }
            }
        }
    }

    public void updateOpponentSpellAndTrapTerritory() {
        opponentSpellAndTrapTerritoryImageViews.forEach(imageView -> imageView.setImage(null));
        ArrayList<Card> opponentSpellAndTrapTerritory = DuelWithUser.getInstance().getEnemyBoard().getSpellAndTrapTerritoryArrayList();
        for (int i = 0; i < opponentSpellAndTrapTerritory.size(); i++) {
            if (opponentSpellAndTrapTerritory.get(i) != null) {
                Card card = opponentSpellAndTrapTerritory.get(i);
                if (card.getIsFacedUp()) {
                    opponentSpellAndTrapTerritoryImageViews.get(i).setImage(new GameCard(card));
                } else {
                    opponentSpellAndTrapTerritoryImageViews.get(i).setImage(new GameCard(card, ""));
                }
            }
        }
    }

    public void updateMyDeckCardsNumber() {
        myDeckCardsNumberLabel.setText(String.valueOf(DuelWithUser.getInstance().getMyBoard().getMainDeck().size()));
    }

    public void updateOpponentDeckCardsNumber() {
        opponentDeckCardsNumberLabel.setText(String.valueOf(DuelWithUser.getInstance().getEnemyBoard().getMainDeck().size()));
    }

    //TODO
    //TODO
    //TODO
    //TODO
    //TODO
    //TODO
    //TODO
    public void setUpGame(int lastRoundResult) {
        firstPlayerBoard = new Board(firstPlayer);
        secondPlayerBoard = new Board(secondPlayer);
        //TODO where do we need turnCounter 3?
        turnCounter = 2;
        startTurn = turnCounter;
        firstPlayerBoard.setStartedTurn(2);
        secondPlayerBoard.setStartedTurn(3);
        firstPlayerBoard.setPlayerHandForFirstPlayer();
        secondPlayerBoard.setPlayerHandForSecondPlayer();
    }

    private void initializeImageViews() {
        myHandImageViews = new ArrayList<>(Arrays.asList(myHandImageView1, myHandImageView2, myHandImageView3,
                myHandImageView4, myHandImageView5, myHandImageView6, myHandImageView7, myHandImageView8, myHandImageView9));
        opponentHandImageViews = new ArrayList<>(Arrays.asList(opponentHandImageView1,
                opponentHandImageView2, opponentHandImageView3, opponentHandImageView4, opponentHandImageView5,
                opponentHandImageView6, opponentHandImageView7, opponentHandImageView8, opponentHandImageView9));

        opponentSpellAndTrapTerritoryImageViews = new ArrayList<>(Arrays.asList(opponentSpellTerritoryImageView1,
                opponentSpellTerritoryImageView2, opponentSpellTerritoryImageView3,
                opponentSpellTerritoryImageView4, opponentSpellTerritoryImageView5));

        opponentMonsterTerritoryImageViews = new ArrayList<>(Arrays.asList(opponentMonsterTerritoryImageView1,
                opponentMonsterTerritoryImageView2, opponentMonsterTerritoryImageView3,
                opponentMonsterTerritoryImageView4, opponentMonsterTerritoryImageView5));

        myMonsterTerritoryImageViews = new ArrayList<>(Arrays.asList(myMonsterTerritoryImageView1,
                myMonsterTerritoryImageView2, myMonsterTerritoryImageView3,
                myMonsterTerritoryImageView4, myMonsterTerritoryImageView5));

        mySpellAndTrapTerritoryImageViews = new ArrayList<>(Arrays.asList(mySpellTerritoryCardImageView1,
                mySpellTerritoryCardImageView2, mySpellTerritoryCardImageView3,
                mySpellTerritoryCardImageView4, mySpellTerritoryCardImageView5));
    }

    //TODO
    //TODO
    //TODO
    //TODO
    //TODO
    //TODO
    //TODO
    //TODO
    //TODO
    //TODO

    private String singleRoundWin(String winnerUsername, int winnerScore, int looserScore) {
        return winnerUsername + " won the game and the score is: " + winnerScore + "-" + looserScore;
    }

    private String oneRoundWin(String winnerSideUsername, String loserSideUsername) {
        User winner = User.getUserByUsername(winnerSideUsername);
        winner.increaseScore(1000);
        winner.increaseMoney(winner.getMaxLP() + 1000);
        winner.clearLP();
        User loser = User.getUserByUsername(loserSideUsername);
        loser.increaseMoney(100);
        loser.clearLP();
        return winnerSideUsername + " won the whole match";
    }

    private String threeRoundWinner(String winnerUsername, String looserUsername, int winnerScore, int looserScore) {
        User winner = User.getUserByUsername(winnerUsername);
        winner.increaseScore(3000);
        winner.increaseMoney(3000 + 3 * winner.getMaxLP());
        winner.clearLP();
        User loser = User.getUserByUsername(looserUsername);
        loser.clearLP();
        loser.increaseMoney(300);
        return winnerUsername + " won the whole match with score: " + winnerScore + "-" + looserScore;
    }

    //TODO
    //TODO
    //TODO
    //TODO
    //TODO
    //TODO
    //TODO

    private void changeFieldImage(String fieldURL) {
        BackgroundSize backgroundSize = new BackgroundSize(1680, 1050, true, true, false, true);
        fieldAnchorPane.setBackground(new Background(new BackgroundImage(new Image("/images/Duel/Field/fie_normal.png")
                , BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize)));
    }

    private void editPhaseVBoxes() {
        editPhaseVBox(PHASE_DRAW, standbyPhaseVBox, PHASE_STANDBY);
        editPhaseVBox(PHASE_STANDBY, mainPhase1VBox, PHASE_MAIN1);
        editPhaseVBox(PHASE_MAIN1, battlePhaseVBox, PHASE_BATTLE);
        editPhaseVBox(PHASE_BATTLE, mainPhase2VBox, PHASE_MAIN2);
        editPhaseVBox(PHASE_MAIN2, endPhaseVBox, PHASE_END);
    }

    private void editPhaseVBox(DuelInfo phase, VBox phaseVBox, DuelInfo nextPhase) {
        phaseVBox.setOnMouseClicked(event -> {
            if (currentPhase == phase) {
                currentPhase = nextPhase;
                processPhase();
            }
        });
        phaseVBox.setOnMouseEntered(event -> {
            if (currentPhase == phase) {
                SoundPlayer.getInstance().playAudioClip(SoundURL.BUTTON_HOVER);
                phaseVBox.setEffect(new Glow(0.6));
            }
        });
        phaseVBox.setOnMouseExited(event -> phaseVBox.setEffect(null));
    }

    private void editImageViews() {
        setOnMouseClickedForImageViews();
        ArrayList<ArrayList<ImageView>> allImageViewLists = new ArrayList<>(Arrays.asList(myHandImageViews,
                opponentHandImageViews, mySpellAndTrapTerritoryImageViews, myMonsterTerritoryImageViews,
                opponentSpellAndTrapTerritoryImageViews, opponentMonsterTerritoryImageViews));
        for (ArrayList<ImageView> imageViewList : allImageViewLists) {
            for (ImageView imageView : imageViewList) {
                imageView.setOnMouseEntered(event -> {
                    if (imageView.getImage() != null) {

                        GameCard gameCard = (GameCard) imageView.getImage();

                        if ((myMonsterTerritoryImageViews.contains(imageView) ||
                                mySpellAndTrapTerritoryImageViews.contains(imageView)) && (!gameCard.getCard().getIsFacedUp())) {
                            onMouseEnteredCardImageView.setImage(new Image(gameCard.getCard().getImageURL()));
                        } else {
                            onMouseEnteredCardImageView.setImage(gameCard);
                        }
                        SoundPlayer.getInstance().playAudioClip(SoundURL.BUTTON_HOVER);
                        imageView.setEffect(new Glow(0.6));
                    }
                });
                imageView.setOnMouseExited(event -> imageView.setEffect(null));
            }
        }
    }

    private void setOnMouseClickedForImageViews() {
        setOnMouseClickedForMyHandImageViews();
        setOnMouseClickedForOpponentHandImageViews();
        setOnMouseClickedForMySpellAndTrapTerritoryImageViews();
        setOnMouseClickedForOpponentSpellAndTrapTerritoryImageViews();
        setOnMouseClickedForMyMonsterTerritoryImageViews();
        setOnMouseClickedForOpponentMonsterTerritoryImageViews();
    }

    private void setOnMouseClickedForMyHandImageViews() {
        for (ImageView imageView : myHandImageViews) {
            imageView.setOnMouseClicked(event -> {
                if (currentPhase == PHASE_MAIN1 && imageView.getImage() != null) {
                    onMouseClickedMyHandImageViewsInMainPhase(imageView, event);
                }
            });
        }
    }

    private void setOnMouseClickedForOpponentHandImageViews() {
        for (ImageView imageView : opponentHandImageViews) {
            imageView.setOnMouseClicked(event -> {
                //TODO
            });
        }
    }

    private void setOnMouseClickedForMySpellAndTrapTerritoryImageViews() {
        for (ImageView imageView : mySpellAndTrapTerritoryImageViews) {
            imageView.setOnMouseClicked(event -> {
                //TODO
            });
        }
    }

    private void setOnMouseClickedForOpponentSpellAndTrapTerritoryImageViews() {
        for (ImageView imageView : opponentSpellAndTrapTerritoryImageViews) {
            imageView.setOnMouseClicked(event -> {
                //TODO
            });
        }
    }

    private void setOnMouseClickedForMyMonsterTerritoryImageViews() {
        for (ImageView imageView : myMonsterTerritoryImageViews) {
            imageView.setOnMouseClicked(event -> {
                if (currentPhase == PHASE_MAIN1 && imageView.getImage() != null) {
                    onMouseClickedMyMonsterTerritoryImageViewsInMainPhase(imageView, event);
                }
            });
        }
    }

    private void setOnMouseClickedForOpponentMonsterTerritoryImageViews() {
        for (ImageView imageView : opponentMonsterTerritoryImageViews) {
            imageView.setOnMouseClicked(event -> {
                //TODO
            });
        }
    }


    private void onMouseClickedMyHandImageViewsInMainPhase(ImageView imageView, MouseEvent event) {
        if (DuelWithUser.getInstance().getMyBoard().getSelectedCard() == null ||
                DuelWithUser.getInstance().getMyBoard().getSelectedCard() != ((GameCard) imageView.getImage()).getCard()) {
            Card card = ((GameCard) imageView.getImage()).getCard();
            DuelWithUser.getInstance().selectCard(card);
        } else {
            if (event.getButton() == MouseButton.PRIMARY) {
                String result = MainPhase1Controller.getInstance().summon(false);
                if (!result.equals("summoned successfully")) showDuelInfoLabel(result);
                else {
                    updateMyHandCards();
                    updateMyMonsterTerritory();
                }
            } else {
                String result = MainPhase1Controller.getInstance().set();
                if (!result.equals("set successfully") && !result.equals("summoned successfully"))
                    showDuelInfoLabel(result);
                else {
                    updateMyHandCards();
                    updateMyMonsterTerritory();
                    updateMySpellAndTrapTerritory();
                }
            }
        }
    }


    private void onMouseClickedMyMonsterTerritoryImageViewsInMainPhase(ImageView imageView, MouseEvent event) {
        if (DuelWithUser.getInstance().getMyBoard().getSelectedCard() == null ||
                DuelWithUser.getInstance().getMyBoard().getSelectedCard() != ((GameCard) imageView.getImage()).getCard()) {
            Card card = ((GameCard) imageView.getImage()).getCard();
            DuelWithUser.getInstance().selectCard(card);
        } else {
            if (event.getButton() == MouseButton.PRIMARY) {
                Card card =  DuelWithUser.getInstance().getMyBoard().getSelectedCard();
                String result = MainPhase1Controller.getInstance().changePosition(!((MonsterCard) card).getIsInAttackPosition());
                if (!result.equals("monster card position\nchanged successfully")) showDuelInfoLabel(result);
                else {
                    updateMyMonsterTerritory();
                }
            } else {
                String result = MainPhase1Controller.getInstance().flipSummon();
                if (!result.equals("flip summoned successfully")) showDuelInfoLabel(result);
                else {
                    updateMyMonsterTerritory();
                }
            }
        }
    }

    private void editSettingHBox() {
        NodeEditor.editNode(0.6, settingHBox);
        settingHBox.setOnMouseClicked(event -> {
            settingVBox.setVisible(true);
            continueButton.setOnMouseClicked(continueButtonEvent -> {
                settingVBox.setVisible(false);
            });
            backToMainMenuButton.setOnMouseClicked(backButtonEvent -> {
                try {
                    FxmlController.getInstance().setSceneFxml(MenuURL.MAIN);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }


    private void showDuelInfoLabel(String duelInfo) {
        duelInfoLabel.setText(duelInfo);
        duelInfoLabel.setVisible(true);
        new Timeline(new KeyFrame(Duration.seconds(2), event -> duelInfoLabel.setVisible(false))).play();
    }
}
