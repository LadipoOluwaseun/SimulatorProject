package app;

public class IDHolder {
    public int ID;
    public String display;

    public IDHolder(int ID, String display) {
        this.ID = ID;
        this.display = display;
    }

    public int getID() {
        return ID;
    }

    public String toString() {
        return display;
    }
}

