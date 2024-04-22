package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
    private final CellType[][] grid;
    private final int[][] clues;

    public PuzzleImpl(int[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            throw new IllegalArgumentException("board cannot be null or empty");
        }

        grid = new CellType[board.length][board[0].length];
        clues = new int[board.length][board[0].length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                switch (board[i][j]) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        grid[i][j] = CellType.CLUE;
                        clues[i][j] = board[i][j];
                        break;
                    case 5:
                        grid[i][j] = CellType.WALL;
                        clues[i][j] = -1;
                        break;
                    case 6:
                        grid[i][j] = CellType.CORRIDOR;
                        clues[i][j] = -1;
                        break;
                    default:
                        throw new IllegalArgumentException("invalid cell value: " + board[i][j]);
                }
            }
        }
    }

    @Override
    public int getWidth() {
        return grid[0].length;
    }

    @Override
    public int getHeight() {
        return grid.length;
    }

    @Override
    public CellType getCellType(int r, int c) {
        if (r < 0 || r >= getHeight() || c < 0 || c >= getWidth()) {
            throw new IndexOutOfBoundsException("row or column is out of bounds");
        }

        return grid[r][c];
    }

    @Override
    public int getClue(int r, int c) {
        if (r < 0 || r >= getHeight() || c < 0 || c >= getWidth()) {
            throw new IndexOutOfBoundsException("row or column is out of bounds");
        }
        if (grid[r][c] != CellType.CLUE) {
            throw new IllegalArgumentException("specified cell is not a clue cell");
        }

        return clues[r][c];
    }
}
