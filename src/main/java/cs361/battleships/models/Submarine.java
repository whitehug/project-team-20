package cs361.battleships.models;

import java.util.ArrayList;

public class Submarine extends Ship{
    public Submarine() {
        this.occupiedSquares = new ArrayList<>();
    }

    @Override
    public Ship cloneType(){
        return new Submarine();
    }

    @Override
    public void placeShip(int x, char y, boolean isVertical){
        this.occupiedSquares.clear();
        if (!isVertical) {
            this.occupiedSquares.add(new ShipPiece(x, y, 1, false, true));
            this.occupiedSquares.add(new ShipPiece(x, (char) (y + 1), 1, false, true));
            this.occupiedSquares.add(new ShipPiece(x, (char) (y + 2), 1, false, true));
            this.occupiedSquares.add(new ShipPiece(x + 1, (char) (y + 2), 1, false, true));
            this.occupiedSquares.add(new ShipPiece(x, (char) (y + 3), 2, true, true));
        } else {
            this.occupiedSquares.add(new ShipPiece(x, y, 1, false, true));
            this.occupiedSquares.add(new ShipPiece(x + 1, y, 1, false, true));
            this.occupiedSquares.add(new ShipPiece(x + 2, y, 1, false, true));
            this.occupiedSquares.add(new ShipPiece(x + 2, (char) (y + 1), 1, false, true));
            this.occupiedSquares.add(new ShipPiece(x + 3, y, 2, true, true));
        }
    }
}
