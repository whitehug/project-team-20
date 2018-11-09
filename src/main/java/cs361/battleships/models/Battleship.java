package cs361.battleships.models;

import java.util.ArrayList;

public class Battleship extends Ship{
    public Battleship() {
        this.occupiedSquares = new ArrayList<>();
        this.capacity = 4;
    }

    @Override
    public Ship cloneType(){
        return new Battleship();
    }

    @Override
    public void placeShip(int x, char y, boolean isVertical) {
        this.occupiedSquares.clear();
        if (!isVertical) {
            this.occupiedSquares.add(new ShipPiece(x, y, 1, false));
            this.occupiedSquares.add(new ShipPiece(x, (char) (y + 1), 1, false));
            this.occupiedSquares.add(new ShipPiece(x, (char) (y + 2), 2, true));
            this.occupiedSquares.add(new ShipPiece(x, (char) (y + 3), 1, false));
        } else {
            this.occupiedSquares.add(new ShipPiece(x, y, 1, false));
            this.occupiedSquares.add(new ShipPiece(x + 1, y, 1, false));
            this.occupiedSquares.add(new ShipPiece(x + 2, y, 2, true));
            this.occupiedSquares.add(new ShipPiece(x + 3, y, 1, false));
        }
    }
}
