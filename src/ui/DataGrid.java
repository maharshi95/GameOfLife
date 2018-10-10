package ui;

import java.util.ArrayList;
import java.util.LinkedList;

public class DataGrid {

    private int[] birthArray = new int[]{3};
    private int[] survivalArray = new int[]{2, 3};

    private int size;
    private int[][] matrix;
    private ArrayList<Integer> survivalRules;
    private ArrayList<Integer> birthRules;

    public DataGrid(int size) {
        this.size = size;
        matrix = new int[size][size];


        birthRules = new ArrayList<>();
        survivalRules = new ArrayList<>();

        for (int i : birthArray) {
            birthRules.add(i);
        }

        for (int i : survivalArray) {
            survivalRules.add(i);
        }
    }

    public DataGrid(int[][] grid) {
        this(grid.length);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = grid[i][j];
            }
        }
    }


    public void setValue(int i, int j, int v) {
        i = (i + size) % size;
        j = (j + size) % size;
        matrix[i][j] = v;
    }

    public int getValue(int i, int j) {
        i = (i + size) % size;
        j = (j + size) % size;
        //System.out.println("getValue: " + i + " " + j + ": " + matrix[i][j]);
        return matrix[i][j];
    }

    public boolean isAlive(int i, int j) {
        //System.out.println("isAlive: " + i + " " + j + " " + (val));
        return getValue(i, j) > 0;
    }

    public int getAliveNeighbours(int i, int j) {
        //System.out.println("getAliveNeighbours: " + i +" " + j);
        int count = 0;
        for (int a = i - 1; a <= i + 1; a++) {
            for (int b = j - 1; b <= j + 1; b++) {
                if ((a != i || b != j) && isAlive(a, b)) {
                    count++;
                }
            }
        }
        return count;
    }

    public void setMatrix(int[][] grid) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = grid[i][j];
            }
        }
    }

    public int[][] getMatrix() {
        int[][] mat = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mat[i][j] = matrix[i][j];
            }
        }
        return mat;
    }

    public int[][] getNextGen() {
        int[][] currentGen = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int n = getAliveNeighbours(i, j);
                currentGen[i][j] = matrix[i][j];
                if (isAlive(i, j) && !survivalRules.contains(n)) {
                    currentGen[i][j] = 0;
                } else if (!isAlive(i, j) && birthRules.contains(n)) {
                    currentGen[i][j] = 1;
                }
            }
        }
        return currentGen;
    }

    public int[][] evolve() {
        matrix = getNextGen();
        return matrix;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                str.append(isAlive(i, j) ? "#" : ".").append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }
}

