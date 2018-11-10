package cs361.battleships.models;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class BoardTest {

    @Test
    public void testRevealSonar() {
        Board board = new Board();
        board.placeShip(new Minesweeper(), 5, 'D', true);
        assertTrue(board.sonar(5, 'D'));
    }

    @Test
    public void testMissSonar() {
        Board board = new Board();
        board.placeShip(new Battleship(), 5, 'D', true);
        board.placeShip(new Minesweeper(), 5, 'E', true);
        board.placeShip(new Battleship(), 5, 'F', true);
        board.placeShip(new Minesweeper(), 8, 'E', true);
        assertFalse(board.sonar(7, 'E'));
    }
    @Test
    public void testInvalidPlacement() {
        Board board = new Board();
        assertFalse(board.placeShip(new Destroyer(), 11, 'C', true));
    }

    @Test
    public void testValidMinesweeperPlacement() {
        Board board = new Board();
        assertTrue(board.placeShip(new Minesweeper(), 1, 'A', true));
        assertTrue(board.placeShip(new Minesweeper(), 2, 'D', false));
    }

    @Test
    public void testValidDestroyerPlacement() {
        Board board = new Board();
        assertTrue(board.placeShip(new Destroyer(), 1, 'A', true));
        assertTrue(board.placeShip(new Minesweeper(), 2, 'D', false));
    }

    @Test
    public void testValidMBattleshipPlacement() {
        Board board = new Board();
        assertTrue(board.placeShip(new Battleship(), 1, 'A', true));
        assertTrue(board.placeShip(new Minesweeper(), 2, 'D', false));
    }

    @Test
    public void testValidPlacement() {

        //for loops based on board dimensions
        for(int i = 1; i < 10; i++) {
            for(int j = 0; j < 9; j++) {
                Board board1 = new Board();
                assertTrue(board1.placeShip(new Minesweeper(), i, (char)(j + 'A'), false));

                Board board2 = new Board();
                assertTrue(board2.placeShip(new Minesweeper(), i, (char)(j + 'A'), true));
            }
        }

    }

    @Test
    public void testOverlap(){
        Board board = new Board();
        for(int i = 1; i <= board.getDimensions().x; i++) {
            for(int j = 0; j < board.getDimensions().y; j+=2) {
                assertTrue(board.placeShip(new Minesweeper(), i, (char)(j + 'A'), false));
            }
        }

        for(int i = 1; i <= 10; i+=2) {
            for(int j = 0; j < 10; j++) {
                assertFalse(board.placeShip(new Minesweeper(), i, (char)(j + 'A'), true));
            }
        }
    }

    @Test
    public void testContainesSquare() {
        Board board = new Board();
        board.placeShip(new Minesweeper(), 7, 'C', false);
        Square square = new Square(7, 'C');
        assertTrue(board.containsSquare(square));
    }

    @Test
    public void testAttackMiss(){
        Board board = new Board();
        board.placeShip(new Minesweeper(), 7, 'C', false);
        for(int i = 1; i <= board.getDimensions().x; i++){
            for(int j = 0; j < board.getDimensions().y; j++){
                Result r = board.attack(i, (char)(j + 'A'));
                if(j != 3 && i != 7 && i != 8){
                    assertTrue(r.getResult() == AtackStatus.MISS);
                }
            }
        }
    }

    @Test
    public void testAttackHitSunkSurrenderSmall(){
        Board board = new Board();
        board.placeShip(new Minesweeper(), 7, 'C', false);
        board.placeShip(new Minesweeper(), 6, 'C', false);
        assertTrue(AtackStatus.SUNK == board.attack(7, 'C').getResult());
        assertTrue(AtackStatus.HIT == board.attack(7, 'D').getResult());
        assertTrue(AtackStatus.SURRENDER == board.attack(6, 'C').getResult());
    }

    @Test
    public void testAttackInvalid(){
        Board board = new Board();
        assertTrue(AtackStatus.INVALID == board.attack(11, 'C').getResult());
        assertTrue(AtackStatus.INVALID == board.attack(9, 'K').getResult());
    }

    @Test
    public void testAttackHitSunkSurrenderBig(){
        //fill board with battleships
        Board board = new Board();
        for(int i = 1; i <= board.getDimensions().x; i++) {
            for(int j = 0; j < board.getDimensions().y - 4 + 1; j+=4) {
                assertTrue(board.placeShip(new Battleship(), i, (char)(j + 'A'), false));
            }
        }


        //test every square
        for(int i = 1; i <= board.getDimensions().x; i++){
            for(int j = 0; j < 8; j++) {
                assertTrue(AtackStatus.HIT == board.attack(i, (char)('A' + j)).getResult());
            }
        }
        for(int i = 1; i <= board.getDimensions().x; i++) {
            for (int j = 8; j < 10; j++) {
                assertTrue(AtackStatus.MISS == board.attack(i, (char) ('A' + j)).getResult());
            }
        }

    }

    @Test
    public void testGetShips(){
        Board b = new Board();
        List<Ship> ships = new ArrayList<>();
        ships.add(new Destroyer());
        ships.add(new Battleship());
        b.setShips(ships);
        assertTrue(ships == b.getShips());
    }

    @Test
    public void testGetAttack(){
        Board b = new Board();
        List<Result> attacks = new ArrayList<>();
        attacks.add(new Result());
        attacks.add(new Result());
        b.setAttacks(attacks);
        assertTrue(attacks == b.getAttacks());
    }


}