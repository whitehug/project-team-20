package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {

    @Test
    public void TestSonarMiss() {
        Game game = new Game();
        assertFalse(game.sonar(3, 'B'));
    }
    @Test
    public void TestBadInputPlaceShip() {

        Game game = new Game();
        assertFalse(game.placeShip(new Battleship(), 0, 'Z', true));
    }
    @Test
    public void TestGoodInputPlaceShip() {

        Game game = new Game();
        for (int i = 1; i < 11; i++) assertTrue(game.placeShip(new Battleship(), i, 'F', false));

    }
    @Test
    public void TestBadInputAttack() {

        Game game = new Game();
        assertFalse(game.attack(99, '#'));
    }
    @Test
    public void TestGoodInputAttack() {
        Game game = new Game();
        for(int i=1; i<11; i++) assertTrue(game.attack( i,'D'));

    }
}