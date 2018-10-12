package cs361.battleships.models;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShipTest {

    private List<Square> occupiedSquares;

    @Test
    public void testMinesweeperCreation() {
        Ship ship = new Ship("Minesweeper");
        Square square = new Square(1, 'a');
        occupiedSquares = ship.getOccupiedSquares();
        occupiedSquares.add(new Square(1, 'a'));
        Square square2 = occupiedSquares.get(0);
        occupiedSquares.add(new Square(1, 'b'));
        assertTrue(square.getColumn()==square2.getColumn() || square.getRow()==square2.getRow());
        square.setColumn('b');
        square2 = occupiedSquares.get(1);
        assertTrue(square.getColumn()==square2.getColumn() || square.getRow()==square2.getRow());
    }

    @Test
    public void testDestroyerCreation() {
        Ship ship = new Ship("DESTROYER");
        Square square = new Square(1, 'a');
        occupiedSquares = ship.getOccupiedSquares();
        occupiedSquares.add(new Square(1, 'a'));
        Square square2 = occupiedSquares.get(0);
        occupiedSquares.add(new Square(1, 'b'));
        occupiedSquares.add(new Square(1, 'c'));
        assertTrue(square.getColumn()==square2.getColumn() || square.getRow()==square2.getRow());
        square.setColumn('b');
        square2 = occupiedSquares.get(1);
        assertTrue(square.getColumn()==square2.getColumn() || square.getRow()==square2.getRow());
        square.setColumn('c');
        square2 = occupiedSquares.get(2);
        assertTrue(square.getColumn()==square2.getColumn() || square.getRow()==square2.getRow());
    }

    @Test
    public void testBattleshipCreation() {
        Ship ship = new Ship("battleship");
        Square square = new Square(1, 'a');
        occupiedSquares = ship.getOccupiedSquares();
        occupiedSquares.add(new Square(1, 'a'));
        Square square2 = occupiedSquares.get(0);
        occupiedSquares.add(new Square(1, 'b'));
        occupiedSquares.add(new Square(1, 'c'));
        occupiedSquares.add(new Square(1, 'd'));
        assertTrue(square.getColumn()==square2.getColumn() || square.getRow()==square2.getRow());
        square.setColumn('b');
        square2 = occupiedSquares.get(1);
        assertTrue(square.getColumn()==square2.getColumn() || square.getRow()==square2.getRow());
        square.setColumn('c');
        square2 = occupiedSquares.get(2);
        assertTrue(square.getColumn()==square2.getColumn() || square.getRow()==square2.getRow());
        square.setColumn('d');
        square2 = occupiedSquares.get(3);
        assertTrue(square.getColumn()==square2.getColumn() || square.getRow()==square2.getRow());
    }
}
