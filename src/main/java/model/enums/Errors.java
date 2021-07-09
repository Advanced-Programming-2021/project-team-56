package model.enums;

public enum Errors {

    EMPTY_FIELD_USERNAME("Username's field is empty"),
    EMPTY_FIELD_NICKNAME("Nickname's field is empty"),
    EMPTY_FIELD_PASSWORD("Password's field is empty");


    public String value;

    Errors(String value) {
        this.value = value;
    }
}
