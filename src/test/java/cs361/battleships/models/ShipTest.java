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
        assertTrue(ship.getCapacity() == 2);
    }

    @Test
    public void testDestroyerCreation() {
        Ship ship = new Destroyer();
        assertTrue(ship.getCapacity() == 3);
    }

    @Test
    public void testBattleshipCreation() {
        Ship ship = new Battleship();
        assertTrue(ship.getCapacity() == 4);
    }

    @Test
    public void testEmptyShipCreation() {
        Ship ship = new Ship();
        assertTrue(ship.getCapacity() == 0);
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
    }
}
