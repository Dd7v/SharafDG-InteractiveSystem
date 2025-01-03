import java.util.HashMap;
import java.util.Scanner;

public class SharafDGUtilityClass {
    HashMap<Integer, Double> SData = new HashMap<>();
    Scanner scan = new Scanner(System.in);

    //Data
    public SharafDGUtilityClass() {
        SData.put(101, 742.4);
        SData.put(731, 233.1);
        SData.put(115, 8248.0);
        SData.put(107, 270.2);
        //System.out.println("loaded Data: " + SData);
    }

    public void menu() {
        int Option;
        do {
            System.out.println("\nUtility Menu");
            System.out.println("1. Add Sales Data");
            System.out.println("2. Search for Sales Data");
            System.out.println("3. Present all Sales Data");
            System.out.println("4. Close program");
            System.out.print("Enter your option: ");
            Option = scan.nextInt();

            switch (Option) {
                case 1 -> AddData();
                case 2 -> SearchData();
                case 3 -> PresentsData();
                case 4 -> System.out.println("Exiting the program.");
                default -> System.out.println("Invalid Option. Please try again.");
            }
        } while (Option != 4);
    }

    // Adding Data
    public void AddData() {
        System.out.print("Enter Sales Representative ID: ");
        int RID = scan.nextInt();

        System.out.print("Enter Sales Profit: ");
        double salesProfit = scan.nextDouble();

        SData.put(RID, salesProfit);
        System.out.println("Your data has been successfully recorded.");
    }

    // Searching for Data
    public void SearchData() {
        System.out.print("Enter Sales Representative ID: ");
        int RID = scan.nextInt();

        if (SData.containsKey(RID)) {
            System.out.println("Sales Representative ID: " + RID +
                    "\nSales Profit: " + SData.get(RID));
        } else {
            System.out.println("No data found for Sales Representative ID: " + RID);
        }
    }

    // Presenting All Data
    public void PresentsData() {
        if (SData.isEmpty()) {
            System.out.println("No available data currently.");
        } else {
            System.out.println("All Sales Data:");
            SData.forEach((RID, salesProfit) -> System.out.println("Sales Representative ID: " + RID + ", Sales Profit: " + salesProfit));
        }
    }

    public static void main(String[] args) {
        SharafDGUtilityClass util = new SharafDGUtilityClass();
        util.menu();
    }
}
