package repository;

import domain.User;
import exceptions.RepositoryException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class RepositoryUsersDB extends RepositoryUsers{

    private final String url = "jdbc:postgresql://localhost:5432/labMap";
    private final String user = "postgres";
    private final String password = "postgres";

    private Connection connection;

    public RepositoryUsersDB() {
        try{
            connection = DriverManager.getConnection(url, user, password);
            loadFromDB();
        }
        catch (SQLException e){
            System.out.println("database does not connect properly...");
            System.out.println(e.getMessage());
            System.exit(2);
        }
    }

    private void loadFromDB(){
        try {
            String select_all_query = "select * from users";
            PreparedStatement preparedStatement = connection.prepareStatement(select_all_query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("password");
                User new_user = new User(id, username, email, password);
                array.add(new_user);
            }
        } catch(SQLException e){
            System.out.println("database can not do queries...");
            System.exit(1);
        }
    }

    @Override
    public void add(User user) throws RepositoryException {
        super.add(user);
        try{
            String insert_value_query = "INSERT INTO users" +
                    "  (id, username, email, password) VALUES " +
                    " (?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(insert_value_query);
            statement.setInt(1, user.getID());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void remove(User user) throws RepositoryException {
        super.remove(user);
        String delete_user_query = "delete from users where id = ?;";
        try{
            PreparedStatement statement = connection.prepareStatement(delete_user_query);
            statement.setInt(1, user.getID());
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
