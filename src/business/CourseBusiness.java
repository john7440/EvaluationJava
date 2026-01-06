package business;

import dao.CourseDao;
import dao.DaoFactory;
import entity.Course;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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
}
