package repository;

import domain.Friendship;
import domain.Message;

import java.sql.*;
import java.time.LocalDateTime;

public class RepositoryMessagesDB extends RepositoryMessages{

    private final String url = "jdbc:postgresql://localhost:5432/labMap";
    private final String user = "postgres";
    private final String password = "postgres";

    private Connection connection;

    public RepositoryMessagesDB(){
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
            String select_all_query = "select * from messages";
            PreparedStatement preparedStatement = connection.prepareStatement(select_all_query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int user1 = rs.getInt("userid1");
                int user2 = rs.getInt("userid2");
                String text = rs.getString("text");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                var message = new Message(text, date, user1, user2);
                messages.add(message);
            }
        } catch(SQLException e){
            System.out.println("database can not do queries...");
            System.exit(1);
        }
    }

    @Override
    public void add(Message message){
        super.add(message);
        try{
            String insert_value_query = "INSERT INTO messages" +
                    "  (userid1, userid2, text, date) VALUES " +
                    " (?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(insert_value_query);
            statement.setInt(1, message.getUserID1());
            statement.setInt(2, message.getUserID2());
            statement.setString(3, message.getText());
            statement.setTimestamp(4, Timestamp.valueOf(message.getDate()));
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
