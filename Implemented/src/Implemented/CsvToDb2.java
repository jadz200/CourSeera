package Implemented;

import java.io.*;
import java.util.*;

import Interfaces.Course;
import Interfaces.CsvToDb;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
/*  requires: The name of an already present .csv file (as a String) that we can read from.
              A valid List<Object>
    effects:  returns a List of object Courses that are present in the .csv file*/
public class CsvToDb2 implements CsvToDb{
    @Override
    public void csvToDb(List<Course> courses, String csvFile) {
        try{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmm");
            Scanner inputStream = new Scanner(new File(csvFile));
            while(inputStream.hasNext()){
                //read single line, put in string
                String data = inputStream.next();
                if(data.equals(",")){
                    return;
                }
                //Put each row of the CSV into an array and each cell will represent one index
                String[] list=data.split(",");
                if(!list[0].equals("Fall2021-2022(202210)")){
                    return;
                }
                //initialize and declare variable so we can create the Object Course
                String    crn=list[1];
                String    subject=list[2];
                String    course_num=list[3];
                String    section=list[4];
                String    title=list[5];
                float     credithrs=Float.parseFloat(list[6]);
                String    college=list[8];
                int       actual_enrol=Integer.parseInt(list[9]);
                int       seats_available=Integer.parseInt(list[10]);
                LocalTime begin_time;
                LocalTime end_time;
                //Some Courses don't have a begin and end Time so we want to not 
                if(!list[11].equals(".")){
                    begin_time=LocalTime.parse(list[11], dtf);     
                }else{
                    begin_time=LocalTime.parse("00:01");
                }
                if(!list[12].equals(".")){
                    end_time=LocalTime.parse(list[12], dtf);    
                }else{
                    end_time=LocalTime.parse("00:01");
                }
                String    bldg=list[13];;
                String    room=list[14];;
                boolean   monday=(list[15].equals("M")?true:false);
                boolean   tuesday=(list[16].equals("T")?true:false);
                boolean   wednesday=(list[17].equals("W")?true:false);
                boolean   thursday=(list[18].equals("R")?true:false);
                boolean   friday=(list[19].equals("F")?true:false);
                boolean   saturday=(list[20].equals("S")?true:false);
                String    instructor_first=list[33];;
                String    instructor_last=list[34];;
                Course  course= new Course2(crn,subject,course_num,section,title,credithrs,college,actual_enrol,seats_available,begin_time,end_time,bldg,room,monday,tuesday,wednesday,thursday,friday,saturday,instructor_first,instructor_last);
                //Add each course to the big List courses
                courses.add(course);
            }
            inputStream.close();            
        }catch(FileNotFoundException e){
        }
    }
    
}
