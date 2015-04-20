import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseEngine
{
    ArrayList<AnalysisEngine> engines;
    public DatabaseEngine(ArrayList<AnalysisEngine> engines) {
        this.engines = engines;
        this.engines.forEach(this::enterData);
    }
    private void enterData(AnalysisEngine e) {
        try {
            /* Checks for current years in the table and adds a year value
            to the table if the year value does not exist
             */
            checkYears(e);


            checkTableData(e);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    private void checkTableData(AnalysisEngine e) {
        ArrayList<Integer> years = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        Statement s = null;
        int quarter;
        boolean newQ = false;
        try {
            conn = createConnection();
            s = conn.createStatement();

            int ID = getStockID(e);

            String sql = "SELECT DISTINCT Y.YearValue " +
                         "FROM Stocks.StockYear AS Y CROSS JOIN Stocks.YearData AS D JOIN Stocks.Stock AS S " +
                         "WHERE Y.ID = D.YearID " +
                         "AND D.StockID = " + ID + ";";
            rs = s.executeQuery(sql);

            while (rs.next()) {
                int year = Integer.parseInt(rs.getString("YearValue"));
                if (year != e.year)
                    years.add(Integer.parseInt(rs.getString("YearValue")));
                else {
                    sql = "SELECT Q.QuarterNumber " +
                            "FROM Stocks.StockQuarter AS Q CROSS JOIN Stocks.QuarterData AS D " +
                            "WHERE Q.ID = D.QuarterID;";
                    rs = s.executeQuery(sql);
                    while (rs.next()) {
                        quarter = Integer.parseInt(rs.getString("QuarterNumber"));
                        if (quarter == e.quarter) {
                            newQ = true;
                        }
                    }
                    if (!newQ) {
                        addCurrentYearData(year, e);
                    }
                }
            }
            for (int i = 0; i < e.years.size(); i++)
            {
                if (!years.contains(e.years.get(i))) {
                    if (e.quarter == 4 && i == e.years.size() - 1)
                    {

                    }else {
                        addOldYearData(e.years.get(i), e);
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally
        {
            closeConnection(rs, s, conn);
        }
    }

    private int getStockID (AnalysisEngine e) {
        int stockID = 0;
        String stockSQL = "SELECT Stocks.Stock.ID FROM Stocks.Stock " +
                "WHERE Stocks.Stock.StockSymbol = '" + e.symbol + "';";

        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        try {
            conn = createConnection();
            s = conn.createStatement();

            rs = s.executeQuery(stockSQL);
            if (rs.next())
                stockID = Integer.parseInt(rs.getString("ID"));

        } catch (Exception ex) {

        }
        finally
        {
            closeConnection(rs, s, conn);
        }
        return stockID;
    }


    private void addCurrentYearData(int year, AnalysisEngine e) {
        int index = e.years.indexOf(year);

        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        String sql;

        try {
            conn = createConnection();
            s = conn.createStatement();

            int buys = 0, sells = 0;

            for (int i = 0; i < e.insiderBuys.size(); i++) {
                buys += e.insiderBuys.get(i);
            }
            for (int i = 0; i < e.insiderSells.size(); i++) {
                sells += e.insiderSells.get(i);
            }

            int yearID = 0;
            int quarterID = 0;
            int stockID = 0;
            String stockSQL = "SELECT Stocks.Stock.ID FROM Stocks.Stock " +
                    "WHERE Stocks.Stock.StockSymbol = '" + e.symbol + "';";

            try {
                rs = s.executeQuery(stockSQL);
                rs.next();
                stockID = Integer.parseInt(rs.getString("ID"));

            } catch (Exception ex) {
                if (rs == null)
                    addStock(e.symbol, e.stockName);
            }
            String quarterSQL = "SELECT Stocks.StockQuarter.ID FROM Stocks.StockQuarter " +
                    "WHERE Stocks.StockQuarter.QuarterNumber = '" + e.quarter + "';";

            try {
                rs = s.executeQuery(quarterSQL);
                rs.next();
                quarterID = Integer.parseInt(rs.getString("ID"));

            } catch (Exception ex) {

            }


            String yearSQL = "SELECT Stocks.StockYear.ID FROM Stocks.StockYear " +
                    "WHERE Stocks.StockYear.YearValue = " + year + ";";

            try {
                rs = s.executeQuery(yearSQL);
                rs.next();
                yearID = Integer.parseInt(rs.getString("ID"));
            } catch (Exception ex) {

            }

            sql =   "INSERT INTO Stocks.QuarterData (" +
                    "YearID, QuarterID, StockID, Price, PERatio, DividendYield, Timeliness, Safety, HighProj, LowProj, " +
                    "InsiderBuys, InsiderSells, RevenuesPerShare, CashFlowPerShare, EarningsPerShare, " +
                    "BookValuePerShare, AverageAnnualPERatio, AverageAnnualDividendYield, Revenues, NetProfit, " +
                    "NetProfitMargin, LongTermDebt, ReturnOnShareEquity, TotalDebt, MarketCap, CurrentAssets, " +
                    "CurrentLiability, AnnualRevenuesPast10, AnnualRevenuesPast5, AnnualRevenuesFuture5, " +
                    "AnnualEarningsPast10, AnnualEarningsPast5, AnnualEarningsFuture5,  AnnualDividendsPast10, " +
                    "AnnualDividendsPast5, AnnualDividendsFuture5, AnnualBookValuePast10, AnnualBookValuePast5, " +
                    "AnnualBookValueFuture5, Strength, PriceStability, GrowthPersistence, EarningsPredictability) " +
                    "VALUES (" +
                    "" + yearID + ", " + quarterID + ", " + stockID + ", " +
                    "" + e.recentPrice + ", " + e.PERatio + ", " + e.dividendYield + ", " + e.timeliness + ", " + e.safety + ", " +
                    "" + e.highProjections[0] + ", " + e.lowProjections[0] + ", " + buys + ", " + sells + ", " +
                    "" + e.revenuesPerShare.get(index) + ", " + e.cashFlowPerShare.get(index) + ", " +
                    "" + e.earningsPerShare.get(index) + ", " + e.bookValuePerShare.get(index) + ", " +
                    "" + e.averageAnnualPERatio.get(index) + ", " + e.averageAnnualDividendYield.get(index) + ", " +
                    "" + e.revenues.get(index) + ", " + e.netProfit.get(index) + ", " +
                    "" + e.netProfitMargin.get(index) + ", " + e.longTermDebt.get(index) + ", " +
                    "" + e.returnOnShareEquity.get(index) + ", '" + e.totalDebt + "', '" + e.marketCap + "', " +
                    "" + e.currentAssets.get(e.currentAssets.size() - 1) + ", " + e.currentLiability.get(e.currentLiability.size() - 1) + ", " +
                    "" + e.annualRevenues.get(0) + ", " + e.annualRevenues.get(1) + ", " + e.annualRevenues.get(2) + ", " +
                    "" + e.annualEarnings.get(0) + ", " + e.annualEarnings.get(1) + ", " + e.annualEarnings.get(2) + ", " +
                    "" + e.annualDividends.get(0) + ", " + e.annualDividends.get(1) + ", " + e.annualDividends.get(2) + ", " +
                    "" + e.annualBookValue.get(0) + ", " + e.annualBookValue.get(1) + ", " + e.annualBookValue.get(2) + ", " +
                    "'" + e.companyStrength + "', " + e.priceStability + ", " + e.growthPersistence + ", " + e.predictability +
            ");";

            s.executeUpdate(sql);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally
        {
            closeConnection(rs, s, conn);
        }
    }

    private void addOldYearData(int year, AnalysisEngine e) {
        int index = e.years.indexOf(year);
        int currentAssetYear = 0, currentAssetIndex = 0;

        Connection conn = null;
        ResultSet rs = null;
        Statement s = null;
        String sql;

        try {
            conn = createConnection();
            s = conn.createStatement();

            int yearID = 0;
            int stockID = 0;
            String stockSQL = "SELECT Stocks.Stock.ID FROM Stocks.Stock " +
                    "WHERE Stocks.Stock.StockSymbol = '" + e.symbol + "';";

            try {
                rs = s.executeQuery(stockSQL);
                rs.next();
                stockID = Integer.parseInt(rs.getString("ID"));

            } catch (Exception ex) {
                if (rs == null || stockID == 0)
                    addStock(e.symbol, e.stockName);
            }


            String yearSQL = "SELECT Stocks.StockYear.ID FROM Stocks.StockYear " +
                    "WHERE Stocks.StockYear.YearValue = " + year + ";";

            try {
                rs = s.executeQuery(yearSQL);
                rs.next();
                yearID = Integer.parseInt(rs.getString("ID"));
            } catch (Exception ex) {

            }

            sql =   "INSERT INTO Stocks.YearData (" +
                    "YearID, StockID, HighProj, LowProj, RevenuesPerShare, CashFlowPerShare, EarningsPerShare, " +
                    "BookValuePerShare, AverageAnnualPERatio, AverageAnnualDividendYield, Revenues, NetProfit, " +
                    "NetProfitMargin, LongTermDebt, ReturnOnShareEquity, CurrentAssets, CurrentLiability";

            sql +=  ") VALUES (" +
                    "" + yearID + ", " +
                    "" + stockID + ", " +
                    "" + e.yearHighs.get(index) + ", " + e.yearLows.get(index) + ", " +
                    "" + e.revenuesPerShare.get(index) + ", " + e.cashFlowPerShare.get(index) + ", " +
                    "" + e.earningsPerShare.get(index) + ", " + e.bookValuePerShare.get(index) + ", " +
                    "" + e.averageAnnualPERatio.get(index) + ", " + e.averageAnnualDividendYield.get(index) + ", " +
                    "" + e.revenues.get(index) + ", " + e.netProfit.get(index) + ", " +
                    "" + e.netProfitMargin.get(index) + ", " + e.longTermDebt.get(index) + ", " +
                    "" + e.returnOnShareEquity.get(index);
            if (currentAssetYear == year) {
                sql += ", " + e.currentAssets.get(currentAssetIndex) + ", " + e.currentLiability.get(currentAssetIndex);
            }
            else
            {
                sql += ", -0.0, -0.0";
            }
            sql +=  ");";
            s.executeUpdate(sql);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally
        {
            closeConnection(rs, s, conn);
        }
    }

    private void checkYears(AnalysisEngine e) {
        ArrayList<Integer> currentYears = getYears();

        for (int i = 0; i < e.years.size(); i++) {
            if (!currentYears.contains(e.years.get(i))) {
                insertYear(e.years.get(i));
            }
        }
    }
    private void addStock(String symbol, String stockName) {
        Connection conn = null;
        Statement s = null;
        try {
            conn = createConnection();
            s = conn.createStatement();

            String sql ="INSERT INTO Stocks.Stock (Stocks.Stock.StockName, Stocks.Stock.StockSymbol) " +
                    "VALUES ('" + stockName + "', '" + symbol + "');";
            s.executeUpdate(sql);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally
        {
            closeConnection(null, s, conn);
        }
    }
    private void insertYear(int year) {
        Connection conn = null;
        Statement s = null;
        try {
            conn = createConnection();
            s = conn.createStatement();

            String sql ="INSERT INTO Stocks.StockYear (YearValue)" +
                        "VALUES (" + year + ");";
            s.executeUpdate(sql);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally
        {
            closeConnection(null, s, conn);
        }
    }
    private ArrayList<Integer> getYears() {
        ArrayList<Integer> years = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        Statement s = null;
        try {
            conn = createConnection();
            s = conn.createStatement();

            String sql = "SELECT YearValue FROM Stocks.StockYear";
            rs = s.executeQuery(sql);

            while (rs.next()) {
                years.add(Integer.parseInt(rs.getString("YearValue")));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally
        {
            closeConnection(rs, s, conn);
        }
        return years;
    }
    private Connection createConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password=Stocks123");
        } catch (Exception e) {e.printStackTrace();}
        return conn;
    }
    private void closeConnection(ResultSet rs, Statement s, Connection conn) {
        try
        {
            if (rs != null) {
                rs.close();
            }
            if (s != null) {
                s.close();
            }
            if (conn != null) {
                conn.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
