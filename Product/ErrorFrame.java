/*
This is a frame that will be constructed whenever the user takes an action that is not valid
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

public class ErrorFrame extends JFrame implements ActionListener{
    
    private JLabel errorMessage;
    private JButton exiter;
    private final Font LABEL_1_FONT = new Font("Arial",Font.BOLD,40);
    
    public static void main(String[] args) {
        ErrorFrame testFrame = new ErrorFrame("Please enter valid values");
    }
    public ErrorFrame(String errorString){
        super();
        
        this.setBounds(300,500,700,600);       
        this.getContentPane().setBackground(Color.RED);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        errorMessage = new JLabel(errorString);
        errorMessage.setFont(LABEL_1_FONT);
        
        exiter = new JButton("Close");
        exiter.addActionListener(this);
        
        this.add(exiter, BorderLayout.SOUTH);
        
        
        this.add(errorMessage, BorderLayout.CENTER);
        this.setVisible(true);
    }

     public void actionPerformed (ActionEvent e){
        String buttonClick = e.getActionCommand();
        //closing
        if(buttonClick.equals("Close")){ 
            this.dispose();
        }
    }
}
