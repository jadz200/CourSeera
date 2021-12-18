package Main;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Implemented.CsvToDb2;
import Implemented.Downloader2;
import Implemented.HtmlToCsv2;
import Implemented.Instructor2;
import Implemented.CourSeeraFactory2;
import Implemented.Room2;
import Interfaces.Downloader;
import Interfaces.CsvToDb;
import Interfaces.HtmlToCsv;
import Interfaces.Instructor;
import Interfaces.Room;
import Interfaces.Schedule;
import Interfaces.Course;
import Interfaces.CourSeeraFactory;
import Interfaces.CourSeera;




public class Main {
	public static void main(String[] args) {
		Downloader dd= new Downloader2();
		HtmlToCsv convert=new HtmlToCsv2();
		CsvToDb csvToDb=new CsvToDb2();
		ArrayList<Course> courses=new ArrayList<Course>();
		CourSeeraFactory fact=new CourSeeraFactory2();
		Scanner sc= new Scanner(System.in); 
		//Loop over The whole Alphabet.
		for(char index='A';index<='Z';index++){
			//Url , htmlFile and CsvFile change depending on the letter that is being worked on 
			String url="https://www-banner.aub.edu.lb/catalog/schd_"+index+".htm";
			String Htmlfile="html/Course-letter-"+index+".html";
			//dd.downloadHtmlToFile(url, Htmlfile);
			String Csvfile="csv/Course-letter-"+index+".csv";
			//convert.htmlToCsv(Htmlfile, Csvfile);
			csvToDb.csvToDb(courses, Csvfile);
		}
		CourSeera courseera=fact.createInstance(courses);
		printHelp();
		String input;
		do {
			input=sc.nextLine();
			Search(input,courseera);
		}while(!input.equals("-end"));
		System.out.println("See you next time!");
		sc.close();
	}
	
	public static void printHelp() {
		System.out.println("Welcome to CourSeera!");
		System.out.println("To search for the schedule of a specific room do: courseera -room-schedule \"BUILDING RoomNumber\"");
		System.out.println("To search for the schedule of a specific room on a specific date do: courseera -room-date \"BUILDING RoomNumber\" \"YYYY, MM, DD\"");
		System.out.println("To search for the schedule of a specific room on a specific day of the week do: courseera -room-dayOfWeek \"BUILDING RoomNumber\" \"Day\"");
		System.out.println("To search who was in this room before: courseera -room-whoWasThereLast \"BUILDING RoomNumber\"");
		System.out.println("To search who's currently in this room: courseera -room-whoIsThereNow \"BUILDING RoomNumber\"");
		System.out.println("To search for the professor schedule: courseera -prof-schedule \"FirstName LastName\"");
		System.out.println("To search where the professor is now: courseera -prof-whereIsProf \"FirstName LastName\"");
		System.out.println("To search where the professor will be: courseera -prof-whereWillProfBe \"FirstName LastName\"");
		System.out.println("To end the program: -end");
	}
	
	public static void printError() {
		System.out.println("Make sure you have written the command using the correct format, if you want to check them again do:-help");
	}
	
