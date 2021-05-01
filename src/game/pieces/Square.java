package game.pieces;

public class Square {

    private String color;
    private boolean fixed;

    private boolean stopping;

    public Square(String color) {
        this.color = color;
        fixed = false;
        stopping = false;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed() {
        fixed = true;
    }

    public void setStopping() {
        stopping = true;
    }

    public void resetStopTime() {
        stopping = false;
    }

    public boolean isStopping() {
        return stopping;
    }

    public void setNotFixed() {
        fixed = false;
    }

    public String getColor() {
        return color;
    }
}
