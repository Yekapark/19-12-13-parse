package org.crawling.tests;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawling2 {

	public static void main(String[] args) {
//		String url = "https://imasoftwareengineer.tistory.com/54";
		String lang = "java";
		String grammer = "IntStream";
		String url = "https://www.google.com/search?q=site%3Atistory.com+" + lang + "+" + grammer + "&oq=site%3Atistory.com+java+for&aqs=chrome..69i57j69i58.16639j0j7&sourceid=chrome&ie=UTF-8";
		
		Document doc = null;
		Document doc2 = null;
		
		String str = "div.rc>div.r>a";
		try {
			doc = Jsoup.connect(url).get();

		} catch (IOException e) {
			e.printStackTrace();
		}

			Elements element = doc.select(str);
				for (Element el : element) { // 하위 뉴스 기사들을 for문 돌면서 출력
//					System.out.println(el.attr("abs:href"));
					String url2 = el.attr("abs:href");
					
					String[] str2 = { "pre", ".colorscripter-code td:nth-child(2) div:nth-child(1)" };
					try {
						doc2 = Jsoup.connect(url2).get();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					for (int i = 0; i < str2.length; i++) {
						Elements element2 = doc2.select(str2[i]);
						if (!str2[i].equals("pre")) {
							for (Element el2 : element2) { // 하위 뉴스 기사들을 for문 돌면서 출력
								System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");
								for (Element al2 : el2.select("div div")) {
									System.out.println(al2.text());
								}
							}
						} else {
							for (Element el2 : element2) { // 하위 뉴스 기사들을 for문 돌면서 출력
								System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
								System.out.println(el2.text());
							}
						}
					}
					
			}
		System.out.println("============================================================");
	}

}
