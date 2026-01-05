package dao;

import config.DatabaseConfig;
import entity.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* Dao for Course entity
**/
public class CourseDao implements IDao<Course>{
    private static final Logger LOGGER = Logger.getLogger(CourseDao.class.getName());
    private static CourseDao instance;
    private DatabaseConfig dbConfig;

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

    private Course mapResultSetToCourse(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setId(rs.getLong("id_Course"));
        course.setName(rs.getString("co_name"));
        course.setDescription(rs.getString("co_description"));
        course.setDuration(rs.getInt("co_duration"));
        course.setType(rs.getString("co_type"));
        course.setPrice(rs.getDouble("co_price"));
        return course;
    }

    @Override
    public List<Course> findAll() {
        String sql = "SELECT * FROM courses";
        List<Course> courses = new ArrayList<>();

        try (Connection connect = dbConfig.getConnection();
             Statement statement = connect.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                courses.add(mapResultSetToCourse(rs));
            }
        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return courses;
    }
}
