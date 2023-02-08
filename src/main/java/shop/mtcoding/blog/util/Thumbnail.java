package shop.mtcoding.blog.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Thumbnail {
    public static String 썸네일추출(String object){
        String img = "";
        Document doc = Jsoup.parse(object); 
        Elements els = doc.select("img"); 
        if (els.size() == 0){ 
            return "/images/dora1.png";
        }else{ 
            Element el = els.get(0); 
            img += el.attr("src"); 
            return img;
        }   
    }
}
