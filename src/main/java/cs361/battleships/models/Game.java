package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import static cs361.battleships.models.AtackStatus.*;

public class Game {

    @JsonProperty private Board playersBoard = new Board();
    @JsonProperty private Board opponentsBoard = new Board();

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean sonar(int x, char y) {
        return opponentsBoard.sonar(x, y);
    }

    public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical);
        if (!successful) {
            return false;
        }

        boolean opponentPlacedSuccessfully;
        Ship opponant = ship.cloneType();
        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            opponentPlacedSuccessfully = opponentsBoard.placeShip(opponant, randRow(), randCol(), randVertical());
        } while (!opponentPlacedSuccessfully);

        return true;
    }
    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean attack(int x, char  y) {
        Result playerAttack = opponentsBoard.attack(x, y);
        if (playerAttack.getResult() == INVALID) {
            return false;
        }

        Result opponentAttackResult;
        do {
            // AI does random attacks, so it might attack the same spot twice
            // let it try until it gets it right
            opponentAttackResult = playersBoard.attack(randRow(), randCol());
        } while(opponentAttackResult.getResult() == INVALID);

        return true;
    }

    private char randCol() {
        int random_col = (int)(Math.random()*10 + 65);
        return (char) random_col;
    }

    private int randRow() {
        int random_row = (int)(Math.random()*10 + 1);
        return random_row;
    }

    private boolean randVertical() {
        int rand_num = (int)(Math.random()*2);
        return rand_num == 1;
    }
}
