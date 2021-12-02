package views;

import controllers.*;
import models.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// Starting screen of the application.
public class StartWindow extends JFrame {

	// Constructor, initializes the start screen.
    public StartWindow(){
    	// Setup the screen
        setSize(600,300);
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Load Game logo
        ImageIcon logo = new ImageIcon();
        try {
            logo = new ImageIcon(ImageIO.read(this.getClass().getResource("/resources/images/pacman_logo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Register Custom fonts
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/resources/fonts/crackman.ttf")));
        } catch (IOException|FontFormatException e) {
            e.printStackTrace();
        }

        // Add elements to the screen (buttons)
        setLayout(new BorderLayout());
        getContentPane().add(new JLabel(logo),BorderLayout.NORTH);

        JPanel buttonsC = new JPanel();
        buttonsC.setBackground(Color.black);
        //buttonsC.setLayout(new FlowLayout(FlowLayout.LEADING,20,10));
        buttonsC.setLayout(new BoxLayout(buttonsC,BoxLayout.Y_AXIS));
        FansyButton startButton = new FansyButton("Start Game");
        FansyButton customButton = new FansyButton("Customize Game");

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        customButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Action listener for button presses
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PacWindow pw = new PacWindow();
                //new PacWindow();
                dispose();
            }
        });

        customButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MapEditor me = new MapEditor();
                dispose();
            }
        });

        buttonsC.add(startButton);
        buttonsC.add(customButton);

        getContentPane().add(buttonsC);
        
        setVisible(true);
    }
}
