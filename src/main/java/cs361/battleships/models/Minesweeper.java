package cs361.battleships.models;

import java.util.ArrayList;

public class Minesweeper extends Ship{
    public Minesweeper() {
        this.occupiedSquares = new ArrayList<>();
        this.capacity = 2;
    }

    @Override
    public Ship cloneType(){
        return new Minesweeper();
    }

    @Override
    public void placeShip(int x, char y, boolean isVertical){
        this.occupiedSquares.clear();
        if(!isVertical) {
            this.occupiedSquares.add(new ShipPiece(x, y,             1, true));
            this.occupiedSquares.add(new ShipPiece(x, (char)(y + 1), 1, false));
        }
        else{
            this.occupiedSquares.add(new ShipPiece(x,           y, 1, true));
            this.occupiedSquares.add(new ShipPiece(x + 1,  y, 1, false));
        }
    }
}
