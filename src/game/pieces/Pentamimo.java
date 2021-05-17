package game.pieces;

import java.util.ArrayList;
import java.util.Collections;

public class Pentamimo {

    public static final ArrayList<Pentamimo> LIST = new ArrayList<>();

    public static final Pentamimo I;
    public static final Pentamimo J;
    public static final Pentamimo G;
    public static final Pentamimo F;
    public static final Pentamimo K;
    public static final Pentamimo P;
    public static final Pentamimo R;
    public static final Pentamimo W;
    public static final Pentamimo L;
    public static final Pentamimo Q;
    public static final Pentamimo S;
    public static final Pentamimo U;

    public static int USED = 0;

    static {
        {
            I = new Pentamimo("orange", 5); // 10
            I.setBlocks(Rotation.ROT0, 0, 1, 1, 1, 2, 1, 3, 1, 4, 1);
            I.setBlocks(Rotation.ROT90, 0, 1, 0, 2, 0, 3, 0, 4, 0, 0);
            I.setBlocks(Rotation.ROT180, 0, 1, 1, 1, 2, 1, 3, 1, 4, 1);
            I.setBlocks(Rotation.ROT270, 0, 1, 0, 2, 0, 3, 0, 4, 0, 0);
        }
        {
            J = new Pentamimo("orange", 5); // 1
            J.setBlocks(Rotation.ROT0, 0, 0, 0, 1, 1, 1, 2, 1, 0, 2);
            J.setBlocks(Rotation.ROT90, 0, 0, 1, 0, 2, 0, 1, 1, 1, 2);
            J.setBlocks(Rotation.ROT180, 2, 0, 0, 1, 1, 1, 2, 1, 2, 2);
            J.setBlocks(Rotation.ROT270, 0, 2, 1, 0, 2, 2, 1, 1, 1, 2);
        }
        {
            G = new Pentamimo("orange", 5); //4
            G.setBlocks(Rotation.ROT0, 0, 1, 1, 1, 2, 1, 3, 1, 3, 2);
            G.setBlocks(Rotation.ROT90, 1, 0, 1, 1, 1, 2, 1, 3, 0, 3);
            G.setBlocks(Rotation.ROT180, 0, 1, 1, 1, 2, 1, 3, 1, 0, 0);
            G.setBlocks(Rotation.ROT270, 1, 0, 1, 1, 1, 2, 1, 3, 2, 0);
        }
        {
            F = new Pentamimo("orange", 5); // 7
            F.setBlocks(Rotation.ROT0, 0, 1, 1, 1, 2, 1, 3, 1, 1, 2);
            F.setBlocks(Rotation.ROT90, 1, 0, 1, 1, 1, 2, 1, 3, 0, 1);
            F.setBlocks(Rotation.ROT180, 0, 1, 1, 1, 2, 1, 3, 1, 2, 0);
            F.setBlocks(Rotation.ROT270, 1, 0, 1, 1, 1, 2, 1, 3, 2, 2);
        }
        {
            K = new Pentamimo("orange", 5); // 11
            K.setBlocks(Rotation.ROT0, 0, 0, 0, 1, 1, 1, 2, 1, 1, 2);
            K.setBlocks(Rotation.ROT90, 0, 1, 1, 0, 1, 1, 2, 0, 1, 2);
            K.setBlocks(Rotation.ROT180, 1, 0, 0, 1, 1, 1, 2, 1, 2, 2);
            K.setBlocks(Rotation.ROT270, 1, 0, 1, 1, 1, 2, 2, 1, 0, 2);
        }
        {
            P = new Pentamimo("orange", 5); // 8
            P.setBlocks(Rotation.ROT0, 0, 0, 0, 1, 0, 2, 1, 0, 2, 0);
            P.setBlocks(Rotation.ROT90, 0, 0, 1, 0, 2, 0, 2, 1, 2, 2);
            P.setBlocks(Rotation.ROT180, 2, 2, 2, 1, 2, 0, 1, 2, 0, 2);
            P.setBlocks(Rotation.ROT270, 0, 0, 0, 1, 0, 2, 1, 2, 2, 2);
        }
        {
            R = new Pentamimo("orange", 5); // 2
            R.setBlocks(Rotation.ROT0, 0, 0, 1, 0, 1, 1, 2, 1, 2, 2);
            R.setBlocks(Rotation.ROT90, 2, 0, 2, 1, 1, 1, 1, 2, 0, 2);
            R.setBlocks(Rotation.ROT180, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2);
            R.setBlocks(Rotation.ROT270, 2, 0, 1, 0, 1, 1, 0, 1, 0, 2);
        }
        {
            W = new Pentamimo("orange", 5); // 5
            W.setBlocks(Rotation.ROT0, 0, 0, 1, 0, 0, 1, 0, 2, 1, 2);
            W.setBlocks(Rotation.ROT90, 0, 0, 1, 0, 0, 1, 2, 0, 2, 1);
            W.setBlocks(Rotation.ROT180, 0, 0, 1, 0, 1, 1, 1, 2, 0, 2);
            W.setBlocks(Rotation.ROT270, 0, 0, 0, 1, 1, 1, 2, 0, 2, 1);
        }
        {
            L = new Pentamimo("orange", 5); // 9
            L.setBlocks(Rotation.ROT0, 0, 1, 0, 2, 1, 1, 1, 2, 1, 0);
            L.setBlocks(Rotation.ROT90, 0, 1, 1, 1, 0, 2, 1, 2, 2, 2);
            L.setBlocks(Rotation.ROT180, 0, 0, 0, 1, 0, 2, 1, 0, 1, 1);
            L.setBlocks(Rotation.ROT270, 0, 0, 1, 0, 2, 0, 1, 1, 2, 1);
        }
        {
            Q = new Pentamimo("orange", 5); // 12
            Q.setBlocks(Rotation.ROT0, 0, 1, 1, 1, 1, 0, 2, 1, 1, 2);
            Q.setBlocks(Rotation.ROT90, 0, 1, 1, 1, 1, 0, 2, 1, 1, 2);
            Q.setBlocks(Rotation.ROT180, 0, 1, 1, 1, 1, 0, 2, 1, 1, 2);
            Q.setBlocks(Rotation.ROT270, 0, 1, 1, 1, 1, 0, 2, 1, 1, 2);
        }
        {
            S = new Pentamimo("orange", 5); // 6
            S.setBlocks(Rotation.ROT0, 0, 0, 1, 0, 1, 1, 2, 1, 3, 1);
            S.setBlocks(Rotation.ROT90, 1, 0, 1, 1, 0, 1, 0, 2, 0, 3);
            S.setBlocks(Rotation.ROT180, 0, 0, 1, 0, 2, 0, 2, 1, 3, 1);
            S.setBlocks(Rotation.ROT270, 1, 0, 1, 1, 1, 2, 0, 2, 0, 3);
        }
        {
            U = new Pentamimo("orange", 5); // 3
            U.setBlocks(Rotation.ROT0, 0, 1, 0, 2, 1, 1, 2, 1, 2, 0);
            U.setBlocks(Rotation.ROT90, 0, 0, 1, 0, 1, 1, 1, 2, 2, 2);
            U.setBlocks(Rotation.ROT180, 0, 1, 0, 2, 1, 1, 2, 0, 2, 1);
            U.setBlocks(Rotation.ROT270, 0, 0, 1, 0, 1, 1, 1, 2, 2, 2);
        }
    }

