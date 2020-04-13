import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import static java.lang.System.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScrapeWebsite {
	
	public static void main(String args[]){
		print("running...");
		//create print writer to write to file
		
	    FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("ScrapedContent");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PrintWriter writer = null;

		    writer = new PrintWriter(fileWriter);		    

		//begin scraping for text content
		Document document;
		try {
			String url = "https://news.chosun.com/site/data/html_dir/2020/04/13/2020041300890.html";
			document = Jsoup.connect(url).get();
	        Elements content = document.getElementsByClass("news_body");
	        for (int i=0; i < content.size(); i++) {
	        	writer.println(content.get(i).text());
	        }
	        
	        //Arraylist to hold visited pages
	        List<String> visitedUrls = new ArrayList<>();
	        visitedUrls.add(url.toLowerCase());
	        //queue to hold links from a page (iterative traversal)
	        Queue<Elements> q = new LinkedList<>();
            Elements links = document.select("a[href]");

            q.add(links);
            while(!q.isEmpty())
            {
            	//get current set of links from queue
            	Elements curr_links = q.poll();
    	        for (Element link : curr_links) {
    	        	//for each link, get url
    	        	String next_url = link.absUrl("href");
 		          
    	        	if(!next_url.contains("chosun") || visitedUrls.contains(next_url.toLowerCase()))
    	        	{
    	        		continue;
    	        	}
    	        	else
    	        	{
    	        		visitedUrls.add(next_url.toLowerCase());
    	        		document = Jsoup.connect(next_url).get();
    	        		Elements info = document.getElementsByClass("news_body");
    	        		
    	        		for (int i=0; i < info.size(); i++) {
    	        			writer.println(info.get(i).text());
   
    	    	        }
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
