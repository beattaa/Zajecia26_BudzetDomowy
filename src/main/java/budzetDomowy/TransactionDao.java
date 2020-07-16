package budzetDomowy;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class TransactionDao {
    private static final String URL = "jdbc:mysql://localhost:3306/zad_domowe?characterEncoding=utf8&serverTimezone=UTC&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Czerwiec123!";
    private Connection connection;

    private static final String INSERT_QUERY = "INSERT INTO TRANSACTIONS (TYPE, DESCRIPTION, AMOUNT, DATE) VALUES (?, ?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT * FROM TRANSACTIONS WHERE TYPE = ?";
    private static final String UPDATE_QUERY = "UPDATE TRANSACTIONS SET DESCRIPTION = ?, AMOUNT = ?, DATE = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM TRANSACTIONS WHERE id = ?";


    public TransactionDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException exception) {
            System.err.println("Błąd przy wczytywaniu sterownika");
        } catch (SQLException exception) {
            System.err.println("Błąd podczas nawiązywania połączenia z bazą danych");
            exception.printStackTrace();
        }
    }

    //R - read
    public List<Transaction> read(int type) {
        List<Transaction> transactionList = new ArrayList<>();
        try {
            PreparedStatement updateSql = connection.prepareStatement(SELECT_QUERY);
            updateSql.setInt(1, type);
            ResultSet resultSet = updateSql.executeQuery();

            while (resultSet.next()) {
                long idFromDatabase = resultSet.getLong("ID");
                int typeFromDatabase = resultSet.getInt("TYPE");
                String descriptionFromDatabase = resultSet.getString("DESCRIPTION");
                double amount = resultSet.getDouble("AMOUNT");
                LocalDate date = resultSet.getDate("DATE").toLocalDate();
                Transaction transaction = new Transaction(idFromDatabase, typeFromDatabase, descriptionFromDatabase, amount, date);
                transactionList.add(transaction);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return transactionList;
    }

    //U - update
    public void update(Transaction transaction) {
        try {
            PreparedStatement updateSql = connection.prepareStatement(UPDATE_QUERY);
            updateSql.setString(1, transaction.getDescription());
            updateSql.setDouble(2, transaction.getAmount());
            updateSql.setDate(3, java.sql.Date.valueOf(transaction.getDate()));
            updateSql.setDouble(4, transaction.getId());
            updateSql.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    //D - delete
    public void delete(long id) {
        try {
            PreparedStatement deleteSql = connection.prepareStatement(DELETE_QUERY);
            deleteSql.setLong(1, id);
            deleteSql.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    //C - create
    public void create(Transaction transaction) {
        try {
            PreparedStatement createSql = connection.prepareStatement(INSERT_QUERY);
            createSql.setInt(1, transaction.getType());
            createSql.setString(2, transaction.getDescription());
            createSql.setDouble(3, transaction.getAmount());
            createSql.setDate(4, java.sql.Date.valueOf(transaction.getDate()));
            createSql.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
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
