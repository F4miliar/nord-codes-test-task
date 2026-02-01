package models.enums;

public enum Actions {

    LOGIN ("LOGIN"),
    ACTION ("ACTION"),
    LOGOUT("LOGOUT");

    private final String action;

    Actions(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
