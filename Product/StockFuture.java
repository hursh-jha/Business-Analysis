/*
This will show all the information of the stock
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
import javax.swing.JTable;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class StockFuture  extends JFrame implements ActionListener{

    private JLabel evaluation;
    private JButton exiter;
    private final Font LABEL_1_FONT = new Font("Arial",Font.BOLD,25);
    private final Font LABEL_2_FONT = new Font("Arial",Font.BOLD,20);
    private JLabel description;
    private JTable stockDisplay;
    private JLabel temporary;
    private String[] adjectives;
    private double score;
    public static void main(String[] args) {
        StockFuture testFrame = new StockFuture(new Stock());
    }
    
    public StockFuture(Stock prediction){
        super();
        this.setBounds(320, 10, 800, 800);
        this.getContentPane().setBackground(Color.CYAN);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        //evaluation of the stock
        adjectives = new String[8];
        
        score = 1;
        
        if(prediction.getPeg()<.5){
            adjectives[0]= " (amazing)";
        }else if (prediction.getPeg()>1.5){
            adjectives[0] = " (very bad)";
        }else if (1<prediction.getPeg()&& prediction.getPeg()<1.5){
            adjectives[0] = " (bad)";
        }else{
            adjectives[0] = " (good)";
        }
        
        score/=Math.abs(prediction.getPeg());
        
        if(prediction.getRoe()>.15 && prediction.getRoe()<.2){
            adjectives[1]= " (good)";
        }else if (prediction.getRoe()>.2){
            adjectives[1] = " (good, but expensive)";
        }else if (prediction.getRoe()<1.5){
            adjectives[1] = " (bad)";
        }
        
        score= score *prediction.getRoe()/.12;
        
        if(prediction.getAltmanZscore()<1.81){
            adjectives[2]= " (very bad)";
        }else if (prediction.getAltmanZscore()<3){
            adjectives[2] = " (okay)";
        }else{
            adjectives[2] = " (good)";
        }
        
        score*=(prediction.getAltmanZscore()/2.4);
        
        if(prediction.getModifiedCscore()<=1){
            adjectives[3]= " (very good)";
        }else if (prediction.getModifiedCscore()<=3){
            adjectives[3] = " (dangerous)";
        }else{
            adjectives[3] = " (very dangerous)";
        }
        
        score*=((1+prediction.getModifiedCscore())/2.5);
        
        if(prediction.getPiotroskiFscore()>=8){
            adjectives[4]= " (very good)";
        }else if (prediction.getPiotroskiFscore()>=6){
            adjectives[4] = " (okay)";
        }else if (prediction.getPiotroskiFscore()>=3){
            adjectives[4] = " (bad)";
        }else{
            adjectives[4] = " (very bad)";
        }
        
        score*=(2.5/(10-prediction.getPiotroskiFscore()));
        
        
        //displaying values
        String values= "<html>"+
                "Price: "+prediction.getArrayParameters()[2]+ "<br>"+
                "PEG: "+prediction.getPeg().toString()+ adjectives[0]+"<br>"+
                "ROE: "+prediction.getRoe().toString()+adjectives[1]+"<br>"+
                "AltmanZScore: "+prediction.getAltmanZscore().toString()+adjectives[2]+"<br>"+
                "ModifiedCScore: "+prediction.getModifiedCscore().toString()+adjectives[3]+"<br>"+
                "PiotroskiFScore: "+prediction.getPiotroskiFscore().toString()+adjectives[4]+"<br>"+
                "ReturnFactor: "+prediction.getReturnFactor().toString()+"<br>"+
                "fAdjustedEarnings: "+prediction.getfAdjustedEarning().toString()+
                "</html>";
        
        
        String summary = prediction.getArrayParameters()[0];
        
        //summarizing the stock
        if(score>1){
            summary= summary+ "is an excellent investment with a score of " + Math.round(score*100);
        }else if(score>.8){
            summary= summary+ "is a good investment with a score of " + Math.round(score*100);
        }else if(score>.6){
            summary= summary+ "is an okay, but not safe investment with a score of " + Math.round(score*100);
        }else{
            summary= summary+ "is a bad investment with a score of " + Math.round(score*100);
        }
        
        evaluation = new JLabel(summary);
        evaluation.setFont(LABEL_1_FONT);
        
        temporary = new JLabel(values);
        temporary.setFont(LABEL_2_FONT);
        description = new JLabel(prediction.getArrayParameters()[0]);
        description.setFont(LABEL_2_FONT);
        
        exiter = new JButton("Close");
        exiter.addActionListener(this);
        
        this.add(evaluation, BorderLayout.NORTH);
        this.add(exiter, BorderLayout.SOUTH);
        this.add(description, BorderLayout.WEST);
        this.add(temporary, BorderLayout.EAST);
        
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if(command.equals("Close")){
            this.dispose();
        }
    }
}
