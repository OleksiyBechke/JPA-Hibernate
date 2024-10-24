package org.example.app.DAO;

import org.example.app.entity.Student;

import java.util.List;

public interface GenericDAO<T, ID> {

    void save(T entity);

    T findById(ID id);

    T findByEmail(String email);

    List<T> findAll();

    Student update(T entity);

    boolean deleteById(ID id);

}
