/*
Has all the methods for db access and manipulation
 */
//package stockanalysis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseAccess {
    //instance variables
    private String dbName;
    private Object[][] data;
    private Connection dbConn;
    
    
    //overloaded constructors
    public DataBaseAccess(String dbName){
        this.dbName = dbName;
        this.data = null;
        setDbConn();
    }
    public DataBaseAccess(){
        this.dbName = ("");
        this.data = null;
        this.dbConn = null;
    }
    
    //create db
    public void createDb(String newDbName){
        this.dbName = newDbName;
        String connectionURL = "jdbc:derby:" + this.dbName + ";create=true";
        
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            this.dbConn = DriverManager.getConnection(connectionURL);
            System.out.println("New database created.");
            this.dbConn.close();
        }catch(Exception err){
            System.out.println("Error creating database: " + newDbName);
            ErrorFrame error = new ErrorFrame("Error Creating DB");
            System.exit(0);
        }
    }

    //getdbname
    public String getDbName() {
        return dbName;
    }

    //set dbname
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
    
    //get data as 2d array
    public Object[][] getData(String tableName, String[] tableHeaders) {
        
        int columnCount = tableHeaders.length;
        ResultSet rs = null;
        Statement s = null;
        String dbQuery = "SELECT * FROM " + tableName;
        ArrayList<ArrayList<String>> dataList = new ArrayList<>();
        
        try{
            s = this.dbConn.createStatement();
            rs = s.executeQuery(dbQuery);
            while(rs.next()){
                ArrayList<String> row = new ArrayList<String>();
                for(int i = 0; i < columnCount; i++){
                    row.add(rs.getString(tableHeaders[i]));
                }
                dataList.add(row);
            }
            this.data = new Object[dataList.size()][columnCount];
            for(int i = 0; i < dataList.size(); i++){
                ArrayList<String> row = new ArrayList<String>();
                row = dataList.get(i);
                for(int j =0; j < columnCount; j++){
                    this.data[i][j] = row.get(j);
                }
            }
        } catch(SQLException err){
            System.out.println("Unable to get the data from the database");
            ErrorFrame error = new ErrorFrame("Error Getting Data from DB");
            err.printStackTrace();
            //System.exit(0);
        }
        return this.data;
    }
    
    //set data
    public void setData(Object[][] data) {
        this.data = data;
    }

    //get dbconn
    public Connection getDbConn() {
        return dbConn;
    }
    
    //set db conn
    public void setDbConn() {
        this.dbConn = null;
        String connectionURL = "jdbc:derby:" + this.dbName;
        
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            this.dbConn = DriverManager.getConnection(connectionURL, "root", "mysql1");
        } catch(SQLException err){
            ErrorFrame error = new ErrorFrame("SQL Connection error");
            System.out.println("SQL Connection error");
            err.printStackTrace();
            System.exit(0);
            
        } catch(ClassNotFoundException ex){
            System.out.println("Class for name not found");
            System.exit(0);
        }
    }
    
    //create table
    public void createTable(String newTable, String[][] columns, String dbName){
        String createQuerry = "CREATE TABLE "+ newTable+" (";
        
        for(int i = 0; i<columns.length; i++){
            if(i!=columns.length-1){
                
                createQuerry = createQuerry + columns[i][0] + " " + columns[i][1]+",";
                
            }else{
                
                createQuerry = createQuerry + columns[i][0] + " " + columns[i][1];
                
            }
        }
        
        createQuerry = createQuerry + ", PRIMARY KEY ("+columns[0][0]+"))";
        
        Statement s;
        setDbName(dbName);
        setDbConn();
        try{
            s = this.dbConn.createStatement();
            s.execute(createQuerry);
            System.out.println("New table created");
            this.dbConn.close();
        } catch(SQLException err){
            System.out.println("Error creating table " + newTable);
            err.printStackTrace();
            System.out.println(createQuerry);
            System.exit(0);
        }
    }
    
    //insert method
    public void insertValues(String table, String[] values){
        String insertQuerry = "INSERT INTO "+table + " VALUES(";
        for(int i = 0; i<values.length;i++){
            if(i!=values.length-1){
                insertQuerry = insertQuerry + values[i]  + ", ";
            }else{
                insertQuerry = insertQuerry + values[i]  + ")";
            }
        }
        Statement s;
        setDbConn();
        
        try{
            s = this.dbConn.createStatement();
            s.execute(insertQuerry);
            
            this.dbConn.close();
        } catch(SQLException err){
            ErrorFrame error = new ErrorFrame("Error Inserting into table " + table);
            System.out.println("Error inserting into table " + table);
            err.printStackTrace();
            System.out.println(insertQuerry);
            //System.exit(0);
        }
    }
    
    public Object[][] joinTable(String[] tableNames, String[] tableHeaders) {
        Object[][] joinedInfo = null;
        ArrayList<Object [][]> tables = new ArrayList<>();
        for(int place = 0; place<tableNames.length;place++){
            String[] headers;
            if(place == 0){
                headers =new String[20];
                for(int i = 0; i<20; i++){
                    headers[i] = tableHeaders[i];
                }
            }else if (place == 1){
                headers =new String[8];
                headers[0] = "Company";
                for(int i = 1; i<8; i++){
                    headers[i] = tableHeaders[i+19];
                }
            }else{
                headers =new String[10];
                headers[0] = "Company";
                for(int i = 1; i<10; i++){
                    headers[i] = tableHeaders[i+26];
                }
            }
            
            tables.add(getData(tableNames[place], headers));
        }
        
        Object[][] joinedTable = new Object[tables.get(0).length][tableHeaders.length];
        
        for(int i = 0; i<(tables.get(0)[0].length +tables.get(0)[1].length +tables.get(0)[2].length );i++){
            for(int j = 0; j<tables.get(0).length;j++){
                System.out.println(j);
                
                if(i!= 20 && i !=28){
                    if(i<20){
                        
                        joinedTable[j][i] = tables.get(0)[j][i];
                        
                    }else if(20<i && i<28){
                        
                        joinedTable[j][i-1]=tables.get(1)[j][i-20];
                        
                    }else{
                        joinedTable[j][i-2]=tables.get(2)[j][i-28];
                    }
                }
            }
        }
        return joinedInfo;
    }
    
    //delete
    public void deleteInfo(String primaryKey, String value, String tableName){
        String deleteQuerry = "DELETE FROM "+tableName+ " WHERE " +primaryKey+ " = "+value;
        Statement s;
        setDbConn();
        
        try{
            s = this.dbConn.createStatement();
            s.execute(deleteQuerry);
            this.dbConn.close();
        } catch(SQLException err){
            ErrorFrame error = new ErrorFrame("Error Deleting from table "+tableName);
            System.out.println("Error deleting from table " + tableName);
            err.printStackTrace();
            System.out.println(deleteQuerry);
           // System.exit(0);
        }
    }
    
    //update method
    public void updateData(String table, String primaryKey, String value, String[][] values){
        String updateQuerry = "UPDATE " + table + " set ";
        
        for(int i = 0; i<values.length;i++ ){
            if(i!=values.length-1){
                
                if(values[i][0].equals("Sector") || values[i][0].equals("Company")){
                    
                   updateQuerry = updateQuerry+values[i][0] + " = " + "'" + values[i][1] +"'" + ", ";
                    
                }else{
                    updateQuerry = updateQuerry+values[i][0] + " = " + values[i][1] + ", ";
                }
                
            }else{
                updateQuerry = updateQuerry+values[i][0] + " = " + values[i][1];
            }
        }
        
        updateQuerry = updateQuerry+" WHERE "+primaryKey + " = "+"'"+value+"'";
        
        Statement s;
        setDbConn();
        
        try{
            s = this.dbConn.createStatement();
            s.execute(updateQuerry);
            this.dbConn.close();
        } catch(SQLException err){
            ErrorFrame error = new ErrorFrame("Error Updating "+table);
            System.out.println("Error updating  " + table);
            System.out.println(updateQuerry);
            err.printStackTrace();
            
            //System.exit(0);
        }
    }
    
    //mainmethod
    public static void main(String[] args) {
        DataBaseAccess test = new DataBaseAccess();
        test.setDbName("sample");
        test.createDb(test.getDbName());
        test.setDbConn();
        String[][] values = {{"test","VarChar(40)"}, {"ici", "Float"}};
        test.createTable("tableau", values, test.getDbName());
        String[] val = {"'hi'", "3"};
        test.deleteInfo("test", "hi", "tableau");
        
        test.insertValues("tae", val);
    } 
}
