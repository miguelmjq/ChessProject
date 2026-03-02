package com.example;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Piece {
    private final boolean color;
    private BufferedImage img;

    public Piece(boolean isWhite, String img_file) {
        this.color = isWhite;

        try {
            if (this.img == null) {
                this.img = ImageIO.read(new File(System.getProperty("user.dir") + img_file));
            }
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public boolean getColor() {
        return color;
    }

    public Image getImage() {
        return img;
    }

    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();

        g.drawImage(this.img, x, y, null);
    }

    // TO BE IMPLEMENTED!
    // return a list of every square that is "controlled" by this piece. A square is
    // controlled
    // if the piece capture into it legally.
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
        ArrayList<Square> controlledSquares = new ArrayList<Square>();
        if (start == null || board == null){   
           return controlledSquares;
        }
        boolean iswhite = this.getColor();
        int row = start.getRow();
        int col = start.getCol();
        if (iswhite){
            if ((row-1>=0)&&(col-1>=0)){
            controlledSquares.add(board[row-1][col-1]);
            }
            if ((row-1>=0)&&(col+1<8)){
            controlledSquares.add(board[row-1][col+1]);
            }
        }
        else{
            if ((row+1<8)&&(col-1>=0)){
            controlledSquares.add(board[row+1][col-1]);
            }
            if ((row+1<8)&&(col+1<8)){
            controlledSquares.add(board[row+1][col+1]);
            }
        }
        

        return controlledSquares;
    }

    // TO BE IMPLEMENTED!
    // implement the move function here
    // it's up to you how the piece moves, but at the very least
    // the rules should be logical and it should never move off the board!
    // returns an arraylist of squares which are legal to move to
    // please note that your piece must have some sort of logic.
    // Just being able to move to every square on the board is not
    // going to score any points.

    // RULES:
    // The pawn can move one piece forward at a time
    // If it is the pawns first move, it can move 2 spaces forward
    // If a piece is one space diagonally forward left or right to the pawn,
    // then it can move to that space and capture it.
    public ArrayList<Square> getLegalMoves(Board b, Square start) {
        ArrayList<Square> moves = new ArrayList<Square>();
        boolean iswhite = this.getColor();
        int sr = start.getRow();
        int sc = start.getCol();

        // white
        if (iswhite) {
            // 1 move forward
            {
                if (sr - 1 >= 0
                        &&
                        !(b.getSquareArray()[sr - 1][sc].isOccupied())) {
                    moves.add(b.getSquareArray()[start.getRow() - 1][start.getCol()]);
                }
            }
            // 2 move forward
            {
                if (sr == 6
                        &&
                        !(b.getSquareArray()[sr - 1][sc].isOccupied())
                        &&
                        !(b.getSquareArray()[sr - 2][sc].isOccupied())) {
                    moves.add(b.getSquareArray()[start.getRow() - 2][start.getCol()]);
                }
            }
            // captures
            {
                //left capture
                if ((sr-1>=0)&&(sc-1>=0)
                    &&
                    b.getSquareArray()[sr-1][sc-1].isOccupied()
                    &&
                    b.getSquareArray()[sr-1][sc-1].getOccupyingPiece().getColor() !=iswhite
                ) {
                    moves.add(b.getSquareArray()[start.getRow()-1][start.getCol()-1]);
                }
                //right capture
                if ((sr-1>=0)&&(sc+1<8)
                    &&
                    b.getSquareArray()[sr-1][sc+1].isOccupied()
                    &&
                    b.getSquareArray()[sr-1][sc+1].getOccupyingPiece().getColor() !=iswhite
                ) {
                    moves.add(b.getSquareArray()[start.getRow()-1][start.getCol()+1]);
                }
            }

     }
        // black
        else {
            // 1 move forward
            {
                if (sr + 1 < 8
                        &&
                        !(b.getSquareArray()[sr + 1][sc].isOccupied())) {
                    moves.add(b.getSquareArray()[start.getRow() + 1][start.getCol()]);
                }
            }
            // 2 move forward
            {
                if (sr == 1
                        &&
                        !(b.getSquareArray()[sr + 1][sc].isOccupied())
                        &&
                        !(b.getSquareArray()[sr + 2][sc].isOccupied())) {
                    moves.add(b.getSquareArray()[start.getRow() + 2][start.getCol()]);
                }
            }
            // captures
            {
                //left capture
                if ((sr+1<8)&&(sc-1>=0)
                    &&
                    b.getSquareArray()[sr+1][sc-1].isOccupied()
                    &&
                    b.getSquareArray()[sr+1][sc-1].getOccupyingPiece().getColor() !=iswhite
                ) {
                    moves.add(b.getSquareArray()[start.getRow()-1][start.getCol()-1]);
                }
                //right capture
                if ((sr+1<8)&&(sc+1<8)
                    &&
                    b.getSquareArray()[sr+1][sc+1].isOccupied()
                    &&
                    b.getSquareArray()[sr+1][sc+1].getOccupyingPiece().getColor() !=iswhite
                ) {
                    moves.add(b.getSquareArray()[start.getRow()-1][start.getCol()+1]);
                }
            }

        }
        return moves;
    }
}
