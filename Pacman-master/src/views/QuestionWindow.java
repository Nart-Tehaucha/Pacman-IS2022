package views;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;

import controllers.PacBoard;
import models.Answer;
import models.Question;

public class QuestionWindow extends JFrame implements ActionListener {
	
	static String ques;
	static String ans1;
	static String ans2;
	static String ans3;
	static String ans4;
	static int correctAns;
	
	private PacBoard pb;
	
	Timer delayTimer;
	
	public QuestionWindow(PacWindow pw, Question q, PacBoard pb) {
		this.pb = pb;
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		this.setSize(500,300);
		this.setLocationRelativeTo(pw);
		
		correctAns = q.getCorrect_ans();
		
		ArrayList<Answer> arrAns = q.getAnswers();
		ans1 = arrAns.get(0).getContent();
		ans2 = arrAns.get(1).getContent();
		ans3 = arrAns.get(2).getContent();
		ans4 = arrAns.get(3).getContent();
		
		JLabel question = new JLabel(q.getContent());
		question.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel error = new JLabel();
		error.setForeground(new Color(255, 36, 36));
		
		//Create the radio buttons.
        JRadioButton rdAnswer1 = new JRadioButton(ans1);
        rdAnswer1.setActionCommand(ans1);
        rdAnswer1.setSelected(true);

        JRadioButton rdAnswer2 = new JRadioButton(ans2);
        rdAnswer2.setActionCommand(ans2);

        JRadioButton rdAnswer3 = new JRadioButton(ans3);
        rdAnswer3.setActionCommand(ans3);

        JRadioButton rdAnswer4 = new JRadioButton(ans4);
        rdAnswer4.setActionCommand(ans4);

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(rdAnswer1);
        group.add(rdAnswer2);
        group.add(rdAnswer3);
        group.add(rdAnswer4);

        
        
        //Register a listener for the radio buttons.
        rdAnswer1.addActionListener(this);
        rdAnswer2.addActionListener(this);
        rdAnswer3.addActionListener(this);
        rdAnswer4.addActionListener(this);
        

        //Put the radio buttons in a column in a panel.
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(rdAnswer1);
        radioPanel.add(rdAnswer2);
        radioPanel.add(rdAnswer3);
        radioPanel.add(rdAnswer4);
        
        JButton button = new JButton("Submit");
        // Closes the window after a delay
        ActionListener delayClose = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	delayTimer.stop();
            	pb.resume();
            	dispose();
            }
        }; 
        delayTimer = new Timer (1000, delayClose);
        
        // Action listener for button presses
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            		JRadioButton button = (JRadioButton) buttons.nextElement();

                    if (button.isSelected()) {
                    	if(pb.checkAnswer(q, button.getText())) {
                    		button.setBackground(new Color(0,255,0));
                		} else {
                			button.setBackground(new Color(255,0,0));
                		}
                    }
                }
            	delayTimer.start();
            }
        });
        
        add(question, BorderLayout.PAGE_START);
        add(radioPanel, BorderLayout.CENTER);
        add(button, BorderLayout.PAGE_END);
        

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
