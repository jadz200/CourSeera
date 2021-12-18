package Interfaces;

public interface Course {
	String getCrn();
	String getSubject();
	String getCourse_num();
	String getSection();
	String getTitle();
	float getCredithrs();
	String getCollege();
	int getActual_enrol();
	int getSeats_available();
	java.time.LocalTime getBegin_time();
	java.time.LocalTime getEnd_time();
	String getBldg();
	String getRoom();
	boolean getMonday();
	boolean getTuesday();
	boolean getWednesday();
	boolean getThursday();
	boolean getFriday();
	boolean getSaturday();
	String getInstructor_first();
	String getInstructor_last();
}
