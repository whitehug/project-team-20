package cs361.battleships.models;

public class ShipPiece extends Square {
    protected int health;
    protected boolean captainsQuarters;

    public ShipPiece(){
        super();
        health = 1;
        captainsQuarters = false;
    }

    public ShipPiece(int row, char column) {
        super(row, column);
        health = 1;
        captainsQuarters = false;
    }

    public ShipPiece(int row, char column, int health, boolean captainsQuarters) {
        super(row, column);
        this.health = health;
        this.captainsQuarters = captainsQuarters;
    }

    boolean damage(){
        this.health--;
        return getSunk();
    }

    boolean getSunk(){
        if(health <= 0){
            return true;
        }
        return false;
    }
}
