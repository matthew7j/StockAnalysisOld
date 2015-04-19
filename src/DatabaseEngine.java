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

            String sql = "SELECT Y.YearValue " +
                         "FROM Stocks.StockYear AS Y CROSS JOIN Stocks.YearData AS D " +
                         "WHERE Y.ID = D.YearID;";
            rs = s.executeQuery(sql);

            while (rs.next()) {
                int year = Integer.parseInt(rs.getString("YearValue"));
                if (year != e.year)
                    years.add(Integer.parseInt(rs.getString("YearValue")));
                else {
                    sql = "SELECT Stocks.StockQuarter.Number " +
                            "FROM Stocks.StockQuarter AS Q CROSS JOIN Stocks.Data AS D " +
                            "WHERE Q.QuarterID = D.QuarterID;";
                    rs = s.executeQuery(sql);
                    while (rs.next()) {
                        quarter = Integer.parseInt(rs.getString("Number"));
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
                    addOldYearData(e.years.get(i), e);
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

    private void addCurrentYearData(int year, AnalysisEngine e) {
        int index = (e.years.size() - 1) - e.years.indexOf(year);

        Connection conn = null;
        Statement s = null;
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

            sql =   "INSERT INTO Stocks.QuarterData (" +
                    "YearID, QuarterID, StockID, Price, PERatio, DividendYield, Timeliness, Safety, HighProj, LowProj, " +
                    "InsiderBuys, InsiderSells, Revenues PerShare, CashFlowPerShare, Earnings PerShare, " +
                    "BookValuePerShare, AverageAnnualPERatio, AverageAnnualDividendYield, Revenues, NetProfit, " +
                    "NetProfitMargin, LongTermDebt, ReturnOnShareEquity, TotalDebt, MarketCap, CurrentAssets, " +
                    "CurrentLiabilities, AnnualRevenuesPast10, AnnualRevenuesPast5, AnnualRevenues Future5, " +
                    "AnnualEarningsPast10, AnnualEarningsPast5, AnnualEarningsFuture5,  AnnualDividends Past10, " +
                    "AnnualDividendsPast5, AnnualDividendsFuture5, AnnualBookValuePast10, AnnualBookValuePast5, " +
                    "AnnualBookValueFuture 5, Strength, PriceStability, GrowthPersistence, EarningsPredictability) " +
                    "VALUES (" +
                    "SELECT Stocks.StockYear.ID FROM Stocks.StockYear WHERE Stocks.StockYear.YearValue = " + year + ", " +
                    "SELECT Stocks.StockQuarter.ID FROM Stocks.StockQuarter WHERE Stocks.StockQuarter.QuarterValue = " + e.quarter + ", " +
                    "SELECT Stocks.Stock.ID FROM Stocks.Stock WHERE Stocks.Stock.Symbol = " + e.symbol + ", " +
                    "" + e.recentPrice + ", " + e.PERatio + ", " + e.dividendYield + ", " + e.timeliness + ", " + e.safety + ", " +
                    "" + e.highProjections[0] + ", " + e.highProjections[1] + ", " + e.highProjections[2] + ", " + e.lowProjections[0] + ", " +
                    "" + e.lowProjections[1] + ", " + e.lowProjections[2] + ", " + buys + ", " + sells + ", " +
                    "" + e.revenuesPerShare.get(e.revenuesPerShare.size() - index) + ", " + e.cashFlowPerShare.get(e.cashFlowPerShare.size() - index) + ", " +
                    "" + e.earningsPerShare.get(e.earningsPerShare.size() - index) + ", " + e.bookValuePerShare.get(e.bookValuePerShare.size() - index) + ", " +
                    "" + e.averageAnnualPERatio.get(e.averageAnnualPERatio.size() - index) + ", " + e.averageAnnualDividendYield.get(e.averageAnnualDividendYield.size() - index) + ", " +
                    "" + e.revenues.get(e.revenues.size() - index) + ", " + e.netProfit.get(e.netProfit.size() - index) + ", " +
                    "" + e.netProfitMargin.get(e.netProfitMargin.size() - index) + ", " + e.longTermDebt.get(e.longTermDebt.size() - index) + ", " +
                    "" + e.returnOnShareEquity.get(e.returnOnShareEquity.size() - index) + ", " + e.totalDebt + ", " + e.marketCap + ", " +
                    "" + e.currentAssets.get(e.currentAssets.size() - 1) + ", " + e.currentLiability.get(e.currentLiability.size() - 1) + ", " +
                    "" + e.annualRevenues.get(0) + ", " + e.annualRevenues.get(1) + ", " + e.annualRevenues.get(2) + ", " +
                    "" + e.annualEarnings.get(0) + ", " + e.annualEarnings.get(1) + ", " + e.annualEarnings.get(2) + ", " +
                    "" + e.annualDividends.get(0) + ", " + e.annualDividends.get(1) + ", " + e.annualDividends.get(2) + ", " +
                    "" + e.annualBookValue.get(0) + ", " + e.annualBookValue.get(1) + ", " + e.annualBookValue.get(2) + ", " +
                    "" + e.companyStrength + ", " + e.priceStability + ", " + e.growthPersistence + ", " + e.predictability +
            ");";

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

    private void addOldYearData(int year, AnalysisEngine e) {
        int index = (e.years.size() - 1) - e.years.indexOf(year);
        int currentAssetYear = 0, currentAssetIndex = 0;

        Connection conn = null;
        Statement s = null;
        String sql;

        try {
            conn = createConnection();
            s = conn.createStatement();

            sql =   "INSERT INTO Stocks.YearData (" +
                    "YearID, QuarterID, StockID, HighProj, LowProj, RevenuesPerShare, CashFlowPerShare, EarningsPerShare, " +
                    "BookValuePerShare, AverageAnnualPERatio, AverageAnnualDividendYield, Revenues, NetProfit, " +
                    "NetProfitMargin, LongTermDebt, ReturnOnShareEquity";
            if (currentAssetYear == year) {
                sql += ", CurrentAssets, CurrentLiability ";
            }



            sql +=  "VALUES (" +
                    "SELECT Stocks.StockYear.ID FROM Stocks.StockYear WHERE Stocks.StockYear.YearValue = " + year + ", " +
                    "SELECT Stocks.StockQuarter.ID FROM Stocks.StockQuarter WHERE Stocks.StockQuarter.QuarterValue = " + 0 + ", " +
                    "SELECT Stocks.Stock.ID FROM Stocks.Stock WHERE Stocks.Stock.Symbol = " + e.symbol + ", " +
                    "" + e.yearHighs.get(e.yearHighs.size() - index) + ", " + e.yearLows.get(e.yearLows.size() - index) + ", " +
                    "" + e.revenuesPerShare.get(e.revenuesPerShare.size() - index) + ", " + e.cashFlowPerShare.get(e.cashFlowPerShare.size() - index) + ", " +
                    "" + e.earningsPerShare.get(e.earningsPerShare.size() - index) + ", " + e.bookValuePerShare.get(e.bookValuePerShare.size() - index) + ", " +
                    "" + e.averageAnnualPERatio.get(e.averageAnnualPERatio.size() - index) + ", " + e.averageAnnualDividendYield.get(e.averageAnnualDividendYield.size() - index) + ", " +
                    "" + e.revenues.get(e.revenues.size() - index) + ", " + e.netProfit.get(e.netProfit.size() - index) + ", " +
                    "" + e.netProfitMargin.get(e.netProfitMargin.size() - index) + ", " + e.longTermDebt.get(e.longTermDebt.size() - index) + ", " +
                    "" + e.returnOnShareEquity.get(e.returnOnShareEquity.size() - index);
            if (currentAssetYear == year) {
                sql += ", " + e.currentAssets.get(currentAssetIndex) + ", " + e.currentLiability.get(currentAssetIndex);
            }
            sql +=  ");";
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

    private void checkYears(AnalysisEngine e) {
        ArrayList<Integer> currentYears = getYears();

        for (int i = 0; i < e.years.size(); i++) {
            if (!currentYears.contains(e.years.get(i))) {
                insertYear(e.years.get(i));
            }
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
