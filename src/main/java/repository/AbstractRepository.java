package repository;

import exceptions.RepositoryException;

import java.util.ArrayList;

public abstract class AbstractRepository<T> {
    protected ArrayList<T> array;
    public abstract void add(T item) throws RepositoryException;
    public abstract void remove(T item) throws RepositoryException;

    public abstract void update(T item, T newItem) throws RepositoryException;
    /***
     * method that returns all the items of a repository.
     * @return ArrayList - of the items
     */
    public ArrayList<T> getItems(){
        return array;
    }
}
