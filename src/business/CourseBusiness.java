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

    /**
     * Filter courses by type (PRESENTIEL or DISTANCIEL)
     * @param type course type
     * @return list of courses of specified type
     */
    public List<Course> filterByType(String type) throws SQLException, ClassNotFoundException {
        if (type == null || (!type.equalsIgnoreCase("PRESENTIEL") && !type.equalsIgnoreCase("DISTANCIEL"))) {
            LOGGER.log(Level.WARNING, () -> "Invalid course type " + type);
            return getAllCourses();
        }

        List<Course> results = courseDao.findByType(type.toUpperCase());
        LOGGER.log(Level.INFO, ()->"Found: " + results.size() + " courses matching type: " + type);
        return results;
    }

    /**
     * Display course details
     * @param course course to display
     */
    public void displayCourseDetails(Course course) throws SQLException, ClassNotFoundException {
        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        System.out.println("\n========== Course Details ==========");
        System.out.println("ID: " + course.getId());
        System.out.println("Name: " + course.getName());
        System.out.println("Description: " + course.getDescription());
        System.out.println("Duration: " + course.getDuration() + " days");
        System.out.println("Type: " + course.getType());
        System.out.println("Price: " + String.format("%.2f", course.getPrice()) + " €");
        System.out.println("====================================\n");

    }
}
