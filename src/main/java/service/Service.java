package service;

import domain.*;
import exceptions.RepositoryException;
import exceptions.ServiceException;
import exceptions.ValidatorException;
import graph.Graph;
import graph.Pair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import repository.*;
import validator.Validator;

import java.time.LocalDateTime;
import java.util.*;

public class Service {
    private int IDGenUsers;
    private int IDGenFriendships;
    private static final Service service = null;
    private final RepositoryUsersDB repoUsers;
    private final RepositoryFriendshipsDB repoFriends;
    private final RepositoryMessagesDB repoMessages;
    private final Validator validator;
    private Service(RepositoryUsersDB r1, RepositoryFriendshipsDB r2, RepositoryMessagesDB r3){
        repoUsers = r1;
        repoFriends = r2;
        repoMessages = r3;
        validator = new Validator();
        this.startUp();
    }

    private void startUp(){
        var users = repoUsers.getItems();
        var user = users.stream().max(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Integer.compare(o1.getID(), o2.getID());
            }
        });
        if (user.isPresent()){
            IDGenUsers = user.get().getID() + 1;
        }
        else{
            IDGenUsers = 1;
        }
        var friendships = repoFriends.getItems();
        var friendship = friendships.stream().max(new Comparator<Friendship>() {
            @Override
            public int compare(Friendship o1, Friendship o2) {
                return Integer.compare(o1.getID(), o2.getID());
            }
        });
        if (friendship.isPresent()){
            IDGenFriendships = friendship.get().getID() + 1;
        }
        else{
            IDGenFriendships = 1;
        }
    }
