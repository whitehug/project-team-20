package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

import java.util.List;

/* cant be abstract for some reason */
public class Ship {

	@JsonProperty protected List<ShipPiece> occupiedSquares;
	@JsonProperty protected int capacity;

	public Ship() {
		this.occupiedSquares = new ArrayList<>();
		this.capacity = 0;
	}

	public Ship cloneType(){
		return new Ship();
	}

	public List<ShipPiece> getOccupiedSquares() {
		return this.occupiedSquares;
	}

	public void placeShip(int x, char y, boolean isVertical){};

	public int getCapacity(){
		return this.capacity;
	}

}
