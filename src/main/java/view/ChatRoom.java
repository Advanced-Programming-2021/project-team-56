package view;

import com.gilecode.yagson.YaGson;
import controller.SoundPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import model.ClientSocket;
import model.enums.MenuURL;
import model.enums.SoundURL;
import server.User;
import view.components.NodeEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatRoom {


    public Label pinnedMessageLabel;
    public TextArea textArea;
    public VBox clickedUserInfo;
    public Circle clickedMagicCircle;
    public Circle clickedAvatarCircle;
    public Label clickedUsernameLabel;
    public Label clickedNicknameLabel;
    public Button backButton;
    public Pane sendPane;
    public ImageView sendArrowImageView;
    public ScrollPane chatBoxScrollPane;
    public Label numberOfOnlineLabel;

    private VBox chatVBox;
    private Timeline updateTimeline;


    @FXML
    public void initialize() {
        initializeChatBox();
        initializePinnedMessage();
        initializeNumberOfOnlineLabel();
        editClickedUserInfoVBox();
        updateTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            initializeChatBox();
            initializePinnedMessage();
            initializeNumberOfOnlineLabel();
        }));
        updateTimeline.setCycleCount(Timeline.INDEFINITE);
        updateTimeline.play();
        editButtons();
    }

    private void editClickedUserInfoVBox() {
        clickedUserInfo.setVisible(false);
        clickedMagicCircle.setFill(new ImagePattern(new Image("/images/Magic-Circle.png")));
    }

    private void editButtons() {
        MainGUI.editMenuButtons(new ArrayList<>(Arrays.asList(backButton)));
        backButton.setOnMouseClicked(event -> {
            try {
                updateTimeline.stop();
                FxmlController.getInstance().setSceneFxml(MenuURL.MAIN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sendPane.setOnMouseEntered(event -> {
            if (textArea.getText().length() != 0) {
                sendArrowImageView.setBlendMode(BlendMode.GREEN);
            }
        });
        sendPane.setOnMouseExited(event -> sendArrowImageView.setBlendMode(BlendMode.SRC_OVER));
        setOnMouseClickedForSendArrow(false, null);
    }

    private void setOnMouseClickedForSendArrow(boolean isEdit, String originalMessage) {
        if (isEdit) {
            sendPane.setOnMouseClicked(event -> {
                if (textArea.getText().length() != 0) {
                    sendEditedChatMessage(textArea.getText(), originalMessage);
                    textArea.setText("");
                    setOnMouseClickedForSendArrow(false, null);
                }
            });
        } else {
            sendPane.setOnMouseClicked(event -> {
                if (textArea.getText().length() != 0) {
                    sendChatMessageToServer(textArea.getText());
                    textArea.setText("");
                }
            });
        }
    }

    private void sendChatMessageToServer(String message) {
        try {
            ClientSocket.dataOutputStream.writeUTF("Chat-New-Message \"" + message + "\"" + User.getCurrentUser().getUsername());
            ClientSocket.dataOutputStream.flush();
            ClientSocket.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeNumberOfOnlineLabel() {
        try {
            ClientSocket.dataOutputStream.writeUTF("Get-Number-Of-LoggedIn");
            ClientSocket.dataOutputStream.flush();
            String serverResponse = ClientSocket.dataInputStream.readUTF();
            numberOfOnlineLabel.setText(serverResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializePinnedMessage() {
        try {
            ClientSocket.dataOutputStream.writeUTF("Chat-get-Pinned-Message");
            ClientSocket.dataOutputStream.flush();
            String serverChats = ClientSocket.dataInputStream.readUTF();
            pinnedMessageLabel.setText(serverChats);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeChatBox() {
        try {
            ClientSocket.dataOutputStream.writeUTF("Chat get-Chats");
            ClientSocket.dataOutputStream.flush();
            String serverChats = ClientSocket.dataInputStream.readUTF();
            initializeChatBoxVBox(serverChats);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeChatBoxVBox(String chats) {
        chatVBox = instantiateVBox();
        editScrollPane(chatVBox);
        String[] singleChats =  chats.split("\n");
        Pattern chatPattern = Pattern.compile("^Username: (\\S+) Message: \"([\\S\\s&&[^\"]]+)\"(REPLY Username: (\\S+) Message: \"([\\S\\s&&[^\"]]+)\")?$");
        for (int i = 0; i < singleChats.length; i++) {
            Matcher matcher = chatPattern.matcher(singleChats[i]);
            if (matcher.find()) {
                if (matcher.group(3) != null) {
                    chatVBox.getChildren().add(instantiateIndividualHBox(true, matcher.group(1), matcher.group(2),
                            matcher.group(4), matcher.group(5)));
                } else {
                    System.out.println("this is not a reply");
                    chatVBox.getChildren().add(instantiateIndividualHBox(false, matcher.group(1), matcher.group(2),
                            null, null));
                }
            }
        }
    }

    private VBox instantiateVBox() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(1, 2, 1, 2));
        vbox.setStyle("-fx-background-color: black");
        return vbox;
    }

    private void editScrollPane(VBox chatVBox) {
        chatBoxScrollPane.setContent(chatVBox);
        chatBoxScrollPane.setFitToWidth(true);
        chatBoxScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private HBox instantiateIndividualHBox(boolean isReply, String username, String message,
                                           String repliedUsername, String repliedMessage) {
        User user = null;
        try {
            ClientSocket.dataOutputStream.writeUTF("Get-User " + username);
            ClientSocket.dataOutputStream.flush();
            YaGson yaGson = new YaGson();
            user = yaGson.fromJson(ClientSocket.dataInputStream.readUTF(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HBox hBox = new HBox(50);
        hBox.setPadding(new Insets(5, 0, 5, 10));
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setStyle("-fx-border-color: #500274; -fx-border-width: 5 0 5 0");
        VBox userInfoVBox = instantiateUserInfoVBox(user);
        Label messageLabel = instantiateMessageLabel(message);
        VBox messageComponentsVBox = instantiateMessageComponentsVBox(user, message);
        hBox.getChildren().add(userInfoVBox);
        hBox.getChildren().add(messageLabel);
        hBox.getChildren().add(messageComponentsVBox);
        if (isReply) hBox.getChildren().add(instantiateReplyInfoVBox(repliedUsername, repliedMessage));
        return hBox;
    }

    private VBox instantiateUserInfoVBox(User user) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        StackPane stackPane = instantiateUserInfoStackPane(user);
        Label usernameLabel = instantiateUsernameLabel(user.getUsername());
        vBox.getChildren().add(stackPane);
        vBox.getChildren().add(usernameLabel);
        return vBox;
    }

    private Label instantiateMessageLabel(String message) {
        Label label = new Label(message);
        label.setPadding(new Insets(10, 10, 10, 10));
        label.setMaxWidth(600);
        label.setTextAlignment(TextAlignment.LEFT);
        label.setWrapText(true);
        label.setStyle("-fx-text-fill: #96a8d7; -fx-font-size: 18px; -fx-font-family: 'Times New Roman';" +
                "-fx-background-color:  #360E6E; -fx-background-radius:  20 20 20 0");
        label.setAlignment(Pos.CENTER_LEFT);
        return label;
    }

    private VBox instantiateMessageComponentsVBox(User user, String message) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        ImageView pinMessageImageView = instantiatePinMessageImageView(message);
        ImageView replyMessageImageView = instantiateReplyMessageImageView(user, message);
        vBox.getChildren().add(pinMessageImageView);
        vBox.getChildren().add(replyMessageImageView);
        if (User.getCurrentUser().getUsername().equals(user.getUsername())) {
            ImageView deleteMessageImageView = instantiateDeleteMessageImageView(message);
            Label editMessageLabel = instantiateEditMessageLabel(message);
            vBox.getChildren().add(deleteMessageImageView);
            vBox.getChildren().add(editMessageLabel);
        }
        return vBox;
    }

    private VBox instantiateReplyInfoVBox(String repliedUsername, String repliedMessage) {
        //TODO sizes;
        Label fromUsernameLabel = new Label("From " + repliedUsername);
        fromUsernameLabel.setStyle("-fx-text-fill: #8800d4; -fx-font-family: 'Times New Roman'; -fx-font-size: 20px");
        Label repliedMessageLabel = new Label(repliedMessage);
        repliedMessageLabel.setMaxWidth(100);
        repliedMessageLabel.setStyle("-fx-text-fill: #8800d4; -fx-font-family: 'Times New Roman'; -fx-font-size: 20px");
        VBox vBox = new VBox(10, fromUsernameLabel, repliedMessageLabel);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }



    private StackPane instantiateUserInfoStackPane(User user) {
        StackPane stackPane = new StackPane();
        stackPane.setOnMouseClicked(event -> showClickedUserInfo(user));
        stackPane.setAlignment(Pos.CENTER);
        Circle backgroundCircle = new Circle(60, Paint.valueOf("#500274"));
        Circle magicCircle = new Circle(60, new ImagePattern(new Image("/images/Magic-Circle.png")));
        Circle avatarCircle = new Circle(38.2, new ImagePattern(new Image(user.getAvatarURL())));
        NodeEditor.editNode(0.3, avatarCircle);
        stackPane.getChildren().add(backgroundCircle);
        stackPane.getChildren().add(magicCircle);
        stackPane.getChildren().add(avatarCircle);
        return stackPane;
    }

    private Label instantiateUsernameLabel(String username) {
        Label label = new Label(username);
        label.setStyle("-fx-text-fill: #8800d4; -fx-font-family: 'Times New Roman'; -fx-font-size: 20px");
        return label;
    }

    private void showClickedUserInfo(User user) {
        clickedUserInfo.setVisible(true);
        clickedAvatarCircle.setFill(new ImagePattern(new Image(user.getAvatarURL())));
        clickedUsernameLabel.setText(user.getUsername());
        clickedNicknameLabel.setText(user.getNickname());
    }



    private ImageView instantiatePinMessageImageView(String message) {
        ImageView pinMessageImageView = new ImageView(new Image("/images/Pin-image.jpg"));
        pinMessageImageView.setFitHeight(50);
        pinMessageImageView.setFitWidth(50);
        pinMessageImageView.setOnMouseEntered(event -> {
            SoundPlayer.getInstance().playAudioClip(SoundURL.BUTTON_HOVER);
            pinMessageImageView.setEffect(new Glow(0.6));
            pinMessageImageView.setBlendMode(BlendMode.GREEN);
        });
        pinMessageImageView.setOnMouseExited(event -> {
            pinMessageImageView.setEffect(null);
            pinMessageImageView.setBlendMode(BlendMode.SRC_OVER);
        });
        pinMessageImageView.setOnMouseClicked(event -> pinMessage(message));
        return pinMessageImageView;
    }

    private ImageView instantiateReplyMessageImageView(User user, String message) {
        ImageView replyMessageImageView = new ImageView(new Image("/images/Reply-Arrow.png"));
        replyMessageImageView.setFitWidth(50);
        replyMessageImageView.setFitHeight(50);
        replyMessageImageView.setOnMouseEntered(event -> {
            SoundPlayer.getInstance().playAudioClip(SoundURL.BUTTON_HOVER);
            replyMessageImageView.setEffect(new Glow(0.6));
            replyMessageImageView.setBlendMode(BlendMode.GREEN);
        });
        replyMessageImageView.setOnMouseExited(event -> {
            replyMessageImageView.setEffect(null);
            replyMessageImageView.setBlendMode(BlendMode.SRC_OVER);
        });
        replyMessageImageView.setOnMouseClicked(event -> replyMessage(user, message));
        return replyMessageImageView;
    }

    private ImageView instantiateDeleteMessageImageView(String message) {
        ImageView deleteMessageImageView = new ImageView(new Image("/images/Trashcan-image.jpg"));
        deleteMessageImageView.setFitHeight(50);
        deleteMessageImageView.setFitWidth(50);
        deleteMessageImageView.setOnMouseEntered(event -> {
            SoundPlayer.getInstance().playAudioClip(SoundURL.BUTTON_HOVER);
            deleteMessageImageView.setEffect(new Glow(0.6));
            deleteMessageImageView.setBlendMode(BlendMode.RED);
        });
        deleteMessageImageView.setOnMouseExited(event -> {
            deleteMessageImageView.setEffect(null);
            deleteMessageImageView.setBlendMode(BlendMode.SRC_OVER);
        });
        deleteMessageImageView.setOnMouseClicked(event -> deleteMessage(message));
        return deleteMessageImageView;
    }

    private Label instantiateEditMessageLabel(String message) {
        Label label = new Label("EDIT");
        label.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 20px; -fx-text-fill: white");
        label.setOnMouseEntered(event -> {
            SoundPlayer.getInstance().playAudioClip(SoundURL.BUTTON_HOVER);
            label.setEffect(new Glow(0.6));
            label.setBlendMode(BlendMode.GREEN);
        });
        label.setOnMouseExited(event -> {
            label.setEffect(null);
            label.setBlendMode(BlendMode.SRC_OVER);
        });
        label.setOnMouseClicked(event -> editMessage(message));
        return label;
    }

    private void pinMessage(String message) {
        try {
            ClientSocket.dataOutputStream.writeUTF("Chat-set-Pinned-Message: \"" + message + "\"");
            ClientSocket.dataOutputStream.flush();
            ClientSocket.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void replyMessage(User toReplyUser, String toReplyMessage) {
        sendPane.setOnMouseClicked(event -> {
            if (textArea.getText().length() != 0) {
                sendReplyMessage(textArea.getText(), toReplyMessage, toReplyUser.getUsername());
                textArea.setText("");
                setOnMouseClickedForSendArrow(false, null);
            }
        });
    }

    private void deleteMessage(String message) {
        try {
            ClientSocket.dataOutputStream.writeUTF("Chat-Delete-Message: \"" + message + "\"" + User.getCurrentUser().getUsername());
            ClientSocket.dataOutputStream.flush();
            ClientSocket.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editMessage(String message) {
        setOnMouseClickedForSendArrow(true, message);
        textArea.setText(message);
    }

    private void sendEditedChatMessage(String editedMessage, String originalMessage) {
        try {
            ClientSocket.dataOutputStream.writeUTF("Chat-Edit-Message: \"" + originalMessage + "\"" +
                    User.getCurrentUser().getUsername() + "\"" + editedMessage + "\"");
            ClientSocket.dataOutputStream.flush();
            ClientSocket.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendReplyMessage(String message, String repliedMessage, String repliedUsername) {
        try {
            ClientSocket.dataOutputStream.writeUTF("Chat-Reply-Message: \"" + message + "\"" +
                    User.getCurrentUser().getUsername() + "\"" + repliedMessage + "\"" + repliedUsername);
            ClientSocket.dataOutputStream.flush();
            ClientSocket.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
