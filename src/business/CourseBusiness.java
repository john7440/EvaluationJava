package business;

import dao.CourseDao;
import dao.DaoFactory;
import entity.Course;
import entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CourseBusiness {
    private static final Logger LOGGER = Logger.getLogger(CourseBusiness.class.getName());
    private  final CourseDao courseDao;

    public CourseBusiness() throws IOException, ClassNotFoundException {
        this.courseDao = DaoFactory.getCourseDao();
    }

    /**
     * Get all available courses
     * @return list of all courses
     */
    public List<Course> getAllCourses() throws SQLException, ClassNotFoundException {
        return courseDao.findAll();
    }

    /**
     * Search courses by keyword (name or description)
     * @param keyword search keyword
     * @return list of matching courses
     */
    public List<Course> searchByKeyword(String keyword) throws SQLException, ClassNotFoundException {
        if (keyword == null || keyword.trim().isEmpty()) {
            LOGGER.log(Level.WARNING, "Search keyword is null or empty");
            return getAllCourses();
        }

         List<Course> results = courseDao.findByKeyword(keyword.trim());
         LOGGER.log(Level.INFO, ()->"Found: " + results.size() + " courses matching keyword: " + keyword);
         return results;
    }
}
