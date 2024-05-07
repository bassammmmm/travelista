package sys.airline.airline_apis.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sys.airline.airline_apis.models.Airport;
import sys.airline.airline_apis.services.AirportService;

@RestController
public class blueprint {
    
    @Autowired
    private AirportService sra;


    @RequestMapping(value = "/sre", method = RequestMethod.POST)
    public void createAirport(@Valid @RequestBody Airport sre) {

        sra.createAirport(sre);
    }

    @RequestMapping(value = "/sre")
    public List<Airport> getAirports() {

        return sra.getAirports();
        
    }

    @RequestMapping(value = "/sre/{id}")
    public Airport getAirport(@PathVariable Long id) {

        return sra.getAirport(id);
    }

    @RequestMapping(value = "/sre/{id}", method = RequestMethod.DELETE)
    public void deleteAirport(@PathVariable Long id) {
        sra.deleteAirport(id);
    }


    // @RequestMapping("/topics/{topicId}/courses")
    // public List<Course> getCoursesOfTopic(@PathVariable String topicId) {
        
    //     return courseService.getCoursesOfTopic(topicId);
    // }

    // @RequestMapping("/courses/{id}")
    // public Course getCourse(@PathVariable String id) {

    //     return courseService.getCourse(id);
    // }


    // @RequestMapping(value = "/topics/{topicId}/courses", method = RequestMethod.POST)
    // public void createCourse(@RequestBody Course course, @PathVariable String topicId) {
    //     course.setTopic(topicService.getTopic(topicId));
    //     courseService.addCourse(course);;

    // }
    
    // @RequestMapping(value = "courses/{id}", method = RequestMethod.PUT)
    // public void updateTopic(@RequestBody Course course, @PathVariable String id) {
    //     courseService.updateCourse(course);

    // }
    

    // @RequestMapping(value="courses/{id}", method=RequestMethod.DELETE)
    // public void deleteTopic(@PathVariable String id) {

    //     courseService.deleteCourse(id);
    
    // }
}
