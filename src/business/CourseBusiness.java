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
        if (type == null || (!type.equalsIgnoreCase("IN-PERSON") && !type.equalsIgnoreCase("REMOTE"))) {
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
     * Validates course data
     * @param course Course to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateCourse(Course course){
        if  (course == null) {
            throw new IllegalArgumentException("Course cannot be null!");
        }
        if (course.getName() == null ||  course.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Course name is required!");
        }
        if (course.getDuration() <= 0) {
            throw new IllegalArgumentException("Course duration must be positive!");
        }
        if (course.getPrice()<= 0) {
            throw new IllegalArgumentException("Course price must be positive!");
        }
        if (course.getType() == null || course.getType().trim().isEmpty()) {
            throw new IllegalArgumentException("Course type is required!");
        }
    }

    /**
     * Creates a new course (Admin only)
     * @param admin Administrator user
     * @param name Course name
     * @param description Course description
     * @param duration Duration in days
     * @param type Type (in-person or remote)
     * @param price Price in euros
     * @return Created course
     */
    public Course createCourse(User admin, String name, String description, int duration, String type, double price){
        checkAdminRights(admin);

        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setDuration(duration);
        course.setType(type);
        course.setPrice(price);

        validateCourse(course);

        Course createdCourse = courseDao.save(course);
        LOGGER.log(Level.INFO, ()->"Created course " + createdCourse.getName() + " with id: " + createdCourse.getId());
        return createdCourse;
    }

    /**
     * Updates an existing course (Admin only)
     * @param admin Administrator user
     * @param id Course ID
     * @param name New course name
     * @param description New description
     * @param duration New duration
     * @param type New type
     * @param price New price
     * @return Updated course
     */
    public Course updateCourse(User admin, Long id, String name, String description, int duration, String type, double price){
        checkAdminRights(admin);

        Course existingCourse = courseDao.findById(id);
        if (existingCourse == null) {
            throw new IllegalArgumentException("Course with ID: " + id + " does not exist!");
        }

        existingCourse.setName(name);
        existingCourse.setDescription(description);
        existingCourse.setDuration(duration);
        existingCourse.setType(type);
        existingCourse.setPrice(price);

        validateCourse(existingCourse);

        Course updatedCourse = courseDao.update(existingCourse);
        LOGGER.log(Level.INFO, () -> "Admin " + admin.getLogin() +
                " updated course ID: " + id);

        return updatedCourse;
    }

    /**
     * Deletes a course (Admin only)
     * @param admin Administrator user
     * @param id Course ID to delete
     * @return true if deletion successful
     */
    public boolean deleteCourse(User admin, Long id){
        checkAdminRights(admin);
        Course course = courseDao.findById(id);

        if (course == null) {
            throw new IllegalArgumentException("Course with ID: " + id + " does not exist!");
        }

        boolean deleted = courseDao.delete(id);

        if  (deleted) {
            LOGGER.log(Level.INFO, () -> "Course " + id + " deleted successfully!");
        }
        return deleted;
    }


    /**
     * Display course details
     * @param course course to display
     */
    public String displayCourseDetails(Course course){
        if (course == null) {
            return "Course not found";
        }

        return "\n=============== Course Details ================\n" +
                "ID: " + course.getId() + "\n" +
                "Name: " + course.getName() + "\n" +
                "Description: " + course.getDescription() + "\n" +
                "Duration: " + course.getDuration() + " days" + "\n" +
                "Type: " + course.getType() + "\n" +
                "Price: " + String.format("%.2f", course.getPrice()) + " €" +"\n" +
                "==================================================\n";
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

    /**
     * Finds a course by ID
     * @param id Course ID
     * @return Course or null if not found
     */
    public Course getCourseById(Long id){
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid course ID!");
        }
        Course course = courseDao.findById(id);

        if (course == null) {
            LOGGER.log(Level.INFO, () -> "No course found with ID: " + id);
        } else {
            LOGGER.log(Level.INFO, () -> "Course retrieved with ID: " + id);
        }
        return course;
    }
}
