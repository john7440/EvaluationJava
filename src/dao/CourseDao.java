package dao;

import entity.Course;

import java.sql.SQLException;
import java.util.List;

public class CourseDao implements IDao<Course>{
    @Override
    public Course save(Course entity) throws SQLException {
        return null;
    }

    @Override
    public Course update(Course entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Course findById(Long id) {
        return null;
    }

    @Override
    public List<Course> findAll() {
        return List.of();
    }
}
