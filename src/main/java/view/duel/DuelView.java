package view.duel;

import controller.SoundPlayer;
import controller.duel.DuelWithUser;
import controller.duel.phases.BattlePhaseController;
import controller.duel.phases.DrawPhaseController;
import controller.duel.phases.MainPhase1Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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

    private static User player1;
    private static User player2;
    private static User firstPlayer;
    private static User secondPlayer;
    private static int numberOfWinsPlayer1;
    private static int numberOfWinsPlayer2;
    private static int numberOfRounds;
    public static boolean summonWithTribute = false;
    public static int numberOfTributes;
    private static boolean isBeginningOfARound;

    public static ArrayList<ImageView> myHandImageViews;
    public static ArrayList<ImageView> opponentHandImageViews;
    public static ArrayList<ImageView> opponentSpellAndTrapTerritoryImageViews;
    public static ArrayList<ImageView> opponentMonsterTerritoryImageViews;
    public static ArrayList<ImageView> myMonsterTerritoryImageViews;
    public static ArrayList<ImageView> mySpellAndTrapTerritoryImageViews;

    private DuelInfo currentPhase;

    public ImageView fieldImageView;
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

    public static void setPlayers(String firstPlayerName, String secondPlayerName) {
        firstPlayer = User.getUserByUsername(firstPlayerName);
        secondPlayer = User.getUserByUsername(secondPlayerName);
        player1 = firstPlayer;
        player2 = secondPlayer;
        numberOfWinsPlayer1 = 0;
        numberOfWinsPlayer2 = 0;
    }

    public static void setNewRoundFirstPlayerUsername(String firstPlayerUsername) {
        if (!firstPlayerUsername.equals(firstPlayer.getUsername())) {
            secondPlayer = User.getUserByUsername(firstPlayer.getUsername());
            firstPlayer = User.getUserByUsername(firstPlayerUsername);
        }
    }

    public static void setNumberOfRounds(int numberOfRounds) {
        DuelView.numberOfRounds = numberOfRounds;
    }

    public static void setIsBeginningOfARound(boolean value) {
        isBeginningOfARound = value;
    }

    @FXML
    public void initialize() {
        initializeFieldComponents();
        initializePlayersInformation();
        initializeImageViews();
        editSettingHBox();
        editPhaseVBoxes();
        editGraveYardImageViews();

        new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            startRound();
        })).play();

        graveYardScrollPane.setFitToHeight(true);
    }

    private void initializeFieldComponents() {
        settingVBox.setVisible(false);
        graveYardScrollPane.setVisible(false);
        duelInfoLabel.setVisible(false);
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

    private void startRound() {
        editImageViews();
        currentPhase = PHASE_DRAW;
        if (isBeginningOfARound) {
            DuelWithUser.getInstance().setUpGame(firstPlayer.getUsername(), secondPlayer.getUsername(), 0);
        }
        updateAll();
        showDuelInfoLabel(PHASE_DRAW.value);
        new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            showDuelInfoLabel(DrawPhaseController.getInstance().run());
            updateFromDrawPhase();
        })).play();
    }

    public void updateAll() {
        updateFieldSpells();
        updateMyHandCards();
        updateOpponentHandCards();
        updateMyMonsterTerritory();
        updateOpponentMonsterTerritory();
        updateMySpellAndTrapTerritory();
        updateOpponentSpellAndTrapTerritory();
        updateMyDeckCardsNumber();
        updateOpponentDeckCardsNumber();
        updateRound();
    }

    private void updateFieldSpells() {
        Card myFieldSpell = DuelWithUser.getInstance().getMyBoard().getFieldSpell();
        Card opponentFieldSpell = DuelWithUser.getInstance().getEnemyBoard().getFieldSpell();
        if (myFieldSpell != null)
            myFieldSpellImageView.setImage(new GameCard(myFieldSpell));
        if (opponentFieldSpell != null)
            opponentFieldSpellImageView.setImage(new GameCard(opponentFieldSpell));

        myFieldSpellImageView.setOnMouseEntered(event -> {
            if (myFieldSpellImageView.getImage() != null) {
                onMouseEnteredCardImageView.setImage(myFieldSpellImageView.getImage());
                SoundPlayer.getInstance().playAudioClip(SoundURL.BUTTON_HOVER);
                myFieldSpellImageView.setEffect(new Glow(0.6));
            }
        });
        opponentFieldSpellImageView.setOnMouseEntered(event -> {
            if (opponentFieldSpellImageView.getImage() != null) {
                onMouseEnteredCardImageView.setImage(opponentFieldSpellImageView.getImage());
                SoundPlayer.getInstance().playAudioClip(SoundURL.BUTTON_HOVER);
                opponentFieldSpellImageView.setEffect(new Glow(0.6));
            }
        });
    }

    public void updateFromDrawPhase() {
        updateMyHandCards();
        updateOpponentHandCards();
        updateMyDeckCardsNumber();
        updateOpponentDeckCardsNumber();
    }

    public void updateAfterActivateSpellEffect() {
        updateMyHandCards();
        updateMyMonsterTerritory();
        updateMySpellAndTrapTerritory();
        updateOpponentMonsterTerritory();
        updateOpponentSpellAndTrapTerritory();
        updateOpponentHandCards();
        updateRound();
        new Timeline(new KeyFrame(Duration.seconds(1), event -> updateMySpellAndTrapTerritory())).play();
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

    public void updateRound() {
        Board enemyBoard = DuelWithUser.getInstance().getEnemyBoard();
        Board myBoard = DuelWithUser.getInstance().getMyBoard();
        int enemyLP = enemyBoard.getLP();
        int myLP = myBoard.getLP();
        opponentLPBar.setPrefWidth(500 * enemyLP / 8000.0);
        opponentLPLabel.setText("LP: " + Math.max(0, enemyLP));
        myLPBar.setPrefWidth(500 * myLP / 8000.0);
        myLPLabel.setText("LP: " + Math.max(0, myLP));
        DuelWithUser.getInstance().setLP();
        if (enemyLP <= 0 || myLP <= 0) {
            String winnerUsername;
            if (enemyLP <= 0) {
                winnerUsername = myBoard.getUser().getUsername();
            } else {
                winnerUsername = enemyBoard.getUser().getUsername();
            }
            configureWinnerAndLooserPlayers(winnerUsername);
        }
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
                DuelWithUser.getInstance().getMyBoard().setSelectedCard(null);
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

    private void editGraveYardImageViews() {
        NodeEditor.editNode(0.6, myGraveyardImageView, opponentGraveyardImageView);
        myGraveyardImageView.setOnMouseExited(event -> myGraveyardImageView.setEffect(null));
        myGraveyardImageView.setOnMouseClicked(event -> {
            graveYardScrollPane.setVisible(true);
            showCardImagesInGraveYard(DuelWithUser.getInstance().getMyBoard().getGraveyard());
        });
        graveYardScrollPane.setOnMouseExited(event -> graveYardScrollPane.setVisible(false));

        opponentGraveyardImageView.setOnMouseExited(event -> opponentGraveyardImageView.setEffect(null));
        opponentGraveyardImageView.setOnMouseClicked(event -> {
            graveYardScrollPane.setVisible(true);
            showCardImagesInGraveYard(DuelWithUser.getInstance().getEnemyBoard().getGraveyard());
        });
        graveYardScrollPane.setOnMouseExited(event -> graveYardScrollPane.setVisible(false));
    }

    private void showCardImagesInGraveYard(ArrayList<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            Image image = new Image(cards.get(i).getImageURL());
            ImageView imageView = new ImageView(image);
            imageView.setOnMouseEntered(event -> imageView.setEffect(new Glow(0.6)));
            imageView.setOnMouseExited(event -> imageView.setEffect(null));
            imageView.setFitWidth(96);
            imageView.setFitHeight(140);
            graveYardGridPane.add(imageView, i, 0);
            GridPane.setMargin(imageView, new Insets(5));
        }
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

    private void processPhase() {
        if (isBeginningOfARound) {
            if (currentPhase == PHASE_BATTLE) {
                showDuelInfoLabel("You cannot battle in first turn\nGo to the next phase");
                return;
            }
        }
        if (currentPhase == PHASE_END) {
            DuelWithUser.getInstance().incrementTurnCounter();
            isBeginningOfARound = false;
            new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                setNewRoundFirstPlayerUsername(secondPlayer.getUsername());
                try {
                    FxmlController.getInstance().setSceneFxml(MenuURL.DUEL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            })).play();
        }
        showDuelInfoLabel(currentPhase.value);
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
                if (imageView.getImage() != null) {
                    if (currentPhase == PHASE_MAIN1 || currentPhase == PHASE_MAIN2) {
                        onMouseClickedMyHandImageViewsInMainPhase(imageView, event);
                    }
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
                if (imageView.getImage() != null) {
                    if (currentPhase == PHASE_MAIN1 || currentPhase == PHASE_MAIN2) {
                        onMouseClickedMySpellAndTrapTerritoryImageViewsInMainPhase(imageView, event);
                    }
                }
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
                if (imageView.getImage() != null) {
                    if (summonWithTribute) {
                        tributeCard(imageView, event);
                    }
                    if (currentPhase == PHASE_MAIN1 || currentPhase == PHASE_MAIN2) {
                        onMouseClickedMyMonsterTerritoryImageViewsInMainPhase(imageView, event);
                    }
                    if (currentPhase == PHASE_BATTLE) {
                        onMouseClickedForMyMonsterTerritoryImageViewsInBattlePhase(imageView, event);
                    }
                }
            });
        }
    }

    private void setOnMouseClickedForOpponentMonsterTerritoryImageViews() {
        for (ImageView imageView : opponentMonsterTerritoryImageViews) {
            imageView.setOnMouseClicked(event -> {
                if (imageView.getImage() != null) {
                    if (currentPhase == PHASE_BATTLE) {
                        onMouseClickedForOpponentMonsterTerritoryImageViewsInBattlePhase(imageView, event);
                    }
                }
            });
        }
    }


    private void onMouseClickedMyHandImageViewsInMainPhase(ImageView imageView, MouseEvent event) {
        Card card = ((GameCard) imageView.getImage()).getCard();
        if (DuelWithUser.getInstance().getMyBoard().getSelectedCard() == null ||
                DuelWithUser.getInstance().getMyBoard().getSelectedCard() != card) {
            DuelWithUser.getInstance().selectCard(card);
        } else {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (DuelWithUser.getInstance().getMyBoard().getSelectedCard() instanceof MonsterCard) {
                    String result = MainPhase1Controller.getInstance().summon(false);
                    if (!result.equals("summoned successfully")) showDuelInfoLabel(result);
                    else {
                        summonAndSetCard(1);
                    }
                } else {
                    String result = MainPhase1Controller.getInstance().activateSpell();
                    showDuelInfoLabel(result);
                    updateAfterActivateSpellEffect();
                    if (card instanceof SpellCard && ((SpellCard) card).getIcon().equals("Field"))
                        fieldSpellActivate(card);
                }
            } else {
                if (card instanceof SpellCard && ((SpellCard) card).getIcon().equals("Field")) return;
                String result = MainPhase1Controller.getInstance().set();
                if (!result.equals("set successfully") && !result.equals("summoned successfully"))
                    showDuelInfoLabel(result);
                else {
                    if (card instanceof MonsterCard) summonAndSetCard(1);
                    else summonAndSetCard(2);
                }
            }
        }
    }

    private void summonAndSetCard(int monsterOrSpell) {
        if (monsterOrSpell == 1) {
            for (int i = 0; i < 5; i++) {
                ImageView monsterImageView = myMonsterTerritoryImageViews.get(i);
                if (monsterImageView.getImage() == null) {
                    monsterImageView.setOpacity(0);
                    updateMyHandCards();
                    updateMyMonsterTerritory();
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> monsterImageView.setOpacity(monsterImageView.getOpacity() + 0.05)));
                    timeline.setCycleCount(20);
                    timeline.play();
                    break;
                }
            }
        } else {
            for (int i = 0; i < 5; i++) {
                ImageView spellImageView = mySpellAndTrapTerritoryImageViews.get(i);
                if (spellImageView.getImage() == null) {
                    spellImageView.setOpacity(0);
                    updateMyHandCards();
                    updateMyMonsterTerritory();
                    updateMySpellAndTrapTerritory();
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> spellImageView.setOpacity(spellImageView.getOpacity() + 0.05)));
                    timeline.setCycleCount(20);
                    timeline.play();
                    break;
                }
            }
        }
    }

    private void fieldSpellActivate(Card card) {
        fieldImageView.setImage(new Image("/images/Duel/Field/" + card.getName() + ".png"));
        myFieldSpellImageView.setImage(new GameCard(card));
    }

    private void onMouseClickedMySpellAndTrapTerritoryImageViewsInMainPhase(ImageView imageView, MouseEvent event) {
        Card card = ((GameCard) imageView.getImage()).getCard();
        if (DuelWithUser.getInstance().getMyBoard().getSelectedCard() == null ||
                DuelWithUser.getInstance().getMyBoard().getSelectedCard() != ((GameCard) imageView.getImage()).getCard()) {
            card = ((GameCard) imageView.getImage()).getCard();
            DuelWithUser.getInstance().selectCard(card);
        } else {
            String result = MainPhase1Controller.getInstance().activateSpell();
            showDuelInfoLabel(result);
            updateAfterActivateSpellEffect();
            if (card instanceof SpellCard && ((SpellCard) card).getIcon().equals("Field"))
                fieldSpellActivate(card);
        }
    }


    private void onMouseClickedMyMonsterTerritoryImageViewsInMainPhase(ImageView imageView, MouseEvent event) {
        try {
            if (DuelWithUser.getInstance().getMyBoard().getSelectedCard() == null ||
                    DuelWithUser.getInstance().getMyBoard().getSelectedCard() != ((GameCard) imageView.getImage()).getCard()) {
                Card card = ((GameCard) imageView.getImage()).getCard();
                DuelWithUser.getInstance().selectCard(card);
            } else {
                if (event.getButton() == MouseButton.PRIMARY) {
                    Card card = DuelWithUser.getInstance().getMyBoard().getSelectedCard();
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
        } catch (Exception ignored) {
        }
    }

    private void onMouseClickedForMyMonsterTerritoryImageViewsInBattlePhase(ImageView imageView, MouseEvent event) {
        if (isBeginningOfARound) return;
        if (DuelWithUser.getInstance().getMyBoard().getSelectedCard() == null ||
                DuelWithUser.getInstance().getMyBoard().getSelectedCard() != ((GameCard) imageView.getImage()).getCard()) {
            DuelWithUser.getInstance().selectCard(((GameCard) imageView.getImage()).getCard());
        } else {
            if (event.getButton() == MouseButton.PRIMARY) {
                showDuelInfoLabel(BattlePhaseController.getInstance().attackUser());
                BattlePhaseController.getInstance().afterBattleEffects();
                updateRound();
            }
        }
    }

    private void onMouseClickedForOpponentMonsterTerritoryImageViewsInBattlePhase(ImageView imageView, MouseEvent event) {
        if (DuelWithUser.getInstance().getMyBoard().getSelectedCard() != null) {
            if (event.getButton() == MouseButton.PRIMARY) {
                showDuelInfoLabel(BattlePhaseController.getInstance().attackCard(getClickedOpponentMonsterImageViewAddress(imageView)));
                BattlePhaseController.getInstance().afterBattleEffects();
                updateRound();
                updateMyMonsterTerritory();
                updateOpponentMonsterTerritory();
            }
        } else {
            showDuelInfoLabel("Please select a card\nfrom your cards first");
        }
    }

    private int getClickedOpponentMonsterImageViewAddress(ImageView clickedImageView) {
        for (int i = 0; i < opponentMonsterTerritoryImageViews.size(); i++) {
            if (opponentMonsterTerritoryImageViews.get(i) == clickedImageView) {
                return i + 1;
            }
        }
        return 0;
    }

    private void tributeCard(ImageView imageView, MouseEvent event) {
        for (int i = 0; i < 5; i++) {
            if (myMonsterTerritoryImageViews.get(i).getImage() != null) {
                if (myMonsterTerritoryImageViews.get(i) == imageView) {
                    numberOfTributes--;
                    MainPhase1Controller.getInstance().tribute(i + 1);
                    updateMyMonsterTerritory();
                    if (numberOfTributes == 0) {
                        updateMyMonsterTerritory();
                        updateMyHandCards();
                        summonWithTribute = false;
                    }
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

    private void configureWinnerAndLooserPlayers(String winnerUsername) {
        User winnerUser;
        User looserUser;
        int winnerNumberOfWins;
        int looserNumberOfWins;
        if (winnerUsername.equals(player1.getUsername())) {
            winnerUser = player1;
            looserUser = player2;
            winnerNumberOfWins = ++numberOfWinsPlayer1;
            looserNumberOfWins = numberOfWinsPlayer2;
        } else {
            looserUser = player1;
            winnerUser = player2;
            winnerNumberOfWins = ++numberOfWinsPlayer2;
            looserNumberOfWins = numberOfWinsPlayer1;
        }
        showRoundResult(winnerUser, looserUser, winnerNumberOfWins, looserNumberOfWins);
    }

    private void showRoundResult(User winnerUser, User looserUser, int winnerNumberOfWins, int looserNumberOfWins) {
        if (numberOfRounds == 3) {
            showDuelInfoLabel(DuelWithUser.getInstance().singleRoundWin(winnerUser.getUsername(),
                    winnerNumberOfWins, looserNumberOfWins));
            new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                try {
                    if (numberOfWinsPlayer1 != 2 && numberOfWinsPlayer2 != 2) {
                        DuelRoundPreparationView.setWinnerUsername(winnerUser.getUsername());
                        DuelRoundPreparationView.setLooserUsername(looserUser.getUsername());
                        FxmlController.getInstance().setSceneFxml(MenuURL.DUEL_ROUND_PREPARATION);
                    } else {
                        if (numberOfWinsPlayer1 == 2) {
                            showDuelInfoLabel(DuelWithUser.getInstance().threeRoundWinner(player1.getUsername(),
                                    player2.getUsername(), numberOfWinsPlayer1, numberOfWinsPlayer2));
                        } else {
                            showDuelInfoLabel(DuelWithUser.getInstance().threeRoundWinner(player2.getUsername(),
                                    player1.getUsername(), numberOfWinsPlayer2, numberOfWinsPlayer1));
                        }
                        new Timeline(new KeyFrame(Duration.seconds(2), anotherEvent -> {
                            try {
                                FxmlController.getInstance().setSceneFxml(MenuURL.MAIN);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        })).play();
                    }
                } catch (IOException ignored) {
                }
            })).play();
        } else {
            showSingleRoundResult(winnerUser.getUsername(), looserUser.getUsername());
        }
    }

    private void showSingleRoundResult(String winnerUsername, String looserUsername) {
        showDuelInfoLabel(DuelWithUser.getInstance().oneRoundWin(winnerUsername, looserUsername));
        new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            try {
                FxmlController.getInstance().setSceneFxml(MenuURL.MAIN);
            } catch (IOException ignored) {
            }
        })).play();
    }
}
