package datasource;

import java.util.List;

public interface Mapper <T> {

    /*Use String to make it compatible for both string and integer
     * and id2 for cartItem*/
    public T find(String id, String id2);

    public List <T> findAll();

    /*empty for mappers without lazy load
     Use String to make it compatible for both string and integer
     */
    public void getFull(String id);

    public void insert(T obj);

    public void update(T obj);

    public void delete(T obj);

}