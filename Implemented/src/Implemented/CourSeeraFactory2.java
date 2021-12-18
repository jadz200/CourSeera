package Implemented;

import java.util.List;

import Interfaces.CourSeeraFactory;
import Interfaces.CourSeera;
import Interfaces.Course;

public class CourSeeraFactory2 implements CourSeeraFactory{

    @Override
    public CourSeera createInstance(List<Course> courses) {
        return new CourSeera2(courses);
    }
    
}
