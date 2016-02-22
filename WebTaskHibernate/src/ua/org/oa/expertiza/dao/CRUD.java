package ua.org.oa.expertiza.dao;

import java.util.Set;

/**
 * Created by expertiza on 03.11.2015.
 */
public interface CRUD <T>{
    T create(T obj);

    Set<T> read();

    T read(int id) throws NotFoundException;

    boolean update(T obj);

    boolean delete(int id);
}
