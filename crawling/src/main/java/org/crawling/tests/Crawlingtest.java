package org.crawling.tests;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawlingtest {

	public static void main(String[] args) {
//		String url = "https://imasoftwareengineer.tistory.com/54";
		String url = "https://dev-bear.tistory.com/entry/JAVA-%EB%91%90%EA%B0%80%EC%A7%80-for-%EB%B0%98%EB%B3%B5%EB%AC%B8";
		Document doc = null;
//        String[] str = {"pre", ".colorscripter-code div"};
//        String[] str = {"pre", ".colorscripter-code td:nth-child(2)"};
		String[] str = { "pre", ".colorscripter-code td:nth-child(2) div:nth-child(1)" };
		// 멈춰!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < str.length; i++) {
			Elements element = doc.select(str[i]);
			if (!str[i].equals("pre")) {
				for (Element el : element) { // 하위 뉴스 기사들을 for문 돌면서 출력
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");
					for (Element al : el.select("div div")) {
						System.out.println(al.text());
					}
				}
			} else {
				for (Element el : element) { // 하위 뉴스 기사들을 for문 돌면서 출력
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					System.out.println(el.text());
				}
			}
		}
		System.out.println("============================================================");
	}

//        // 주요 뉴스로 나오는 태그를 찾아서 가져오도록 한다.
//        Elements element = doc.select("pre");
//         
//        // 1. 헤더 부분의 제목을 가져온다.
////        String title = element.select("h2").text().substring(0, 4);
//// 
////        System.out.println("============================================================");
////        System.out.println(title);
////        System.out.println("============================================================");
////         
//        for(Element el : element) {    // 하위 뉴스 기사들을 for문 돌면서 출력
//            System.out.println(el.text());
//            System.out.println("=====================================");
//        }
//         
//        System.out.println("============================================================");
}
