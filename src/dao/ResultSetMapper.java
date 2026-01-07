package dao;

import entity.Course;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility class for mapping ResultSet to entities
 */
public final class ResultSetMapper {

    private ResultSetMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Map ResultSet to Course object
     * @param rs ResultSet containing course data
     * @return Course object
     * @throws SQLException if database access error occurs
     */
    public static Course mapToCourse(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setId(rs.getLong("id_Course"));
        course.setName(rs.getString("co_name"));
        course.setDescription(rs.getString("co_description"));
        course.setDuration(rs.getInt("co_duration"));
        course.setType(rs.getString("co_type"));
        course.setPrice(rs.getDouble("co_price"));
        return course;
    }

    /**
     * Map ResultSet to User object
     */
    public static User mapToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id_User"));
        user.setLogin(rs.getString("u_login"));
        user.setPassword(rs.getString("u_password"));
        return user;
    }

}
