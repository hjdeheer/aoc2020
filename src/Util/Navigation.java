package Util;

public class Navigation {
    char action;
    int value;


    public Navigation(char action, int value) {
        this.action = action;
        this.value = value;
    }


    public char getAction() {
        return action;
    }

    public int getValue() {
        return value;
    }

    public void setAction(char action) {
        this.action = action;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
