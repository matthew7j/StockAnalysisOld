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
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/?user=root");
            checkYears(e);
        }
        catch (Exception ex) {

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
