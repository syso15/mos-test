import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import static java.lang.System.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
//import org.jsoup.select.Element;
import org.jsoup.select.Elements;

public class ScrapeWebsite {

	/*
	 public void allLinksInUrl() throws IOException {

        Document doc = Jsoup.connect("https://www.wikipedia.org").get();

        Elements links = doc.select("a[href]");

        for (Element link : links) {

            System.out.println("\nlink : " + link.attr("href"));

            System.out.println("text : " + link.text());

        }

    }
    */
	
	public static void main(String args[]){
		print("running...");
		Document document;
		try {
			//Get Document object after parsing the html from given url.
			String url = "https://m.blog.naver.com/PostView.nhn?blogId=boooovely&logNo=221890546101&searchKeyword=%EB%A9%94%EC%9D%B4%ED%81%AC%EC%97%85";
	        document = Jsoup.connect(url).get();
	        System.out.println(document.body().children().text());
	        
	        List<String> visitedUrls = new ArrayList<>();
	        visitedUrls.add(url);
	        Queue<Elements> q = new LinkedList<>();
            Elements links = document.select("a[href]");
            q.add(links);
            while(!q.isEmpty())
            {
            	Elements curr_links = q.poll();
    	        for (Element link : curr_links) {
    	        	String next_url = link.absUrl("href");
    	        	if(visitedUrls.contains(next_url))
    	        	{
    	        		continue;
    	        	}
    	        	else
    	        	{
    	        		visitedUrls.add(next_url);
    	        		document = Jsoup.connect(next_url).get();
    	        		System.out.println(document.body().children().text());
    	        		Elements new_links = document.select("a[href]");
    	        		q.add(new_links);
    	        	}
    	        }
            }

			
		} catch (IOException e) {
			e.printStackTrace();
		}
    		print("done");

	}
	public static void print(String string) {
		System.out.println(string);
	}
}
