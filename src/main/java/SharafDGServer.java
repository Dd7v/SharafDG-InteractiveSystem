import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Server implements Runnable {
    @Override
    public void run() {
        try {
            // Database connection details 
            String host = "jdbc:derby://localhost:1527/ChargeRates"; 
            String user = "SharafDG"; 
            String pass = "DG7310"; 
            Connection connection = DriverManager.getConnection(host, user, pass); 

            ServerSocket serverSocket = new ServerSocket(7311);
            System.out.println("Server is running & listening on port 7311...");

            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("Client connected.");

                    // Receive data from client
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    int RID = in.readInt(); 
                    int lapSold = in.readInt(); 
                    double unitPrice = 90.0;

                    // Calculate sales profit
                    double salesProfit = lapSold * unitPrice;

                    String chargeCode = "";
                    if (salesProfit < 20000) {
                        chargeCode = "1";  
                    } else if (salesProfit >= 20000 && salesProfit < 10000) {
                        chargeCode = "2"; 
                    } else {
                        chargeCode = "3";  
                    }

                    // Fetch the charge rate from the database based on charge code
                    double chargeRate = 0.0;
                    try (PreparedStatement ps = connection.prepareStatement(
                            "SELECT ChargeRate FROM ChargeRates WHERE ChargeCode = ?")) {
                        ps.setString(1, chargeCode); 

                        try (ResultSet result = ps.executeQuery()) {
                            if (result.next()) {
                                chargeRate = result.getDouble("ChargeRate"); 
                            } else {
                                System.out.println("No matching charge rate found.");
                            }
                        }
                    }

                    // Calculate commission
                    double commission = salesProfit * chargeRate;

                    // Send results back to the client
                    try (DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
                        out.writeDouble(salesProfit);
                        out.writeDouble(chargeRate);
                        out.writeDouble(commission);

                        System.out.println("Data sent to client: Sales Profit=" + salesProfit +
                                ", Charge Rate=" + chargeRate + ", Commission=" + commission);
                    }
                } catch (IOException | SQLException e) {
                    System.err.println("Error while handling client: " + e.getMessage());
                }
            }
        } catch (IOException | SQLException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}

class SharafDGServer {
    public static void main(String[] args) {
        Server ser = new Server();
        Thread serThread = new Thread(ser);
        serThread.start();
    }
}
