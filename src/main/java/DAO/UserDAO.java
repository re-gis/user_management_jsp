package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.User;

public class UserDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3307/demo?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    private static final String INSERT_USER_QUERY = "INSERT INTO users " + "(name, email, country) VALUES "
            + " (?, ?, ?);";
    private static final String SELECT_USER_BY_ID_QUERY = "SELECT id, name, email, country FROM users WHERE id = ?;";
    private static final String SELECT_ALL_USERS_QUERY = "SELECT * FROM users;";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?;";
    private static final String UPDATE_USER_QUERY = "update users set name = ?,email= ?, country =? where id = ?;";

    public UserDAO() {
    }

    protected Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public void insertUser(User user) throws SQLException {
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT_USER_QUERY)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getCountry());
            stmt.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public User selectUser(int id) {
        User user = null;
        try (
                Connection conn = getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(SELECT_USER_BY_ID_QUERY);) {

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id, name, email, country);
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try (
                Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_USERS_QUERY);) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY);) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.setInt(4, user.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_USER);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}