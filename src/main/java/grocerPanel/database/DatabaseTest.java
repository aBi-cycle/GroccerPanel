package grocerPanel.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class DatabaseTest {

    @SuppressWarnings({"ConvertToTryWithResources", "UseSpecificCatch"})
    public static void main(String[] args) {


        try {

            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Connected!");
            
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery( "SELECT * FROM product");
            System.out.println("Products:");

            while(result.next()) {
                int id = result.getInt("productID");

                String name = result.getString("name");

                double price = result.getDouble("price");


                System.out.println(
                        id + " | "
                        + name
                        + " | $"
                        + price
                );
            }

            result.close();
            statement.close();
            connection.close();

        } catch(Exception e) {
                System.out.println("Error: " + e.getMessage());
        }

    }
}