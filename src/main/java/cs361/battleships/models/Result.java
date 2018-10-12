package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Result {
	@JsonProperty private AtackStatus attackStatus;
	@JsonProperty private Ship ship;
	@JsonProperty private Square square;


	public AtackStatus getResult() {
		return this.attackStatus;
	}

	public void setResult(AtackStatus result) {
		this.attackStatus = result;
	}

	public Ship getShip() {
		return this.ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public Square getLocation() {
		return this.square;
	}

	public void setLocation(Square square) {
		this.square = square;
	}
}
