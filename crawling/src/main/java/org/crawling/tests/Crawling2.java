package org.crawling.tests;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawling2 {

	static int cnt = 0;

	public static void main(String[] args) {
		int page = 0;

		ParseTests pt = new ParseTests();

//		String url = "https://imasoftwareengineer.tistory.com/54";
		String grammers = "java"; // 여기 넣어주면 됨
		String langs = "java"; // 여기도

		String[] grammer = {}; // 파싱 메소드에 보내줄 배열값.
		grammer = grammers.split(",");

		String grammerurl = grammers.replace(",", "+"); // url에 보내질 문자열
		String langurl = langs.replace(",", "+");

//		for(int i = 0; i < grammer.length; i++) {
//			System.out.println(grammer[i]);
//		}

//		String[] lang = {"java"};
		String lang = "java";

//		String str = "";

		String urlurl1 = "https://www.google.com/search?q=site%3Atistory.com+" + langurl + "+" + grammerurl + "&sourceid=chrome&ie=UTF-8&start=" + page;
		
		
//		String urlurl2 = "https://www.google.com/search?q=site:tistory.com+" + langurl + "+" + grammerurl
//				+ "&safe=off&sxsrf=ACYBGNSUclfnhOg25emoPrhASwyTNgC_KQ:1576483030977&ei=1jj3XbqoO7remAWmlLrQDA&start=10&sa=N&ved=2ahUKEwi667j42LnmAhU6L6YKHSaKDsoQ8tMDegQIDBAw&cshid=1576483058079134&biw=1536&bih=722&dpr=1.25";
//		String urlurl3 = "https://www.google.com/search?q=site:tistory.com+" + langurl + "+" + grammerurl
//				+ "&safe=off&sxsrf=ACYBGNS9w0wb9fnAjdibWdQ0gD9NVPRLZg:1576483853156&ei=DTz3XcSOCc6JmAW7v5KQBA&start=20&sa=N&ved=2ahUKEwiEyb6A3LnmAhXOBKYKHbufBEI4ChDy0wN6BAgMEDI&biw=1536&bih=722";
//		String url = "https://www.google.com/search?q=site%3Atistory.com+java+IntStream&oq=site%3Atistory.com+java+for&aqs=chrome..69i57j69i58.16639j0j7&sourceid=chrome&ie=UTF-8";
//		System.out.println("어떻게 나오게?    " + url);

		Document doc = null;
		Document doc2 = null;

		String str = "div.rc>div.r>a";

		do {

			try {
				doc = Jsoup.connect(urlurl1).get();

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
							for (Element al2 : el2.select("div div")) {
//									System.out.println(al2.text());
//									System.out.println(url2);
								pt.subDirList(al2.text(), grammer);
								System.out.println();
							}
						}
					} else {
						for (Element el2 : element2) { // 하위 뉴스 기사들을 for문 돌면서 출력
//								System.out.println(el2.text());
//								System.out.println(url2);
							pt.subDirList(el2.text(), grammer);
							System.out.println();
						}
					}
				}

			}
			page += 10;
			System.out.println(page);
		} while (cnt <= 10);
//		System.out.println("============================================================");
	}

}
