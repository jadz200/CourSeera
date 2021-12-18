package Implemented;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import Interfaces.HtmlToCsv;
/*  requires: The name of an already present .html file (as a String) that we can read from
              The name of the CSV file  (as a String) where we will output the .
    effects:  Converts The HTML code to a CSV format using regular expressions then writes in the CSV file*/
public class HtmlToCsv2 implements HtmlToCsv{
    @Override
    public void htmlToCsv(String htmlFile, String csvFile)  {
        StringBuilder contentBuilder = new StringBuilder();
        try{
            BufferedReader in = new BufferedReader(new FileReader(htmlFile));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {}
        
        String content = contentBuilder.toString();
        Pattern p = Pattern.compile("</TD><TD><BR> Instructional Method </TD>(.*)</TR>");
        Matcher m = p.matcher(content);
        
        String Courses="";

        while(m.find()) {
            Courses=m.group(1);
        }
        //Some Courses Title have commas we want to replace them with ":" so we don't have additional cells when using csv
        Courses=Courses.replaceAll(",", ":");
        //remove whitespaces for sake of readability
        Courses=Courses.replaceAll("\\s","");
        //remove <TD>
        Courses=Courses.replaceAll("<TD>","");
        //replace </TD> and </TR> for commas and new line so we can append correctly into the csv file
        Courses=Courses.replaceAll("</TD>",",");
        Courses=Courses.replaceAll("</TR>","\n");
        try {
            FileWriter fw = new FileWriter(csvFile);
            List<String> result = Arrays.asList(Courses.split(","));
            for ( String elements:result){
             fw.append(elements);
             fw.append(',')  ;
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
