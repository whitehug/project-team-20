package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public class Square {

	protected int row;
	protected char column;

	public Square(){
		this.row = 0;
		this.column = 0;
	}

	public Square(int row, char column) {
		this.row = row;
		this.column = column;
	}

	public char getColumn() {
		return column;
	}

	public void setColumn(char column) {
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}
