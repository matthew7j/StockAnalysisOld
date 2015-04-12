import java.io.*;

public class AnalysisEngine
{
    protected String data;
    protected int[] insiderBuys;
    protected int[] insiderOptions;
    protected int[] insiderSells;
    protected int[] institutionalBuys;
    protected int[] institutionalHLDs;
    protected int[] institutionalSells;
    protected int[] projectionYears;
    protected int[] years;

    protected double[] accountsPayable;
    protected double[] allDividendsToNetProfit;
    protected double[] averageAnnualDividendYield;
    protected double[] averageAnnualPERatio;
    protected double[] bookValuePerShare;
    protected double[] capitalSpendingPerShare;
    protected double[] cashAssets;
    protected double[] cashFlowPerShare;
    protected double[] commonSharesOutstanding;
    protected double[] currentAssets;
    protected double[] currentLiability;
    protected double[] debtDue;
    protected double[] dividendsDeclaredPerShare;
    protected double[] earningsPerShare;
    protected double[] highProjections;
    protected double[] incomeTaxRate;
    protected double[] inventory;
    protected double[] longTermDebt;
    protected double[] lowProjections;
    protected double[] netProfit;
    protected double[] netProfitMargin;
    protected double[] operatingMargin;
    protected double[] other;
    protected double[] otherDebt;
    protected double[] percentTotalReturn;
    protected double[] receivables;
    protected double[] relativePERatios;
    protected double[] retainedToCommonEquity;
    protected double[] returnOnShareEquity;
    protected double[] returnOnTotalCapital;
    protected double[] revenues;
    protected double[] revenuesPerShare;
    protected double[] shareEquity;
    protected double[] yearHighs;
    protected double[] yearLows;

    protected double[][] fiscalEarningsPerShare;
    protected double[][] fiscalQuarterlyRevenues;
    protected double[][] quarterlyDividends;

    protected int growthPersisence;
    protected int predictability;
    protected int priceStability;
    protected int safety;
    protected int technical;
    protected int timeliness;

    protected double annualRates;
    protected double beta;
    protected double commonStock;
    protected double debtDueIn5Years;
    protected double dividendYield;
    protected double market;
    protected double marketCap;
    protected double medianPERatio;
    protected double PERatio;
    protected double recentPrice;
    protected double relativePERatio;
    protected double totalDebt;
    protected double trailingPERatio;
    protected double workingCapital;

    protected String businessInformation;
    protected String companyInformation;
    protected String dateSafetyChanged;
    protected String dateTechnicalChanged;
    protected String dateTimelinessChanged;
    protected String symbol;

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
        insiderBuys = getInsiderBuyDecisions();
        insiderOptions = getInsiderOptionsDecisions();
        insiderSells = getInsiderSellDecisions();
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
                peRatio = Double.parseDouble(line);
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
                trailingPERatio = Double.parseDouble(line);
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
                medianPERatio = Double.parseDouble(line);
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
                relativePERatio = Double.parseDouble(line);
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
                dividendYield = Double.parseDouble(line);
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
                int indexStart = 0, indexNext = 0;
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
                int indexStart = 0, indexNext = 0;
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
    private int[] getInsiderBuyDecisions(){
        int[] decisions = new int[12];
        int current = 0;
        File file = new File(data + "/Insider_Decisions.txt");

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = "";

                for (int i = 0; i < 3; i++){
                    line = br.readLine();
                }

                for (int i = 0; i < line.length(); i++)
                {
                    if (Character.isDigit(line.charAt(i))){
                        decisions[current++] = line.charAt(i);
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return decisions;
        }
        else
            return decisions;
    }
    private int[] getInsiderOptionsDecisions(){
        int[] decisions = new int[12];
        int current = 0;
        File file = new File(data + "/Insider_Decisions.txt");

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = "";

                for (int i = 0; i < 4; i++){
                    line = br.readLine();
                }

                for (int i = 0; i < line.length(); i++)
                {
                    if (Character.isDigit(line.charAt(i))){
                        decisions[current++] = line.charAt(i);
                    }
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return decisions;

        }
        else
            return decisions;
    }
    private int[] getInsiderSellDecisions(){
        int[] decisions = new int[12];
        int current = 0;
        File file = new File(data + "/Insider_Decisions.txt");

        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
                String line = "";

                for (int i = 0; i < 5; i++){
                    line = br.readLine();
                }

                for (int i = 0; i < line.length(); i++)
                {
                    if (Character.isDigit(line.charAt(i))){
                        decisions[current++] = line.charAt(i);
                    }
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return decisions;
        }
        else
            return decisions;
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

        return string;
    }

}
