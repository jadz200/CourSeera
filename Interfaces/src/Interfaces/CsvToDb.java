package Interfaces;

import java.util.List;

public interface CsvToDb {
	void csvToDb(List<Course> courses, String csvFile);
}
