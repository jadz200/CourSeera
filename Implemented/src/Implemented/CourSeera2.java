package Implemented;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

import Interfaces.Course;
import Interfaces.CourSeera;
import Interfaces.Instructor;
import Interfaces.Room;
import Interfaces.Schedule;


public class CourSeera2 implements CourSeera{

	private Comparator<? super Schedule> c;
	final private List<Course> courses;

    public CourSeera2(List<Course> courses) {
        this.courses = courses;
    }    

    /*  requires:the list of all the courses 
     * a specific room inputed by the user ex: BLISS 205
     * effects:  returns List of all the courses happening in the room sorted by Time*/ 
    @Override
    public List<Schedule> roomSchedule(Room room) {
    	
        List<Schedule> schedules =new ArrayList<Schedule>();
        ListIterator<Course> listIterator=courses.listIterator();
        
        while (listIterator.hasNext()) {
            Course course = listIterator.next();
            if (room.getBuilding().equalsIgnoreCase(course.getBldg()) && (room.getRoomNumber().equalsIgnoreCase(course.getRoom()))) {
            	
            	String[] days=getDays(course);
            	
            	for(String day:days) { 
            		if(day=="") {
            			day="MONDAY";
            		}
                    Schedule schedule = new Schedule2(course,day);
                    schedules.add(schedule);
            	}
            }
            
        }
        schedules.sort(c);

        return schedules;
    }
    /*  requires:the list of all the courses 
     * a specific room inputed by the user ex: BLISS 205
     * a specific Date inputed by the user ex: 2021-12-01
     * effects:  returns List of all the courses happening in the room sorted by Time*/ 
    @Override
    public List<Schedule> roomSchedule(Room room, LocalDate date) {

        String d1 = date.getDayOfWeek().toString().toUpperCase(); //Will make the first char of the 
        DayOfWeek day = DayOfWeek.valueOf(d1);
        return roomSchedule(room, day);
    }
 
    /*  requires:the list of all the courses 
    * a specific room inputed by the user ex: BLISS 205
    * a specific Day of the week inputed by the user ex: Monday
    * effects:  returns List of all the courses happening in the room sorted by Time*/ 
    public static boolean Matches(DayOfWeek target, String[] days) {
    	for(String day:days) {
    		if(day.equals(target.toString())) {
    			return true;
    		}
    	}
		return false;
    }

    /*  requires:the list of all the courses 
     * a specific room inputed by the user ex: BLISS 205
     * a specific Day of the week inputed by the user ex: Monday
     * effects:  returns List of all the courses happening in the room sorted by Time*/ 

    @Override
    public List<Schedule> roomSchedule(Room room, DayOfWeek day) {
        List<Schedule> schedules = new ArrayList<Schedule>();
        if (day == DayOfWeek.SUNDAY) {
        	return schedules;
        }
        ListIterator<Course> listIterator = courses.listIterator();
        while (listIterator.hasNext()) {
            Course course = listIterator.next();
            String[] days=getDays(course);
            if (room.getBuilding().equalsIgnoreCase(course.getBldg()) && (room.getRoomNumber().equalsIgnoreCase(course.getRoom()))  && (Matches(day, days))) {
                Schedule schedule = new Schedule2(course,day.toString());
                schedules.add(schedule);
            }
        }
        schedules.sort(c);

        return schedules;
    }

    /*  requires:the list of all the courses 
     * a specific room inputed by the user ex: BLISS 205
     * effects:  returns the schedule of the course that was there last*/ 
    @Override
    public Schedule whoWasThereLast(Room room) {
        Schedule schedule = null;
        LocalTime tnow = LocalTime.now();
        LocalDate dnow =LocalDate.now();        
        List<Schedule> roomsched=roomSchedule(room, dnow.getDayOfWeek());
        ListIterator<Schedule> listIterator = roomsched.listIterator();

        while (listIterator.hasNext()) {
            Schedule course = listIterator.next();
                         	
                if ((tnow.compareTo(course.getFromTime()) < 0)) {
                    break;
                }
                schedule = course;
        }
        return schedule;
    }

