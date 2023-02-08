package shop.mtcoding.blog.util;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import shop.mtcoding.blog.dto.board.BoardReq.BoardSaveReqDto;

public class HtmlParseTest {
    
    @Test
    public void jsoup_test1() throws Exception{
        //given
        // String html = "<p>123123<img src=\"data:image/jpeg;base64,/9j/4AAQSkZJRgAB\"></p>";

        Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
        // System.out.println((doc.title()));

        System.out.println("************************************");
        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines) {
          System.out.println(headline.attr("title"));
          System.out.println("=======================================");
          System.out.println(headline.absUrl("href"));
        }
    }

    @Test
    public void jsoup_test2() { 
        String html = "<p>123123<img src=\"data:image/jpeg;base64,/9j/4AAQSkZJRgAB\"><img src=\"data:image/jpeg;base64,/9j/34fghZJRgAB\"><img src=\"data:image/jpeg;base64,/9j/4shxcvhJRgAB\"></p>";        
        String img = "";
        Document doc = Jsoup.parse(html); // html 에서 도큐먼트 분리
        Elements els = doc.select("img"); // 태그 선택 - 지정한 이름의 태그들만 분리
        if (els.size() == 0){  // 지정한 태그가 나오지 않았을 때
            // 임시 사진 제공
        }else{ 
            Element el = els.get(0); // 첫번째 태그에서 사진을 추출할거야
            img += el.attr("src"); // 하나의 태그에서 속성값을 꺼낼거야 .. 보통 태그 하나에 이미지 하나니까 여기서 끝
            System.out.println(img);
            // System.out.println(img); 
        }
    }
}
