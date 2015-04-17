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

        }

    }
    private void checkTableData(AnalysisEngine e) {
        ArrayList<Integer> years = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        Statement s = null;
        try {
            conn = createConnection();
            s = conn.createStatement();

            String sql = "SELECT Stocks.Years.YearValue " +
                         "FROM Stocks.Years AS Y CROSS JOIN Stocks.Data AS D " +
                         "WHERE Y.YearID = D.ID;";
            rs = s.executeQuery(sql);

            while (rs.next()) {
                int year = Integer.parseInt(rs.getString("YearValue"));
                if (year != e.year)
                    years.add(Integer.parseInt(rs.getString("YearValue")));
            }
            for (int i = 0; i < e.years.size(); i++)
            {
                if (!years.contains(e.years.get(i))) {
                    addYearData(e.years.get(i), e);
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
    private void addYearData(int year, AnalysisEngine e) {
        int index = (e.years.size() - 1) - e.years.indexOf(year);

        Connection conn = null;
        ResultSet rs = null;
        Statement s = null;

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

            String sql ="INSERT INTO Stocks.StockQuarter (" +
                    "YearID, QuarterID, StockID, Price, P/E, Dividend Yield, Timeliness, Safety, HighProj, HighProjGain, " +
                    "HighProjAnnualReturn, LowProj, " +
                    "InsiderBuys, InsiderSells, Revenues Per Share, Cash Flow Per Share, Earnings Per Share, " +
                    "Book Value Per Share, Average Annual P/E Ratio, Average Annual Dividend Yield, Revenues, Net Profit, " +
                    "Net Profit Margin, Long-term Debt, Return on Share Equity, Total Debt, Market Cap, Current Assets, " +
                    "Current Liabilities, Annual Revenues, Annual Cash Flow, Annual Earnings, Annual Dividends, " +
                    "Annual Book Value, Strength, Price Stability, Growth Persistence, Earnings Predictability) " +
                    "VALUES (" +
                    "SELECT Stocks.Years.ID FROM Stocks.Years WHERE Stocks.Years.YearValue = " + year + ", " +
                    "SELECT Stocks.Quarters.ID FROM Stocks.Quarters WHERE Stocks.Quarters.QuarterValue = " + e.quarter + ", " +
                    "SELECT Stocks.Stock.ID FROM Stocks.Stock WHERE Stocks.Stock.Symbol = " + e.symbol + ", " +
                    "" + e.recentPrice + ", " + e.PERatio + ", " + e.dividendYield + ", " + e.timeliness + ", " + e.safety + ", " +
                    "" + e.highProjections[0] + ", " + e.highProjections[1] + ", " + e.highProjections[2] + ", " + e.lowProjections[0] + ", " +
                    "" + e.lowProjections[1] + ", " + e.lowProjections[2] + ", " + buys + ", " + sells + ", " +
                    "" + e.revenuesPerShare.get(e.revenuesPerShare.size() - index) + ", " + e.cashFlowPerShare.get(e.cashFlowPerShare.size() - index) + ", " +
                    "" + e.earningsPerShare.get(e.earningsPerShare.size() - index) + ", " + e.bookValuePerShare.get(e.bookValuePerShare.size() - index) + ", " +
                    "" + e.averageAnnualPERatio.get(e.averageAnnualPERatio.size() - index) + ", " + e.averageAnnualDividendYield.get(e.averageAnnualDividendYield.size() - index) + ", " +
                    "" + e.revenues.get(e.revenues.size() - index) + ", " + e.netProfit.get(e.netProfit.size() - index) + ", " +
                    "" + e.netProfitMargin.get(e.netProfitMargin.size() - index) + ", " + e.longTermDebt.get(e.longTermDebt.size() - index) + ", " +
                    "" + e.returnOnShareEquity.get(e.returnOnShareEquity.size() - index) + ", " +
            ")";
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
    private void insertYear(int year) {
        Connection conn = null;
        ResultSet rs = null;
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
            closeConnection(rs, s, conn);
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