/*
    private void startUp(){
        ArrayList<User> users = repoUsers.loadFromFile();
        String message = "";
        for(int i = 0; i < users.size(); i++){
            User user = users.get(i);
            try{
                validator.ValidateUser(user.getUsername(), user.getEmail(), user.getPassword());
            }
            catch(ValidatorException e){
                message += "error at line " + (i + 1) + ".\n";
            }
        }
        if (!message.equals("")){
            System.out.println(message);
            System.exit(1);
        }
        else {
            for(int i = 0; i < users.size() - 1; i++){
                for(int j = i + 1; j < users.size(); j++){
                    if(users.get(i).equals(users.get(j))) {
                        System.out.println("users with same ID and/or mail in the file.");
                        System.exit(2);
                    }
                }
            }
            users.forEach(user -> {
                try {
                    repoUsers.add(user);
                    if(user.getID() >= IDGenUsers){
                        IDGenUsers = user.getID() + 1;
                    }
                }
                catch(RepositoryException ignored){}
            });
        }

        ArrayList<Friendship> friendships = repoFriends.loadFromFile();
        for(int i = 0; i < friendships.size() - 1; i++){
            if (friendships.get(i).getUserID1() == friendships.get(i).getUserID2()){
                System.out.println("friendship with same users at line " + (i + 1) + "\n");
                System.exit(2);
            }
            for(int j = i + 1; j < friendships.size(); j++){
                if(friendships.get(i).equals(friendships.get(j))) {
                    System.out.println("same friendship in file. at lines " + (i + 1) + " and " + (j + 1) + "\n");
                    System.exit(2);
                }
            }
        }
        friendships.forEach(friendship -> {
            try {
                repoFriends.add(friendship);
                if(friendship.getID() >= IDGenFriendships){
                    IDGenFriendships = friendship.getID() + 1;
                }
            }
            catch(RepositoryException ignored){}
        });
    }
 */

    /***
     * method used to get the instance of the service (singleton).
     * @param r1 the users' repository
     * @param r2 the friendships' repository
     * @return Service - the service instance
     */
    public static Service getService(RepositoryUsersDB r1, RepositoryFriendshipsDB r2, RepositoryMessagesDB r3){
        if(service == null){
            return new Service(r1, r2, r3);
        }
        return service;
    }

    /***
     * method that adds a user to the users' repository.
     * @param username the username of the user
     * @param email the email of the user
     * @param password the password of the user
     * @throws RepositoryException if the repository fails in adding the user
     * @throws ValidatorException if the username, email and/or password are invalid
     */
    public void addUser(String username, String email, String password) throws RepositoryException, ValidatorException{
        validator.ValidateUser(username, email, password);
        User user = new User(IDGenUsers++, username, email, password);
        repoUsers.add(user);
    }

    /***
     * method that removes a friendship from the friendships' repository.
     * @param ID - the ID of the friendship that is going to be deleted
     * @throws RepositoryException if the repository fails in removing the friendship
     */
    public void removeUser(int ID) throws RepositoryException, ServiceException{
        User user = new User(ID, "", "", "");
        if(!repoFriends.hasFriendships(ID)) {
            repoUsers.remove(user);
        }
        else{
            throw new ServiceException("the user has relations. Can't be removed.");
        }
    }

    /***
     * method that adds a friendship in the friendships' repository.
     * @param ID1 the ID of the first user
     * @param ID2 the ID of the second user
     * @throws RepositoryException if the repository fails in adding the friendship
     * @throws ServiceException if the IDs are the same or at least one of the users does not exist
     */
    public void addFriendship(int ID1, int ID2) throws RepositoryException, ServiceException{
        if(ID1 == ID2){
            throw new ServiceException("same ID given.");
        }
        if(!repoUsers.foundUserByID(ID1) || !repoUsers.foundUserByID(ID2)){
            throw new ServiceException("user with given ID does not exist.");
        }
        Friendship friendship = new Friendship(IDGenFriendships++, ID1, ID2, LocalDateTime.now(), true);
        repoFriends.add(friendship);
    }

    /***
     * method that removes a friendship from the friendships' repository.
     * @param ID the ID of the friendship
     * @throws RepositoryException if the repository fails in removing the friendship
     */
    public void removeFriendship(int ID) throws RepositoryException{
        Friendship friendship = new Friendship(ID, -1, -1, LocalDateTime.now());
        repoFriends.remove(friendship);
    }

    /***
     * method that creates a Graph, where the vertices are the users and the edges are the friendships.
     * @return Graph - the graph
     */
    private Graph makeSocialNetworkGraph(){
        ArrayList<Friendship> friendships = repoFriends.getItems();
        ArrayList<User> users = repoUsers.getItems();
        Graph graph = new Graph();
        for(var user: users){
            graph.addVertex(user.getID());
        }
        for(var friendship: friendships){
            graph.addEdge(friendship.getUserID1(), friendship.getUserID2());
        }
        return graph;
    }

    /***
     * method that gets the number of communities in the social app.
     * @return int - the number of social communities.
     */
    public int getCommunities(){
        Graph graph = makeSocialNetworkGraph();
        return graph.connectedComponents();
    }

    /***
     * method that finds the length of the longest path (an approximation) in the graph created by the social app.
     * @return int - the length of the longest path
     */
    public Stack<Pair<Integer, Integer>> getLongestPath(){
        Graph graph = makeSocialNetworkGraph();
        return graph.longestPath();
    }

    /***
     * method that returns all the users.
     * @return ArrayList - containing the users
     */
    public ArrayList<User> getUsers(){
        return repoUsers.getItems();
    }
    /***
     * method that returns all the friendships.
     * @return ArrayList - containing the friendships
     */
    public ArrayList<Friendship> getFriendships(){
        return repoFriends.getItems();
    }

    public Optional<User> checkUserPresent(String email, String password){
        return repoUsers.findUserByEmailPw(new User(-1, "", email, password));
    }

    public List<FriendshipDTO> getFriendsOf(User current_user) {
        List<FriendshipDTO> friends = FXCollections.observableArrayList();
        int user_id = current_user.getID();
        var all_friendships = repoFriends.getFriendsOf(user_id);
        for(Friendship friendship: all_friendships){
            FriendshipDTO fr;
            if(friendship.getUserID1() == user_id && !friendship.isPending()){
                fr = new FriendshipDTO(friendship.getUserID2(), repoUsers.getUsernameOfID(friendship.getUserID2()), friendship.getDate(), friendship.isPending());
                friends.add(fr);
            }
            if(friendship.getUserID2() == user_id && !friendship.isPending()){
                fr = new FriendshipDTO(friendship.getUserID1(), repoUsers.getUsernameOfID(friendship.getUserID1()), friendship.getDate(), friendship.isPending());
                friends.add(fr);
            }
        }
        return friends;
    }
    public List<FriendshipDTO> getPendingOf(User current_user) {
        List<FriendshipDTO> friends = FXCollections.observableArrayList();
        int user_id = current_user.getID();
        var all_friendships = repoFriends.getFriendsOf(user_id);
        for(Friendship friendship: all_friendships){
            if(friendship.getUserID2() == user_id && friendship.isPending()){
                FriendshipDTO fr = new FriendshipDTO(friendship.getUserID1(), repoUsers.getUsernameOfID(friendship.getUserID1()), friendship.getDate(), friendship.isPending());
                friends.add(fr);
            }
        }
        return friends;
    }

    public void handleRequest(int id1, int id2, boolean accepted) throws RepositoryException {
        var friendship = repoFriends.findFriendship(id1, id2);
        if(friendship.isPresent()){
            var new_fr = friendship.get();
            if(accepted) {
                new_fr.setPending(false);
                repoFriends.update(friendship.get(), new_fr);
            }
            else{
                repoFriends.remove(new_fr);
            }
        }
    }

    public Optional<Integer> getIdOfEmail(String email) {
        return repoUsers.getIdOfEmail(email);
    }

    public List<UserDTO> getNotFriends(int ID) {
        var users = repoUsers.getItems();
        List<UserDTO> returnList = FXCollections.observableArrayList();
        for(User user: users){
            if (user.getID() != ID){
                if (repoFriends.findFriendship(ID, user.getID()).isEmpty()){
                    returnList.add(new UserDTO(user.getID(), user.getUsername()));
                }
            }
        }
        return returnList;
    }

    public List<UserDTO> getFriendRequestsSent(int ID) {
        var users = repoUsers.getItems();
        List<UserDTO> returnList = FXCollections.observableArrayList();
        for(User user: users){
            if (user.getID() != ID){
                var friendship = repoFriends.findFriendship(ID, user.getID());
                if (friendship.isPresent() && friendship.get().getUserID1() == ID && friendship.get().isPending()){
                    returnList.add(new UserDTO(user.getID(), user.getUsername()));
                }
            }
        }
        return returnList;
    }

    public List<Message> getMessages(){
        return repoMessages.getAll();
    }

    public List<MessageDTO> getMessagesBetween(int id1, int id2, String username1, String username2){
        var messages = repoMessages.getAll();
        List<MessageDTO> returnList = FXCollections.observableArrayList();
        for (Message message: messages){
            if (message.getUserID1() == id1 && message.getUserID2() == id2){
                returnList.add(new MessageDTO(username1, message.getText(), message.getDate()));
            }
            if (message.getUserID1() == id2 && message.getUserID2() == id1){
                returnList.add(new MessageDTO(username2, message.getText(), message.getDate()));
            }
        }
        return returnList;
    }
    public void addMessage(int id1, int id2, String text){
        var message = new Message(text, LocalDateTime.now(), id1, id2);
        repoMessages.add(message);
    }
}
