package repository;

import domain.User;
import exceptions.RepositoryException;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class RepositoryUsers extends AbstractRepository<User>{
    public RepositoryUsers(){
        array = new ArrayList<>();
    }

    /***
     * method that adds a user to the repository if it does not exist.
     * @param user - the user we try to add
     * @throws RepositoryException - if the user already exists.
     */
    @Override
    public void add(User user) throws RepositoryException {
        if(!array.contains(user)){
            array.add(user);
        }
        else{
            throw new RepositoryException("user already exists.");
        }
    }

    /***
     * method that checks if a user exists in the repository.
     * @param ID - the ID of the user we try to find
     * @return true - if the user is found
     *         false - otherwise
     */
    public boolean foundUserByID(int ID){
        for(var user: array){
            if(user.getID() == ID){
                return true;
            }
        }
        return false;
    }

    public Optional<User> findUserByEmailPw(User partial_user){
        for (User user: array){
            if(user.equals(partial_user)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public String getUsernameOfID(int ID){
        for(User user: array){
            if(user.getID() == ID){
                return user.getUsername();
            }
        }
        return "";
    }

    /***
     * method that removes a user from the repository.
     * @param user - the user we try to delete
     * @throws RepositoryException - if the user does not exist.
     */
    @Override
    public void remove(User user) throws RepositoryException{
        if(!array.remove(user)){
            throw new RepositoryException("user does not exist.");
        }
    }

    @Override
    public void update(User user, User newUser) throws RepositoryException {
        var i = array.indexOf(user);
        if (i == -1){
            throw new RepositoryException("user you try to modify does not exist.");
        }
        if (array.contains(newUser)){
            throw new RepositoryException("repo already contains that user.");
        }
        var us = array.get(i);
        us.setUsername(newUser.getUsername());
        us.setPassword(newUser.getPassword());
    }

    public Optional<Integer> getIdOfEmail(String email) {
        for(User user: array){
            if (Objects.equals(user.getEmail(), email)){
                return Optional.of(user.getID());
            }
        }
        return Optional.empty();
    }
}
