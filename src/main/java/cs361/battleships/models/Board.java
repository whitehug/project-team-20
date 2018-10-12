package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		int iny;
		if(y < 'a'){
			iny = y - 'A';
		}
		else{
			iny = y - 'a';
		}

		int shipLength = ship.getCapacity();

		//within bounds
		if(!isVertical) {
			if (x > 0 && x <= this.dimensions.x && iny >= 0 && iny < this.dimensions.y - shipLength + 1) {
				List<Square> sqAdd = new ArrayList<>();
				for(int i = 0; i < shipLength; i++){
					//add squares to ship
					Square s = new Square(x, (char)(iny + 'A' + i));
					//ensure no boat
					if(this.containsSquare(s)) {
						return false;
					}
					sqAdd.add(s);

				}
				for(Square adding : sqAdd){
					ship.addOccupiedSquares(adding);
				}
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
					sqAdd.add(s);

				}
				for(Square adding : sqAdd){
					ship.addOccupiedSquares(adding);
				}
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
		Square sq = new Square(x, y);
		r.setLocation(sq);

		//check within bounds
		int iny;
		if(y < 'a'){
			iny = y - 'A';
		}
		else{
			iny = y - 'a';
		}
		if (!(x > 0 && x <= this.dimensions.x && iny >= 0 && iny < this.dimensions.y)) {
			r.setResult(AtackStatus.INVALID);
			this.attacks.add(r);
			return r;
		}
		for(Result atks : this.attacks){
			if(atks.getLocation().getRow() == x && atks.getLocation().getColumn() == y){
				r.setResult(AtackStatus.INVALID);
				this.attacks.add(r);
				return r;
			}
		}

		//default cause
		r.setResult(AtackStatus.MISS);
		this.attacks.add(r);

		//set hit
		Ship s = null;
		for (Ship ship : this.ships) {
			for(Square square : ship.getOccupiedSquares()){
				if(square.getRow() == x && square.getColumn() == y){
					r.setResult(AtackStatus.HIT);
					s = ship;
				}
			}
		}

		//check if boat sunk and change
		boolean sunk = true;
		if(s != null) {
			for (Square square : s.getOccupiedSquares()) {
				boolean containedHit = false;
				for (Result res : this.attacks) {
					if (res.getResult() == AtackStatus.HIT && res.getLocation().getRow() == square.getRow() && res.getLocation().getColumn() == square.getColumn()) {
						containedHit = true;
					}
				}
				if (!containedHit) {
					sunk = false;
				}
			}
			if (sunk) {
				r.setResult(AtackStatus.SUNK);
			}

			//check if surrender
			int countSunk = 0;
			for (Result at : this.attacks) {
				if (at.getResult() == AtackStatus.SUNK) {
					countSunk++;
				}
			}
			if (countSunk == this.ships.size()) {
				r.setResult(AtackStatus.SURRENDER);
			}
		}

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
