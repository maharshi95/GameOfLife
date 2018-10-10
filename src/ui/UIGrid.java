package ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GCompound;

public class UIGrid extends GCompound{
	
	private final int N;
	private Cell[][] matrix;
	
	private boolean leftPress;
	private boolean rightPress;
	private boolean listening;
	
	private MouseAdapter adaptor;
	
	public UIGrid(int n, int cellSize) {
		N = n;
		matrix = new Cell[N][N];
		initMouseAdapter();
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				matrix[i][j] = new Cell(cellSize);
				matrix[i][j].setLocation(j*cellSize, i*cellSize);
				add(matrix[i][j]);
			}
		}	
		
		listening = false;
		leftPress = false;
		rightPress = false;
	}
	
	private void initMouseAdapter() {
		adaptor = new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				Cell cell = (Cell)e.getSource();
				if(e.getButton() == 1) {
					
					cell.setAlive(true);
					leftPress = true;
				}
				else {
					cell.setAlive(false);
					rightPress = true;
				}
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				Cell cell = (Cell) e.getSource();
				if(leftPress) {
					cell.setAlive(true);
				}
				else if(rightPress) {
					cell.setAlive(false);
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				leftPress = false;
				rightPress = false;
			}
		};
	}
	
	public void startListening() {
		if(!listening) {
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					matrix[i][j].addMouseListener(adaptor);
				}
			}
			listening = true;
		}
	}
	
	public void stopListening() {
		if(listening) {
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					matrix[i][j].removeMouseListener(adaptor);
				}
			}
			listening = false;
		}
	}
	
	public int[][] getDataGrid() {
		int[][] mat = new int[N][N];
		for(int i=0; i<N; i++)
			for(int j=0; j<N; j++)
				mat[i][j] = matrix[i][j].isAlive() ? 1 : 0;
		return mat;
	}
	
	public void reload(int[][] mat) {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				matrix[i][j].setAlive(mat[i][j] > 0);
			}
		}
	}
	
	public void reset() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				matrix[i][j].setAlive(false);
			}
		}
	}
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				str.append(matrix[i][j].isAlive() ? "# " : ". ");
			}
			str.append("\n");
		}
		return str.toString();
	}
}
