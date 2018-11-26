package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Board {

	@JsonProperty private List<Ship>  ships;
	@JsonProperty private List<Result>  attacks;
	@JsonProperty private boolean sonarList;
	@JsonProperty private Point dimensions;
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		this.dimensions = new Point(10, 10);
		this.ships = new ArrayList<>();
		this.sonarList = false;
		this.attacks = new ArrayList<>();
	}

	public boolean sonar(int x, char y) {
		this.sonarList = false;
		for (Ship ship : this.ships) {
			for (Square square : ship.getOccupiedSquares()) {
				if (square.getRow() == x && square.getColumn() == y) {
					this.sonarList = true;
				}
			}
		}
		return this.sonarList;
	}

	public boolean containsSquare(Square s, boolean underwater){
		for(Ship allships : this.ships){
			for(ShipPiece allsquares : allships.getOccupiedSquares()){
				if(
						Character.toLowerCase(allsquares.getColumn()) == Character.toLowerCase(s.getColumn()) &&
						allsquares.getRow() == s.getRow() &&
						allsquares.getUnderwater() == underwater
				){
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
		ship.placeShip(x, y, isVertical);
		for(ShipPiece sp : ship.getOccupiedSquares()){
			if(this.containsSquare(sp, sp.getUnderwater())) {
                ship.clearSquare();
                return false;
            }
            if(sp.getColumn() - 'A' < 0 || sp.getColumn() - 'A' >= this.dimensions.x){
                System.out.println("failed to place boat");
                ship.clearSquare();
                return false;
            }
			if(sp.getRow() - 1 < 0 || sp.getRow() - 1 >= this.dimensions.y) {
                System.out.println("failed to place boat");
                ship.clearSquare();
                return false;
            }
		}
		ships.add(ship);
		return true;
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
				//make sure its a surface ship
				if(sp.getRow() == x && sp.getColumn() == y && sp.getUnderwater() == false){
					if(sp.damage() && sp.captainsQuarters){
						for(Ship s2 : this.ships){
						    //check for if all ships are sunk
							boolean sunk = false;
							for(ShipPiece sp2 : s2.getOccupiedSquares()) {
								if(sp2.getSunk() && sp2.captainsQuarters){
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
