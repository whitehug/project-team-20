package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	@JsonProperty private int capacity;

	public Ship() {
		this.occupiedSquares = new ArrayList<>();
		this.capacity = 0;
	}

	public Ship(String kind) {
		kind = kind.toUpperCase();
		if(kind.equals("BATTLESHIP")){

			this.occupiedSquares = new ArrayList<>(4);
			this.capacity = 4;
		}
		else if(kind.equals("DESTROYER")) {

			this.occupiedSquares = new ArrayList<>(3);
			this.capacity = 3;
		}
		else if(kind.equals("MINESWEEPER")) {

			this.occupiedSquares = new ArrayList<>(2);
			this.capacity = 2;
		}
		else {

			this.occupiedSquares = new ArrayList<>();
			this.capacity = 0;
		}
	}

	public List<Square> getOccupiedSquares() {
		return this.occupiedSquares;
	}

	public void setOccupiedSquares(List<Square> occupied) {
		this.occupiedSquares = occupied;
		this.capacity = this.occupiedSquares.size();
	}

	public void addOccupiedSquares(Square s) {
		this.occupiedSquares.add(s);
		if(this.occupiedSquares.size() > this.capacity){
			this.capacity = this.occupiedSquares.size();
		}
	}

	public int getCapacity(){
		return this.capacity;
	}
}