    private int[][] blocksPositions;

    public int[][] getBlocksPositions() {
        return blocksPositions;
    }

    private String color;
    private int size;

    public Pentamimo(String color, int size) {
        this.blocksPositions = new int[4][size * 2];
        this.color = color;
        this.size = size;
        LIST.add(this);
        Collections.shuffle(LIST);
    }

    public Square getSquareAt(Rotation currentRotation, int x, int y) {
        if ((this.blocksPositions[currentRotation.posInArray][0] == x && this.blocksPositions[currentRotation.posInArray][1] == y) ||
                (this.blocksPositions[currentRotation.posInArray][2] == x && this.blocksPositions[currentRotation.posInArray][3] == y) ||
                (this.blocksPositions[currentRotation.posInArray][4] == x && this.blocksPositions[currentRotation.posInArray][5] == y) ||
                (this.blocksPositions[currentRotation.posInArray][6] == x && this.blocksPositions[currentRotation.posInArray][7] == y) ||
                (this.blocksPositions[currentRotation.posInArray][8] == x && this.blocksPositions[currentRotation.posInArray][9] == y)) {

            return new Square(color);
        } else {
            return null;
        }
    }

    private void setBlocks(Rotation rot, int... coords) {
        this.blocksPositions[rot.posInArray] = coords;
    }

    public int getSize() {
        return size;
    }

    public enum Rotation {
        ROT0(0),
        ROT90(1),
        ROT180(2),
        ROT270(3);

        private int posInArray;

        public int getPosInArray() {
            return posInArray;
        }

        Rotation(int posInArray) {
            this.posInArray = posInArray;
        }

        public Rotation rotateClockwise() {
            if (posInArray > 0)
                return Rotation.values()[posInArray - 1];
            else
                return Rotation.values()[3];
        }
    }
}
