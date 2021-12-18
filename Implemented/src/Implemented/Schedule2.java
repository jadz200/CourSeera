package Implemented;

import java.time.LocalTime;
import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import Interfaces.Course;
import Interfaces.Schedule;

public class Schedule2 implements Schedule,Comparable<Schedule>{

	private final String room;
	private final LocalTime fromTime;
	private final LocalTime toTime;
	private final String instructor;
	private final String course;
	private final String day;
	/*  requires:  room, fromTime, toTime, instructor, course, day
		effects:   constructs Schedule2 with the values inputed*/
	public Schedule2(Course course,String Day) {
		this.room = course.getBldg()+" "+course.getRoom();
		this.fromTime = course.getBegin_time();
		this.toTime = course.getEnd_time();
		this.instructor = course.getInstructor_first()+" "+course.getInstructor_last();
		this.course = course.getSubject()+" "+course.getCourse_num();
		this.day = Day;
	}
	
	@Override
	public String getRoom() {
		return this.room;
	}

	@Override
	public LocalTime getFromTime() {
		return this.fromTime;
	}

	@Override
	public LocalTime getToTime() {
		return this.toTime;
	}

	@Override
	public String getInstructor() {
		return this.instructor;
	}

	@Override
	public String getCourse() {
		return this.course;
	}

	@Override
	public String getDay() {
		return this.day;
	}
	@Override
	public int compareTo(Schedule o1) {
		String d=this.getDay();
		DayOfWeek dayofweek=DayOfWeek.valueOf(d);	
        long v = dayofweek.getLong(ChronoField.DAY_OF_WEEK);       
		LocalTime t=this.getFromTime();
		String d1=o1.getDay();
		DayOfWeek dayofweek1=DayOfWeek.valueOf(d1);	
		long v1= dayofweek1.getLong(ChronoField.DAY_OF_WEEK);     
		LocalTime t1=o1.getFromTime();
		
		if(v==v1) {
			return t.compareTo(t1);
		}else{
			return dayofweek.compareTo(dayofweek1);	
		}
	}
}

