/*
This is the frame while the user enters the ihnformation for the stock that they want to enter
 */
//package stockanalysis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class EnterStock extends JFrame implements ActionListener{

    private final int firstTable = 20;
    private final int secondTable = 8;
    private final int thirdTable = 10;
    private Boolean status;
    private Boolean alreadyClicked;
    private DataBaseAccess dbObj= new DataBaseAccess("StockInformation");
    private JButton exiter;
    private JButton enterButton;
    private JButton backButton;
    private JMenuBar helpBar;
    private JMenuItem helpButton; 
    private JPanel choiceButtons;
    private JPanel allButtons;
    private JPanel allItems;
    private final Font LABEL_1_FONT = new Font("Arial",Font.BOLD,40);
    private final Font LABEL_2_FONT = new Font("Arial",Font.BOLD,18);
    private JLabel description;
    private JPanel allTextfields;
    private JPanel allLabels;
    private JLabel updateLabel;
    private String[] parameters = {"Company", "Sector", "Price", 
                   "Earnings Per Share", "Net Income", "Share HolderEquity", "Enterprise Value (Cr)",
                      "Market Cap (Cr)", "Altman Z-score", "Piotroski F-score", "Modified C-score",
                      "Earning Yield (%)","Operating Margin (%)", "Free Cash Flow (Cr)", "Long-term Debt (Cr)",
                      "Networth (Cr)","Dividend Yield (%)", "Enterprise Value / Ebidta","Price / Sales","Price / Cash Flow",
                      
                      "1-Day Return","1-Week Return","1-Month Return","3-Month Return","1-Year Return", 
                      "3-Year Return","5-Year Return","10-Year Return",
                       
                         "1-Year Revenue Growth", "1-Year EPS Growth", "1-Year Book Value Growth",
                        "3-Year Revenue Growth", "3-Year EPS Growth", "3 Year Book Value Growth", 
                        "5 Year Revenue Growth", "5-Year EPS Growth", "5 Year Book Value Growth", };
    
    private  String[] tableHeaders = {("Company"),"Sector", "Price","EPS", "NetIncome", "ShareHolderEquity", 
            "EnterpriseValue", "MarketCap", "AltmanZScore", "PiotroskiFScore","ModifiedCScore", 
            "EarningYield", "OperatingMargin", "FreeCashFlow", "LongTermDebt","NetWorth","DividendYield",
            "EnterpriseValueEBIDTA", "PriceSales","CashFlow",
        
            "OneDayReturn", "OneWeekReturn", "OneMonthReturn", "OneYearReturn", "ThreeYearReturn", 
            "FiveYearReturn","TenYearReturn",
        
            "OneYearRevenueGrowth", "OneYearEPSGrowth", "OneYearBookValueGrowth", "ThreeYearRevenueGrowth", 
            "ThreeYearEPSGrowth", "ThreeYearBookValueGrowth", "FiveYearRevenueGrowth", "FiveYearEPSGrowth", 
            "FiveYearBookValueGrowth"};
    private JLabel[] nameArray = new JLabel[parameters.length];      
    private JTextField[] textFieldArray = new JTextField[parameters.length];
    private JPanel[] eachItem = new JPanel[parameters.length];
    
    private JScrollPane scrollBar;
    
    
    public EnterStock(){
        super();
       
        alreadyClicked = false;
        
        this.setBounds(320, 10, 800, 800);
        this.getContentPane().setBackground(Color.CYAN);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        
        allTextfields = new JPanel();
        allTextfields.setLayout(new BoxLayout(allTextfields, BoxLayout.Y_AXIS));
        
        allLabels = new JPanel();
        allLabels.setLayout(new BoxLayout(allLabels, BoxLayout.Y_AXIS));
        
        helpButton = new JMenuItem("Help");
        helpButton.addActionListener(this);
        
        helpBar = new JMenuBar();
        helpBar.add(helpButton);
        
        updateLabel = new JLabel("<html><br>To confirm overriding of stock information <br>press Enter Stock again</html>");
        updateLabel.setFont(LABEL_2_FONT);
        updateLabel.setVisible(false);
        allButtons = new JPanel();
        allButtons.setLayout(new BorderLayout());
        
        choiceButtons = new JPanel();
        
        exiter = new JButton("Close");
        exiter.addActionListener(this);
        
        enterButton = new JButton("Enter Stock");
        enterButton.addActionListener(this);
        
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        
        choiceButtons.add(enterButton);
        choiceButtons.add(backButton);
        
        allButtons.add(choiceButtons, BorderLayout.NORTH);
        allButtons.add(exiter, BorderLayout.SOUTH);
        
        for(int i = 0; i < parameters.length; i++ ){
            textFieldArray[i] = new JTextField(firstTable);
        }
        
        for(int i = 0; i < parameters.length; i++ ){
            nameArray[i] = new JLabel(parameters[i]);
        }
        
        allItems = new JPanel();
        allItems.setLayout(new BoxLayout(allItems, BoxLayout.Y_AXIS));
        
        for(int i = 0; i < parameters.length; i++ ){
            eachItem[i] = new JPanel();
            eachItem[i].setLayout(new BorderLayout());
            eachItem[i].add(nameArray[i], BorderLayout.CENTER);
            eachItem[i].add(textFieldArray[i], BorderLayout.EAST);
            eachItem[i].setBackground(Color.CYAN);
            allItems.add(eachItem[i]);
        }
        
        
        allItems.setBackground(Color.CYAN);
        
        scrollBar=new JScrollPane(allItems,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        description = new JLabel("Enter Stock");
        description.setFont(LABEL_1_FONT);
        
        this.add(helpBar, BorderLayout.NORTH);
        this.add(allButtons, BorderLayout.SOUTH);
        this.add(scrollBar, BorderLayout.EAST);
        this.add(description, BorderLayout.WEST);
        this.add(updateLabel, BorderLayout.CENTER);
        this.setVisible(true);
    }
    
    public static void main(String[] args) {
       EnterStock entry = new EnterStock();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if(command.equals("Enter Stock")){
            
            Boolean valid = true;
            
            //checks if valid data has been entered
            for(int i =  2; i< textFieldArray.length;i++){
                try{
                    Double.parseDouble(textFieldArray[i].getText());
                }catch(NumberFormatException err){
                    ErrorFrame validNumber = new ErrorFrame("Please enter valid values");
                    valid = false;
                    i = textFieldArray.length;
                }
            }
            
            if(valid){
                //finds if it is in the table or not
                String checker = "Select Company from StockValues where company = " +"'"+ textFieldArray[0].getText()+"'";

                dbObj.setDbConn();
                Connection dbConn = dbObj.getDbConn();

                try{
                    Statement s = dbConn.createStatement();
                    ResultSet rs = s.executeQuery(checker); 
                   status = rs.next();
                    dbConn.close();
                }catch(SQLException err){
                    System.out.println("Error updating  table ");
                    err.printStackTrace();
                    System.exit(0);
                }

                //checks if the user has already tried to change a value
                if(alreadyClicked){
                    alreadyClicked = false;
                    String[][] valuesArray = new String[firstTable][2];
                    for(int i = 0; i<firstTable;i++){
                        valuesArray[i][0] = tableHeaders[i];
                        valuesArray[i][1] = textFieldArray[i].getText();
                    }

                    //inserts values for each table
                    if(Double.parseDouble(textFieldArray[3].getText()) == Double.parseDouble(textFieldArray[29].getText())){
                        ErrorFrame error = new ErrorFrame("please enter valid values");
                    }else{
                        try{
                            dbObj.updateData("StockValues", "Company", textFieldArray[0].getText(), valuesArray);
                        }catch(Exception err){
                            ErrorFrame error = new ErrorFrame("please enter valid values");
                        }
                        String[][] returnArray = new String[secondTable][2];
                        returnArray[0][0] = tableHeaders[0];
                        returnArray[0][1] = textFieldArray[0].getText();
                        for(int i = 1; i<secondTable;i++){
                            returnArray[i][0] = tableHeaders[i+firstTable-1];
                            returnArray[i][1] = textFieldArray[i+firstTable-1].getText();
                        }
                        try{
                            dbObj.updateData("PredictedReturn", "Company", textFieldArray[0].getText(), returnArray);
                        }catch(Exception err){
                            ErrorFrame error = new ErrorFrame("please enter valid values");
                        }
                        String[][] growthArray = new String[thirdTable][2];
                        //returnArray[0][0] = tableHeaders[0];
                        growthArray[0][0] = "Company";
                        growthArray[0][1] = textFieldArray[0].getText();
                        for(int i = 1; i<thirdTable;i++){
                            growthArray[i][0] = tableHeaders[i+firstTable+secondTable-2];
                            growthArray[i][1] = textFieldArray[i+firstTable+secondTable-2].getText();
                        }
                        try{
                            dbObj.updateData("PredictedGrowth", "Company", textFieldArray[0].getText(), growthArray);
                        }catch(Exception err){
                            ErrorFrame error = new ErrorFrame("please enter valid values");
                        }
                        StockFuture newStock = new StockFuture(stockData());
                    }

                    //if the table does not exists
                }if(!status){

                    if(Double.parseDouble(textFieldArray[3].getText()) == Double.parseDouble(textFieldArray[29].getText())){
                        ErrorFrame error = new ErrorFrame("please enter valid values");
                    }else{
                        for(int i = 0; i< parameters.length;i++){
                            System.out.println(textFieldArray[i].getText());
                        }

                        String[] valuesTable = new String[firstTable];

                        valuesTable[0] = "'"+textFieldArray[0].getText()+"'";
                        valuesTable[1] = "'"+textFieldArray[1].getText()+"'";

                        for(int i = 2;i<firstTable;i++){
                            valuesTable[i] = (textFieldArray[i].getText());

                        }
                        try{
                            dbObj.insertValues("StockValues", valuesTable);
                        }catch(Exception err){
                            ErrorFrame error = new ErrorFrame("Please enter valid values");
                        }
                        String[] returnTable= new String[8];
                        returnTable[0] = "'"+textFieldArray[0].getText()+"'";
                        for(int i = 1; i<secondTable;i++){
                            returnTable[i] = textFieldArray[i+firstTable-1].getText();
                        }

                        try{
                            dbObj.insertValues("PredictedReturn", returnTable);
                        }catch(Exception err){
                            ErrorFrame error = new ErrorFrame("Please enter valid values");
                        }

                        String[] growthTable= new String[thirdTable];
                        growthTable[0] = "'"+textFieldArray[0].getText()+"'";
                        for(int i = 1; i<thirdTable;i++){
                            growthTable[i] = textFieldArray[i+firstTable+secondTable-2].getText();
                        }
                        try{
                            dbObj.insertValues("PredictedGrowth", growthTable);
                        }catch(Exception err){
                            ErrorFrame error = new ErrorFrame("Please enter valid values");
                        }
                        StockFuture newStock = new StockFuture(stockData());
                    }

                    //System.out.println("this will insert data into databsae and compute other parameters");
                    

                }else{
                    updateLabel.setVisible(true);
                    this.validate();
                    this.repaint();
                    alreadyClicked = true;
                }
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
    public Stock stockData(){
       
        String[] temp = new String[23];
        
        for(int i = 0; i<20; i++){
            temp[i] = textFieldArray[i].getText();
        }
        
        temp[20] = textFieldArray[29].getText();
        temp[21] = textFieldArray[20].getText();
        temp[22] = textFieldArray[23].getText();
        
        Stock stockInfo = new Stock(temp);
        
        return stockInfo;
    }
    
}
