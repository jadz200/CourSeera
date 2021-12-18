package Interfaces;

import java.time.LocalTime;

public interface Schedule {
	String getRoom();
	LocalTime getFromTime();
	LocalTime getToTime();
	String getInstructor();
	String getCourse();
	String getDay();
}