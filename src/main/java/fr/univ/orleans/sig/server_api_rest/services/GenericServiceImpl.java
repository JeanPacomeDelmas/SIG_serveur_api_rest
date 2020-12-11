package fr.univ.orleans.sig.server_api_rest.services;

import java.util.Collection;

public class GenericServiceImpl<T> implements GenericService<T> {

    @Override
    public Collection<T> findAll() {
        return null;
    }

    @Override
    public T findById(int id) {
        return null;
    }

    @Override
    public T save(T entity) {
        return null;
    }

    @Override
    public T update(T entity) {
        return null;
    }

    @Override
    public boolean delete(T entity) {
        return false;
    }
}
