package view.components;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;

public class NodeEditor {


    public static void setNodesGlow(Node... nodes) {
        for (Node node : nodes) {
            node.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    node.setEffect(new Glow(0.8));
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
