package telegrambot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Implemented.CsvToDb2;
import Implemented.Downloader2;
import Implemented.HtmlToCsv2;

import Implemented.CourSeeraFactory2;
import Interfaces.Downloader;
import Interfaces.CsvToDb;
import Interfaces.HtmlToCsv;
import Interfaces.Instructor;
import Interfaces.Room;
import Interfaces.Schedule;
import Interfaces.Course;
import Interfaces.CourSeeraFactory;
import Interfaces.CourSeera;
import Main.Main;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TBot extends TelegramLongPollingBot {
	public void onUpdateReceived(Update update) {
		
		CourSeeraFactory fact = new CourSeeraFactory2();
		CourSeera        core = fact.createInstance(downloader());
		
		int year=0;
		int month=0;
		int days=0;
		
		// if there is a message that has text
		if (update.hasMessage() && update.getMessage().hasText()) {
			// get the text of the message
			String receivedText = update.getMessage().getText();
			String text=receivedText;
			String command      = text.substring(0,text.indexOf(" ")+1);
			List<Schedule> lst                = new ArrayList<Schedule>();
			
			if (command.equals("/whoisthere ")) {
	
				text=text.replace("/whoisthere ", "");
				
				Room room = Main.makeRoom(text);
				
				lst.add(core.whoIsThereNow(room));
				
			} else if (command.equals("/whereisprof ")) {
				
				text=text.replace("/whereisprof ", "");

				Instructor inst = Main.makeInstructor(text);
				
				receivedText = core.whereIsProf(inst).getRoom();
				
			} else if (command.equals("/whowastherelast ")) {
				
				text=text.replace("/whowastherelast ", "");

				Room room = Main.makeRoom(text);
				
				lst.add(core.whoWasThereLast(room));
				
			} else if (command.equals("/wherewillprofbe ")) {
				
				text=text.replace("/wherewillprofbe ", "");

				Instructor inst = Main.makeInstructor(text);
				
				lst.addAll(core.whereWillProfBe(inst));
				
			} else if (command.equals("/profschedule ")) {
				
				text=text.replace("/profschedule ", "");

				Instructor inst = Main.makeInstructor(text);
				
				lst.addAll(core.profSchedule(inst));
				
				
			} else if (command.equals("/roomschedule ")) {
				
				text=text.replace("/roomschedule ", "");

				Room room = Main.makeRoom(text);
				
				lst.addAll(core.roomSchedule(room));
				
			} else if (command.equals("/roomscheduledate ")) {
				
				text=text.replace("/roomscheduledate ", "");

				Room room = Main.makeRoom(text);

				String date_str="";
				
				Pattern p3=Pattern.compile("\\d{4}, \\d{2}, \\d{2}");
				Matcher m3=p3.matcher(text);
				
				if(m3.find()) {
					
					date_str=m3.group(0);
				}
				
				String[] arr=date_str.split(", ");
				if(arr.length!=3) {
					System.out.println("Make sure you have written the date format correctly");
					return;
				}
				 year=Integer.parseInt(arr[0]);
				 month=Integer.parseInt(arr[1]);
				 days=Integer.parseInt(arr[2]);
				
				try {
					LocalDate date=LocalDate.of(year,month,days);
				}catch(NumberFormatException e) {
					System.out.println("Make sure you have written the dates correctly");
					return;
				}
				
				LocalDate date=LocalDate.of(year,month,days);
				lst.addAll(core.roomSchedule(room, date));
				
			} else if (command.equals("/roomscheduleday ")) {
				
				text=text.replace("/roomscheduleday ", "");

				Room room = Main.makeRoom(text);
				
				String day="";
				
				Pattern p3=Pattern.compile("\"\\s\"[aA-zZ]+");
				Matcher m3=p3.matcher(text);
				
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
				
				lst.addAll(core.roomSchedule(room, dayofweek));
				
			}else if (receivedText.equals("/start")) {
				receivedText = "I started";
			}else if(receivedText.equals("/help")) {
				receivedText="Welcome to CourSeera!\r\n"
						+ "To search for the schedule of a specific room do: /roomschedule Building RoomNumber \r\n"
						+ "To search for the schedule of a specific room on a specific date do: /roomscheduledate BUILDING RoomNumber YYYY, MM, DD\r\n"
						+ "To search for the schedule of a specific room on a specific day of the week do: /roomscheduleday BUILDING RoomNumber Day\r\n"
						+ "To search who was in this room before: /whowastherelast BUILDING RoomNumber\r\n"
						+ "To search who's currently in this room: /whoistherenow BUILDING RoomNumber\r\n"
						+ "To search for the professor schedule: /profschedule FirstName LastName\r\n"
						+ "To search where the professor is now: /whereisprof FirstName LastName\r\n"
						+ "To search where the professor will be: /wherewillprofbe FirstName LastName";
			}else {
				receivedText = "Please check your spelling.";
			}
			// send a reply
			SendMessage message = new SendMessage();
			message.setChatId(update.getMessage().getChatId().toString());
			try {
				if(command.equals("/roomscheduledate ")) {
					
					if(lst.isEmpty()) {
						message.setText("There is no courses in this room on that day");
						execute(message);
						return;
					}
					
					message.setText("Room schedule of "+lst.get(0).getRoom()+" on "+days+" "+month+" "+year);
					execute(message);
					for(Schedule course:lst) {
						message.setText(course.getFromTime()+"->"+course.getToTime()+": "+course.getCourse()+" "+course.getInstructor());
						execute(message);
					}
					lst.clear();
					receivedText = "";
				}else if(command.equals("/roomscheduleday ")) {
					
					if(lst.isEmpty()) {
						message.setText("There is no courses in this room on that day");
						execute(message);
						return;
					}

					message.setText("Room schedule of "+lst.get(0).getRoom()+" on "+lst.get(0).getDay().substring(0,1)+lst.get(0).getDay().substring(1).toLowerCase());
					execute(message);
					for(Schedule course:lst) {
						message.setText(course.getFromTime()+"->"+course.getToTime()+": "+course.getCourse()+" "+course.getInstructor());
						execute(message);
					}
					lst.clear();
					receivedText = "";
					
				}else if (command.equals("/roomschedule ")) {
					
					if(lst.isEmpty()) {
						message.setText("There is no courses in this room");
						execute(message);
						return;
					}
					
					int counter=-1;
					message.setText("Room schedule of: "+lst.get(0).getRoom());
					execute(message);
					for(Schedule course:lst) {
						if(course.getDay().equals("MONDAY") && counter<0) {
							counter=0;
							message.setText("Monday");
							execute(message);
						}else if(course.getDay().equals("TUESDAY") && counter<1) {
							counter=1;
							message.setText("Tuesday");
							execute(message);
						}else if(course.getDay().equals("WEDNESDAY") && counter<2) {
							counter=2;
							message.setText("Wednesday");
							execute(message);
						}else if(course.getDay().equals("THURSDAY") && counter<3) {
							counter=3;
							message.setText("Thursday");
							execute(message);
						}else if(course.getDay().equals("FRIDAY") && counter<4) {
							counter=4;
							message.setText("Friday");
							execute(message);
						}else if(course.getDay().equals("SATURDAY") && counter<5) {
							counter=5;
							message.setText("Saturday");
							execute(message);
						}
						message.setText(course.getFromTime()+"->"+course.getToTime()+": "+course.getCourse()+" "+course.getInstructor());
						execute(message);
					}
					lst.clear();
					receivedText = "";
					
				}else if( command.equals("/whoisthere ")) {
					if(lst.isEmpty()) {
						message.setText("No one is there");
						execute(message);
						return;
					}
					message.setText(lst.get(0).getFromTime()+"->"+lst.get(0).getToTime()+": "+lst.get(0).getCourse()+" "+lst.get(0).getInstructor());
					execute(message);
				
				}else if(command.equals("/whowastherelast ")) {
				
					if(lst.isEmpty()) {
						message.setText("There was no courses in this room");
						execute(message);
						return;
					}
					
					message.setText(lst.get(0).getFromTime()+"->"+lst.get(0).getToTime()+": "+lst.get(0).getCourse()+" "+lst.get(0).getInstructor());
					execute(message);
				
				}else if(command.equals("/profschedule ")) {
					
					if(lst.isEmpty()) {
						message.setText("This professor does not give courses in AUB");
						execute(message);
						return;
					}
					
					int counter=-1;

					message.setText("Schedule of professor "+lst.get(0).getInstructor());
					execute(message);

					for(Schedule course:lst) {
						if(course.getDay().equals("MONDAY") && counter<0) {
							counter=0;
							message.setText("Monday");
							execute(message);
						}else if(course.getDay().equals("TUESDAY") && counter<1) {
							counter=1;
							message.setText("Tuesday");
							execute(message);
						}else if(course.getDay().equals("WEDNESDAY") && counter<2) {
							counter=2;
							message.setText("Wednesday");
							execute(message);
						}else if(course.getDay().equals("THURSDAY") && counter<3) {
							counter=3;
							message.setText("Thursday");
							execute(message);
						}else if(course.getDay().equals("FRIDAY") && counter<4) {
							counter=4;
							message.setText("Friday");
							execute(message);
						}else if(course.getDay().equals("SATURDAY") && counter<5) {
							counter=5;
							message.setText("Saturday");
							execute(message);
						}

						message.setText(course.getFromTime()+"->"+course.getToTime()+": "+course.getCourse()+" "+course.getInstructor()+" "+course.getRoom());
						execute(message);

					}
				}else if(command.equals("/whereisprof ")) {
				
					if(lst.isEmpty()) {
						message.setText("This professor is currently not  in AUB");
						execute(message);
						return;
					}
				
					message.setText(lst.get(0).getFromTime()+"->"+lst.get(0).getToTime()+": "+lst.get(0).getCourse()+" "+lst.get(0).getInstructor());
					execute(message);
				
				}else if(command.equals("/wherewillprofbe ")) {
					
					if(lst.isEmpty()) {
						message.setText("This professor will not be in AUB today");
						execute(message);
						return;
					}
					
					for(Schedule course:lst) {
						message.setText(course.getDay()+" "+course.getFromTime()+"->"+course.getToTime()+": "+course.getInstructor()+" "+course.getCourse()+" "+course.getRoom());
					}
					
				}else {
					message.setText(receivedText);
					execute(message);
					receivedText = "";
				}
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}

	public String getBotUsername() {
		return "AUBCourseSearcherbot";
	}

	@Override
	public String getBotToken() {
		return null
	}

	public ArrayList<Course> downloader() {
		Downloader dd= new Downloader2();
		HtmlToCsv convert=new HtmlToCsv2();
		CsvToDb csvToDb=new CsvToDb2();
		ArrayList<Course> courses=new ArrayList<Course>();
		//Loop over The whole Alphabet.
		for(char index='A';index<='Z';index++){
			//Url , htmlFile and CsvFile change depending on the letter that is being worked on 
			String url="https://www-banner.aub.edu.lb/catalog/schd_"+index+".htm";
			String Htmlfile="../Main/html/Course-letter-"+index+".html";
			//dd.downloadHtmlToFile(url, Htmlfile);
			String Csvfile="../Main/csv/Course-letter-"+index+".csv";
			//convert.htmlToCsv(Htmlfile, Csvfile);
			csvToDb.csvToDb(courses, Csvfile);
		}
		return courses;
	}
}