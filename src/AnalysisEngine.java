import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AnalysisEngine
{
    protected ArrayList<ArrayList<Double>> yearValues;

    protected String data;
    protected String stockName;

    protected int[] projectionYears;

    protected ArrayList<Integer> years;

    protected double[] highProjections;
    protected double[] lowProjections;

    protected ArrayList<Double> annualBookValue;
    protected ArrayList<Double> annualDividends;
    protected ArrayList<Double> annualEarnings;
    protected ArrayList<Double> annualRevenues;
    protected ArrayList<Double> averageAnnualDividendYield;
    protected ArrayList<Double> averageAnnualPERatio;
    protected ArrayList<Double> bookValuePerShare;
    protected ArrayList<Double> cashFlowPerShare;
    protected ArrayList<Double> currentAssets;
    protected ArrayList<Double> currentLiability;
    protected ArrayList<Double> currentPositionYears;
    protected ArrayList<Double> earningsPerShare;
    protected ArrayList<Double> longTermDebt;
    protected ArrayList<Double> netProfit;
    protected ArrayList<Double> netProfitMargin;
    protected ArrayList<Double> returnOnShareEquity;
    protected ArrayList<Double> revenues;
    protected ArrayList<Double> revenuesPerShare;
    protected ArrayList<Double> yearHighs;
    protected ArrayList<Double> yearLows;

    protected ArrayList<Integer> insiderBuys;
    protected ArrayList<Integer> insiderOptions;
    protected ArrayList<Integer> insiderSells;

    protected int growthPersistence;
    protected int predictability;
    protected int priceStability;
    protected int safety;
    protected int timeliness;
    protected int quarter;
    protected int year;

    protected double dividendYield;
    protected double PERatio;
    protected double recentPrice;

    protected String companyStrength;
    protected String insiderDecisionRange;
    protected String marketCap;
    protected String symbol;
    protected String totalDebt;

    public AnalysisEngine(String data){
        this.data = data;
        getQuarterYear();
        this.analysis();
        System.out.println(this.toString());
        new DataAnalysisEngine(this);
    }
    private void getQuarterYear() {
        String s = data.substring(data.lastIndexOf("\\"));
        try {
            int y = Integer.parseInt(s.substring(s.lastIndexOf("_") + 1));
            int start = s.indexOf("_");
            int end = s.indexOf("_", start + 1);
            int m = Integer.parseInt(s.substring(start + 1, end));

            if (m < 4) {
                quarter = 1;
            }
            else if (m < 7) {
                quarter = 2;
            }
            else if (m < 10) {
                quarter = 3;
            }
            else {
                quarter = 4;
            }

            year = 2000 + y;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void analysis(){
        symbol = getSymbol();
        recentPrice = getRecentPrice();
        PERatio = getPERatio();
        dividendYield = getDividendYield();
        timeliness = getTimeliness();
        safety = getSafety();
        highProjections = getHighProjections();
        lowProjections = getLowProjections();
        projectionYears = getProjectionYears();
        insiderDecisionRange = getInsiderDecisionsRange();
        insiderBuys = getInsiderBuyDecisions();
        insiderOptions = getInsiderOptionsDecisions();
        insiderSells = getInsiderSellDecisions();
        years = getYears();
        revenuesPerShare = getDoubleValue("Revenues per sh", "BottomHalf.txt", true);
        cashFlowPerShare = getDoubleValue("Cash Flow", "BottomHalf.txt", true);
        earningsPerShare = getDoubleValue("Earnings per sh", "BottomHalf.txt", true);
        bookValuePerShare = getDoubleValue("Book Value per sh", "BottomHalf.txt", true);
        averageAnnualPERatio = getDoubleValue("Avg Ann'l PIE Ratio", "BottomHalf.txt", true);
        averageAnnualDividendYield = getDoubleValue("Avg Ann'l Div'd Yield", "BottomHalf.txt", true);
        revenues = getDoubleValue("Revenues ($mill)", "BottomHalf.txt", true);
        netProfit = getDoubleValue("Net Profit", "BottomHalf.txt", true);
        netProfitMargin = getDoubleValue("Net Profit Margin", "BottomHalf.txt", true);
        longTermDebt = getDoubleValue("Long-Term Debt", "BottomHalf.txt", true);
        returnOnShareEquity = getDoubleValue("Return on Shr. Equity", "BottomHalf.txt", true);
        currentPositionYears = getDoubleValue("CURRENT POSITION", "CurrentPosition.txt", true);
        currentAssets = getDoubleValue("Current Assets", "CurrentPosition.txt", true);
        currentLiability = getDoubleValue("Current Liab.", "CurrentPosition.txt", true);
        annualRevenues = getDoubleValue("Revenues", "AnnualRates.txt", false);
        annualEarnings = getDoubleValue("Earnings", "AnnualRates.txt", false);
        annualDividends = getDoubleValue("Dividends", "AnnualRates.txt", false);
        annualBookValue = getDoubleValue("Book Value", "AnnualRates.txt", false);
        yearHighs = getDoubleValue("High:", "Hi_Lo.txt", true);
        yearLows = getDoubleValue("Low:", "Hi_Lo.txt", true);
        totalDebt = getTotalDebt();
        marketCap = getMarketCap();
        companyStrength = getStrength();
        priceStability = getStats("Stability");
        growthPersistence = getStats("Persistence");
        predictability = getStats("Predictability");
        stockName = getStockName();
    }

    private String getStockName() {
        File file = new File(data + "/Symbol.txt");
        String name = "";
        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();

                Scanner s = new Scanner(line);

                name = s.next();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return name;
    }

    private ArrayList<Double> checkForFile(String filename, String value){
        ArrayList<Double> values = new ArrayList<>();
        File file = new File(data + "/" + filename + ".txt");

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();

                while (!line.contains(value)){
                    String temp;
                    temp = br.readLine();

                    if (temp == null){
                        break;
                    }
                    line = temp;
                }

                Scanner sc = new Scanner(line);

                while (sc.hasNext()){
                    if (sc.hasNextDouble()) {
                        String d = sc.next();

                        if (d.compareTo("1") != 0){
                            if (!d.contains(","))
                                values.add(Double.parseDouble(d));
                        }
                    }
                    else {
                        String s = sc.next();
                        if (s.contains("%") && s.length() > 1){
                            String st = s.substring(0, s.indexOf('%'));

                            if (st.contains("(")){
                                st = st.substring(st.indexOf('(') + 1, st.length());
                            }
                            values.add(Double.parseDouble(st));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return values;
        }
        else
            return values;
    }
    private String getSymbol(){
        File file = new File(data + "/Symbol.txt");

        if (file.exists()) {
            String symbol = "";
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();
                int index = line.indexOf('.');
                symbol = line.substring(index + 1, line.length());
                symbol = symbol.toUpperCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return symbol;
        }
        else
            return "File does not exist";
    }
    private double getRecentPrice(){
        File file = new File(data + "/RecentPrice.txt");

        if (file.exists()) {
            double price = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();
                price = Double.parseDouble(line);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return price;
        }
        else
            return 0;
    }
    private double getPERatio(){
        File file = new File(data + "/PE_Ratio.txt");

        if (file.exists()) {
            double peRatio = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();
                try {
                    peRatio = Double.parseDouble(line);
                }catch(Exception e){
                    //e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return peRatio;
        }
        else
            return 0;
    }

    private double getDividendYield(){
        File file = new File(data + "/DividendYield.txt");

        if (file.exists()) {
            double dividendYield = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();

                try {
                    dividendYield = Double.parseDouble(line);
                }
                catch (NumberFormatException e){
                    //e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dividendYield;
        }
        else
            return 0;
    }
    private int getSafety(){
        File file = new File(data + "/Safety.txt");

        if (file.exists()) {
            int safety = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();

                try {
                    safety = Integer.parseInt(line.substring(0, 1));
                }catch(Exception e){
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return safety;
        }
        else
            return 0;
    }
    private int getTimeliness(){
        File file = new File(data + "/Timeliness.txt");

        if (file.exists()) {
            int timeliness = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();
                try
                {
                    timeliness = Integer.parseInt(line.substring(0, 1));
                }catch(Exception e){
                    //e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return timeliness;
        }
        else
            return 0;
    }
    private double[] getHighProjections(){
        double[] high = new double[3];
        File file = new File(data + "/Hi_Lo_Proj.txt");

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                int indexStart = 0, indexNext;
                String line = br.readLine();

                while (!line.contains("High")){
                    line = br.readLine();
                }

                for (int i = 0; i < 3; i++)
                {
                    int index;
                    if (i == 0)
                        indexStart = line.indexOf(' ');
                    indexNext = line.indexOf(' ', indexStart + 1);

                    if (i == 1) {
                        high[i] = Double.parseDouble(line.substring(indexStart + 2, indexNext - 2));
                    }
                    else if (i == 2) {
                        index = line.lastIndexOf('%');
                        high[i] = Double.parseDouble(line.substring(indexStart, index));
                    }
                    else
                        high[i] = Double.parseDouble(line.substring(indexStart, indexNext));
                    indexStart = indexNext;
                }


            } catch (Exception e) {
                //e.printStackTrace();
            }
            return high;
        }
        else
            return high;
    }
    private double[] getLowProjections(){
        double[] low = new double[3];
        File file = new File(data + "/Hi_Lo_Proj.txt");

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                int indexStart = 0, indexNext;
                String line = br.readLine();

                while (!line.contains("Low")){
                    line = br.readLine();
                }

                for (int i = 0; i < 3; i++)
                {
                    int index;
                    if (i == 0)
                        indexStart = line.indexOf(' ');
                    indexNext = line.indexOf(' ', indexStart + 1);

                    if (i == 1) {
                        String s = line.substring(indexStart + 2, indexNext - 2);
                        if (s.compareTo("Ni") != 0)
                            low[i] = Double.parseDouble(line.substring(indexStart + 2, indexNext - 2));
                        else
                            low[i] = 0;
                    }
                    else if (i == 2) {
                        index = line.lastIndexOf('%');
                        low[i] = Double.parseDouble(line.substring(indexStart, index));
                    }
                    else
                        low[i] = Double.parseDouble(line.substring(indexStart, indexNext));
                    indexStart = indexNext;
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
            return low;
        }
        else
            return low;
    }
    private int[] getProjectionYears(){
        int[] years = new int[2];
        File file = new File(data + "/Hi_Lo_Proj.txt");

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                int index;
                String line;

                line = br.readLine();
                index = line.indexOf('-');

                years[0] = Integer.parseInt(line.substring(0, index));
                int year = Integer.parseInt(line.substring(index + 1, line.indexOf(' ')));

                if (years[0] % 1000 > 97)
                {
                    int num = years[0] / 100;
                    num++;
                    num *= 100;
                    year += num;
                }
                else
                {
                    int num = years[0] / 100;
                    num *= 100;
                    year += num;
                }
                years[1] = year;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return years;
        }
        else
            return years;
    }
    private String getInsiderDecisionsRange(){

        File file = new File(data + "/Insider_Decisions.txt");
        String returnString = " ";

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line;

                br.readLine();
                line = br.readLine();

                returnString = line.trim();
                returnString = returnString.replaceAll("\t", "");
                returnString = returnString.replaceAll(" ", "");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnString;
    }
    private ArrayList<Integer> getInsiderBuyDecisions(){
        ArrayList<Integer> decisions = new ArrayList<>();
        File file = new File(data + "/Insider_Decisions.txt");

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = "";

                while (!line.contains("Buy")){
                    line = br.readLine();
                }
                Scanner sc = new Scanner(line);
                while (sc.hasNext()){
                    if (sc.hasNextInt()){
                        decisions.add(sc.nextInt());
                    }
                    else{
                        sc.next();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return decisions;
    }
    private ArrayList<Integer> getInsiderOptionsDecisions(){
        ArrayList<Integer> decisions = new ArrayList<>();
        File file = new File(data + "/Insider_Decisions.txt");

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = "";

                while (!line.contains("Options")){
                    line = br.readLine();
                }
                Scanner sc = new Scanner(line);
                while (sc.hasNext()){
                    if (sc.hasNextInt()){
                        decisions.add(sc.nextInt());
                    }
                    else{
                        sc.next();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return decisions;
    }
    private ArrayList<Integer> getInsiderSellDecisions(){
        ArrayList<Integer> decisions = new ArrayList<>();
        File file = new File(data + "/Insider_Decisions.txt");

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = "";

                while (!line.contains("Sell")){
                    line = br.readLine();
                }
                Scanner sc = new Scanner(line);
                while (sc.hasNext()){
                    if (sc.hasNextInt()){
                        decisions.add(sc.nextInt());
                    }
                    else{
                        sc.next();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return decisions;
    }
    private ArrayList<Integer> getYears(){
        ArrayList<Integer> years = new ArrayList<>();

        File file = new File(data + "/DataYears.txt");

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();

                Scanner sc = new Scanner(line);

                while(sc.hasNext()){
                    if (sc.hasNextInt()) {
                        int year = sc.nextInt();

                        if (year > 9999){
                            int test = year / 1000;
                            if (test == 12 || test == 11){
                                year = Integer.parseInt(Integer.toString(year).substring(1));
                            }
                        }
                        if (year != 1)
                            years.add(year);
                    }
                    else
                        sc.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return years;
    }
    private ArrayList<Double> getDoubleValue(String value, String filename, boolean fill){
        ArrayList<Double> values = new ArrayList<>();
        ArrayList<Double> test = new ArrayList<>();
        File file = new File(data + "/" + filename);

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();

                while (!line.contains(value)){
                    String temp;
                    temp = br.readLine();

                    if (temp == null){
                        test = checkForFile(filename, value);
                        break;
                    }
                    line = temp;
                }
                Scanner sc = new Scanner(line);
                if (test.size() > 0) {
                    values.clear();

                    for (int i = 0; i < test.size(); i++)
                    {
                        values.add(test.get(i));
                    }
                }
                else {
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
                            if (s.contains("d")){
                                s = s.replaceAll("d", "-");
                                if (!s.contains(",")) {
                                    try {
                                        values.add(Double.parseDouble(s));
                                    } catch (Exception e) {
                                        //e.printStackTrace();
                                    }
                                }
                            }
                            if (s.equals("-") || s.equals("--")){
                                values.add(-0.00);
                            }
                            if (s.equals("NFM")){
                                values.add(-0.000);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (fill) {
            while (values.size() < years.size()) {
                values.add(0, -0.0);
            }
        }
        return values;
    }

    private String getTotalDebt()
    {
        File file = new File(data + "/capitalStructure.txt");
        String returnVal = "0";

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = "";
                while (!line.contains("Total Debt"))
                {
                    line = br.readLine();

                    if (line == null)
                        break;
                }
                if (line == null) {
                    File file2 = new File(data + "/BottomHalf.txt");

                    if (file2.exists()) {
                        try {
                            BufferedReader br2 = new BufferedReader(new FileReader(file2.getPath()));
                            String line2 = "";
                            while (!line2.contains("Total Debt")) {
                                line2 = br2.readLine();
                            }
                            int start = line2.indexOf('$') + 1;
                            int end = line2.indexOf('D', start + 1);

                            returnVal = line2.substring(start, end);

                        } catch (Exception e) {
                            //e.printStackTrace();
                        }
                    }
                }

                if (line != null) {
                    int start = line.indexOf('$');
                    int end = line.indexOf(' ', start + 1);
                    end = line.indexOf(' ', end + 1);

                    returnVal = line.substring(start, end);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnVal;
    }

    private String getMarketCap()
    {
        File file = new File(data + "/capitalStructure.txt");
        String returnVal = "0";

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = "";
                while (!line.contains("MARKET CAP")){
                    line = br.readLine();
                    if (line == null)
                    {
                        break;
                    }
                }

                if (line == null) {
                    File file2 = new File(data + "/BottomHalf.txt");

                    if (file2.exists()) {
                        try {
                            BufferedReader br2 = new BufferedReader(new FileReader(file2.getPath()));
                            String line2 = "";
                            while (!line2.contains("MARKET CAP")) {
                                line2 = br2.readLine();
                            }
                            int start = line2.indexOf(':') + 1;

                            int end = line2.indexOf('(', start + 1);
                            returnVal = line2.substring(start, end);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    int start = line.indexOf(':') + 1;

                    int end = line.indexOf('(', start + 1);
                    returnVal = line.substring(start, end);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnVal;
    }
    private int getStats(String s)
    {
        File file = new File(data + "/companyStats.txt");
        int returnVal = 0;

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = "";
                while (!line.contains(s)){
                    line = br.readLine();
                }
                Scanner sc = new Scanner(line);

                while (sc.hasNext()) {
                    if (sc.hasNextInt())
                        returnVal = sc.nextInt();
                    else
                        sc.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnVal;
        }
        else
            return 0;
    }

    private String getStrength()
    {
        File file = new File(data + "/companyStats.txt");
        String returnVal = "";

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();

                while (!line.contains("Strength")){
                    line = br.readLine();
                }

                Scanner sc = new Scanner(line);

                while (sc.hasNext()){
                    if (sc.next().contains("Strength")){
                        returnVal = sc.next();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnVal;
        }
        else
            return returnVal;
    }

    public String toString(){
        String string = "Symbol: " + symbol + "\n" +
                        "Recent Price: " + recentPrice + "\n" +
                        "P/E Ratio: " + PERatio + "\n" +
                        "Dividend Yield: " + dividendYield + "\n" +
                        "Timeliness: " + timeliness + "\n" +
                        "Safety: " + safety + "\n" +
                        projectionYears[0] + " - " + projectionYears[1] +
                        "\n\tHigh: " + highProjections[0] + "\t" + highProjections[1] + " \t" + highProjections[2] + " \n" +
                        "\tLow: " + lowProjections[0] + " \t" + lowProjections[1] + " \t" + lowProjections[2] + " \n";
        string += "Insider Decisions: \n";
        for (int i = 0; i < insiderDecisionRange.length(); i++){
            string += insiderDecisionRange.charAt(i) + " ";
        }
        string += "\nInsider Buys: ";
        for (int i = 0; i < insiderBuys.size(); i++){
            string += insiderBuys.get(i) + " ";
        }
        string += "\nInsider Options: ";
        for (int i = 0; i < insiderOptions.size(); i++){
            string += insiderOptions.get(i) + " ";
        }
        string += "\nInsider Sells: ";
        for (int i = 0; i < insiderSells.size(); i++){
            string += insiderSells.get(i) + " ";
        }
        string += "\n\nYears: ";
        for (int i = 0; i < years.size(); i++){
            string += years.get(i) + " ";
        }
        string += "\nRevenues Per Share: ";
        for (int i = 0; i < revenuesPerShare.size(); i++){
            string += revenuesPerShare.get(i) + " ";
        }
        string += "\nCash Flow Per Share: ";
        for (int i = 0; i < cashFlowPerShare.size(); i++){
            string += cashFlowPerShare.get(i) + " ";
        }
        string += "\nEarnings Per Share: ";
        for (int i = 0; i < earningsPerShare.size(); i++){
            string += earningsPerShare.get(i) + " ";
        }
        string += "\nBook Value Per Share: ";
        for (int i = 0; i < bookValuePerShare.size(); i++){
            string += bookValuePerShare.get(i) + " ";
        }
        string += "\nAverage Annual P/E Ratio: ";
        for (int i = 0; i < averageAnnualPERatio.size(); i++){
            string += averageAnnualPERatio.get(i) + " ";
        }
        string += "\nAverage Annual Dividend Yield: ";
        for (int i = 0; i < averageAnnualDividendYield.size(); i++){
            string += averageAnnualDividendYield.get(i) + " ";
        }
        string += "\nRevenues: ";
        for (int i = 0; i < revenues.size(); i++){
            string += revenues.get(i) + " ";
        }
        string += "\nNet Profit: ";
        for (int i = 0; i < netProfit.size(); i++){
            string += netProfit.get(i) + " ";
        }
        string += "\nNet Profit Margin: ";
        for (int i = 0; i < netProfitMargin.size(); i++){
            string += netProfitMargin.get(i) + " ";
        }
        string += "\nLong Term Debt: ";
        for (int i = 0; i < longTermDebt.size(); i++){
            string += longTermDebt.get(i) + " ";
        }
        string += "\nReturn on Share Equity: ";
        for (int i = 0; i < returnOnShareEquity.size(); i++){
            string += returnOnShareEquity.get(i) + " ";
        }
        string += "\nMarket Cap: ";
        string += marketCap;
        string += "\nCurrent Position Years: ";
        for (int i = 0; i < currentPositionYears.size(); i++){
            string += currentPositionYears.get(i) + " ";
        }
        string += "\nCurrent Assets: ";
        for (int i = 0; i < currentAssets.size(); i++){
            string += currentAssets.get(i) + " ";
        }
        string += "\nCurrent Liability: ";
        for (int i = 0; i < currentLiability.size(); i++){
            string += currentLiability.get(i) + " ";
        }
        string += "\nAnnual Revenues: ";
        for (int i = 0; i < annualRevenues.size(); i++){
            string += annualRevenues.get(i) + " ";
        }
        string += "\nAnnual Earnings: ";
        for (int i = 0; i < annualEarnings.size(); i++){
            string += annualEarnings.get(i) + " ";
        }
        string += "\nAnnual Dividends: ";
        for (int i = 0; i < annualDividends.size(); i++){
            string += annualDividends.get(i) + " ";
        }
        string += "\nAnnual Book Value: ";
        for (int i = 0; i < annualBookValue.size(); i++){
            string += annualBookValue.get(i) + " ";
        }
        string += "\nCompany Strength: ";
        string += companyStrength + " ";

        string += "\nPrice Stability: ";
        string += priceStability + " ";

        string += "\nGrowth Persistence: ";
        string += growthPersistence + " ";

        string += "\nPredictability: ";
        string += predictability + " ";

        return string;
    }

}
