package game.states;

import game.pieces.Grid;
import game.pieces.Pentamimo;

import java.util.ArrayList;

public class Solution {
    private ArrayList<Pentamimo> LIST;
    private boolean hasSolution;
    private int[][] matrix;
    private int SIZE;

    public Solution(ArrayList<Pentamimo> LIST) {
        this.LIST = LIST;
        hasSolution = false;
        SIZE = Grid.SIZE;

        matrix = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    public boolean foundSolution() {
        return hasSolution;
    }

    public void solutionFound(boolean found) {
        hasSolution = found;
    }

    public void drawSolution(){
        if(hasSolution){
            for(int i = 0; i < SIZE - 1; i ++){
                for(int j = 0; j < SIZE - 1; j ++){
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.print("\n");
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~");
    }

    public void findSolution() {
        int[][] b = new int[SIZE - 1][SIZE - 1];

        for (int i = 0; i < SIZE - 1; i++)
            for (int j = 0; j < SIZE - 1; j++)
                b[i][j] = 0;

        foundPlaceForPentamimo(LIST, 0, b);
    }


    private void foundPlaceForPentamimo(ArrayList<Pentamimo> list, int x, int[][] res) {
        if (hasSolution) {
            return;
        }
        if (x == 12) {
            matrix = res;
            hasSolution = true;
            return;
        }

        checkForRotation(list.get(x), Pentamimo.Rotation.ROT0, res, x);
        checkForRotation(list.get(x), Pentamimo.Rotation.ROT90, res, x);
        checkForRotation(list.get(x), Pentamimo.Rotation.ROT180, res, x);
        checkForRotation(list.get(x), Pentamimo.Rotation.ROT270, res, x);
    }

    private void checkForRotation(Pentamimo pentamimo, Pentamimo.Rotation rotation, int[][] res, int x) {
        int[][] blocks = pentamimo.getBlocksPositions();
        int[][] arr = new int[SIZE - 1][SIZE - 1];

        for (int i = 0; i < SIZE - 1; i++) {
            for (int j = 0; j < SIZE - 1; j++) {
                arr[i][j] = res[i][j];
            }
        }

        for (int i = 0; i < SIZE - 1; i++) {
            for (int j = 0; j < SIZE - 1; j++) {
                boolean foundPlace = true;
                for (int q = 0; q < 10; q += 2) {
                    int coordinateY = blocks[rotation.getPosInArray()][q];
                    int coordinateX = blocks[rotation.getPosInArray()][q + 1];

                    if (coordinateX + j < SIZE - 1 && coordinateY + i < SIZE - 1
                            && arr[coordinateY + i][coordinateX + j] == 0) {
                        if (coordinateY + i + 1 < SIZE - 1 && arr[coordinateY + i + 1][coordinateX + j] != 0)
                            foundPlace = false;
                        if (coordinateY + i - 1 >= 0 && arr[coordinateY + i - 1][coordinateX + j] != 0)
                            foundPlace = false;
                        if (coordinateX + j + 1 < SIZE - 1 && arr[coordinateY + i][coordinateX + j + 1] != 0)
                            foundPlace = false;
                        if (coordinateX + j - 1 >= 0 && arr[coordinateY + i][coordinateX + j - 1] != 0)
                            foundPlace = false;
                        if (coordinateY + i + 1 < SIZE - 1 && coordinateX + j - 1 >= 0 && arr[coordinateY + i + 1][coordinateX + j - 1] != 0)
                            foundPlace = false;
                        if (coordinateY + i + 1 < SIZE - 1 && coordinateX + j + 1 < SIZE - 1 && arr[coordinateY + i + 1][coordinateX + j + 1] != 0)
                            foundPlace = false;
                        if (coordinateY + i - 1 >= 0 && coordinateX + j - 1 >= 0 && arr[coordinateY + i - 1][coordinateX + j - 1] != 0)
                            foundPlace = false;
                        if (coordinateY + i - 1 >= 0 && coordinateX + j + 1 < SIZE - 1 && arr[coordinateY + i - 1][coordinateX + j + 1] != 0)
                            foundPlace = false;

                    } else foundPlace = false;
                }
                if (foundPlace) {
                    for (int q = 0; q < 10; q += 2) {
                        int coordinateY = blocks[rotation.getPosInArray()][q];
                        int coordinateX = blocks[rotation.getPosInArray()][q + 1];
                        arr[coordinateY + i][coordinateX + j] = 1;
                    }
                    foundPlaceForPentamimo(LIST, x + 1, arr);
                }
            }
        }
    }
}
