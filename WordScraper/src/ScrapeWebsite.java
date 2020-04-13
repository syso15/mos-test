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
	
	public static void main(String args[]){
		print("running...");
		Document document;
		try {
			String url = "https://news.chosun.com/site/data/html_dir/2020/04/13/2020041300890.html";
					//"https://m.blog.naver.com/jiro6134/221896897879";
			document = Jsoup.connect(url).get();
	        Elements content = document.getElementsByClass("news_body");
	        for (int i=0; i < content.size(); i++) {
	        	System.out.println(content.get(i).text());
	        }
	        
	        //Arraylist to hold visited pages
	        List<String> visitedUrls = new ArrayList<>();
	        visitedUrls.add(url);
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
 		          
    	        	if(!next_url.contains("chosun") || visitedUrls.contains(next_url))
    	        	{
    	        		continue;
    	        	}
    	        	else
    	        	{
    	        		visitedUrls.add(next_url);
    	        		document = Jsoup.connect(next_url).get();
    	        		Elements info = document.getElementsByClass("news_body");
    	        		
    	        		for (int i=0; i < info.size(); i++) {
    	    	        	System.out.println(info.get(i).text());
   
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
