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

            ResultSetMapper.mapCourseToInsertStatement(statement, course);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating course failed!");
            }

            try(ResultSet generatedKeys = statement.getGeneratedKeys()){
                if (generatedKeys.next()){
                    course.setId(generatedKeys.getLong(1));
                    LOGGER.log(Level.INFO, () ->"Course created successfully with ID: " + course.getId());
                } else {
                    throw new SQLException("Creating course failed, no ID obtained");
                }
            }
            return  course;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Course update(Course course) {
        if  (course == null || course.getId() == null || course.getId() <=0) {
            throw new IllegalArgumentException("Invalid course for update!");
        }

        String sql = "UPDATE course SET co_name = ?, co_description = ?, co_duration = ?, co_type = ?, co_price = ? WHERE id_Course = ?";

        try(Connection connection = dbConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){

            ResultSetMapper.mapCourseToUpdateStatement(statement, course);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                LOGGER.log(Level.WARNING, () ->"No course found with ID: " + course.getId() + " !");
                return null;
            }
            LOGGER.log(Level.INFO, () -> "Course " +  course.getId() + " updated successfully!");
            return course;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, () ->"Error updating course ID: " + course.getId());
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid course id!");
        }

        String sql = "DELETE FROM course WHERE id_Course = ?";

        try(Connection connection = dbConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();

            boolean deleted = affectedRows > 0;

            if (deleted) {
                LOGGER.log(Level.INFO, () -> "Course " + id + " deleted successfully!");
            } else {
                LOGGER.log(Level.WARNING, () -> "Course " + id + " could not be deleted!");
            }
            return deleted;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, () ->"Error deleting course ID: " + id);
        }
        return false;
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
