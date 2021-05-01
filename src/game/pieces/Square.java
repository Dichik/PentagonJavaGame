package game.pieces;

public class Square {

    private String color;
    private boolean fixed;

    private boolean stopping;
    private int stopTime;

    public Square(String color) {
        this.color = color;
        this.fixed = false;
        this.stopping = false;
        this.stopTime = 0;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed() {
        if (this.stopping)
            this.stopTime++;

        if (stopTime == 5) {
            this.fixed = true;
            this.stopTime = 0;
        }
    }

    public void setStopping() {
        this.stopping = true;
    }

    public void resetStopTime() {
        this.stopTime = 0;
        this.stopping = false;
    }

    public boolean isStopping() {
        return stopping;
    }

    public void setNotFixed() {
        this.fixed = false;
    }

    public String getColor() {
        return color;
    }

    public void forceFix() {
        this.fixed = true;
    }
}
