package views;


//java Program to create a simple JDialog
import java.awt.event.*;
import java.io.IOException;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

import controllers.JFXLauncher;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class LoserAnnouncment  extends JFrame {

//	public static void main(String[] args) throws IOException {
		

		        public static void loserWindow(String userName) throws HeadlessException, IOException {
		        	
		        	
		        	
		        	JFrame frame = buildFrame();
		        	
		        	final BufferedImage image = ImageIO.read(new File("Pacman-master/resources/images/gameover.png"));

			        JPanel pane = new JPanel() {
			            @Override
			            protected void paintComponent(Graphics g) {
			                super.paintComponent(g);
			                g.drawImage(image, 0, 0, null);
			            }
			        };
			        
			        pane.setBackground(Color.white);
			        

			        JPanel bottomBar = new JPanel();
			        bottomBar.setBackground(Color.white);
			        
			        
			        JButton b1 = new JButton();     
			        b1.setSize(50,50);
			        b1.setVisible(true);
			        b1.setText("PLAY AGAIN");
//			        bottomBar.add(b1);

			        JButton b2 = new JButton();     
			        b2.setSize(50,50);
			        b2.setVisible(true);
			        b2.setText("EXIT");
			       // bottomBar.add(b2);
			        
			        b1.addActionListener(new ActionListener() { 
			        	  public void actionPerformed(ActionEvent e) { 
			        	    frame.dispose();
			        	    new PacWindow(userName);
			        	  } 
			        	} );
			        
			        b2.addActionListener(new ActionListener() { 
			        	  public void actionPerformed(ActionEvent e) { 
			        	    frame.dispose();
			        	  } 
			        	} );
			        
			        JPanel buttonsPanel = new JPanel(new GridLayout(1,2));
			        

			     
			 
			        
			        buttonsPanel.add(b1);
			        Component spacing = Box.createRigidArea(new Dimension(5, 0));
			        
			        buttonsPanel.add(spacing);
			        buttonsPanel.add(b2);
			        //JButton[] buttons = new JButton[10];
			        //buttons[0] = b1;
			        //buttons[1] =b2;
		
			        
			        bottomBar.add(buttonsPanel);
//			        bottomBar.add(lbScore);
			        frame.add(bottomBar,BorderLayout.SOUTH);
			        frame.add(pane);
			        
			       
			    }
		

	private static JFrame buildFrame() {
	    JFrame frame = new JFrame();
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    frame.setSize(660, 460);
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    return frame;
	}

	
}


				




