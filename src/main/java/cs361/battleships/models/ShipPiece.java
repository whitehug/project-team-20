package cs361.battleships.models;
import com.fasterxml.jackson.annotation.JsonProperty;
public class ShipPiece extends Square {
    @JsonProperty protected int health;
    @JsonProperty protected boolean captainsQuarters;
    @JsonProperty protected boolean underwater;

    public ShipPiece(){
        super();
        this.health = 1;
        this.captainsQuarters = false;
        this.underwater = false;
    }

    public ShipPiece(int row, char column) {
        super(row, column);
        this.health = 1;
        this.captainsQuarters = false;
        this.underwater = false;
    }

    public ShipPiece(int row, char column, int health, boolean captainsQuarters, boolean underwater) {
        super(row, column);
        this.health = health;
        this.captainsQuarters = captainsQuarters;
        this.underwater = underwater;
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

    boolean getCaptainsQuarters(){
        return this.captainsQuarters;
    }

    boolean getUnderwater(){
        return this.underwater;
    }
}
