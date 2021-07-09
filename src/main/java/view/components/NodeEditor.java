package view.components;

import controller.SoundPlayer;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import model.enums.SoundURL;

public class NodeEditor {


    public static void editNode(double level, Node... nodes) {
        for (Node node : nodes) {
            node.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    node.setEffect(new Glow(level));
                    SoundPlayer.getInstance().playAudioClip(SoundURL.BUTTON_HOVER);
                }
            });
            node.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    node.setEffect(null);
                }
            });
        }
    }
}
