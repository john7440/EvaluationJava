package business;

import dao.CourseDao;
import dao.DaoFactory;
import entity.Course;
import entity.User;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CourseBusiness {
    private static final Logger LOGGER = Logger.getLogger(CourseBusiness.class.getName());
    private  final CourseDao courseDao;

    public CourseBusiness(){
        this.courseDao = DaoFactory.getCourseDao();
    }

    /**
     * Get all available courses
     * @return list of all courses
     */
    public List<Course> getAllCourses() {
        return courseDao.findAll();
    }

    /**
     * Search courses by keyword (name or description)
     * @param keyword search keyword
     * @return list of matching courses
     */
    public List<Course> searchByKeyword(String keyword) {
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
    public List<Course> filterByType(String type){
        if (type == null || (!type.equalsIgnoreCase("PRESENTIEL") && !type.equalsIgnoreCase("DISTANCIEL"))) {
            LOGGER.log(Level.WARNING, () -> "Invalid course type " + type);
            return getAllCourses();
        }

        List<Course> results = courseDao.findByType(type.toUpperCase());
        LOGGER.log(Level.INFO, ()->"Found: " + results.size() + " courses matching type: " + type);
        return results;
    }

    /**
     * Verifies if user has admin rights
     * @param user User to verify
     * @throws SecurityException if user is not admin
     */
    private void checkAdminRights(User user){
        if (user == null || !user.isAdmin()) {
            throw new SecurityException("Access denied: Administrator privileges required!");
        }
    }


    /**
     * Display course details
     * @param course course to display
     */
    @SuppressWarnings("unused")
    public void displayCourseDetails(Course course){
        if (course == null) {
            System.out.println("Course not found");
            return;
        }

        System.out.println("\n=============== Course Details ================");
        System.out.println("ID: " + course.getId());
        System.out.println("Name: " + course.getName());
        System.out.println("Description: " + course.getDescription());
        System.out.println("Duration: " + course.getDuration() + " days");
        System.out.println("Type: " + course.getType());
        System.out.println("Price: " + String.format("%.2f", course.getPrice()) + " €");
        System.out.println("==================================================\n");

    }

    /**
     * Display list of courses
     * @param courses list of courses to display
     */
    public void displayCourseList(List<Course> courses) {
        if (courses == null || courses.isEmpty()) {
            System.out.println("No courses available");
            return;
        }

        System.out.println("\n======================== Available Courses ================================");
        for (Course course : courses) {
            System.out.printf("ID: %-5d | %-22s | %-12s | %2d days | %5.2f €%n",
                    course.getId(),
                    course.getName(),
                    course.getType(),
                    course.getDuration(),
                    course.getPrice());
        }
        System.out.println("==========================================================================\n");
    }
}
