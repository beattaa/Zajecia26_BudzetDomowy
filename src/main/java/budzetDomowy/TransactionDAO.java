package budzetDomowy;

import java.sql.*;

public class TransactionDAO implements DAO {
    private static final String URL = "jdbc:mysql://localhost:3306/zad_domowe?characterEncoding=utf8&serverTimezone=UTC&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "xxx";
    private Connection connection;

    public TransactionDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException exception) {
            System.err.println("Błąd przy wczytywaniu sterownika");
            return;
        } catch (
                SQLException e) {
            System.err.println("Błąd przy nawiązywaniu połączenia");
            return;
        }
    }

    @Override
    public void select(int type) {
        String query = "SELECT * FROM TRANSACTIONS WHERE TYPE=" + type;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String description = resultSet.getString("DESCRIPTION");
                double amount = resultSet.getDouble("AMOUNT");
                System.out.println(id + " " + description + ": " + amount);

            }
        } catch (SQLException e) {
            System.err.println("Błąd przy zapytaniu do bazy danych");
            e.printStackTrace();
        }
    }

    @Override
    public void update(String value, int id) {
        try {
            String query = "UPDATE TRANSACTIONS SET DESCRIPTION=? WHERE ID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Błąd przy zapytaniu do bazy danych");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            String query = "DELETE FROM TRANSACTIONS WHERE ID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Błąd przy zapytaniu do bazy danych");
            e.printStackTrace();
        }
    }

    @Override
    public void create(int type, String desc, double amount, Date date) {
        try {
            String query = "INSERT INTO TRANSACTIONS " +
                    "(TYPE, DESCRIPTION, AMOUNT, DATE) " +
                    "VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, type);
            preparedStatement.setString(2, desc);
            preparedStatement.setDouble(3, amount);
            preparedStatement.setDate(4, date);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Błąd przy zapytaniu do bazy danych");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException exception) {
            System.err.println("Błąd przy zamykaniu połączenia");
            exception.printStackTrace();
        }
    }
}
