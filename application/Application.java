package application;

import view.BoardView;

public class Application {

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new BoardView().setVisible(true);
			    
			}
		});
	}

}
