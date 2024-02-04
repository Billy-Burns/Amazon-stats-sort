/**
 * (c)2023 Billy Burns. All rights reserved.
 * Solution to Homework 5
 * @author Billy Burns
 * created on March 23, 2023
 * Acknowledgements: Evan Leviss, Jake Romer, Dr. Khuri, CS Center
 */
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class AmazonCategoricalStatistics {
    private static void printCategoricalStats(String[] values, int index)
    {
        int[] freq = new int [index];
        int counter = 0;
        for (int i = 0; i < index; ++i){
            freq[i] = 0;
        }
        for (int i = 0; i < index; ++i){
            counter = 0;
            for (int j = 0; j < index; ++j){
                if( values[i].equals(values[j]) && !values[i].equals("")){
                    counter++;
                }
                freq[i] = counter;
            }
        }
        int mostFreq = StdStats.max(freq);
        int locate = 0;
        while (freq[locate] != mostFreq){
            locate++;
        }
        System.out.printf("%s (appeared %d times)\n",values[locate], freq[locate]);
    }

    private static double[] normalize(double[] price, int index)
    {
        double max = StdStats.max(price);
        for(int i = 0; i < price.length; ++i){
            price[i] = price[i] / max;
        }
        return price;
    }

    private static void printNumericStats(double [] price, int index)
    {
    double minVal = StdStats.min(price,0, index);
    double maxVal = StdStats.max(price,0, index);
    double meanVal = StdStats.mean(price,0, index);
    double stdDeviation = StdStats.stddev(price,0, index);
    System.out.printf("%-11s%,10.2f\n", "Min:",minVal);
    System.out.printf("%-11s%,10.2f\n", "Max:",maxVal);
    System.out.printf("%-11s%,10.2f\n", "Mean:",meanVal);
    System.out.printf("%-11s%,10.2f\n", "Std Dev:",stdDeviation);
    }
    private static void printReport(boolean normalizeFlag) {
        try {
            File inputFile = new File("./input/amazon_product_details.csv");

            Scanner scnr = new Scanner(inputFile, "UTF-8");
            final int NUM_ITEMS = 1406;
            final int NUM_FEATURES = 8;
            String[] item_name = new String[NUM_ITEMS];
            double[] actual_price = new double[NUM_ITEMS];
            double[] selling_price = new double[NUM_ITEMS];
            String[] brand = new String[NUM_ITEMS];
            String[] model = new String[NUM_ITEMS];
            String[] color = new String[NUM_ITEMS];
            String[] form_factor = new String[NUM_ITEMS];
            String[] connectivity_type = new String[NUM_ITEMS];


            String[] title = scnr.nextLine().split(",");


            int index = 0;
            while (scnr.hasNextLine()) {
                String line = scnr.nextLine();

                String[] items = line.split(",");

                System.out.println();
                 if (items.length == 8 && !items[1].equals("") && !items[2].equals(""))   {
                    for (int i = 0; i < NUM_FEATURES; ++i) {
                        if (i == 0) {
                            item_name[index] = items[i];
                        } else if (i == 1) {
                            actual_price[index] = Double.parseDouble(items[i]);
                        } else if (i == 2) {
                            selling_price[index] = Double.parseDouble(items[i]);
                        } else if (i == 3) {
                            brand[index] = items[i];
                        } else if (i == 4) {
                            model[index] = items[i];
                        } else if (i == 5) {
                            color[index] = items[i];
                        } else if (i == 6) {
                            form_factor[index] = items[i];
                        } else {
                            connectivity_type[index] = items[i];
                        }
                    }
                     index++;
                }

            }

            if(normalizeFlag)
            {
                normalize(actual_price, index);
                normalize(selling_price, index);
            }

            System.out.printf("Basic stats for: %s\n", title[1]);
            printNumericStats(actual_price, index);
            System.out.printf("\nBasic stats for: %s\n", title[2]);
            printNumericStats(selling_price, index);
            System.out.printf("\n%s\n", ".....................................");
            System.out.printf("Most frequent %s:\n", title[3]);
            printCategoricalStats(brand, index);
            System.out.printf("%s\n", ".....................................");
            System.out.printf("Most frequent %s:\n", title[4]);
            printCategoricalStats(model, index);
            System.out.printf("%s\n", ".....................................");
            System.out.printf("Most frequent %s:\n", title[5]);
            printCategoricalStats(color, index);
            System.out.printf("%s\n", ".....................................");
            System.out.printf("Most frequent %s:\n", title[6]);
            printCategoricalStats(form_factor, index);
            System.out.printf("%s\n", ".....................................");
            System.out.printf("Most frequent %s:\n", title[7]);
            printCategoricalStats(connectivity_type, index);
            System.out.printf("%s\n", ".....................................");
            System.out.printf("%s\n","It appears that consumers highly value these products and specs." );
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    public static void main(String[] args) {
        printReport(false);

    }
}
