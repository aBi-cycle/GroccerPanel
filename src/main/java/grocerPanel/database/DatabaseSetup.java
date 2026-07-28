package grocerPanel.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;


public class DatabaseSetup {

    private static final String SCHEMA_FILE =
            "database/schema.sql";

    private static final String INSERT_FILE =
            "database/insert.sql";


    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {

        try {
            Connection connection =
                    DatabaseConnection.getConnection();

            System.out.println("Connected to database");

            executeSQLFile(connection, SCHEMA_FILE);

            executeSQLFile(connection, INSERT_FILE);
            System.out.println("Database setup complete!");

            connection.close();

        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("ConvertToTryWithResources")
    private static void executeSQLFile(
            Connection connection,
            String filePath
    ) throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        StringBuilder sql = new StringBuilder();

        String line;

        while((line = reader.readLine()) != null) {
            line = line.trim();
            // Ignore comments and blank lines
            if(line.startsWith("--") || line.isEmpty()) {
                continue;
            }
            sql.append(line);

            // Execute each command when semicolon appears
            if(line.endsWith(";")) {
                Statement statement = connection.createStatement();
                statement.execute(sql.toString());
                statement.close();
                sql.setLength(0);
            }
        }
        reader.close();
    }

}