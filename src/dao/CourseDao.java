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
 * Singleton pattern is used intentionally
**/
@SuppressWarnings("java:S6548")
public class CourseDao implements IDao<Course>{
    private static final Logger LOGGER = Logger.getLogger(CourseDao.class.getName());
    private final DatabaseConfig dbConfig;

    private CourseDao() {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    private static class SingletonHolder {
        private static final CourseDao INSTANCE = new CourseDao();
    }

    public static CourseDao getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public Course save(Course course){
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }

        String sql = "INSERT INTO course(co_name, co_description, co_duration, co_type, co_price)VALUES (?,?,?,?,?)";

        try( Connection connection = dbConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

        }
    }

    @Override
    public Course update(Course entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
    }

    @Override
    public Course findById(Long id) {
        String sql = "SELECT c.* FROM course c WHERE id_Course = ?";

        try(Connection connection = dbConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()){
                if (rs.next()){
                    return  ResultSetMapper.mapToCourse(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding course by ID", e);
        }
        return null;
    }

    @Override
    public List<Course> findAll() {
        String sql = "SELECT c.* FROM course c";
        List<Course> courses = new ArrayList<>();

        try (Connection connect = dbConfig.getConnection();
             Statement statement = connect.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                courses.add(ResultSetMapper.mapToCourse(rs));
            }
        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error finding all courses", e);
        }
        return courses;
    }

    public List<Course> findByType(String type) {
        String sql = "SELECT c.* FROM course c WHERE co_type = ?";
        List<Course> courses = new ArrayList<>();

        try(Connection connect = dbConfig.getConnection();
            PreparedStatement statement = connect.prepareStatement(sql)){

            statement.setString(1, type);

            try (ResultSet rs = statement.executeQuery()){
                while (rs.next()) {
                    courses.add(ResultSetMapper.mapToCourse(rs));
                }
            }
            LOGGER.log(Level.INFO, () ->"Found " + courses.size() + " courses of type: " + type);
        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error finding courses by type", e);
        }

        return courses;
    }

    public List<Course> findByKeyword(String keyword) {
        String sql = "SELECT c.* FROM course c WHERE co_name LIKE ?";
        List<Course> courses = new ArrayList<>();

        try(Connection connect = dbConfig.getConnection();
        PreparedStatement statement = connect.prepareStatement(sql)){

            String searchPattern = "%" + keyword + "%";
            statement.setString(1, searchPattern);

            try (ResultSet rs = statement.executeQuery()){
                while (rs.next()) {
                    courses.add(ResultSetMapper.mapToCourse(rs));
                }
            }
            LOGGER.log(Level.INFO, () ->"Found " + courses.size() + " courses with keyword: " + keyword);
        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error finding courses by keyword", e);
        }
        return courses;
    }
}
