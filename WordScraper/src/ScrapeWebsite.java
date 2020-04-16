import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
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
	
	public static void main(String args[]) throws IOException{
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
        //Arraylist to hold visited pages
        List<String> visitedUrls = new ArrayList<>();
        //queue to hold links from a page (iterative traversal)
        Queue<Elements> q = new LinkedList<>();
        
			try {
			
			String url = "https://news.chosun.com/site/data/html_dir/2020/04/13/2020041300890.html";
			document = Jsoup.connect(url).userAgent("Mozilla").ignoreContentType(true).followRedirects(true).ignoreHttpErrors(true).get();
			visitedUrls.add(url.toLowerCase());
			Elements content = document.getElementsByClass("news_body");
	        for (int i=0; i < content.size(); i++) {
	        	writer.println(content.get(i).text());
	        }
	        Elements links = document.select("a[href]");
	        q.add(links);
			} catch (MalformedURLException e) {
			e.printStackTrace(); 	
			}	
         
            
            while(!q.isEmpty())
            {
            	//get current set of links from queue
            	Elements curr_links = q.poll();
    	        for (Element link : curr_links) {
    	        	//for each link, get url
    	        	String next_url;
    	        	try {
    	            next_url = link.absUrl("href");
 		          
    	        	if(!next_url.contains("chosun") || !next_url.contains("http") || !next_url.contains("https")|| visitedUrls.contains(next_url.toLowerCase()))
    	        	{
    	        		continue;
    	        	}
    	        	else
    	        	{
    	        		visitedUrls.add(next_url.toLowerCase());
    	        		document = Jsoup.connect(next_url).userAgent("Mozilla").followRedirects(true).ignoreContentType(true).ignoreHttpErrors(true).get();
    	        		Elements info = document.getElementsByClass("news_body");
    	        		
    	        		for (int i=0; i < info.size(); i++) {
    	        			writer.println(info.get(i).text());
   
    	    	        }
    	        		Elements new_links = document.select("a[href]");
    	        		q.add(new_links);
    	        	}
        	        } catch (MalformedURLException e) {
        	            throw new RuntimeException(e);
        	        }
    	        }
            }
            print("done");

	}
    		

	
	public static void print(String string) {
		System.out.println(string);
	}
}
