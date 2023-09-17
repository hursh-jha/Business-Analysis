/*
 This is responsible for being able to install the initial database
 */
//package stockanalysis;

import java.sql.Connection;

public class Install {
    
    private static String newTable;
        private static DataBaseAccess objDb = new DataBaseAccess();
        private static Connection myDbConn;
       
    public static void main(String[] args) {
        
        objDb.setDbName("StockInformation");
        objDb.createDb(objDb.getDbName());
        objDb.setDbConn();
        
        
        
        String[][] columns = {
            {"Company", "varchar(30)"}, 
            {"Sector", "varchar(30)"},
            {"Price", "Float"},
            {"EPS", "Float"},
            {"NetIncome", "Float"},
            {"ShareHolderEquity", "Float"},
            {"EnterpriseValue", "Float"},
            {"MarketCap", "Float"},
            {"AltmanZScore", "Float"},
            {"PiotroskiFScore", "Float"},
            {"ModifiedCScore", "Float"},
            {"EarningYield", "Float"},
            {"OperatingMargin", "Float"},
            {"FreeCashFlow", "Float"},
            {"LongTermDebt", "Float"},
            {"NetWorth", "Float"},
            {"DividendYield", "Float"},
            {"EnterpriseValueEBIDTA", "Float"},
            {"PriceSales", "Float"},
            {"CashFlow", "Float"}
        };
                
        
        objDb.createTable("StockValues", columns,"StockInformation");
        
        String[][] columnsFuture = {
            {"Company", "varchar(30)"}, 
            {"OneDayReturn", "Float"},
            {"OneWeekReturn", "Float"},
            {"OneMonthReturn", "Float"},
            {"OneYearReturn", "Float"},
            {"ThreeYearReturn", "Float"},
            {"FiveYearReturn", "Float"},
            {"TenYearReturn", "Float"}
        };
        
        objDb.createTable("PredictedReturn", columnsFuture, "StockInformation");
        
        String[][] columnsGrowth = {
            {"Company", "varchar(30)"}, 
            {"OneYearRevenueGrowth", "Float"},
            {"OneYearEPSGrowth", "Float"},
            {"OneYearBookValueGrowth", "Float"},
            {"ThreeYearRevenueGrowth", "Float"},
            {"ThreeYearEPSGrowth", "Float"},
            {"ThreeYearBookValueGrowth", "Float"},
            {"FiveYearRevenueGrowth", "Float"},
            {"FiveYearEPSGrowth", "Float"},
            {"FiveYearBookValueGrowth", "Float"}
        };
        
        objDb.createTable("PredictedGrowth", columnsGrowth, "StockInformation");
    }
}
