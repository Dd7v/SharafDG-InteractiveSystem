import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestDatabaseConnection {
    public static void main(String[] args) {
        String url = "jdbc:derby://localhost:1527/ChargeRates";
        String username = "SharafDG";
        String password = "DG7310";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected successfully!");

            // Test query
            String sql = "SELECT ChargeCode, ChargeRate FROM ChargeRates";
            try (PreparedStatement ps = connection.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    String chargeCode = rs.getString("ChargeCode");
                    String chargeRate = rs.getString("ChargeRate");

                    System.out.println("ChargeCode: " + chargeCode + ", ChargeRate: " + chargeRate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

