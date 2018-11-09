package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MiscTest {

    @Test
    public void testShipPieceConstructor() {
        ShipPiece sp1 = new ShipPiece();
        ShipPiece sp2 = new ShipPiece(1, 'A');

        assertTrue(sp1.getColumn() == sp2.getColumn());
        assertTrue(sp1.getRow() == sp2.getRow());
    }
}
