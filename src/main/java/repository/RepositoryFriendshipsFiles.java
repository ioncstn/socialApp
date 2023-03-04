package repository;

import domain.Friendship;
import exceptions.RepositoryException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class RepositoryFriendshipsFiles extends RepositoryFriendships{
    private final String path;

    public RepositoryFriendshipsFiles(String path) {
        this.path = path;
    }

    public ArrayList<Friendship> loadFromFile(){
        ArrayList<Friendship> friendships = new ArrayList<>();
        Path filePath = Paths.get(path);
        try{
            List<String> lines = Files.readAllLines(filePath);
            lines.forEach(line -> {
                String[] words = line.split(",");
                int IDFriendship, ID1, ID2;
                try{
                    IDFriendship = Integer.parseInt(words[0]);
                    ID1 = Integer.parseInt(words[1]);
                    ID2 = Integer.parseInt(words[2]);
                    LocalDateTime date = LocalDateTime.now();
                    try {
                        date = LocalDateTime.parse(words[3] + " 0:0", Formatter.formatter);
                    }
                    catch(DateTimeParseException e){
                        System.out.println("date not valid...");
                        e.printStackTrace();
                        System.exit(2);
                    }
                    Friendship friendship = new Friendship(IDFriendship, ID1, ID2, date);
                    friendships.add(friendship);
                }
                catch(NumberFormatException e){
                    System.out.println("ID was not a positive integer.");
                    e.printStackTrace();
                    System.exit(2);
                }
            });
        }
        catch (IOException e){
            System.out.println("error while reading from friendships file.");
            e.printStackTrace();
            System.exit(2);
        }
        return friendships;
    }

    private void saveToFile(){
        try{
            FileWriter writer = new FileWriter(path);
            for (Friendship friendship :
                    array) {
                String line = friendship.getID() + "," + friendship.getUserID1() + "," + friendship.getUserID2() + "," + friendship.getDate().format(Formatter.formatter).split(" ")[0]+ '\n';
                writer.write(line);
            }
            writer.close();
        }
        catch (IOException e){
            System.out.println("error when writing to file.");
            e.printStackTrace();
            System.exit(2);
        }
    }

    @Override
    public void remove(Friendship friendship) throws RepositoryException {
        super.remove(friendship);
        this.saveToFile();
    }

    @Override
    public void add(Friendship friendship) throws RepositoryException {
        super.add(friendship);
        this.saveToFile();
    }

    @Override
    public void update(Friendship friendship, Friendship newFriendship) throws RepositoryException {
        super.update(friendship, newFriendship);
        this.saveToFile();
    }
}
