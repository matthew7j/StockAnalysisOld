import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DataAnalysisEngine {
    AnalysisEngine engine;
    ArrayList<ArrayList<Double>> tableValues = new ArrayList();

    ArrayList<ArrayList<String>> differentStrings = new ArrayList<>();

    ArrayList<String> tableValueStrings = new ArrayList();

    ArrayList<Integer> changed = new ArrayList();

    public DataAnalysisEngine(AnalysisEngine engine) {
        this.engine = engine;
        fillStrings();
        analyze();
    }

    private void fillStrings() {
        ArrayList<String> revsPerShare = new ArrayList<>();
        revsPerShare.add("Revenues Per Share");
        revsPerShare.add("Revenues per sh");
        ArrayList<String> cashFlowPerShare = new ArrayList<>();
        cashFlowPerShare.add("Cash Flow Per Share");
        cashFlowPerShare.add("\"Cash Flow\" per sh");
        ArrayList<String> earningsPerShare = new ArrayList<>();
        earningsPerShare.add("Earnings Per Share");
        earningsPerShare.add("Earnings per sh");
        ArrayList<String> averageAnnualPERatio = new ArrayList<>();
        averageAnnualPERatio.add("Average Annual PE Ratio");
        averageAnnualPERatio.add("Avg Ann’l P/E Ratio");
        ArrayList<String> averageAnnualDividendYield = new ArrayList<>();
        averageAnnualDividendYield.add("Average Annual Dividend Yield");
        averageAnnualDividendYield.add("Avg Ann’l Div’d Yield");
        ArrayList<String> revenues = new ArrayList<>();
        revenues.add("Revenues");
        revenues.add("Revenues ($mill)");
        ArrayList<String> netProfit = new ArrayList<>();
        netProfit.add("Net Profit");
        netProfit.add("Net Profit ($mill)");
        ArrayList<String> netProfitMargin = new ArrayList<>();
        netProfitMargin.add("Net Profit Margin");
        netProfitMargin.add("Net Profit Margin");
        ArrayList<String> longTermDebt = new ArrayList<>();
        longTermDebt.add("Long Term Debt");
        longTermDebt.add("Long-Term Debt");
        ArrayList<String> returnOnShareEquity = new ArrayList<>();
        returnOnShareEquity.add("Return on Share Equity");
        returnOnShareEquity.add("Return on Shr. Equity");

        differentStrings.add(revsPerShare);
        differentStrings.add(cashFlowPerShare);
        differentStrings.add(earningsPerShare);
        differentStrings.add(averageAnnualPERatio);
        differentStrings.add(averageAnnualDividendYield);
        differentStrings.add(revenues);
        differentStrings.add(netProfit);
        differentStrings.add(netProfitMargin);
        differentStrings.add(longTermDebt);
        differentStrings.add(returnOnShareEquity);
    }

    private void analyze() {
        getTableValues();
        if (analyzeYears()) {
            System.out.println("\nNew Years: \n");
            for (int i = 0; i < engine.years.size(); i++) {
                System.out.print(engine.years.get(i) + " ");
            }
        }
        if (analyzeTable()) {
            System.out.println("\nNew Values: ");
            for (int i = 0; i < changed.size(); i++) {
                for (int j = 0; j < tableValues.get(changed.get(i)).size(); j++) {
                    System.out.print(tableValues.get(changed.get(i)).get(j) + " ");
                }
                System.out.println();
            }
        }
        if (analyzePE()) {
            System.out.println("\nNew P/E Ratio: " + engine.PERatio);
        }
        analyzeAnnualValues();
        confirmValues();
    }

    private void confirmValues() {
        double[][] table = new double[30][30];
        Object[][] values = new Object[30][30];

        for (int i = 1; i < 19; i++) {
            addValuesToTable("Year" + i + "Vertical", table, i - 1);
        }
        addValuesToTable("YearFuture", table, 18);

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < differentStrings.size(); i++) {
            finalizeValues(differentStrings.get(i), values, table);
        }

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                System.out.print(values[i][j] + " ");
            }
            System.out.println();
        }

        compareValues(values);
    }

    private void compareValues(Object[][] values) {
        for (int i = 0; i < engine.yearValues.size(); i++) {
            int row = 0, column = 0;
            for (int j = 0; j < engine.yearValues.get(i).size(); j++) {
                String s = values[row][column++].toString();
                double d = 0.0;
                int consecutive = 0;
                try {
                    d = Double.parseDouble(s);
                }
                catch (Exception e) {

                }
                if (engine.yearValues.get(i).get(j).compareTo(d) == 0) {
                    consecutive++;
                }
                else {
                    if (consecutive > 2) {

                    }
                    else {
                        break;
                    }
                }
            }
        }
    }

    private void finalizeValues(ArrayList<String> s, Object[][] values, double[][] table) {

        /*
        *   The purpose of this method is to search the table array and add the necessary rows into the values array.
        *   Necessary rows are determined by if they are the data for the rows that are relevant for the analysis.
        *   This method also adds the Year Value to the end of each row to be able to easily identify which row holds
        *   which data.
        */

        File file = new File(engine.data + "/YearValues.txt");
        int currentLine = 0;
        int currentRow = 0, currentColumn = 0;

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();

                while (line != null) {
                    for (int i = 0; i < s.size(); i++) {
                        if (line.contains(s.get(i))) {
                            for (int j = 0; j < table[currentLine].length; j++) {
                                values[currentRow][currentColumn++] = table[currentLine][j];
                            }
                            values[currentRow][currentColumn++] = s.get(0);
                            break;
                        }
                    }
                    currentLine++;
                    currentRow++;
                    currentColumn = 0;
                    line = br.readLine();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addValuesToTable(String s, double[][] table, int column) {

        File file = new File(engine.data + "/" + s + ".txt");
        int row = 0;

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();

                while (line != null) {
                    Scanner sc = new Scanner(line);
                    if (sc.hasNextDouble()) {
                        String d = sc.next();
                        table[row++][column] = Double.valueOf(d);
                    }
                    else {
                        String d = sc.next();
                        if (d.contains("%") && d.length() > 1) {
                            String st = d.substring(0, d.indexOf('%'));

                            if (st.contains("(")) {
                                st = st.substring(st.indexOf('(') + 1, st.length());
                            }
                            table[row++][column] = Double.valueOf(st);
                        }
                        else {
                            if (line.contains("d")){
                                line = line.replaceAll("d", "-");
                                if (!line.contains(",")) {
                                    try {
                                        table[row++][column] = Double.valueOf(line);
                                    } catch (Exception e) {
                                        //e.printStackTrace();
                                    }
                                }
                            }
                            else if (line.contains("NFM") || line.contains("NMF") || line.equals("-") || line.equals("--") ||
                                    line.equals("- -")){
                                table[row++][column] = 0.0;
                            }
                            else {
                                table[row++][column] = 0.0;
                                break;
                            }
                        }
                    }
                    line = br.readLine();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getTableValues() {
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

    private boolean analyzeYears() {
        int previousYear = 0, currentYear;
        boolean returnVal = false;

        for (int i = 0; i < engine.years.size(); i++) {
            currentYear = engine.years.get(i);
            if (previousYear >= currentYear) {
                engine.years.add(i, previousYear + 1);
                engine.years.remove(i+1);
                returnVal = true;
            }
            else if (currentYear - previousYear != 1 && i != 0) {
                engine.years.add(i, previousYear + 1);
                engine.years.remove(i+1);
                returnVal = true;
            }
            previousYear = currentYear;
        }
        return returnVal;
    }

    private boolean analyzeTable() {
        boolean returnVal = false;
        for (int i = 0; i < tableValues.size(); i++) {
            boolean zeroFound = false;
            if (tableValues.get(i).size() > 1) {
                for (int j = tableValues.get(i).size() - 1; j > -1; j--) {

                    if (tableValues.get(i).get(j).equals(-0.0)) {
                        zeroFound = true;
                    } else if (zeroFound) {
                        tableValues.get(i).remove(j);
                        changed.add(i);
                        returnVal = true;
                    }
                }
            } else {
                if (analyzeEmptyValues(i)) {
                    changed.add(i);
                    returnVal = true;
                }
            }

        }
        return returnVal;
    }
    private boolean analyzePE() {
        boolean returnVal = false;

        if (String.valueOf(engine.PERatio).substring(String.valueOf(engine.PERatio).indexOf('.') + 1, String.valueOf(engine.PERatio).length()).length() > 1 &&
                String.valueOf(engine.PERatio).substring(String.valueOf(engine.PERatio).length() - 1).equals("1"))
        {
            String s = String.valueOf(engine.PERatio).substring(0, String.valueOf(engine.PERatio).length() - 1);
            engine.PERatio = Double.parseDouble(s);
            returnVal = true;
        }

        return returnVal;
    }

    private void analyzeAnnualValues() {
        if (engine.annualRevenues.size() != 3) {
            engine.annualRevenues = fixAnnualValues(engine.annualRevenues);
        }
        if (engine.annualEarnings.size() != 3) {
            engine.annualEarnings = fixAnnualValues(engine.annualEarnings);
        }
        if (engine.annualBookValue.size() != 3) {
            engine.annualBookValue = fixAnnualValues(engine.annualBookValue);
        }
        if (engine.annualDividends.size() != 3) {
            engine.annualDividends = fixAnnualValues(engine.annualDividends);
        }
    }

    private ArrayList<Double> fixAnnualValues(ArrayList<Double> list) {
        if (list.size() == 0) {
            list.add(0.0);
            list.add(0.0);
            list.add(0.0);
        }
        if (list.size() == 1) {
            if (list.get(0) == 0.0) {
                list.add(0.0);
                list.add(0.0);
            }
            else {
                list.add(0, 0.0);
                list.add(1, 0.0);
            }
        }
        else if (list.size() == 2) {
            if (list.get(0) == 0.0) {
                if (list.get(1) == 0.0) {
                    list.add(0.0);
                }
                else {
                    list.add(0, 0.0);
                }
            }
            else {
                if (list.get(1) != 0.0) {
                    list.add(0.0);
                }
            }
        }
        else if (list.size() == 4) {
            if (list.get(0) == 0.0 && list.get(1) == 0.0) {
                list.remove(0);
            }
        }
        return list;
    }

    private boolean analyzeEmptyValues(int index) {
        boolean returnVal = false;
        String val = tableValueStrings.get(index);
        switch (val) {
            case "Average Annual PE Ratio":
                engine.averageAnnualPERatio.clear();
                engine.averageAnnualPERatio = getDoubleValue("Avg Anal PIE Ratio", "bottomHalf.txt");
                if (engine.averageAnnualPERatio.size() > 0) {
                    returnVal = true;
                }
                tableValues.get(index).clear();
                tableValues.remove(index);
                tableValues.add(index, engine.averageAnnualPERatio);
                break;
            case "Revenues Per Share":
                engine.revenuesPerShare.clear();
                engine.revenuesPerShare = getDoubleValue("Sales per sh", "bottomHalf.txt");
                if (engine.revenuesPerShare.size() > 0) {
                    returnVal = true;
                }
                tableValues.get(index).clear();
                tableValues.remove(index);
                tableValues.add(index, engine.revenuesPerShare);
                break;
            case "Revenues":
                engine.revenues.clear();
                engine.revenues = getDoubleValue("Sales ($mill)", "bottomHalf.txt");
                if (engine.revenues.size() > 0) {
                    returnVal = true;
                }
                tableValues.get(index).clear();
                tableValues.remove(index);
                tableValues.add(index, engine.revenues);
                break;
            case "Average Annual Dividend Yield":
                engine.averageAnnualDividendYield.clear();
                engine.averageAnnualDividendYield = getDoubleValue("Avg Ann'l Died Yield", "bottomHalf.txt");
                if (engine.averageAnnualDividendYield.size() > 0) {
                    returnVal = true;
                }
                tableValues.get(index).clear();
                tableValues.remove(index);
                tableValues.add(index, engine.averageAnnualDividendYield);
                break;
        }
        return returnVal;
    }

    private ArrayList<Double> getDoubleValue(String value, String filename) {
        ArrayList<Double> values = new ArrayList<>();
        ArrayList<Double> test = new ArrayList<>();
        File file = new File(engine.data + "/" + filename);

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();

                while (!line.contains(value)) {
                    String temp;
                    temp = br.readLine();

                    if (temp == null) {
                        break;
                    }
                    line = temp;
                }
                Scanner sc = new Scanner(line);
                if (test.size() > 0) {
                    values.clear();

                    for (int i = 0; i < test.size(); i++) {
                        values.add(test.get(i));
                    }
                } else {
                    while (sc.hasNext()) {
                        if (sc.hasNextDouble()) {
                            String d = sc.next();

                            if (d.compareTo("1") != 0) {
                                if (!d.contains(","))
                                    values.add(Double.parseDouble(d));
                            }
                        } else {
                            String s = sc.next();
                            if (s.contains("%") && s.length() > 1) {
                                String st = s.substring(0, s.indexOf('%'));

                                if (st.contains("(")) {
                                    st = st.substring(st.indexOf('(') + 1, st.length());
                                }
                                values.add(Double.parseDouble(st));
                            }
                            if (s.contains("d")) {
                                s = s.replaceAll("d", "-");
                                if (!s.contains(",")) {
                                    try {
                                        values.add(Double.parseDouble(s));
                                    } catch (Exception e) {
                                        //e.printStackTrace();
                                    }
                                }
                            }
                            if (s.equals("-") || s.equals("--")) {
                                values.add(-0.00);
                            }
                            if (s.equals("NFM")) {
                                values.add(-0.000);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return values;
        } else
            return values;
    }
}
