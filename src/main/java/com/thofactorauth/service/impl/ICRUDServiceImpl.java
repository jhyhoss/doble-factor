package com.thofactorauth.service.impl;


import com.thofactorauth.repository.IGenericRepo;
import com.thofactorauth.service.ICRUDService;

import java.util.List;

public abstract class ICRUDServiceImpl<T, ID> implements ICRUDService<T, ID> {

    protected abstract IGenericRepo<T, ID> getRepo();

    @Override
    public T save(T t) {
        return getRepo().save(t);
    }

    @Override
    public T update(T t) {
        return getRepo().save(t);
    }

    @Override
    public List<T> findAll() {
        return getRepo().findAll();
    }

    @Override
    public T findById(ID id) {
        return getRepo().findById(id).orElse(null);
    }

    @Override
    public void delete(ID id) {
        getRepo().deleteById(id);
     }

}
