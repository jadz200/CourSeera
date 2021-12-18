package Implemented;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import Interfaces.Downloader;
/*  requires: A valid url that we can download and a .html file.
    effects:  Will download the HTML code of the URL into the HTML file*/
public class Downloader2 implements Downloader{
    @Override
    public  void downloadHtmlToFile(String url, String file){
		try {
            // Create URL object
            URL link = new URL(url);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(link.openStream()));
  
            // Enter filename in which you want to download the html
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
              
            // read each line from stream till the end
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
            }
            reader.close();
            writer.close();
        }
        //catches if the URL is Malformed
        catch (MalformedURLException mue) {
            System.out.println("Malformed URL Exception raised");
        }
        //catches I/O errors
        catch (IOException ie) {
            System.out.println("IOException raised");
        }
	}
}
