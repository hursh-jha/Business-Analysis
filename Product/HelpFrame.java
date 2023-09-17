/*
This frame is to provide a general trouble shooting guide
 */
//package stockanalysis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class HelpFrame extends JFrame implements ActionListener{
     //instance variables
    private final Font ABOUT_FONT = new Font("Avenir", Font.BOLD | Font.ITALIC,14);
       
    private JLabel helpAdvice;
    
    private JFrame errorFrame;
    
    private JButton exiter;
   
    public HelpFrame() {
        //setting up frame
        this.setBounds(300,500,700,600);       
        this.getContentPane().setBackground(Color.YELLOW);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        //initiallize
        helpAdvice = new JLabel("<html>This application has two parts, analyzing/entering a new stock and comparing already entered stocks.<br> To enter a new stock or analyze it, please select the Enter Stock button, then enter the relevant information.<br> To compare information just select the compare info button and then select the stocks you want to compare, then they are automatically compared.</html>");
        helpAdvice.setFont(ABOUT_FONT);
        
        exiter = new JButton("Close");
        exiter.addActionListener(this);
        
        //adding
        this.add(helpAdvice, BorderLayout.NORTH);
        this.add(exiter, BorderLayout.SOUTH);
        
        this.setVisible(true);
        
    } 
    @Override
    public void actionPerformed (ActionEvent e){
        String buttonClick = e.getActionCommand();
        //closing
        if(buttonClick.equals("Close")){ 
            this.dispose();
        }
    }
    public static void main(String[] args) {
        HelpFrame obj = new HelpFrame();
    }
}
