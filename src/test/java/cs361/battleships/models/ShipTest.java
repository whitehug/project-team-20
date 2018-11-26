package cs361.battleships.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShipTest {

    private List<Square> occupiedSquares;

    @Test
    public void testMinesweeperCreation() {
        Ship ship = new Minesweeper();
        ship.placeShip(0,'A', false);
        assertTrue(ship.getOccupiedSquares().size() == 2);
    }

    @Test
    public void testDestroyerCreation() {
        Ship ship = new Destroyer();
        ship.placeShip(0,'A', false);
        assertTrue(ship.getOccupiedSquares().size() == 3);
    }

    @Test
    public void testBattleshipCreation() {
        Ship ship = new Battleship();
        ship.placeShip(0,'A', false);
        assertTrue(ship.getOccupiedSquares().size() == 4);
    }

    @Test
    public void testSubmarineCreation() {
        Ship ship = new Submarine();
        ship.placeShip(0,'A', false);
        assertTrue(ship.getOccupiedSquares().size() == 5);
    }

    @Test
    public void testEmptyShipCreation() {
        Ship ship = new Ship();
        ship.placeShip(0,'A', false);
        assertTrue(ship.getOccupiedSquares().size() == 0);
    }

    @Test
    public void testCloneType() {
        Ship ship = new Ship();
        assertTrue(ship.cloneType() instanceof Ship);
        ship = new Minesweeper();
        assertTrue(ship.cloneType() instanceof Minesweeper);
        ship = new Destroyer();
        assertTrue(ship.cloneType() instanceof Destroyer);
        ship = new Battleship();
        assertTrue(ship.cloneType() instanceof Battleship);
        ship = new Submarine();
        assertTrue(ship.cloneType() instanceof Submarine);
    }
}
