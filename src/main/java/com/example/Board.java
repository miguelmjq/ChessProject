package com.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

//You will be implmenting a part of a function and a whole function in this document.
// Please follow the directions for the 
//suggested order of completion that should make testing easier.
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
    // Resource location constants for piece images
    private static final String path = "/src/main/java/com/example/Pictures/";
    @SuppressWarnings("unused")
    private static final String RESOURCES_WBISHOP_PNG = path + "wbishop.png";
    @SuppressWarnings("unused")
    private static final String RESOURCES_BBISHOP_PNG = path + "bbishop.png";
    @SuppressWarnings("unused")
    private static final String RESOURCES_WKNIGHT_PNG = path + "wknight.png";
    @SuppressWarnings("unused")
    private static final String RESOURCES_BKNIGHT_PNG = path + "bknight.png";
    @SuppressWarnings("unused")
    private static final String RESOURCES_WROOK_PNG = path + "wrook.png";
    @SuppressWarnings("unused")
    private static final String RESOURCES_BROOK_PNG = path + "brook.png";
    @SuppressWarnings("unused")
    private static final String RESOURCES_WKING_PNG = path + "wking.png";
    @SuppressWarnings("unused")
    private static final String RESOURCES_BKING_PNG = path + "bking.png";
    @SuppressWarnings("unused")
    private static final String RESOURCES_BQUEEN_PNG = path + "bqueen.png";
    @SuppressWarnings("unused")
    private static final String RESOURCES_WQUEEN_PNG = path + "wqueen.png";
    private static final String RESOURCES_WPAWN_PNG = path + "wpawn.png";
    private static final String RESOURCES_BPAWN_PNG = path + "bpawn.png";

    // Logical and graphical representations of board
    private final Square[][] board;
    @SuppressWarnings("unused")
    private final GameWindow g;

    // contains true if it's white's turn.
    private boolean whiteTurn;

    // if the player is currently dragging a piece this variable contains it.
    Piece currPiece;
    private Square fromMoveSquare;

    // used to keep track of the x/y coordinates of the mouse.
    private int currX;
    private int currY;

    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        // TO BE IMPLEMENTED FIRST
        // for (.....)
        // populate the board with squares here. Note that the board is composed of 64
        // squares alternating from white to black.
        boolean whiteSquare = true;
        for (int row = 0; row < 8; row++) {
            int column = 0;
            for (column = 0; column < 8; column++) {
                board[row][column] = new Square(this, whiteSquare, row, column);
                this.add(board[row][column]);
                whiteSquare = !whiteSquare;
            }
            whiteSquare = !whiteSquare;
        }
        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;

    }

    // set up the board such that the black pieces are on one side and the white
    // pieces are on the other.
    // since we only have one kind of piece for now you need only set the same
    // number of pieces on either side.
    // it's up to you how you wish to arrange your pieces.
    void initializePieces() {
        board[7][4].put(new Piece(true, RESOURCES_WKING_PNG));
        board[0][4].put(new Piece(true, RESOURCES_BKING_PNG));
        for (int i = 0; i < 8; i++) {
            board[6][i].put(new Piece(true, RESOURCES_WPAWN_PNG));
            board[1][i].put(new Piece(false, RESOURCES_BPAWN_PNG));
        }

    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

    @Override
    public void paintComponent(Graphics g) {
        @SuppressWarnings("unused")
        Image backgroundImage = null;
        URL imageUrl = null;
        if (currPiece != null) {
            imageUrl = getClass().getResource("/src/main/java/com/example/" + currPiece.getImage());
        }

        if (imageUrl != null) {
            // This is the cleanest way to get an AWT Image object from a URL
            backgroundImage = Toolkit.getDefaultToolkit().createImage(imageUrl);
        } 

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[x][y];
                if (sq == fromMoveSquare)
                    sq.setBorder(BorderFactory.createLineBorder(Color.blue));
                sq.paintComponent(g);
               // System.out.println("Painting square at " + x + ", " + y);

            }
        }
        if (currPiece != null) {
            if ((currPiece.getColor() && whiteTurn)
                    || (!currPiece.getColor() && !whiteTurn)) {
                final Image img = currPiece.getImage();
                g.drawImage(img, currX, currY, null);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied() && sq.getOccupyingPiece().getColor() == whiteTurn) {
            currPiece = sq.getOccupyingPiece();
            fromMoveSquare = sq;
            sq.setDisplay(false);
        }
        repaint();
    }

    // TO BE IMPLEMENTED!
    // should move the piece to the desired location only if this is a legal move.
    // use the pieces "legal move" function to determine if this move is legal, then
    // complete it by
    // moving the new piece to it's new board location.
    @Override
    public void mouseReleased(MouseEvent e) {
        @SuppressWarnings("unused")
        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        
        // using currPiece
        if(fromMoveSquare!= null){
            if (currPiece!=null && currPiece.getLegalMoves(this, fromMoveSquare).contains(endSquare))
                endSquare.put(currPiece);
                fromMoveSquare.removePiece();
                fromMoveSquare.setDisplay(true);
        }
        currPiece = null;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}