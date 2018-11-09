package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Board {

	@JsonProperty private List<Ship>  ships;
	@JsonProperty private List<Result>  attacks;
	@JsonProperty private Point dimensions;
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		this.dimensions = new Point(10, 10);
		this.ships = new ArrayList<>();
		this.attacks = new ArrayList<>();
	}

	public boolean containsSquare(Square s){
		for(Ship allships : this.ships){
			for(Square allsquares : allships.getOccupiedSquares()){
				if(Character.toLowerCase(allsquares.getColumn()) == Character.toLowerCase(s.getColumn()) && allsquares.getRow() == s.getRow()){
					return true;
				}
			}
		}
		return false;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		int iny = y - 'a';
		if(y < 'a'){
			iny = y - 'A';
		}

		int shipLength = ship.getCapacity();

		//within bounds
		if(!isVertical) {
			if (x > 0 && x <= this.dimensions.x && iny >= 0 && iny < this.dimensions.y - shipLength + 1) {
				for(int i = 0; i < shipLength; i++){
					//add squares to ship
					Square s = new Square(x, (char)(iny + 'A' + i));
					//ensure no boat
					if(this.containsSquare(s)) {
						return false;
					}
				}
				ship.placeShip(x, y, isVertical);
				ships.add(ship);
				return true;
			}
		}
		else{
			if (x > 0 && x <= this.dimensions.x - shipLength + 1 && iny >= 0 && iny < this.dimensions.y) {
				List<Square> sqAdd = new ArrayList<>();
				for(int i = 0; i < shipLength; i++){
					//add squares to ship
					Square s = new Square(x+i, (char)(iny + 'A'));
					//ensure no boat
					if(this.containsSquare(s)) {
						return false;
					}

				}
				ship.placeShip(x, y, isVertical);
				ships.add(ship);
				return true;
			}
		}

		return false;
	}


	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		Result r = new Result();
		r.setLocation(new Square(x, y));

		//check within bounds
		int iny = y - 'a';
		if(y < 'a'){
			iny = y - 'A';
		}
		if (!(x > 0 && x <= this.dimensions.x && iny >= 0 && iny < this.dimensions.y)) {
			r.setResult(AtackStatus.INVALID);
			this.attacks.add(r);
			return r;
		}

		for(Ship s : this.ships){
			for(ShipPiece sp : s.getOccupiedSquares()){
				if(sp.getRow() == x && sp.getColumn() == y){
					if(sp.damage() && sp.captainsQuarters){
						for(Ship s2 : this.ships){
						    //check for if all ships are sunk
							boolean sunk = false;
							for(ShipPiece sp2 : s2.getOccupiedSquares()) {
								if(sp2.getSunk()){
									sunk = true;
								}
							}
							if(!sunk){
								r.setResult(AtackStatus.SUNK);
								r.setShip(s);
								this.attacks.add(r);
								return r;
							}
						}
						//if all sunk surrender
						r.setResult(AtackStatus.SURRENDER);
						r.setShip(s);
						this.attacks.add(r);
						return r;
					}
					else{
					    //just a normal hit
						r.setResult(AtackStatus.HIT);
						r.setShip(s);
						this.attacks.add(r);
						return r;
					}
				}
			}
		}

		//default cause
		r.setResult(AtackStatus.MISS);
		this.attacks.add(r);

		return r;
	}

	public List<Ship> getShips() {
		return this.ships;
	}

	public void setShips(List<Ship> ships) {
		this.ships = ships;
	}

	public List<Result> getAttacks() {
		return this.attacks;
	}

	public void setAttacks(List<Result> attacks) {
		this.attacks = attacks;
	}

	public Point getDimensions(){
		return this.dimensions;
	}

}
