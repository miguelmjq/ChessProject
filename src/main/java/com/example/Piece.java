package com.example;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
@SuppressWarnings("unused")
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
        return null;
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
    // If a piece is one space diagonally left or right to the pawn,
    // then it can move to that space and capture it.
    public ArrayList<Square> getLegalMoves(Board b, Square start) {
        ArrayList<Square> moves = new ArrayList<Square>();
        //capture check white
        //NOT READY NEEDS FIXING SERPARATE THE IF STATEMENTS
        if(((b.getSquareArray()[start.getRow()-1][start.getCol()-1]).isOccupied()||(b.getSquareArray()[start.getRow()-1][start.getCol()+1]).isOccupied())
        &&
        (b.getSquareArray()[start.getRow()-1][start.getCol()-1].getColor()!=true)||b.getSquareArray()[start.getRow()-1][start.getCol()+1].getColor()!=true)
        {
            moves.add(b.getSquareArray()[start.getRow()-1][start.getCol()-1]);
            moves.add(b.getSquareArray()[start.getRow()-1][start.getCol()+1]);
        }
        
        // white moveset
        if (start.getColor()) {
            if (start.getRow() == 6 && ((b.getSquareArray()[start.getRow() - 2][start.getCol()]).isOccupied() != true)) {
                moves.add(b.getSquareArray()[start.getRow() - 2][start.getCol()]);
            }
            if (start.getRow()-1 >= 0 && (((b.getSquareArray()[start.getRow()-1][start.getCol()]).isOccupied() != true))) {
                moves.add(b.getSquareArray()[start.getRow() - 1][start.getCol()]);
            }

        }
        return moves;
    }
}