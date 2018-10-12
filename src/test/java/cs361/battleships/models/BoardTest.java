package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class BoardTest {

    @Test
    public void testInvalidPlacement() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 11, 'C', true));
    }

    @Test
    public void testValidPlacement() {
        //for loops based on board dimensions
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                Board board1 = new Board();
                assertTrue(board1.placeShip(new Ship("MINESWEEPER"), i, (char)(j + 'A'), true));

                Board board2 = new Board();
                assertTrue(board2.placeShip(new Ship("MINESWEEPER"), j, (char)(i + 'A'), false));
            }
        }

    }

    @Test
    public void testOverlap(){
        Board board = new Board();
        for(int i = 0; i < board.getDimensions().x; i++) {
            for(int j = 0; j < board.getDimensions().y; j+=2) {
                assertTrue(board.placeShip(new Ship("MINESWEEPER"), i, (char)(j + 'A'), true));
            }
        }

        for(int i = 0; i < 10; i+=2) {
            for(int j = 0; j < 10; j++) {
                assertFalse(board.placeShip(new Ship("MINESWEEPER"), i, (char)(j + 'A'), false));
            }
        }
    }

    @Test
    public void testContainesSquare() {
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 7, 'C', true);
        Square square = new Square(7, 'C');
        assertTrue(board.containsSquare(square));
    }

    @Test
    public void testAttackMiss(){
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 7, 'C', true);
        for(int i = 0; i < board.getDimensions().x; i++){
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
        board.placeShip(new Ship("MINESWEEPER"), 7, 'C', true);
        board.placeShip(new Ship("MINESWEEPER"), 6, 'C', true);
        assertTrue(AtackStatus.HIT == board.attack(7, 'C').getResult());
        assertTrue(AtackStatus.SUNK == board.attack(7, 'D').getResult());
        assertTrue(AtackStatus.HIT == board.attack(6, 'C').getResult());
        assertTrue(AtackStatus.SURRENDER == board.attack(6, 'D').getResult());
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
        for(int i = 0; i < board.getDimensions().x; i++) {
            for(int j = 0; j < board.getDimensions().y - 4 + 1; j+=4) {
                assertTrue(board.placeShip(new Ship("BATTLESHIP"), i, (char)(j + 'A'), true));
            }
        }


        //test every square
        for(int i = 0; i < board.getDimensions().x; i++){
            for(int j = 0; j < 3; j++) {
                assertTrue(AtackStatus.HIT == board.attack(i, (char)('A' + j)).getResult());
            }
        }
        for(int i = 0; i < board.getDimensions().x; i++){
                assertTrue(AtackStatus.SUNK == board.attack(i, (char)('A' + 3)).getResult());
        }
        for(int i = 0; i < board.getDimensions().x; i++){
            for(int j = 4; j < 7; j++) {
                assertTrue(AtackStatus.HIT == board.attack(i, (char)('A' + j)).getResult());
            }
        }
        for(int i = 0; i < board.getDimensions().x - 1; i++){
            assertTrue(AtackStatus.SUNK == board.attack(i, (char)('A' + 7)).getResult());
        }
        assertTrue(AtackStatus.SURRENDER == board.attack(9, (char)('A' + 7)).getResult());
        for(int i = 0; i < board.getDimensions().x; i++) {
            for (int j = 8; j < 10; j++) {
                assertTrue(AtackStatus.MISS == board.attack(i, (char) ('A' + j)).getResult());
            }
        }
    }
}
