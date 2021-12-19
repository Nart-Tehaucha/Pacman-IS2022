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

public class Delete extends JFrame {

//	public static void main(String[] args) throws IOException {
		

		        public static void winnerWindow(String userName) throws HeadlessException, IOException {
		        	
		        	
		        	
		        	JFrame frame = buildFrame();
		        	
		        	final BufferedImage image = ImageIO.read(new File("Pacman-master/resources/images/victory.png"));

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
			        
			        
			        
			        frame.show();
			        
			    }
		

	private static JFrame buildFrame() {
	    JFrame frame = new JFrame();
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    frame.setSize(632, 365);
	    frame.setVisible(true);
	    return frame;
	}
//		public static void createFrame()
//	    {
//	        EventQueue.invokeLater(new Runnable()
//	        {
//	            @Override
//	            public void run()
//	            {
//	                JFrame frame = new JFrame("Test");
//	                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
////	                try 
////	                {
////	                  // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
////	                } catch (Exception e) {
////	                   e.printStackTrace();
////	                }
//	                JPanel panel = new JPanel();
//	                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//	                panel.setOpaque(true);
//	                JTextArea textArea = new JTextArea(15, 50);
//	                textArea.setWrapStyleWord(true);
//	                textArea.setEditable(false);
//	                textArea.setFont(Font.getFont(Font.SANS_SERIF));
//	                JScrollPane scroller = new JScrollPane(textArea);
//	                scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//	                scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//	                JPanel inputpanel = new JPanel();
//	                inputpanel.setLayout(new FlowLayout());
//	                JTextField input = new JTextField(20);
//	                JButton button = new JButton("Enter");
//	                DefaultCaret caret = (DefaultCaret) textArea.getCaret();
//	                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
//	                panel.add(scroller);
//	                inputpanel.add(input);
//	                inputpanel.add(button);
//	                panel.add(inputpanel);
//	                frame.getContentPane().add(BorderLayout.CENTER, panel);
//	                frame.pack();
//	                frame.setLocationByPlatform(true);
//	                frame.setVisible(true);
//	                frame.setResizable(false);
//	                input.requestFocus();
//	            }
//	        });
//	    }
	
}


				




 