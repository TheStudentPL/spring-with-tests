package pl.edu.wszib.springwithtests.service;

import java.util.List;

public interface AbstractService<T, ID> {
// T - DTO

    T add(T t);
    T update(T t);
    void remove(ID id);
    T find(ID id);
    List<T> findAll();

}