	public static void Search(String input,CourSeera courseera) {
		if(input.indexOf("courseera ")!=-1) {

			input=input.replace("courseera ","");
			
			if(input.indexOf("-room")!=-1) {
				
				input=input.replace("-room", "");
				
				if(input.indexOf("-schedule ")!=-1) {
					
					input=input.replace("-schedule ", "");
					
					Room room=makeRoom(input);
				
					printRoomSchedule(room,courseera);
					
				}else if(input.indexOf("-dayOfWeek ")!=-1){
					
					input=input.replace("-dayOfWeek ", "");

					Room room=makeRoom(input);

					String day="";
					
					Pattern p3=Pattern.compile("\"\\s\"[aA-zZ]+");
					Matcher m3=p3.matcher(input);
					
					if(m3.find()) {
						
						day=m3.group(0);
						day=day.substring(3).toUpperCase();
					}
					try {
						DayOfWeek dayofweek=DayOfWeek.valueOf(day);						
					}catch(IllegalArgumentException e) {
						System.out.println("Make sure you have written the day correctly");
						return;
					}
					DayOfWeek dayofweek=DayOfWeek.valueOf(day);
					printRoomSchedule(room,dayofweek,courseera);
					
				}else if(input.indexOf("-date ")!=-1){
					
					input=input.replace("-date ", "");
					Room room=makeRoom(input);

					String date_str="";
					
					Pattern p3=Pattern.compile("\\d{4}, \\d{2}, \\d{2}");
					Matcher m3=p3.matcher(input);
					
					if(m3.find()) {
						
						date_str=m3.group(0);
					}
					
					String[] arr=date_str.split(", ");
					if(arr.length!=3) {
						System.out.println("Make sure you have written the date format correctly");
						return;
					}
					int year=Integer.parseInt(arr[0]);
					int month=Integer.parseInt(arr[1]);
					int day=Integer.parseInt(arr[2]);
					
					try {
						LocalDate date=LocalDate.of(year,month,day);
					}catch(NumberFormatException e) {
						System.out.println("Make sure you have written the dates correctly");
						return;
					}
					
					LocalDate date=LocalDate.of(year,month,day);
					
					printRoomSchedule(room, date, courseera);
					
				}else if(input.indexOf("-whoWasThereLast ")!=-1){
					
					input=input.replace("-whoWasThereLast ", "");
					
					Room room=makeRoom(input);
					
					printWhoWasThereLast(room,courseera);
				
				}else if(input.indexOf("-whoIsThereNow ")!=-1){
					
					input=input.replace("-whoIsThereNow ", "");
					
					Room room=makeRoom(input);
					
					printWhoIsThereNow(room,courseera);
					
				}else {
					
					printError();

				}
				
			}else if(input.indexOf("-prof")!=-1) {
				
				input=input.replace("-prof", "");
				
				if(input.indexOf("-schedule ")!=-1) {
					
					input=input.replace("-schedule ", "");
					
					Instructor instructor=makeInstructor(input);
					
					printProfessorSchedule(instructor,courseera);
					
				}else if(input.indexOf("-whereIsProf ")!=-1) {
					
					input=input.replace("-whereIsProf ", "");
					
					Instructor instructor=makeInstructor(input);

					printWhereIsProf(instructor,courseera);

				}else if(input.indexOf("-whereWillProfBe ")!=-1) {
					
					input=input.replace("-whereWillProfBe ", "");
					
					Instructor instructor=makeInstructor(input);

					printWhereWillProfBe(instructor,courseera);

				}else {
					
					printError();
				}
				
			}else {
				
				printError();
				
			}
			
		}else if(input.indexOf("-end")!=-1) {
			
		}else if(input.indexOf("-help")!=-1){
			
			printHelp();
			
		}else {
			
			printError();
			
		}
	}
	public static Room makeRoom(String input) {
		
		Pattern p1=Pattern.compile("[aA-zZ]+");
		Matcher m1=p1.matcher(input);
		String Building="";
		
		if(m1.find()) {
			Building=m1.group(0).toUpperCase();
		}
		
		Pattern p2=Pattern.compile("\\d+");
		Matcher m2=p2.matcher(input);
		String room_nbr="";
		
		if(m2.find()) {
			room_nbr=m2.group(0).toUpperCase();
		}
		
		Room room=new Room2(Building,room_nbr);
		return room;
	}
	public static Instructor makeInstructor(String input) {
		Pattern p1=Pattern.compile("[aA-zZ]+");
		Matcher m1=p1.matcher(input);
		
		String firstName="";
		
		if(m1.find()) {
			firstName=m1.group(0).toLowerCase();
			firstName=firstName.substring(0,1).toUpperCase()+firstName.substring(1);
		}
		input=input.replace(firstName, "");
		input=input.replaceAll(" ", "");
		Pattern p2=Pattern.compile("(\\s?[aA-zZ]+)+");
		Matcher m2=p2.matcher(input);
		String lastName="";
		
		if(m2.find()) {
			lastName=m2.group(0);
		}
		
		Instructor instructor=new Instructor2(firstName,lastName);
		
		return instructor;		
	}
	public static void printRoomSchedule(Room room,CourSeera courseera) {
		List<Schedule> courses=courseera.roomSchedule(room);
		int counter=-1;
		if(courses.isEmpty()) {
			System.out.println("There is no courses in "+room.getBuilding()+" "+room.getRoomNumber()+"\nMake sure you have correctly typed the name and the number of the room");
			return;
		}
		System.out.println("Room schedule of: "+room.getBuilding()+" "+room.getRoomNumber());
		for(Schedule course:courses) {
			if(course.getDay().equals("MONDAY") && counter<0) {
				System.out.println("Monday");
				counter=0;
			}else if(course.getDay().equals("TUESDAY") && counter<1) {
				System.out.println("Tuesday");
				counter=1;
			}else if(course.getDay().equals("WEDNESDAY") && counter<2) {
				System.out.println("Wednesday");
				counter=2;
			}else if(course.getDay().equals("THURSDAY") && counter<3) {
				System.out.println("Thursday");
				counter=3;
			}else if(course.getDay().equals("FRIDAY") && counter<4) {
				System.out.println("Friday");
				counter=4;
			}else if(course.getDay().equals("SATURDAY") && counter<5) {
				System.out.println("Saturday");
				counter=5;
			}
			System.out.println(course.getFromTime()+"->"+course.getToTime()+": "+course.getCourse()+" "+course.getInstructor());
		}
	}
	
