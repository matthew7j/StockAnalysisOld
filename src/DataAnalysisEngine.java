import java.util.ArrayList;

public class DataAnalysisEngine
{
    AnalysisEngine engine;
    ArrayList<Integer> bottomHalfFrequency;
    ArrayList<ArrayList<Double>> tableValues;
    public DataAnalysisEngine(AnalysisEngine engine){
        this.engine = engine;
        analyze();
    }
    private void analyze(){
        getBottomHalfFrequency();
        getTableValues();
        if (analyzeYears()) {
            System.out.println("\nNew Years: \n");
            for (int i = 0; i < engine.years.size(); i++){
                System.out.print(engine.years.get(i) + " ");
            }
        }
    }

    private void getBottomHalfFrequency(){
        bottomHalfFrequency.add(engine.revenuesPerShare.size());
        bottomHalfFrequency.add(engine.cashFlowPerShare.size());
        bottomHalfFrequency.add(engine.earningsPerShare.size());
        bottomHalfFrequency.add(engine.bookValuePerShare.size());
        bottomHalfFrequency.add(engine.averageAnnualPERatio.size());
        bottomHalfFrequency.add(engine.averageAnnualDividendYield.size());
        bottomHalfFrequency.add(engine.revenues.size());
        bottomHalfFrequency.add(engine.netProfit.size());
        bottomHalfFrequency.add(engine.netProfitMargin.size());
        bottomHalfFrequency.add(engine.longTermDebt.size());
        bottomHalfFrequency.add(engine.returnOnShareEquity.size());
    }

    private void getTableValues(){
        tableValues.add(engine.revenuesPerShare);
        tableValues.add(engine.cashFlowPerShare);
        tableValues.add(engine.earningsPerShare);
        tableValues.add(engine.bookValuePerShare);
        tableValues.add(engine.averageAnnualPERatio);
        tableValues.add(engine.averageAnnualDividendYield);
        tableValues.add(engine.revenues);
        tableValues.add(engine.netProfit);
        tableValues.add(engine.netProfitMargin);
        tableValues.add(engine.longTermDebt);
        tableValues.add(engine.returnOnShareEquity);
    }

    private boolean analyzeYears(){
        int previousYear = 0, currentYear;

        for (int i = 0; i < engine.years.size(); i++){
            currentYear = engine.years.get(i);
            if (previousYear >= currentYear) {
                engine.years.remove(previousYear);
                return true;
            }
            if (currentYear - previousYear != 1){
                engine.years.remove(previousYear);
                return true;
            }
        }
        return false;
    }
    private void anaylzeTable(){
        for (int i = 0; i < tableValues.size(); i++){
            if (tableValues.get(i).size() > 1) {
                for (int j = 0; j < tableValues.get(i).size(); j++){
                    if (!String.valueOf(tableValues.get(i).get(j)).contains(".")){
                        tableValues.get(i).remove(j);
                    }
                }
            }
        }
    }
}
