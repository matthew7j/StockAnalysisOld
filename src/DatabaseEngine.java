import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseEngine
{
    ArrayList<AnalysisEngine> engines;
    public DatabaseEngine( ArrayList<AnalysisEngine> engines) {
        this.engines = engines;
    }
    private void enterData(AnalysisEngine e) {
        //Class.forName("com.mysql.jdbc.Driver").newInstance();

    }
}
