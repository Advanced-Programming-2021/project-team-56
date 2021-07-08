package server.model.enums;

public enum ProcessResult {

    SIGNUP_SUCCESSFUL("User created successfully!"),
    LOGIN_SUCCESSFUL("User logged in successfully!"),
    LOGIN_FAILED("Username and password didn't match!");


    public String value;

    ProcessResult(String value) {
        this.value = value;
    }
}
