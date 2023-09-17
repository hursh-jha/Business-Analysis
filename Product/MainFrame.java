/*
The initial page that the user opens up and can naviage to either entering a stock or comparing a stock
 */
//package stockanalysis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;



public class MainFrame extends JFrame implements ActionListener{
    
    private JButton exiter;
    private JButton enterButton;
    private JButton deleteButton;
    private JButton compareButton;
    private JMenuBar helpBar;
    private JMenuItem helpButton; 
    private JPanel choiceButtons;
    private JPanel allButtons;
    private JLabel description;
    private JLabel imageLabel;
    private JPanel northPanel;
    private final Font LABEL_1_FONT = new Font("Arial",Font.BOLD,52);
    
    public MainFrame(){
        super();
        
        helpButton = new JMenuItem("Help");
        helpButton.addActionListener(this);
        
        java.net.URL welcomePicture = getClass().getResource("coolStock.jpg");
        ImageIcon welcomeIcon = new ImageIcon(new ImageIcon(welcomePicture).getImage().getScaledInstance(600, 500, Image.SCALE_DEFAULT));
        
        description = new JLabel("Stock Analysis");
        
        imageLabel = new JLabel(welcomeIcon);
        
        helpBar = new JMenuBar();
        helpBar.add(helpButton);
        
        allButtons = new JPanel();
        allButtons.setLayout(new BorderLayout());
        
        choiceButtons = new JPanel();
        
        exiter = new JButton("Close");
        exiter.addActionListener(this);
        
        enterButton = new JButton("Enter Stock");
        enterButton.addActionListener(this);
        
        deleteButton = new JButton("Delete Stock");
        deleteButton.addActionListener(this);
        
        compareButton = new JButton("Compare Stocks");
        compareButton.addActionListener(this);
        
        choiceButtons.add(enterButton);
        choiceButtons.add(compareButton);
        
        northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        choiceButtons.add(deleteButton);
        allButtons.add(choiceButtons, BorderLayout.NORTH);
        allButtons.add(exiter, BorderLayout.SOUTH);
        this.setBounds(320, 10, 800, 800);
        this.getContentPane().setBackground(Color.BLUE);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        northPanel.setBackground(Color.BLUE);
        
        description.setFont(LABEL_1_FONT);
        
        northPanel.add(helpBar, BorderLayout.NORTH) ;
        northPanel.add(description, BorderLayout.SOUTH);
        
        
        
        this.add(imageLabel, BorderLayout.CENTER);
        this.add(allButtons, BorderLayout.SOUTH);
        this.add(northPanel,BorderLayout.NORTH);
        
        this.setVisible(true);
        
    }
    
    public static void main(String[] args) {
        MainFrame obj = new MainFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if(command.equals("Enter Stock")){
            EnterStock newStock = new EnterStock();
            this.dispose();
        }else if(command.equals("Compare Stocks")){
            CompareStockSelection stockComparison = new CompareStockSelection();
        }else if(command.equals("Close")){
            this.dispose();
        }else if (command.equals("Help")){
            HelpFrame helper = new HelpFrame();
        }else if (command.equals("Delete Stock")){
            DeleteStock deleter = new DeleteStock();
            this.dispose();
        }
    }
}
