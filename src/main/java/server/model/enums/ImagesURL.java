package model.enums;

public enum ImagesURL {
    Cursor1("images/Cursor/Cursor1.png"),
    Cursor2("images/Cursor/Cursor2.png");

    private String value;

    ImagesURL(String value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return value;
    }
}
