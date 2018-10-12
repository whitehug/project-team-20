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
        Ship ship = new Ship("Minesweeper");
        assertTrue(ship.getCapacity() == 2);
    }

    @Test
    public void testDestroyerCreation() {
        Ship ship = new Ship("DESTROYER");
        assertTrue(ship.getCapacity() == 3);
    }

    @Test
    public void testBattleshipCreation() {
        Ship ship = new Ship("BATTLESHIP");
        assertTrue(ship.getCapacity() == 4);
    }

    @Test
    public void testAddSquares(){
        Ship ship = new Ship("BATTLESHIP");
        for(int i = 0; i < 4; i++) {
            Square square = new Square(i, (char)(i + 'A'));
            ship.addOccupiedSquares(square);
            assertTrue(ship.getOccupiedSquares().contains(square));
            assertTrue(ship.getCapacity() == 4);
        }


        Square square = new Square(7, (char)('F'));
        ship.addOccupiedSquares(square);
        assertTrue(ship.getOccupiedSquares().contains(square));
        assertTrue(ship.getCapacity() == 5);
    }

    @Test
    public void testSetSquares(){
        Ship ship = new Ship("BATTLESHIP");
        List<Square> toadd = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            Square square = new Square(i, (char)(i + 'A'));
            toadd.add(square);
        }
        ship.setOccupiedSquares(toadd);
        assertTrue(toadd.equals(ship.getOccupiedSquares()));
        assertTrue(ship.getCapacity() == 4);
    }

    @Test
    public void testAddSquares(){
        Ship ship = new Ship("BATTLESHIP");
        for(int i = 0; i < 4; i++) {
            Square square = new Square(i, (char)(i + 'a'));
            ship.addOccupiedSquares(square);
            assertTrue(ship.getOccupiedSquares().contains(square));
        }
        assertTrue(ship.getOccupiedSquares().size() == 4);
    }

    @Test
    public void testSetSquares(){
        Ship ship = new Ship("BATTLESHIP");
        List<Square> toadd = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            Square square = new Square(i, (char)(i + 'a'));
            toadd.add(square);
        }
        ship.setOccupiedSquares(toadd);
        assertTrue(toadd.equals(ship.getOccupiedSquares()));
        assertTrue(ship.getOccupiedSquares().size() == 4);
    }
}
