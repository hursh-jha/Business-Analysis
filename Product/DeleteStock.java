/*
This frame will be used to be able to delete stocks from the database
 */
//package stockanalysis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class DeleteStock extends JFrame implements ActionListener{

    private Object[][] dataSet;
    private JScrollPane scrollBar;
    private JCheckBox[] tempBoxes;
    private JLabel[]tempNames;
    private JButton exiter;
    private JButton enterButton;
    private JButton backButton;
    private JMenuBar helpBar;
    private JMenuItem helpButton; 
    private JPanel choiceButtons;
    private JPanel allButtons;
    private JPanel eachItem;
    private JPanel allItems;
    private final Font LABEL_1_FONT = new Font("Arial",Font.BOLD,52);
    private JLabel description;
    private String[] columnNames = {"Select", "Stock Name"};
    private ArrayList<String> selected;
    private DataBaseAccess dbObj;
    
    
    public DeleteStock(){
        super();
        //setting up frame
        this.setBounds(320, 10, 800, 800);
        this.getContentPane().setBackground(Color.ORANGE);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
       
        
        allItems = new JPanel();
        allItems.setLayout(new BoxLayout(allItems, BoxLayout.Y_AXIS));
        
        dbObj = new DataBaseAccess("StockInformation");
        String[] columns = {"Company","Sector", "Price","EPS", "NetIncome", "ShareHolderEquity", 
            "EnterpriseValue", "MarketCap", "AltmanZScore", "PiotroskiFScore","ModifiedCScore", 
            "EarningYield", "OperatingMargin", "FreeCashFlow", "LongTermDebt","NetWorth","DividendYield",
            "EnterpriseValueEBIDTA", "PriceSales","CashFlow"};
        dataSet = dbObj.getData("StockValues", columns);

        tempBoxes = new JCheckBox[dataSet.length];
        tempNames = new JLabel[dataSet.length];
        
        //creates boxes and fields
        for(int i = 0; i< dataSet.length; i++){
            eachItem = new JPanel();
            eachItem.setLayout(new BorderLayout());
            
            tempBoxes[i] = new JCheckBox();
            tempNames[i] =new JLabel(dataSet[i][0].toString());
            
            eachItem.add(tempBoxes[i], BorderLayout.CENTER);
            eachItem.add(tempNames[i], BorderLayout.EAST);
            eachItem.setBackground(Color.ORANGE);
            
            allItems.add(eachItem);
        }
        allItems.setBackground(Color.ORANGE);
        helpButton = new JMenuItem("Help");
        helpButton.addActionListener(this);
        
        helpBar = new JMenuBar();
        helpBar.add(helpButton);
        
        allButtons = new JPanel();
        allButtons.setLayout(new BorderLayout());
        
        choiceButtons = new JPanel();
        
        exiter = new JButton("Close");
        exiter.addActionListener(this);
        
        enterButton = new JButton("Delete");
        enterButton.addActionListener(this);
        
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        
        choiceButtons.add(enterButton);
        choiceButtons.add(backButton);
        
        allButtons.add(choiceButtons, BorderLayout.NORTH);
        allButtons.add(exiter, BorderLayout.SOUTH);
        
        description = new JLabel("Select Stocks");
        description.setFont(LABEL_1_FONT);  
        
        this.add(description, BorderLayout.WEST);
        this.add(helpBar, BorderLayout.NORTH);
        this.add(allButtons, BorderLayout.SOUTH);
        
        
        scrollBar=new JScrollPane(allItems,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scrollBar, BorderLayout.CENTER);
        
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if(command.equals("Delete")){
            selected = new ArrayList<>();
            for(int i = 0; i < tempBoxes.length; i++){
                if(((JCheckBox) tempBoxes[i]).isSelected()){
                    System.out.println(i + " is checked");
                    selected.add(dataSet[i][0].toString());
                }
            }
            //finds the selected values and deletes them
            for(int i = 0; i<selected.size();i++){
                dbObj.deleteInfo("Company", "'"+selected.get(i).toString()+"'", "StockValues");
            }
            
            
            ErrorFrame error = new ErrorFrame("Data has been Deleted");
            
            
            
        }else if(command.equals("Back")){
            MainFrame homeFrame = new MainFrame();
            this.dispose();
        }else if(command.equals("Close")){
            this.dispose();
        }else if (command.equals("Help")){
            HelpFrame helper = new HelpFrame();
        }
    }
    public static void main(String[] args) {
        DeleteStock deleter = new DeleteStock();
    }
    
}
