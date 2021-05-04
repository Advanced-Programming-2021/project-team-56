package model;

public class Update {

    private static Update update;

    private Update() {

    }

    public static Update getInstance() {
        if (update == null)
            update = new Update();
        return update;
    }

    private GameCommandType gameCommandType;

    public void setGameCommandType(GameCommandType gameCommandType) {
        this.gameCommandType = gameCommandType;
    }

    public GameCommandType getGameCommandType() {
        return gameCommandType;
    }
}
