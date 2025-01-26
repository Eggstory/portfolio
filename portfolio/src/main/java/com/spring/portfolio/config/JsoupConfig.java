/*
package com.spring.portfolio.config;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsoupConfig {

    public static void main(String[] args) throws IOException {
        String weatherURL = "https://weather.naver.com/today";

//        try {
            // WeatherURL로부터 HTML 문서 가져오기
            Document doc = Jsoup.connect(weatherURL).get();

            // HTML 문서 출력
//            System.out.println(doc.toString());
//        } catch (IOException e) {
            // 예외 처리
//            System.err.println("오류 발생: " + e.getMessage());
//        }

        Elements elements = doc.select(".scroll_area .weather_table_wrap ._cnTime");
        String[] str = elements.text().split(" ");
        for(int i = 0; i < str.length; i++) {
            System.out.println(str[i]);
        }

    }

//    String url = "https://www.melon.com/chart/index.htm";
//    Document doc = Jsoup.connect(url).get();
//
//    Elements elem = doc.select("tbody tr.lst50, tbody tr.lst100");
//
//    List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
//    HashMap<String, Object> map = null;
//
//    for(Element chartList : elem) {
//            // 반복할 때마다 새로운 hashmap 생성
//            map = new HashMap<String, Object>();
//
//            // 순위 hashmap 에 저장
//            map.put("rank", chartList.select("span.rank").html());
//            // ------- 곡명, 아티스트, 앨범명도 같은 방식으로 추가 > 코드 생략 ----
//
//            // 앨범 이미지 src 속성 hashmap 에 저장
//            map.put("imgSrc", chartList.select("a.image_typeAll img").attr("src"));
//
//            // 이미지 a 태그 href 에서 숫자만 출력 (앨범 번호)
//            Elements albHref = chartList.select("a.image_typeAll");
//            String[] albNumber = albHref.attr("href").split("\\D+");
//            String albNo = "";
//            for (String no : albNumber) {
//                if (!no.isEmpty()) {
//                    albNo += no;
//                }
//            }
//            map.put("albNo", albNo);
//
//            // list 에 hashmap 추가
//            list.add(map);
//        }

}
*/