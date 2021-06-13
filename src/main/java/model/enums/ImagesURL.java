package model.enums;

public enum ImagesURL {
    Cursor("images/Cursor/Cursor1.png");
    private String value;

    ImagesURL(String value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return value;
    }
}
