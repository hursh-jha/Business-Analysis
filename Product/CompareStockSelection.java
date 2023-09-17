/*
This is a list of stocks that the suser van select from to compare
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

public class CompareStockSelection extends JFrame implements ActionListener{
    
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
    private DataBaseAccess dbObj;
    private String[] columnNames = {"Select", "Stock Name"};
    private ArrayList<String> selected;
    public CompareStockSelection(){
        super();
        
        this.setBounds(320, 10, 800, 800);
        this.getContentPane().setBackground(Color.GREEN);
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
        
        //creates all of the items
        for(int i = 0; i< dataSet.length; i++){
            eachItem = new JPanel();
            eachItem.setLayout(new BorderLayout());
            
            tempBoxes[i] = new JCheckBox();
            tempNames[i] =new JLabel(dataSet[i][0].toString());
            
            eachItem.add(tempBoxes[i], BorderLayout.CENTER);
            eachItem.add(tempNames[i], BorderLayout.EAST);
            eachItem.setBackground(Color.GREEN);
            
            allItems.add(eachItem);
        }
        //setting up frame
        allItems.setBackground(Color.GREEN);
        helpButton = new JMenuItem("Help");
        helpButton.addActionListener(this);
        
        helpBar = new JMenuBar();
        helpBar.add(helpButton);
        
        allButtons = new JPanel();
        allButtons.setLayout(new BorderLayout());
        
        choiceButtons = new JPanel();
        
        exiter = new JButton("Close");
        exiter.addActionListener(this);
        
        enterButton = new JButton("Compare");
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
    
    public static void main(String[] args) {
        CompareStockSelection stockFrame = new CompareStockSelection();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        
        if(command.equals("Compare")){
            //gets all selected stocks
            selected = new ArrayList<>();
            for(int i = 0; i < tempBoxes.length; i++){
                if(((JCheckBox) tempBoxes[i]).isSelected()){
                    System.out.println(i + " is checked");
                    selected.add(dataSet[i][0].toString());
                }
            }
            
            //gets the needed info
            ArrayList<Object []> goodData = new ArrayList<>();
                    
            for(int i = 0; i<dataSet.length;i++){
                for(int j = 0; j<selected.size();j++){
                    if(dataSet[i][0].toString().equals(selected.get(j))){
                        goodData.add(dataSet[i]);
                    }
                }
            }
            if(selected.size()<2){
                ErrorFrame error = new ErrorFrame("Please select at least two stocks");
            }else{
                StockComparisonDisplay compare = new StockComparisonDisplay(stockData());
            }
            
            
        }else if(command.equals("Back")){
            MainFrame homeFrame = new MainFrame();
            this.dispose();
        }else if(command.equals("Close")){
            this.dispose();
        }else if (command.equals("Help")){
            HelpFrame helper = new HelpFrame();
        }
    }
    
    //gets info from db and makes stock objects with them
    public Stock[] stockData(){
        Stock[] stockInfo = new Stock[selected.size()];
        
        String[] columns = {
            "Company", "OneYearRevenueGrowth", "OneYearEPSGrowth", "OneYearBookValueGrowth" , 
            "ThreeYearRevenueGrowth", "ThreeYearEPSGrowth", "ThreeYearBookValueGrowth", 
            "FiveYearRevenueGrowth","FiveYearEPSGrowth", "FiveYearBookValueGrowth"};
        
        Object[][] dataSetTwo = dbObj.getData("PredictedGrowth", columns);
        
        String[] recolumns = {
            "Company","OneDayReturn", "OneWeekReturn", "OneMonthReturn", "OneYearReturn", 
            "ThreeYearReturn", "FiveYearReturn", "TenYearReturn"};
        
        Object[][] dataSetThree = dbObj.getData("PredictedReturn", recolumns);
        
        for(int i = 0; i < selected.size();i++){
            String[] temp = new String[23];
            
            for(int j = 0; j<20; j++){
                temp[j] = (String)dataSet[i][j];
            }
            temp[20] = (String)dataSetTwo[i][2];
            temp[21] = (String)dataSetTwo[i][1];
            temp[22] = (String)dataSetTwo[i][4];
            
            Stock temporary = new Stock(temp);
            stockInfo[i] = temporary;
        }
        
        return stockInfo;
    }
}
