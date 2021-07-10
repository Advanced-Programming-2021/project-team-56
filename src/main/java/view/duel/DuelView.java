package view.duel;

import controller.SoundPlayer;
import controller.duel.DuelWithUser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextFlow;
import model.Card;
import javafx.util.Duration;
import model.Board;
import model.GameCard;
import model.Output;
import model.User;
import model.enums.MenuURL;
import model.enums.SoundURL;
import view.FxmlController;
import view.components.NodeEditor;
import view.duel.phase.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class DuelView {

    private static User firstPlayer;
    private static User secondPlayer;
    private static int numberOfRounds;

    public AnchorPane fieldAnchorPane;
    public Label opponentLPLabel;
    public Pane opponentLPBar;
    public ImageView clickedCardImageView;
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

    public static ArrayList<ImageView> myHandImageViews;
    public static ArrayList<ImageView> opponentHandImageViews;
    public static ArrayList<ImageView> opponentSpellTerritoryImageViews;
    public static ArrayList<ImageView> opponentMonsterTerritoryImageViews;
    public static ArrayList<ImageView> myMonsterTerritoryImageViews;
    public static ArrayList<ImageView> mySpellTerritoryCardImageViews;

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
        initializeImageViews();
        new Timeline(new KeyFrame(Duration.seconds(1), event -> setUpGame(0))).play();
//        DuelWithUser.getInstance().run(firstPlayer.getUsername(), secondPlayer.getUsername(), String.valueOf(numberOfRounds));

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

    //TODO
    //TODO
    //TODO
    //TODO
    //TODO
    //TODO
    //TODO

//    public String run() {
//        int roundResult = 0;
//        if (numberOfRounds == 3) {
//            int numberOfWinsPlayer1 = 0;
//            int numberOfWinsPlayer2 = 0;
//            while (numberOfWinsPlayer1 != 2 && numberOfWinsPlayer2 != 2) {
//                setUpGame(roundResult);
//                roundResult = phaseCaller(firstPlayerUsername);
//                if (roundResult == 1) {
//                    numberOfWinsPlayer1++;
//                    duelWithUserView.printEndMessage(singleRoundWin(firstPlayerUsername,
//                            numberOfWinsPlayer1, numberOfWinsPlayer2));
//                } else {
//                    numberOfWinsPlayer2++;
//                    duelWithUserView.printEndMessage(singleRoundWin(secondPlayerUsername,
//                            numberOfWinsPlayer2, numberOfWinsPlayer1));
//                }
//            }
//            if (numberOfWinsPlayer1 == 2) {
//                return (threeRoundWinner(firstPlayerUsername,
//                        secondPlayerUsername, numberOfWinsPlayer1, numberOfWinsPlayer2));
//            } else {
//                return (threeRoundWinner(secondPlayerUsername,
//                        firstPlayerUsername, numberOfWinsPlayer2, numberOfWinsPlayer1));
//            }
//        } else {
//            setUpGame(firstPlayerUsername, secondPlayerUsername, roundResult);
//            roundResult = phaseCaller(firstPlayerUsername);
//            if (roundResult == 1) {
//                return (oneRoundWin(firstPlayerUsername, secondPlayerUsername));
//            } else {
//                return (oneRoundWin(secondPlayerUsername, firstPlayerUsername));
//            }
//        }
//    }
//
//    public int phaseCaller(String firstPlayerUsername) {
//        String result;
//        while (true) {
//            switch (phaseCounter) {
//                case 1:
//                    result = DrawPhaseView.getInstance().run();
//                    if (result.equals(Output.ILost.toString())) {
//                        return surrender(firstPlayerUsername);
//                    }
//                    break;
//                case 2:
//                    int winner = finishTheRound(StandByPhaseView.getInstance().run(), firstPlayerUsername);
//                    if (winner != 0) {
//                        return winner;
//                    }
//                    break;
//                case 3:
//                    EffectView.getInstance().output("phase: Main Phase 1");
//                    if (MainPhase1View.getInstance().run().equals(Output.ILost.toString())) {
//                        return surrender(firstPlayerUsername);
//                    }
//                    break;
//                case 4:
//                    if (startTurn != turnCounter) {
//                        int loser = finishTheRound(BattlePhaseView.getInstance().run(), firstPlayerUsername);
//                        if (loser != 0) {
//                            return loser;
//                        }
//                    }
//                    break;
//                case 5:
//                    EffectView.getInstance().output("phase: Main Phase 2");
//                    if (MainPhase1View.getInstance().run().equals(Output.ILost.toString())) {
//                        return surrender(firstPlayerUsername);
//                    }
//                    break;
//                case 6:
//                    if (EndPhaseView.getInstance().run().equals(Output.ILost.toString())) {
//                        return surrender(firstPlayerUsername);
//                    }
//                    break;
//            }
//            phaseCounter++;
//            if (phaseCounter == 7) {
//                phaseCounter -= 6;
//                turnCounter++;
//            }
//        }
//    }

    private int finishTheRound(String result, String firstPlayerUsername) {
//        if (result.equals(Output.IWon.toString())) {
//            setLP();
//            if (getMyBoard().getUser().getUsername().equals(firstPlayerUsername)) {
//                return 1;
//            }
//            return 2;
//        } else if (result.equals(Output.ILost.toString())) {
//            setLP();
//            if (getEnemyBoard().getUser().getUsername().equals(firstPlayerUsername)) {
//                return 1;
//            }
//            return 2;
//        }
        return 0;
    }

    private void setLP() {
//        getEnemyBoard().getUser().getPlayerLP().add(getEnemyBoard().getLP());
//        getMyBoard().getUser().getPlayerLP().add(getMyBoard().getLP());
    }

    private int surrender(String firstPlayerUsername) {
        setLP();
//        if (getMyBoard().getUser().getUsername().equals(firstPlayerUsername)) {
//            return 2;
//        }
        return 1;
    }

    //TODO
    //TODO
    //TODO
    //TODO
    //TODO
    //TODO
    //TODO
    public void setUpGame(int lastRoundResult) {
        editImageViews();
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

        opponentSpellTerritoryImageViews = new ArrayList<>(Arrays.asList(opponentSpellTerritoryImageView1,
                opponentSpellTerritoryImageView2, opponentSpellTerritoryImageView3,
                opponentSpellTerritoryImageView4, opponentSpellTerritoryImageView5));

        opponentMonsterTerritoryImageViews = new ArrayList<>(Arrays.asList(opponentMonsterTerritoryImageView1,
                opponentMonsterTerritoryImageView2, opponentMonsterTerritoryImageView3,
                opponentMonsterTerritoryImageView4, opponentMonsterTerritoryImageView5));

        myMonsterTerritoryImageViews = new ArrayList<>(Arrays.asList(myMonsterTerritoryImageView1,
                myMonsterTerritoryImageView2, myMonsterTerritoryImageView3,
                myMonsterTerritoryImageView4, myMonsterTerritoryImageView5));

        mySpellTerritoryCardImageViews = new ArrayList<>(Arrays.asList(mySpellTerritoryCardImageView1,
                mySpellTerritoryCardImageView2, mySpellTerritoryCardImageView3,
                mySpellTerritoryCardImageView4, mySpellTerritoryCardImageView5));
    }

    private void editImageViews() {
        ArrayList<ArrayList<ImageView>> allImageViewLists = new ArrayList<>(Arrays.asList(myHandImageViews,
                opponentHandImageViews, mySpellTerritoryCardImageViews, myMonsterTerritoryImageViews,
                opponentSpellTerritoryImageViews, opponentMonsterTerritoryImageViews));
        for (ArrayList<ImageView> imageViewList : allImageViewLists) {
            for (ImageView imageView : imageViewList) {
                //TODO getButton PRIMARY -- SECONDARY
//                imageView.setOnMouseClicked(event -> {
//                    System.out.println(event.getEventType());
//                    System.out.println(event.getClickCount());
//                    System.out.println(event.hashCode());
//                    System.out.println(event.toString());
//                    System.out.println(event.getButton());
//                });
                imageView.setOnMouseEntered(event -> {
                    if (imageView.getImage() != null) {
                        SoundPlayer.getInstance().playAudioClip(SoundURL.BUTTON_HOVER);
                        imageView.setEffect(new Glow(0.6));
                    }
                });
                imageView.setOnMouseExited(event -> imageView.setEffect(null));
            }
        }
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
}
