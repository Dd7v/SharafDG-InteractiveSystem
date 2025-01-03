import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable {
    @Override
    public void run() {
        try {
        
            // Connect to the server
            Socket socket = new Socket("localhost", 7311);
            System.out.println("Connected to the server.");

            try (Scanner scanner = new Scanner(System.in);
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                 DataInputStream in = new DataInputStream(socket.getInputStream())) {

                // Data Input from the user
                System.out.print("Enter Sales Representative ID: ");
                int RID = scanner.nextInt();
                System.out.print("Enter Number of Laptops Sold: ");
                int lapSold = scanner.nextInt();

                // Send data to the server
                out.writeInt(RID);
                out.writeInt(lapSold);
                out.flush();
                System.out.println("Data sent to server.");

                // Read results from the server
                try {
                    double salesProfit = in.readDouble();
                    System.out.println("\nSales Profit received: " + salesProfit);

                    double Rate = in.readDouble();
                    System.out.println("\nRate received: " + Rate);

                    double commission = in.readDouble();
                    System.out.println("\nCommission received: " + commission);

                    // Display Data results
                    System.out.println("\nSales Profit: " + salesProfit);
                    System.out.println("\nRate: " + Rate);
                    System.out.println("\nCommission: " + commission);
                } catch (IOException e) {
                    System.out.println("Error reading data from server: " + e.getMessage());
                }
            } catch (IOException e) {
                System.out.println("Error during data communication: " + e.getMessage());
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("Unable to connect to server: " + e.getMessage());
        }
    }
}

public class SharafDGClient {
    public static void main(String[] args) {
        Client cl = new Client();
        Thread clThread = new Thread(cl);
        clThread.start();
    }
}

