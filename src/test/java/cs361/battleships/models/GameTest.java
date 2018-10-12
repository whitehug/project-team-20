package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {

    @Test
    public void TestBadInputPlaceShip() {

        Game game = new Game();
        assertFalse(game.placeShip(new Ship("BATTLESHIP"), 0, 'Z', true));
    }
    @Test
	public void TestGoodInputPlaceShip() {

        Game game = new Game();
        assertTrue(game.placeShip(new Ship("BATTLESHIP"), 1, 'A', false));
    }
	@Test
    public void TestBadInputAttack() {

        Game game = new Game();
        assertFalse(game.attack(99, '#'));
    }
	@Test
    public void TestGoodInputAttack() {
        Game game = new Game();
        assertTrue(game.attack( 7,'D'));
    }
}
