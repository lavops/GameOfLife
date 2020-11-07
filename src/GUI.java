import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

public class GUI extends JFrame {
	int rows;
	int cols;
	int brojAktivnih;
	int brojEvolucija = 0;
	
	JPanel[][] polja;
	JLabel labela;
	Timer t;
	Engine engine;
	
	public GUI(int _rows, int _cols, int _brojAktivnih) {
		this.rows = _rows;
		this.cols = _cols;
		this.brojAktivnih = _brojAktivnih;
		
		engine = new Engine(rows, cols, brojAktivnih);
		polja = new JPanel[rows][cols];
		
		setSize(1000, 1000);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) ((dimension.getWidth() - getWidth()) / 2), (int) ((dimension.getHeight() - getHeight()) / 2));
		setTitle("Game Of Life");
		
		napraviHeader();
		nacrtajTablu();
		nacrtajOpcije();
		
		setVisible(true);
	}
	
	private void nacrtajTablu() {
		JPanel panel = new JPanel(new GridLayout(rows,cols,1,1));
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				polja[i][j] = new JPanel();
				polja[i][j].setPreferredSize(new Dimension(10,10));
				Border b = BorderFactory.createLineBorder(Color.LIGHT_GRAY,0);
				polja[i][j].setBorder(b);
				if(engine.matrica[i][j] == 1)
					polja[i][j].setBackground(Color.BLACK);
				else
					polja[i][j].setBackground(Color.WHITE);
				panel.add(polja[i][j]);
			}
			
		}
		
		getContentPane().add(panel, BorderLayout.CENTER);
	}
	
	private void nacrtajOpcije() {
		JPanel opcije = new JPanel();
		
		JButton sledeciKorak = new JButton("Nova igra");
		opcije.add(sledeciKorak);
		sledeciKorak.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				t.stop();
				brojEvolucija = 0;
				engine = new Engine(rows, cols, brojAktivnih);
				osveziTablu();
				t.start();
			}
		});
		
		t = new Timer(200, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				engine.novaStanja();
				
				if(engine.postojiRazlika) {
					brojEvolucija++;
					osveziTablu();
				}
				else {
					t.stop();
				}
			}
		});
		
		t.start();
		
		getContentPane().add(opcije, BorderLayout.SOUTH);
	}
	
	private void napraviHeader() {
		JPanel header = new JPanel();
		labela = new JLabel("Broj evolucija : " + brojEvolucija);
		labela.setForeground(Color.BLUE);
		header.add(labela);
		getContentPane().add(header, BorderLayout.NORTH);
	}
	
	private void osveziTablu() {
		labela.setText("Broj evolucija : " + brojEvolucija);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if(engine.matrica[i][j] == 1)
					polja[i][j].setBackground(Color.BLACK);
				else
					polja[i][j].setBackground(Color.WHITE);
			}
			
		}
	}
}
