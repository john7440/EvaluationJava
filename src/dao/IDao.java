package dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Generic DAO interface
 * @param <T> Entity type
 */
public interface IDao<T>{
    T save(T entity) throws SQLException;
    T update(T entity);
    boolean delete(Long id);
    T findById(Long id);
    List<T> findAll();
}
