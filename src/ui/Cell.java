package ui;

import java.awt.Color;

import acm.graphics.GRect;

public class Cell extends GRect{

	private boolean alive;
	private double side;
	
	private static final Color color_alive = Color.red.darker();
	private static final Color color_dead = Color.white;
	
	public Cell(double side) {
		super(side,side);
		setColor(Color.LIGHT_GRAY);
		setAlive(false);
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(Boolean selected) {
		alive = selected;
		if(alive)
			setFillColor(color_alive);
		else
			setFillColor(color_dead);
		setFilled(true);
	}
	
	public void toggle() {
		setAlive(!alive);
	}
}
