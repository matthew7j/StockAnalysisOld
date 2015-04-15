import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AnalysisEngine
{
    protected String data;

    protected int[] projectionYears;

    protected int[][] fiscalQuarterlyRevenues;

    protected ArrayList<Integer> accountsPayable;
    protected ArrayList<Integer> cashAssets;
    protected ArrayList<Integer> currentAssets;
    protected ArrayList<Integer> currentLiability;
    protected ArrayList<Integer> currentPositionYears;
    protected ArrayList<Integer> debtDue;
    protected ArrayList<Integer> inventory;
    protected ArrayList<Integer> receivables;
    protected ArrayList<Integer> years;

    protected double[] highProjections;
    protected double[] lowProjections;

    protected ArrayList<Double> allDividendsToNetProfit;
    protected ArrayList<Double> annualBookValue;
    protected ArrayList<Double> annualCashFlow;
    protected ArrayList<Double> annualDividends;
    protected ArrayList<Double> annualEarnings;
    protected ArrayList<Double> annualRevenues;
    protected ArrayList<Double> averageAnnualDividendYield;
    protected ArrayList<Double> averageAnnualPERatio;
    protected ArrayList<Double> bookValuePerShare;
    protected ArrayList<Double> capitalSpendingPerShare;
    protected ArrayList<Double> cashFlowPerShare;
    protected ArrayList<Double> commonSharesOutstanding;
    protected ArrayList<Double> depreciation;
    protected ArrayList<Double> dividendsDeclaredPerShare;
    protected ArrayList<Double> earningsPerShare;
    protected ArrayList<Double> incomeTaxRate;
    protected ArrayList<Double> longTermDebt;
    protected ArrayList<Double> netProfit;
    protected ArrayList<Double> netProfitMargin;
    protected ArrayList<Double> operatingMargin;
    protected ArrayList<Double> relativePERatios;
    protected ArrayList<Double> retainedToCommonEquity;
    protected ArrayList<Double> returnOnShareEquity;
    protected ArrayList<Double> returnOnTotalCapital;
    protected ArrayList<Double> revenues;
    protected ArrayList<Double> revenuesPerShare;
    protected ArrayList<Double> shareEquity;
    protected ArrayList<Double> workingCapital;
    protected ArrayList<Double> yearHighs;
    protected ArrayList<Double> yearLows;

    protected ArrayList<Integer> insiderBuys;
    protected ArrayList<Integer> insiderOptions;
    protected ArrayList<Integer> insiderSells;
    protected ArrayList<Integer> institutionalBuys;
    protected ArrayList<Integer> institutionalHLDs;
    protected ArrayList<Integer> institutionalSells;

    protected double[][] fiscalEarningsPerShare;
    protected double[][] quarterlyDividends;
    protected double[][] percentTotalReturn;

    protected int growthPersistence;
    protected int predictability;
    protected int priceStability;
    protected int safety;
    protected int technical;
    protected int timeliness;

    protected double beta;

    protected double dividendYield;
    protected double market;
    protected double medianPERatio;
    protected double PERatio;
    protected double recentPrice;
    protected double relativePERatio;
    protected double trailingPERatio;

    protected String businessInformation;
    protected String commonStock;
    protected String companyInformation;
    protected String companyStrength;
    protected String dateSafetyChanged;
    protected String dateTechnicalChanged;
    protected String dateTimelinessChanged;
    protected String debtDueIn5Years;
    protected String insiderDecisionRange;
    protected String marketCap;
    protected String symbol;
    protected String totalDebt;

    public AnalysisEngine(String data){
        this.data = data;
        this.analysis();
        System.out.println(this.toString());
    }
    private void analysis(){
        symbol = getSymbol();
        recentPrice = getRecentPrice();
        PERatio = getPERatio();
        trailingPERatio = getTrailingPERatio();
        medianPERatio = getMedianPERatio();
        relativePERatio = getRelativePERatio();
        dividendYield = getDividendYield();
        timeliness = getTimeliness();
        dateTimelinessChanged = getTimelinessInformation();
        safety = getSafety();
        dateSafetyChanged = getSafetyInformation();
        technical = getTechnical();
        dateTechnicalChanged = getTechnicalInformation();
        beta = getBeta();
        market = getMarket();
        highProjections = getHighProjections();
        lowProjections = getLowProjections();
        projectionYears = getProjectionYears();
        insiderDecisionRange = getInsiderDecisionsRange();
        insiderBuys = getInsiderBuyDecisions();
        insiderOptions = getInsiderOptionsDecisions();
        insiderSells = getInsiderSellDecisions();
        institutionalBuys = getInstitutionalBuys();
        institutionalSells = getInstitutionalSells();
        institutionalHLDs = getInstitutionalHLDs();
        years = getYears();
        revenuesPerShare = getDoubleValue("Revenues per sh");
        cashFlowPerShare = getDoubleValue("Cash Flow");
        earningsPerShare = getDoubleValue("Earnings per sh");
        dividendsDeclaredPerShare = getDoubleValue("Div'ds DecI'd per sh");
        capitalSpendingPerShare = getDoubleValue("Cap'l Spending per sh");
        bookValuePerShare = getDoubleValue("Book Value per sh");
        commonSharesOutstanding = getDoubleValue("Common Shs Outst'g");
        averageAnnualPERatio = getDoubleValue("Avg Ann'l PIE Ratio");
        relativePERatios = getDoubleValue("Relative PIE Ratio");
        averageAnnualDividendYield = getDoubleValue("Avg Ann'l Div'd Yield");
        revenues = getDoubleValue("Revenues ($mill)");
        operatingMargin = getDoubleValue("Operating Margin");
        depreciation = getDoubleValue("Depreciation");
        netProfit = getDoubleValue("Net Profit");
        incomeTaxRate = getDoubleValue("Income Tax Rate");
        netProfitMargin = getDoubleValue("Net Profit Margin");
        workingCapital = getDoubleValue("Working Cap'l");
        longTermDebt = getDoubleValue("Long-Term Debt");
        shareEquity = getDoubleValue("Shr. Equity");
        returnOnTotalCapital = getDoubleValue("Return on Total Cap'l");
        returnOnShareEquity = getDoubleValue("Return on Shr. Equity");
        retainedToCommonEquity = getDoubleValue("Retained to Corn Eq");
        allDividendsToNetProfit = getDoubleValue("All Div'ds to Net Prof");/*
        currentPositionYears = getCurrentPosition("CURRENT POSITION");
        cashAssets = getCurrentPosition("Cash Assets");
        receivables = getCurrentPosition("Receivables");
        inventory = getCurrentPosition("Inventory");
        currentAssets = getCurrentPosition("Current Assets");
        accountsPayable = getCurrentPosition("Payable");
        debtDue = getCurrentPosition("Debt");
        currentLiability = getCurrentPosition("Current Liab.");
        annualRevenues = getAnnualRates("Revenues");
        annualCashFlow = getAnnualRates("Cash Flow");
        annualEarnings = getAnnualRates("Earnings");
        annualDividends = getAnnualRates("Dividends");
        annualBookValue = getAnnualRates("Book Value");
        fiscalQuarterlyRevenues = getQuarterlyRevenues();
        fiscalEarningsPerShare = getEarningsPerShare();
        quarterlyDividends = getQuarterlyDividendsPaid();
        businessInformation = getBusinessInformation();
        companyInformation = getCompanyInformation();
        totalDebt = getTotalDebt();
        debtDueIn5Years = getDebtDueIn5Years();
        commonStock = getCommonStock();
        marketCap = getMarketCap();
        companyStrength = getStrength();
        priceStability = getStats("Stability");
        growthPersistence = getStats("Persistence");
        predictability = getStats("Predictability");
        yearHighs = getHighs();
        yearLows = getLows();
        percentTotalReturn = getPercentTotalReturn();*/
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
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
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
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
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
                }catch(Exception e){}
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return peRatio;
        }
        else
            return 0;
    }
    private double getTrailingPERatio(){
        File file = new File(data + "/TrailingMedianPE_Ratio.txt");

        if (file.exists()) {
            double trailingPERatio = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();
                line = line.replaceAll(",", ".");
                try {
                    trailingPERatio = Double.parseDouble(line);
                }catch(Exception e){}

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return trailingPERatio;
        }
        else
            return 0;
    }
    private double getMedianPERatio(){
        File file = new File(data + "/TrailingMedianPE_Ratio.txt");

        if (file.exists()) {
            double medianPERatio = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                br.readLine();
                String line = br.readLine();
                line = line.replaceAll(",", ".");
                try {
                    medianPERatio = Double.parseDouble(line);
                }
                catch (NumberFormatException e){
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return medianPERatio;
        }
        else
            return 0;
    }
    private double getRelativePERatio(){
        File file = new File(data + "/RelativePE_Ratio.txt");

        if (file.exists()) {
            double relativePERatio = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();

                try {
                    relativePERatio = Double.parseDouble(line);
                }
                catch (NumberFormatException e){
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return relativePERatio;
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

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
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
                safety = Integer.parseInt(line.substring(0,1));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return safety;
        }
        else
            return 0;
    }
    private String getSafetyInformation(){
        File file = new File(data + "/Safety.txt");

        if (file.exists()) {
            String safety = "";

            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();
                safety = line.substring(2,line.length());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return safety;
        }
        else
            return "Error getting safety information";
    }
    private int getTechnical(){
        File file = new File(data + "/Technical.txt");

        if (file.exists()) {
            int technical = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();
                technical = Integer.parseInt(line.substring(0,1));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return technical;
        }
        else
            return 0;
    }
    private String getTechnicalInformation(){
        File file = new File(data + "/Technical.txt");

        if (file.exists()) {
            String technical = "";

            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();
                technical = line.substring(2,line.length());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return technical;
        }
        else
            return "Error getting technical information";
    }
    private int getTimeliness(){
        File file = new File(data + "/Timeliness.txt");

        if (file.exists()) {
            int timeliness = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();
                timeliness = Integer.parseInt(line.substring(0,1));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return timeliness;
        }
        else
            return 0;
    }
    private String getTimelinessInformation(){
        File file = new File(data + "/Timeliness.txt");

        if (file.exists()) {
            String timeliness = "";

            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();
                timeliness = line.substring(2,line.length());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return timeliness;
        }
        else
            return "Error getting timeliness information";
    }
    private double getBeta(){
        File file = new File(data + "/Beta.txt");

        if (file.exists()) {
            double beta = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();

                int index = line.indexOf(' ');
                int endIndex = line.indexOf(' ', index + 1);

                beta = Double.parseDouble(line.substring(index + 1, endIndex));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return beta;
        }
        else
            return 0;
    }
    private double getMarket(){
        File file = new File(data + "/Beta.txt");

        if (file.exists()) {
            double market = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();

                int index = line.indexOf('(');

                market = Double.parseDouble(line.substring(index + 1, line.indexOf('=') - 1));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return market;
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


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
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
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
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

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
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

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return returnString;
    }
    private ArrayList<Integer> getInsiderBuyDecisions(){
        ArrayList<Integer> decisions = new ArrayList<Integer>();
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

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return decisions;
    }
    private ArrayList<Integer> getInsiderOptionsDecisions(){
        ArrayList<Integer> decisions = new ArrayList<Integer>();
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


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return decisions;
    }
    private ArrayList<Integer> getInsiderSellDecisions(){
        ArrayList<Integer> decisions = new ArrayList<Integer>();
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

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return decisions;
    }
    private ArrayList<Integer> getInstitutionalBuys(){
        ArrayList<Integer> decisions = new ArrayList<Integer>();
        File file = new File(data + "/Institutional_Decisions.txt");

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

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return decisions;
    }
    private ArrayList<Integer> getInstitutionalSells(){
        ArrayList<Integer> decisions = new ArrayList<Integer>();
        File file = new File(data + "/Institutional_Decisions.txt");

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

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return decisions;
    }
    private ArrayList<Integer> getInstitutionalHLDs(){
        ArrayList<Integer> decisions = new ArrayList<Integer>();
        File file = new File(data + "/Institutional_Decisions.txt");

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = "";

                while (!line.contains("Hld's")){
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

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return decisions;
    }
    private ArrayList<Integer> getYears(){
        ArrayList<Integer> years = new ArrayList<Integer>();

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
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return years;
    }
    private ArrayList<Double> getDoubleValue(String value){
        ArrayList<Double> values = new ArrayList<Double>();
        File file = new File(data + "/BottomHalf.txt");

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
                            values.add(Double.parseDouble(s.substring(0, s.indexOf('%'))));
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return values;
        }
        else
            return values;
    }
    private ArrayList<Integer> getCurrentPosition(String s)
    {
        ArrayList<Integer> values = new ArrayList<Integer>();
        int val = 0;
        File file = new File(data + "/currentPosition.txt");

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = "";
                while (!line.contains(s)) {
                    line = br.readLine();
                }

                Scanner sc = new Scanner(line);

                while (sc.hasNextInt()){
                    val = sc.nextInt();
                    values.add(val);
                }
                if (s.compareTo("CURRENT POSITION") == 0)
                {
                    values.add(val + 1);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return values;
        }
        else
            return values;
    }

    private ArrayList<Double> getAnnualRates(String s)
    {
        ArrayList<Double> values = new ArrayList<Double>();
        File file = new File(data + "/AnnualRates.txt");

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();
                while (!line.contains(s)) {
                    line = br.readLine();
                }

                Scanner sc = new Scanner(line);

                while (sc.hasNextInt()){
                    values.add(sc.nextDouble());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return values;
        }
        else
            return values;
    }
    private int[][] getQuarterlyRevenues()
    {
        int[][] values = new int[5][5];

        File file = new File(data + "/quarterlyRevenues.txt");

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = "";
                for (int i = 0; i < 4; i++) {
                    line = br.readLine();
                }

                for (int i = 0; i < 5; i++)
                {
                    line = line.replace(".", " ");
                    Scanner sc = new Scanner(line);
                    for (int j = 0; j < 5; j++)
                    {
                        values[i][j] = sc.nextInt();
                    }
                    line = br.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return values;
        }
        else
            return values;
    }
    private double[][] getEarningsPerShare()
    {
        double[][] values = new double[5][6];
        int j = 0;

        File file = new File(data + "/quarterlyEarningsPerShare.txt");

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = "";
                for (int i = 0; i < 4; i++) {
                    line = br.readLine();
                }

                for (int i = 0; i < 5; i++)
                {
                    Scanner sc = new Scanner(line);
                    while(sc.hasNext()){
                        if (sc.hasNextDouble()){
                            values[i][j++] = sc.nextDouble();
                        }
                        else {
                            sc.next();
                        }
                    }
                    j = 0;
                    line = br.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return values;
        }
        else
            return values;
    }
    private double[][] getQuarterlyDividendsPaid()
    {
        double[][] values = new double[5][5];

        File file = new File(data + "/quarterlyDividends.txt");

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = "";
                for (int i = 0; i < 3; i++) {
                    line = br.readLine();
                }

                for (int i = 0; i < 5; i++)
                {
                    Scanner sc = new Scanner(line);
                    for (int j = 0; j < 5; j++)
                    {
                        values[i][j] = sc.nextDouble();
                    }
                    line = br.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return values;
        }
        else
            return values;
    }
    private String getBusinessInformation()
    {
        String file = data + "/CompanyBusiness.txt";
        String result = "";
        try {
            result = Files.lines(Paths.get(file)).collect(Collectors.joining());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return result;
    }
    private String getCompanyInformation()
    {
        String file = data + "/CompanyInformation.txt";
        String result = "";
        try {
            result = Files.lines(Paths.get(file)).collect(Collectors.joining());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return result;
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
                }

                int start = line.indexOf('$');
                int end = line.indexOf(' ', start + 1);
                end = line.indexOf(' ', end + 1);

                returnVal = line.substring(start, end);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return returnVal;
    }
    private String getDebtDueIn5Years() {
        File file = new File(data + "/capitalStructure.txt");
        String returnVal = "0";

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = "";
                while (!line.contains("Total Debt")) {
                    line = br.readLine();
                }

                int start = line.indexOf('$');
                start = line.indexOf('$', start + 1);
                int end = line.indexOf(' ', start);
                end = line.indexOf(' ', end);

                returnVal = line.substring(start, end);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return returnVal;
    }

    private String getCommonStock()
    {
        File file = new File(data + "/capitalStructure.txt");
        String returnVal = "0";

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = "";
                while (!line.contains("Common Stock")){
                    line = br.readLine();
                }

                int start = line.indexOf(' ');
                start = line.indexOf(' ', start + 1);
                int end = line.indexOf(' ', start + 1);

                returnVal = line.substring(start, end);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
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
                }
                int start = line.indexOf(':') + 1;

                int end = line.indexOf(' ', start + 1);
                returnVal = line.substring(start, end);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
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
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
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
                returnVal = line.substring(line.lastIndexOf(' '), line.length() - 1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return returnVal;
        }
        else
            return returnVal;
    }
    private ArrayList<Double> getHighs()
    {
        File file = new File(data + "/hi_lo.txt");
        ArrayList<Double> highs = new ArrayList<Double>();

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = br.readLine();
                Scanner sc = new Scanner(line);

                while(sc.hasNextDouble()){
                    highs.add(sc.nextDouble());
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return highs;
        }
        else
            return highs;
    }
    private ArrayList<Double> getLows()
    {
        File file = new File(data + "/hi_lo.txt");
        ArrayList<Double> lows = new ArrayList<Double>();

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line;
                br.readLine();
                line = br.readLine();
                Scanner sc = new Scanner(line);

                while(sc.hasNextDouble()){
                    lows.add(sc.nextDouble());
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return lows;
        }
        else
            return lows;
    }
    private double[][] getPercentTotalReturn()
    {
        File file = new File(data + "/percentTotalReturn.txt");
        double[][] vals = new double[3][2];

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = "";
                for (int i = 0; i < 4; i++) {
                    line = br.readLine();
                }
                Scanner sc;
                for (int i = 0; i < 3; i++)
                {
                    sc = new Scanner(line);
                    for (int j = 0; j < 2; j++){
                        while(sc.hasNext()) {
                            if (sc.hasNextDouble())
                                vals[i][j] = sc.nextDouble();
                            else
                                sc.next();
                        }
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return vals;
        }
        else
            return vals;
    }

    public String toString(){
        String string = "Symbol: " + symbol + "\n" +
                        "Recent Price: " + recentPrice + "\n" +
                        "P/E Ratio: " + PERatio + "\n" +
                        "Trailing P/E Ratio: " + trailingPERatio + "\n" +
                        "Median P/E Ratio: " + medianPERatio + "\n" +
                        "Relative P/E Ratio: " + relativePERatio + "\n" +
                        "Dividend Yield: " + dividendYield + "\n" +
                        "Timeliness: " + timeliness + "\n" +
                        "Safety: " + safety + "\n" +
                        "Technical: " + technical + "\n" +
                        projectionYears[0] + " - " + projectionYears[1] +
                        "\n\tHigh: " + highProjections[0] + "\t" + highProjections[1] + " \t" + highProjections[2] + " \n" +
                        "\tLow: " + lowProjections[0] + " \t" + lowProjections[1] + " \t" + lowProjections[2] + " \n";
        string += "Insider Decisions: \n";
        for (int i = 0; i < insiderDecisionRange.length(); i++){
            string += insiderDecisionRange.charAt(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < insiderBuys.size(); i++){
            string += insiderBuys.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < insiderOptions.size(); i++){
            string += insiderOptions.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < insiderSells.size(); i++){
            string += insiderSells.get(i) + " ";
        }
        string += "\n\n";

        string += "Institutional Decisions: \n";
        for (int i = 0; i < institutionalBuys.size(); i++){
            string += institutionalBuys.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < institutionalSells.size(); i++){
            string += institutionalSells.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < institutionalHLDs.size(); i++){
            string += institutionalHLDs.get(i) + " ";
        }
        string += "\n\n";

        for (int i = 0; i < years.size(); i++){
            string += years.get(i) + " ";
        }
        string += "\n";

        for (int i = 0; i < revenuesPerShare.size(); i++){
            string += revenuesPerShare.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < cashFlowPerShare.size(); i++){
            string += cashFlowPerShare.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < earningsPerShare.size(); i++){
            string += earningsPerShare.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < dividendsDeclaredPerShare.size(); i++){
            string += dividendsDeclaredPerShare.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < capitalSpendingPerShare.size(); i++){
            string += capitalSpendingPerShare.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < bookValuePerShare.size(); i++){
            string += bookValuePerShare.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < commonSharesOutstanding.size(); i++){
            string += commonSharesOutstanding.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < averageAnnualPERatio.size(); i++){
            string += averageAnnualPERatio.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < relativePERatios.size(); i++){
            string += relativePERatios.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < averageAnnualDividendYield.size(); i++){
            string += averageAnnualDividendYield.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < revenues.size(); i++){
            string += revenues.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < operatingMargin.size(); i++){
            string += operatingMargin.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < depreciation.size(); i++){
            string += depreciation.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < netProfit.size(); i++){
            string += netProfit.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < incomeTaxRate.size(); i++){
            string += incomeTaxRate.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < netProfitMargin.size(); i++){
            string += netProfitMargin.get(i) + " ";
        }
        string += "\n";

        for (int i = 0; i < workingCapital.size(); i++){
            string += workingCapital.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < longTermDebt.size(); i++){
            string += longTermDebt.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < shareEquity.size(); i++){
            string += shareEquity.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < returnOnTotalCapital.size(); i++){
            string += returnOnTotalCapital.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < returnOnShareEquity.size(); i++){
            string += returnOnShareEquity.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < retainedToCommonEquity.size(); i++){
            string += retainedToCommonEquity.get(i) + " ";
        }
        string += "\n";
        for (int i = 0; i < allDividendsToNetProfit.size(); i++){
            string += allDividendsToNetProfit.get(i) + " ";
        }
        string += "\n";

        return string;
    }

}
