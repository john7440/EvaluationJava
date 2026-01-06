package dao;

import config.DatabaseConfig;
import entity.Course;

import java.io.IOException;
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

    private CourseDao() throws IOException, ClassNotFoundException {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    public static CourseDao getInstance() throws IOException, ClassNotFoundException {
        if (instance == null) {
            instance = new CourseDao();
            }
        return instance;
    }

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
            LOGGER.log(Level.SEVERE, "Error finding all courses", e);
        }
        return courses;
    }

    public List<Course> findByType(String type) throws SQLException {
        String sql = "SELECT * FROM courses WHERE co_type = ?";
        List<Course> courses = new ArrayList<>();

        try(Connection connect = dbConfig.getConnection();
            PreparedStatement statement = connect.prepareStatement(sql)){

            statement.setString(1, type);

            try (ResultSet rs = statement.executeQuery()){
                while (rs.next()) {
                    courses.add(mapResultSetToCourse(rs));
                }
            }
            LOGGER.log(Level.INFO, "Found " + courses.size() + " courses of type: " + type);
        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error finding courses by type", e);
        }

        return courses;
    }

    public List<Course> findByKeyword(String keyword) throws SQLException {
        String sql = "SELECT * FROM courses WHERE co_name LIKE ? OR co_description LIKE ? ";
        List<Course> courses = new ArrayList<>();

        try(Connection connect = dbConfig.getConnection();
        PreparedStatement statement = connect.prepareStatement(sql)){

            String searchPattern = "%" + keyword + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);

            try (ResultSet rs = statement.executeQuery()){
                while (rs.next()) {
                    courses.add(mapResultSetToCourse(rs));
                }
            }
            LOGGER.log(Level.INFO, "Found " + courses.size() + " courses with keyword: " + keyword);
        } catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Error finding courses by keyword", e);
        }
        return courses;
    }
}
