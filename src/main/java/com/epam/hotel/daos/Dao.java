package com.epam.hotel.daos;

import java.util.List;

public interface Dao<T> {
    T create(T entity);

    boolean delete(long id);

    boolean update(T entity);

    List<T> getAll();

    T getById(long id);
}