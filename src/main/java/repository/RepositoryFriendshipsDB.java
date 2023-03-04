package repository;

import domain.Friendship;
import domain.User;
import exceptions.RepositoryException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;

public class RepositoryFriendshipsDB extends RepositoryFriendships{
    private final String url = "jdbc:postgresql://localhost:5432/labMap";
    private final String user = "postgres";
    private final String password = "postgres";

    private Connection connection;

    public RepositoryFriendshipsDB() {
        try{
            connection = DriverManager.getConnection(url, user, password);
            loadFromDB();
        }
        catch (SQLException e){
            System.out.println("database does not connect properly...");
            System.exit(2);
        }
    }

    private void loadFromDB(){
        try {
            String select_all_query = "select * from friendships";
            PreparedStatement preparedStatement = connection.prepareStatement(select_all_query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int user1 = rs.getInt("user1");
                int user2 = rs.getInt("user2");
                int id = rs.getInt("id");
                LocalDateTime date = rs.getDate("date").toLocalDate().atTime(0, 0);
                boolean pending = rs.getBoolean("pending");
                Friendship new_friendship = new Friendship(id, user1, user2, date, pending);
                array.add(new_friendship);
            }
        } catch(SQLException e){
            System.out.println("database can not do queries...");
            System.exit(1);
        }
    }

    @Override
    public void add(Friendship friendship) throws RepositoryException {
        super.add(friendship);
        try{
            String insert_value_query = "INSERT INTO friendships" +
                    "  (id, user1, user2, date, pending) VALUES " +
                    " (?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(insert_value_query);
            statement.setInt(1, friendship.getID());
            statement.setInt(2, friendship.getUserID1());
            statement.setInt(3, friendship.getUserID2());
            statement.setDate(4, Date.valueOf(friendship.getDate().toLocalDate()));
            statement.setBoolean(5, friendship.isPending());
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void remove(Friendship friendship) throws RepositoryException {
        super.remove(friendship);
        String delete_user_query = "delete from friendships where id = ?;";
        try{
            PreparedStatement statement = connection.prepareStatement(delete_user_query);
            statement.setInt(1, friendship.getID());
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void update(Friendship friendship, Friendship newFriendship) throws RepositoryException {
        super.update(friendship, newFriendship);
        String update_friendship_query = "update friendships set pending = ? where id = ?;";
        try{
            PreparedStatement statement = connection.prepareStatement(update_friendship_query);
            statement.setBoolean(1, newFriendship.isPending());
            statement.setInt(2, friendship.getID());
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