	public static void printRoomSchedule(Room room,DayOfWeek day,CourSeera courseera) {
		List<Schedule> courses=courseera.roomSchedule(room,day);
		if(courses.isEmpty()) {
			System.out.println("There is no courses in "+room.getBuilding()+" "+room.getRoomNumber()+"on "+day.toString()+"\nMake sure you have correctly typed the name and the number of the room and day");
			return;
		}
		System.out.println("Room schedule of "+room.getBuilding()+" "+room.getRoomNumber()+" on "+day.toString().substring(0,1)+day.toString().substring(1).toLowerCase());
		for(Schedule course:courses) {
			System.out.println(course.getFromTime()+"->"+course.getToTime()+": "+course.getCourse()+" "+course.getInstructor());
		}
	}
	
	public static void printRoomSchedule(Room room,LocalDate date,CourSeera courseera) {
		List<Schedule> courses=courseera.roomSchedule(room,date);
		if(courses.isEmpty()) {
			System.out.println("There is no courses in "+room.getBuilding()+" "+room.getRoomNumber()+" on "+date.toString());
			return;
		}
		System.out.println("Room schedule of "+room.getBuilding()+" "+room.getRoomNumber()+" on "+date.getMonth().toString().substring(0,1)+date.getMonth().toString().substring(1).toLowerCase()+" "+date.getDayOfMonth()+" "+date.getYear());
		for(Schedule course:courses) {
			System.out.println(course.getFromTime()+"->"+course.getToTime()+": "+course.getCourse()+" "+course.getInstructor());
		}
	}
	public static void printWhoWasThereLast(Room room,CourSeera courseera) {
		Schedule course=courseera.whoWasThereLast(room);
		if(course==null) {
			System.out.println("There was no one before in this room");
		}else {
			System.out.println(course.getDay()+" "+course.getFromTime()+"->"+course.getToTime()+": "+course.getInstructor()+" "+course.getCourse());
		}
	}
	public static void printWhoIsThereNow(Room room,CourSeera courseera) {
		Schedule course=courseera.whoIsThereNow(room);
		if(course==null) {
			System.out.println("There is no one in this room");
		}else {
			System.out.println(course.getDay()+" "+course.getFromTime()+"->"+course.getToTime()+": "+course.getInstructor()+" "+course.getCourse());
		}
	}
	public static void printProfessorSchedule(Instructor instructor,CourSeera courseera) {
		int counter=-1;
		List<Schedule> courses=courseera.profSchedule(instructor);
		if(courses.isEmpty()) {
			System.out.println("Professor "+instructor.getFirstName()+" "+instructor.getLastName()+" does not give courses in AUB"+"\nMake sure you have correctly typed the name of the professor");
			return;
		}
		System.out.println("Professor schedule of: "+instructor.getFirstName()+" "+instructor.getLastName());
		for(Schedule course:courses) {
			if(course.getDay().equals("MONDAY") && counter<0) {
				System.out.println("Monday");
				counter=0;
			}else if(course.getDay().equals("TUESDAY") && counter<1) {
				System.out.println("Tuesday");
				counter=1;
			}else if(course.getDay().equals("WEDNESDAY") && counter<2) {
				System.out.println("Wednesday");
				counter=2;
			}else if(course.getDay().equals("THURSDAY") && counter<3) {
				System.out.println("Thursday");
				counter=3;
			}else if(course.getDay().equals("FRIDAY") && counter<4) {
				System.out.println("Friday");
				counter=4;
			}else if(course.getDay().equals("SATURDAY") && counter<5) {
				System.out.println("Saturday");
				counter=5;
			}
			System.out.println(course.getFromTime()+"->"+course.getToTime()+": "+course.getCourse()+" "+course.getInstructor()+" "+course.getRoom());
		}
	}
	public static void printWhereIsProf(Instructor instructor,CourSeera courseera) {
		Schedule course=courseera.whereIsProf(instructor);
		if(course==null) {
			System.out.println("Professor is not in a class now \nMake sure you have correctly typed the name of the professor");
		}else {
			System.out.println("The professor is at "+course.getRoom()+" giving "+course.getCourse());
		}
	}
	public static void printWhereWillProfBe(Instructor instructor,CourSeera courseera) {
		List<Schedule> courses=courseera.whereWillProfBe(instructor);
		if(courses.isEmpty()) {
			System.out.println("Professor "+instructor.getFirstName()+" "+instructor.getLastName()+"does not give courses in AUB"+"\nMake sure you have correctly typed the name of the professor");
		}
		for(Schedule course:courses) {
			System.out.println(course.getDay()+" "+course.getFromTime()+"->"+course.getToTime()+": "+course.getInstructor()+" "+course.getCourse()+" "+course.getRoom());
		}
	}

}