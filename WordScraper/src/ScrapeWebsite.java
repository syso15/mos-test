import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import static java.lang.System.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
//import org.jsoup.select.Element;
import org.jsoup.select.Elements;

public class ScrapeWebsite {
	public static void main(String args[]){
		print("running...");
		Document document;
		try {
			//Get Document object after parsing the html from given url.
	        document = Jsoup.connect("https://www.naver.com/").get();

	        //Get the actual text from the page, excluding the HTML
	        String text = document.body().text();
	        System.out.println(text);
			
	        //testing, remove later
			//Elements ps = doc.select("p");

			//String title = document.title(); //Get title
			//print("  Title: " + title); //Print title.

			//Elements price = document.select("list-card-heading:contains($)"); //Get price
			//Elements address = document.select("span[itemprop]:contains(DenverCO)"); //Get address
			
	        //output to csv eventually
		} catch (IOException e) {
			e.printStackTrace();
		}
    		print("done");

	}
	public static void print(String string) {
		System.out.println(string);
	}

}
