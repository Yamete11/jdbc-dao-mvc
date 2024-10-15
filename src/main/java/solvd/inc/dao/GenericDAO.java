package solvd.inc.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T> {
    void create(T entity);
    Optional<T> getById(Long id);
    List<T> findAll();
    void update(T entity);
    void deleteById(Long id);
}
