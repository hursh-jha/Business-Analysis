/*
This will compare all selected stocks and rank them
 */
//package stockanalysis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class StockComparisonDisplay extends JFrame implements ActionListener{
    
    private JButton exiter;
    private final Font LABEL_1_FONT = new Font("Arial",Font.BOLD,52);
    private JLabel description;
    private JScrollPane scrollBar;
    private JTable comparisonTable;
    private DataBaseAccess dbObj = new DataBaseAccess("StockInformation");
    
    public StockComparisonDisplay(Stock[] goodData){
        super();
        
        this.setBounds(320, 10, 800, 800);
        this.getContentPane().setBackground(Color.GREEN);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        description = new JLabel("STOCK COMPARISON TABLE HERE");
        description.setFont(LABEL_1_FONT);  
        
        this.add(description, BorderLayout.CENTER);
        
        exiter = new JButton("Close");
        exiter.addActionListener(this);
        
        String[] tableHeaders = {"Company","Price", "PEG","ROE", "AltmanZScore", "ModifiedCScore", 
            "PiotroskiFScore", "ReturnFactor", "FAdjustedEarning"}; 
       
        String[][] tableData = new String[goodData.length][9];

        //stores all the info into a 2d array
        for(int i = 0; i < goodData.length;i++){
            tableData[i][0] = goodData[i].getArrayParameters()[0];
            tableData[i][1] = goodData[i].getArrayParameters()[2];
            tableData[i][2] = goodData[i].getPeg().toString();
            tableData[i][3] = goodData[i].getRoe().toString();
            tableData[i][4] = goodData[i].getAltmanZscore().toString();
            tableData[i][5] = goodData[i].getModifiedCscore().toString();
            tableData[i][6] = goodData[i].getPiotroskiFscore().toString();
            tableData[i][7] = goodData[i].getReturnFactor().toString();
            tableData[i][8] = goodData[i].getfAdjustedEarning().toString();
        }
        
        //has the indexes of each stock, acting like a map
        int[][]replecaTable = new int[goodData.length][9];
        
        for(int i = 0; i < goodData.length;i++){
            for(int j= 0; j<9;j++){
                replecaTable[i][j] = i;
            }
        }
        
        for(int i = 2; i < replecaTable[0].length;i++){
            Boolean done;
            done = false;
            
            while(!done){
                done = true;
                for(int j = 0; j<replecaTable.length-1;j++){
                    //sorts the values based in repleca table based on good data
                    if(i== 5){
                        if(Double.parseDouble(tableData[replecaTable[j][i]][i])>Double.parseDouble(tableData[replecaTable[j+1][i]][i])){
                            int temp = (replecaTable[j][i]);
                            replecaTable[j][i] = replecaTable[j+1][i];
                            replecaTable[j+1][i]=temp;
                            done = false;
                        }
                    }else{
                        if(Double.parseDouble(tableData[replecaTable[j][i]][i])<Double.parseDouble(tableData[replecaTable[j+1][i]][i])){
                            int temp = (replecaTable[j][i]);
                            replecaTable[j][i] = replecaTable[j+1][i];
                            replecaTable[j+1][i]=temp;
                            done = false;        
                        }
                    }
                }
            }
        }
        
        //uses the re ordered indexes to recategorize the data
        for(int i = 0; i < goodData.length;i++){
            tableData[i][0] = goodData[replecaTable[i][0]].getArrayParameters()[0];
            tableData[i][1] = goodData[replecaTable[i][1]].getArrayParameters()[0]+": "+goodData[replecaTable[i][1]].getArrayParameters()[2];
            tableData[i][2] = goodData[replecaTable[i][2]].getArrayParameters()[0]+": "+goodData[replecaTable[i][2]].getPeg().toString();
            tableData[i][3] = goodData[replecaTable[i][3]].getArrayParameters()[0]+": "+goodData[replecaTable[i][3]].getRoe().toString();
            tableData[i][4] = goodData[replecaTable[i][4]].getArrayParameters()[0]+": "+goodData[replecaTable[i][4]].getAltmanZscore().toString();
            tableData[i][5] = goodData[replecaTable[i][5]].getArrayParameters()[0]+": "+goodData[replecaTable[i][5]].getModifiedCscore().toString();
            tableData[i][6] = goodData[replecaTable[i][6]].getArrayParameters()[0]+": "+goodData[replecaTable[i][6]].getPiotroskiFscore().toString();
            tableData[i][7] =goodData[replecaTable[i][7]].getArrayParameters()[0]+": "+ goodData[replecaTable[i][7]].getReturnFactor().toString();
            tableData[i][8] =goodData[replecaTable[i][8]].getArrayParameters()[0]+": "+ goodData[replecaTable[i][8]].getfAdjustedEarning().toString();
        }
        
        comparisonTable = new JTable(tableData,tableHeaders);
        comparisonTable.setBackground(Color.GREEN);
        scrollBar=new JScrollPane(comparisonTable,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        
        scrollBar.setBackground(Color.GREEN);
        
        this.add(exiter, BorderLayout.SOUTH);
        this.add(scrollBar, BorderLayout.CENTER);
                
        this.setVisible(true);
       
    }
    
    public static void main(String[] args) {
        ArrayList<String> dummyValues = new ArrayList<>();
        dummyValues.add("1");
        //StockComparisonDisplay compsarison = new StockComparisonDisplay(dummyValues);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if(command.equals("Close")){
            this.dispose();
        }
    }
    
}
