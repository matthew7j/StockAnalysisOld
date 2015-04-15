import java.util.ArrayList;

public class DataAnalysisEngine
{
    AnalysisEngine engine;
    public DataAnalysisEngine(AnalysisEngine engine){
        this.engine = engine;
        analyze();
    }
    private void analyze(){
        if (analyzeYears()) {
            System.out.println("\nNew Years: \n");
            for (int i = 0; i < engine.years.size(); i++){
                System.out.print(engine.years.get(i) + " ");
            }
        }
    }

    private boolean analyzeYears(){
        ArrayList<Integer> years = engine.years;
        int previousYear = 0, currentYear;

        for (int i = 0; i < years.size(); i++){
            currentYear = years.get(i);
            if (previousYear >= currentYear) {
                years.remove(previousYear);
                return true;
            }
            if (currentYear - previousYear != 1){
                years.remove(previousYear);
                return true;
            }
        }
        return false;
    }
}
