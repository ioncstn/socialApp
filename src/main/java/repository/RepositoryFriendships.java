package repository;

import domain.Friendship;
import domain.FriendshipDTO;
import exceptions.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryFriendships extends AbstractRepository<Friendship>{
    public RepositoryFriendships(){
        array = new ArrayList<>();
    }

    /***
     * method that adds a friendship to the repository if it does not exist already.
     * @param friendship - the friendship we try to add
     * @throws RepositoryException - if the friendship already exists
     */
    public void add(Friendship friendship) throws RepositoryException {
        if(!array.contains(friendship)){
            array.add(friendship);
        }
        else{
            throw new RepositoryException("friendship already exists.");
        }
    }

    /***
     * method that removes a friendship from the repository, if it exists.
     * @param friendship - the friendship we try to delete
     * @throws RepositoryException - if the friendship does not exist
     */
    public void remove(Friendship friendship) throws RepositoryException {
        if(!array.remove(friendship)){
            throw new RepositoryException("friendship does not exist.");
        }
    }

    @Override
    public void update(Friendship friendship, Friendship newFriendship) throws RepositoryException {
        var i = array.indexOf(friendship);
        if (i == -1){
            throw new RepositoryException("friendship you try to modify does not exist.");
        }
        var fr = array.get(i);
        fr.setDate(newFriendship.getDate());
        fr.setPending(newFriendship.isPending());
    }

    /***
     * method that removes all the friendships of a user.
     * @param ID - the id of the user we delete the friendships of
     */
    public void removeAllFriendshipsOfUser(int ID){
        for(int i = 0; i < array.size(); i++){
            if(array.get(0).getUserID2() == ID || array.get(0).getUserID1() == ID){
                array.remove(i);
                i--;
            }
        }
    }

    public Optional<Friendship> findFriendship(int id1, int id2){
        for(Friendship friendship: array){
            if((friendship.getUserID2() == id1 && friendship.getUserID1() == id2) || (friendship.getUserID2() == id2 && friendship.getUserID1() == id1)){
                return Optional.of(friendship);
            }
        }
        return Optional.empty();
    }

    /***
     * method that checks if a user has any friendships.
     * @param ID the ID of the user
     * @return  true if he has friendships
     *          false otherwise
     */
    public boolean hasFriendships(int ID){
        for(var friendship : array){
            if(friendship.getUserID2() == ID || friendship.getUserID1() == ID){
                return true;
            }
        }
        return false;
    }

    public List<Friendship> getFriendsOf(int ID){
        List<Friendship> friends = new ArrayList<>();
        for(Friendship friendship: array){
            if(friendship.getUserID1() == ID || friendship.getUserID2() == ID){
                friends.add(friendship);
            }
        }
        return friends;
    }
}
