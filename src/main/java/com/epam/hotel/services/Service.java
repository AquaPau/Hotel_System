package com.epam.hotel.services;

import java.util.List;

public interface Service<T> {
    T create(T entity);

    boolean delete(long id);

    boolean update(T entity);

    List<T> getAll();

    T getById(long id);
}
