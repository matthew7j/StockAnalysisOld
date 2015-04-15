import java.util.ArrayList;

public class DataAnalysisEngine
{
    AnalysisEngine engine;
    ArrayList<ArrayList<Double>> tableValues = new ArrayList<ArrayList<Double>>();
    ArrayList<String> tableValueStrings = new ArrayList<String>();

    ArrayList<Integer> changed = new ArrayList<Integer>();

    public DataAnalysisEngine(AnalysisEngine engine){
        this.engine = engine;
        analyze();
    }
    private void analyze(){
        getTableValues();
        if (analyzeYears()) {
            System.out.println("\nNew Years: \n");
            for (int i = 0; i < engine.years.size(); i++){
                System.out.print(engine.years.get(i) + " ");
            }
        }
        if (analyzeTable()){
            System.out.println("\nNew Values: ");
            for (int i = 0; i < changed.size(); i++){
                for (int j = 0; j < tableValues.get(changed.get(i)).size(); j++) {
                    System.out.print(tableValues.get(changed.get(i)).get(j) + " ");
                }
                System.out.println();
            }
        }
    }

    private void getTableValues(){
        tableValues.add(engine.revenuesPerShare);
        tableValueStrings.add("Revenues Per Share");
        tableValues.add(engine.cashFlowPerShare);
        tableValueStrings.add("Cash Flow Per Share");
        tableValues.add(engine.earningsPerShare);
        tableValueStrings.add("Earnings Per Share");
        tableValues.add(engine.bookValuePerShare);
        tableValueStrings.add("Book Value Per Share");
        tableValues.add(engine.averageAnnualPERatio);
        tableValueStrings.add("Average Annual PE Ratio");
        tableValues.add(engine.averageAnnualDividendYield);
        tableValueStrings.add("Average Annual Dividend Yield");
        tableValues.add(engine.revenues);
        tableValueStrings.add("Revenues");
        tableValues.add(engine.netProfit);
        tableValueStrings.add("Net Profit");
        tableValues.add(engine.netProfitMargin);
        tableValueStrings.add("Net Profit Margin");
        tableValues.add(engine.longTermDebt);
        tableValueStrings.add("Long Term Debt");
        tableValues.add(engine.returnOnShareEquity);
        tableValueStrings.add("Return on Share Equity");
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
    private boolean analyzeTable(){
        boolean returnVal = false;
        for (int i = 0; i < tableValues.size(); i++){
            boolean zeroFound = false;
            if (tableValues.get(i).size() > 1) {
                for (int j = tableValues.get(i).size() - 1; j > -1; j--){
                    if (tableValues.get(i).get(j).equals(-0.0)) {
                        zeroFound = true;
                    }
                    else if (zeroFound) {
                        tableValues.get(i).remove(j);
                        changed.add(i);
                        returnVal = true;
                    }
                }
            }
            else {
                analyzeEmptyValues(i);
            }

        }
        return returnVal;

    }

    private boolean analyzeEmptyValues(int index) {
        switch (tableValueStrings.get(index)){

        }
        return false;
    }
}
