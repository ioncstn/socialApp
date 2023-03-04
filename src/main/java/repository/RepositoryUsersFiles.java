package repository;

import domain.User;
import exceptions.RepositoryException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RepositoryUsersFiles extends RepositoryUsers{
    private final String path;

    public RepositoryUsersFiles(String path) {
        this.path = path;
    }

    public ArrayList<User> loadFromFile(){
        Path filePath = Paths.get(path);
        ArrayList<User> users = new ArrayList<>();
        try{
            List<String> lines = Files.readAllLines(filePath);
            lines.forEach(line ->{
                String[] words = line.split(",");
                int ID;
                try{
                    ID = Integer.parseInt(words[0]);
                    User user = new User(ID, words[1], words[2], words[3]);
                    users.add(user);
                }
                catch(NumberFormatException e){
                    System.out.println("ID was not a positive integer.");
                }
            });
        }
        catch(IOException e){
            System.out.println("error when reading from file.");
            e.printStackTrace();
        }
        return users;
    }

    private void saveToFile(){
        try{
            FileWriter writer = new FileWriter(path);
            for (User user :
                    array) {
                String line = user.getID() + "," + user.getUsername() + "," + user.getEmail() + "," + user.getPassword() + '\n';
                writer.write(line);
            }
            writer.close();
        }
        catch (IOException e){
            System.out.println("error when writing to file.");
            e.printStackTrace();
        }
    }

    @Override
    public void add(User user) throws RepositoryException {
        super.add(user);
        this.saveToFile();
    }

    @Override
    public void remove(User user) throws RepositoryException {
        super.remove(user);
        this.saveToFile();
    }

    @Override
    public void update(User user, User newUser) throws RepositoryException {
        super.update(user, newUser);
        this.saveToFile();
    }
}
