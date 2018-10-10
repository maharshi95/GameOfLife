package ui;

public class Test {

	public static void main(String[] args) {
		DataGrid grid = new DataGrid(10);
		for(int i=4; i<8; i++) {
			grid.setValue(i, 3, 1);
		}
		System.out.println(grid + "\n");
		grid.evolve();
		System.out.println(grid + "\n");
		grid.evolve();
		System.out.println(grid + "\n");
		grid.evolve();
		System.out.println(grid + "\n");
		grid.evolve();
		System.out.println(grid + "\n");
		
//		for(int i=0; i<10; i++) {
//			for(int j=0; j<10; j++) {
//				int n = grid.getAliveNeighbours(i, j);
//				if(n > 0) System.out.println("" + i + " " + j + ": " + n);
//			}
//		}
	}
}
