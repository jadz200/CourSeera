package Implemented;
import java.time.LocalTime;

import Interfaces.Course;
public class Course2 implements Course{

    private String crn;
    private String subject;
    private String course_num;
    private String section;
    private String title;
    private float credithrs;
    private String college;
    private int actual_enrol;
    private int seats_available;
    private String bldg;
    private LocalTime begin_time;
    private LocalTime end_time;
    private String room;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday; 
    private String instructor_first;
    private String instructor_last;
/*  requires: crn, subject, course_num, section, title, credithrs, college,
              actual_enrol, seats_available, begin_time, end_time, bldg, room,
              monday, tuesday, wednesday, thursday, friday, saturday, instructor_first, instructor_last,
    effects:  constructs the Object Course with the value inputed*/
    public Course2(String crn,String subject,String course_num,String section,String title,float credithrs,String college,int actual_enrol,int seats_available,LocalTime begin_time,LocalTime end_time,String bldg,String room,boolean monday,boolean tuesday,boolean wednesday,boolean thursday,boolean friday,boolean saturday,String instructor_first,String instructor_last){
        this.crn= crn;
        this.subject=subject;
        this.course_num=course_num;
        this.section=section;
        this.title=title;
        this.credithrs=credithrs;
        this.college=college;
        this.actual_enrol=actual_enrol;
        this.seats_available=seats_available;
        this.begin_time=begin_time;
        this.end_time=end_time;
        this.bldg=bldg;
        this.room=room;
        this.monday=monday;
        this.tuesday=tuesday;
        this.wednesday=wednesday;
        this.thursday=thursday;
        this.friday=friday;
        this.saturday=saturday;
        this.instructor_first=instructor_first;
        this.instructor_last=instructor_last;
    }
    
    @Override
    public String getCrn() {
        return this.crn;
    }

    @Override
    public String getSubject() {
        return this.subject;
    }

    @Override
    public String getCourse_num() {
        return this.course_num;
    }

    @Override
    public String getSection() {
        return this.section;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public float getCredithrs() {
        return this.credithrs;
    }

    @Override
    public String getCollege() {
        return this.college;
    }

    @Override
    public int getActual_enrol() {
        return this.actual_enrol;
    }

    @Override
    public int getSeats_available() {
        return this.seats_available;
    }

    @Override
    public LocalTime getBegin_time() {
        return this.begin_time;
    }

    @Override
    public LocalTime getEnd_time() {
        return this.end_time;
    }

    @Override
    public String getBldg() {
        return this.bldg;
    }

    @Override
    public String getRoom() {
        return this.room;
    }

    @Override
    public boolean getMonday() {
        return this.monday;
    }

    @Override
    public boolean getTuesday() {
        return this.tuesday;
    }

    @Override
    public boolean getWednesday() {
        return this.wednesday;
    }

    @Override
    public boolean getThursday() {
        return this.thursday;
    }

    @Override
    public boolean getFriday() {
        return this.friday;
    }

    @Override
    public boolean getSaturday() {
        return this.saturday;
    }

    @Override
    public String getInstructor_first() {
        return this.instructor_first;
    }

    @Override
    public String getInstructor_last() {
        return this.instructor_last;
    }

}

