package views;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class QuestionWindow extends JFrame {
	public QuestionWindow(PacWindow pw) {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		this.setSize(300,200);
		this.setLocationRelativeTo(pw);
		this.setVisible(true);
		
	}
}