    /*  requires:the list of all the courses 
     * a specific room inputed by the user ex: BLISS 205
     * effects:  returns the schedule of the course that is happening now*/ 
    @Override
    public Schedule whoIsThereNow(Room room) {
        Schedule schedule = null;

        LocalTime tnow = LocalTime.now();
        LocalDate dnow =LocalDate.now();
        ListIterator<Course> listIterator = courses.listIterator();

        while (listIterator.hasNext()) {
            Course course = listIterator.next();
            boolean matches=false;
        	String[] days=getDays(course);
        	for(String day:days) {
        		if(day.equalsIgnoreCase(dnow.getDayOfWeek().toString())) {
        			matches=true;
        			break;
        		}
        	}
            if (room.getBuilding().equalsIgnoreCase(course.getBldg()) && (room.getRoomNumber().equalsIgnoreCase(course.getRoom()))  && matches==true) {

                if ((tnow.compareTo(course.getBegin_time())>=0) && (tnow.compareTo(course.getEnd_time())<=0)) {
                    schedule = new Schedule2(course,dnow.getDayOfWeek().toString());
                    break;
                }

            }
        }
        return schedule;
    }

    /*  requires:the list of all the courses 
     * a specific Instructor inputed by the user ex: Izzat ElHajj
     * effects:  returns a List of all the schedules that the instructor teach sorted by the time and date*/ 
    @Override
    public List<Schedule> profSchedule(Instructor instructor) {
        List<Schedule> schedules = new ArrayList<Schedule>();
        ListIterator<Course> listIterator = courses.listIterator();

        while (listIterator.hasNext()) {
            Course course = listIterator.next();
            if (instructor.getFirstName().equalsIgnoreCase(course.getInstructor_first()) && (instructor.getLastName().equalsIgnoreCase(course.getInstructor_last()))) {

            	String[] days=getDays(course);
            	for(String day:days) {
            		if(day=="") {
            			day="MONDAY";
            		}
                    Schedule schedule = new Schedule2(course,day);
                    schedules.add(schedule);
            	}
            }
        }
        schedules.sort(c);

        return schedules;
    }

 
    
    /*  requires:the list of all the courses 
     * a specific Instructor inputed by the user ex: Izzat ElHajj
     * effects:  returns a schedule which show where the professor is currently teaching*/ 
    @Override
    public Schedule whereIsProf(Instructor instructor) {
        Schedule schedule = null;
        List<Schedule> profCourses=profSchedule(instructor);
        ListIterator<Schedule> listIterator = profCourses.listIterator();
        LocalTime tnow= LocalTime.now();
        LocalDate dnow=LocalDate.now();
        while (listIterator.hasNext()) {
            Schedule course = listIterator.next();
                if ((tnow.compareTo(course.getFromTime()) >= 0) && (tnow.compareTo(course.getToTime()) <= 0) && dnow.getDayOfWeek().toString().equals(course.getDay())) {
                    schedule = course;
                    break;
                }
        }
        return schedule;
    }

    
    
    /*  requires:the list of all the courses 
     * a specific Instructor inputed by the user ex: Izzat ElHajj
     * effects:  returns a schedule which show where the professor will be teaching*/ 
    @Override
    public List<Schedule> whereWillProfBe(Instructor instructor) {
        List<Schedule> schedules = new ArrayList<Schedule>();
        List<Schedule> profCourses=profSchedule(instructor);
        ListIterator<Schedule> listIterator = profCourses.listIterator();        
        LocalTime tnow = LocalTime.now();
        LocalDate dnow=LocalDate.now();
        while (listIterator.hasNext()) {
            Schedule course = listIterator.next();
            	if (course.getFromTime() == null)
                         continue;
            	
            	if (tnow.compareTo(course.getFromTime()) < 0 && dnow.getDayOfWeek().toString().equals(course.getDay())) {
                    schedules.add(course);
                	
                }
        }

        return schedules;
    }
    /*  requires:a specific course 
     * effects:  returns an array with all the days where this course is taught*/ 
    public String[] getDays(Course course) {
    	String days="";
    	if(course.getMonday()==true) {
    		days+="MONDAY ";
    	}
    	if(course.getTuesday()==true) {
    		days+="TUESDAY ";
    	}
    	if(course.getWednesday()==true) {
    		days+="WEDNESDAY ";
    	}
    	if(course.getThursday()==true) {
    		days+="THURSDAY ";
    	}
    	if(course.getFriday()==true) {
    		days+="FRIDAY ";
    	}
    	if(course.getSaturday()==true) {
    		days+="SATURDAY ";
    	}
    	
		return days.split(" ");
    	
    }
}


