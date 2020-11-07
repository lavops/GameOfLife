import java.io.Console;
import java.util.Random;

public class Engine {
	int rows;
	int cols;
	int matrica[][];
	int sledeca[][];
	int brojAktivnih;
	boolean postojiRazlika;
	
	public Engine(int _rows, int _cols, int _brojAktivnih) {
		this.rows = _rows;
		this.cols = _cols;
		this.brojAktivnih = _brojAktivnih;
		napraviMatricu();
		popuniMatricuNulama();
		popuniMatricuAktivnim();
		//printMatricu();
	}
	
	private void napraviMatricu() {
		this.matrica = new int[rows][cols];
		this.sledeca = new int[rows][cols];
	}
	
	private void popuniMatricuNulama() {
		Random r = new Random();
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				matrica[i][j] = 0;
				sledeca[i][j] = 0;
			}
		}
	}
	
	private void popuniMatricuAktivnim() {
		Random r = new Random();
		
		while(brojAktivnih > 0) {
			int i = r.nextInt(rows);
			int j = r.nextInt(cols);
			
			if(matrica[i][j] == 0)
			{
				matrica[i][j] = 1;
				brojAktivnih--;
			}
		}
	}
	
	private void printMatricu() {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				System.out.print(matrica[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public void novaStanja() {
		
		this.postojiRazlika = false;
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				
				int stanje = this.matrica[i][j];
				int susedi = prebrojSusede(i, j);
				
				if(stanje == 0 && susedi == 3) {
					this.sledeca[i][j] = 1;
					this.postojiRazlika = true;
				}
				else if(stanje == 1 && (susedi < 2 || susedi > 3)) {
					this.sledeca[i][j] = 0;
					this.postojiRazlika = true;
				}
				else {
					this.sledeca[i][j] = this.matrica[i][j];
				}
			}
		}
		
		this.matrica = this.sledeca;
	}
	
	public int prebrojSusede(int x, int y) {
		int suma = 0;
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				//int row = (x + i + rows) % rows;
				//int col = (y + j + cols) % cols;
				//suma += matrica[row][col];
				boolean inBoundsX = (x + i >= 0) && (x + i < matrica.length);
				boolean inBoundsY = (y + j >= 0) && (y + j < matrica[x].length);
				if(inBoundsX && inBoundsY)
					suma += matrica[x + i][y + j];
			}
		}
		
		suma -= matrica[x][y];
		return suma;
	}
}
