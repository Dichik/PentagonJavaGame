package game.pieces;

import java.util.Stack;

public class Square {
    private boolean fixed;
    private Stack<String> st;

    private boolean stopping;

    public Square(String color) {
        st = new Stack<>();
        st.add(color);
        this.fixed = false;
        this.stopping = false;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed() {
        this.fixed = true;
    }

    public void setStopping() {
        this.stopping = true;
    }

    public boolean isStopping() {
        return stopping;
    }

    public String getColor() {
        return st.peek() ;
    }

    /**
     * 02.05.2021 added
     */
    public void add(String color) {
        st.add(color) ;
    }

    public void removeColor() {
        st.pop() ;
    }

    public boolean isEmpty(){
        return st.empty() ;
    }
    public boolean hasAnotherColor() {
        if(st.size() > 1){
            return true ;
        }
        return false;
    }
}
