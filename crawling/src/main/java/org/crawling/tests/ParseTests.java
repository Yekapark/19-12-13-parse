package org.crawling.tests;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Stack;

public class ParseTests {

//	public void runtime(String d) {
//
//		// 실행하고 있는 현재 위치
//		subDirList(System.getProperty("user.dir"));
//
//	}

	// 검색 메소드
	Crawling2 c2 = new Crawling2();
	public void subDirList(String all, String[] grammer) {
		

		
		
		boolean allKeyword = true; // 모든 키워드 찾는 변수
		boolean keywordOn = false;

		String[] keywords = grammer;

		InputStream is = new ByteArrayInputStream(all.getBytes());

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		try {

			String line = "";

//			System.out.println("------------------------------------------------------");

			// 파일이름 출력

			boolean isComment = false; // 여러줄 주석
			boolean isCode = false; // 검색에 해당되는 코드
			boolean isBrace = false; // 브레이스

			boolean tenline = false; // 열줄만 출력하는 변수
			boolean flag = false; // 한줄에 브레이스 짝이 맞을 때.
			int count = 0;

			Stack<String> checkBrace = new Stack<String>(); // 브레이스 개수 체크

			String code = "";

			int tbrace = 0;
			int k = 0;

			// 한줄씩 읽어온다
			for (int i = 1; (line = br.readLine()) != null; i++) {

//				System.out.println("라인    " + line);
				
				if (!line.startsWith("import ") && !line.startsWith("public")) {

					count++; // 세줄 이내로 브레이스가 없다면 10줄만 뿌려주는 변수.

					int open = 0; // 한 줄에 { 갯수
					int close = 0; // 한 줄에 } 갯수

					if (line.contains("{")) {
						open++;
					}
					if (line.contains("}")) {
						close++;
					}

					if (open > 0 && open == close) { // 한줄에 브레이스 열고닫는게 둘다 있다?
						flag = true; // 플래그 on
						tbrace++; // 티 브레이스 +1
					} else {
						flag = false; // 누적되지 않는다면 플래그와 tbrace 원래대로.
						tbrace = 0;
					}

					if (allKeyword) { // 키워드 전체검색
						for (int n = 0; n < keywords.length; n++) {
							if (line.indexOf(keywords[n]) != -1 && keywordOn == false) {

								keywordOn = true;
//								System.out.println("====================================================");
//								System.out.println("검색 키워드 <" + keywords[n] + ">");
//								System.out.println("해당 주석: " + line);
//								System.out.println("----------------------------------------------------");

								code = "";

								isCode = true; // 코드 시작을 알려줌

								k = i + 11; // 혹시나 열줄만 출력할거면 아래 10줄만 출력하기 위한 변수
							}
						}
					}

					if (!allKeyword) {

						// 1. 여러줄 주석
						if (line.trim().startsWith("/*")) { // /*로 시작하는 줄이다?
							isComment = true; // isComment 열어준다.
						}

						// 주석일때
						if (isComment) { // 여러 줄 주석일 때 시작.
							for (int n = 0; n < keywords.length; n++) {
								if (line.indexOf(keywords[n]) != -1) {

//									System.out.println("====================================================");
//									System.out.println("검색 키워드 <" + keywords[n] + ">");
//									System.out.println("해당 주석: " + line);
//									System.out.println("----------------------------------------------------");

									code = "";

									isCode = true; // 코드입니다.

								}
							}

							// 주석 끝 (여러줄 주석)
							if (line.trim().endsWith("*/")) { // */이 나오면
								isComment = false; // isComment 닫아준다.
							}

						} // if(iscomment)

					}
					// -----------------------------------------------------------------------------------------------------------

					// 2. 한줄 주석
					if (line.contains("//") && !allKeyword) { // 라인에 //가 포함되어있다.
						// 주석인데 해당 키워드 존재할때
						if (line.trim().startsWith("//")) {

							for (int n = 0; n < keywords.length; n++) {
								if (line.contains(keywords[n])) {
//									System.out.println("====================================================");
//									System.out.println("검색 키워드 <" + keywords[n] + ">");
//									System.out.println("해당 주석: " + line);
//									System.out.println("----------------------------------------------------");

									code = "";

									isCode = true; // 코드 시작을 알려줌

									k = i + 11; // 혹시나 열줄만 출력할거면 아래 10줄만 출력하기 위한 변수
								}
							} // end of for
						}

					} // 한줄 주석 끝
//								System.out.println("케이   " + k + "  텐 라인   " + tenline);
//									-----------------------------------------------------------------------------------------------------------
//									-----------------------------------------------------------------------------------------------------------
//									-----------------------------------------------------------------------------------------------------------
//									-----------------------------------------------------------------------------------------------------------
//									-----------------------------------------------------------------------------------------------------------
//								System.out.println("========================================");
//								System.out.println(code);

					// 브레이스 유무
					if (isCode) {
						isBrace = ((line.indexOf("{")) != -1 || (line.contains("}"))) ? true : isBrace;
					}

//								System.out.println("케이   " + k + "  텐 라인   " + tenline  + "이스코드 이스브레이스" + isCode +"" + isBrace);
//								System.out.println("플래그    " + flag + "tbrace    " + tbrace);

					if (isCode && tenline) { // 열 줄만 나오게 하는 코드이다.
						if (i <= k) {
							code += i + ":" + line + "\n";
							isBrace = false;
						}
						if (i == k + 1) {
							System.out.println(code);
							System.out.println(c2.cnt++);
							code = "";
							flag = false;
							isBrace = false;
							tenline = false;
							count = 0;
							isCode = false;
							keywordOn = false;

						}
					}

					else if (isCode && isBrace) {

						code += i + ":" + line + "\n";
//									System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//									System.out.println(code);

						// 코드는 시작했다.
						// 브레이스가 열려있다.
						// flag = 한줄에 { } 둘 다 있을때 true.
						if (flag && tbrace >= 2) { // 두 줄 연속으로 {} 있으면?
							tenline = true; // 열줄만 출력하게 tenline이 트루가 됩니다.
							tbrace = 0; // 어차피 열 줄만 출력하도록 마음먹었으니 tbrace는 0으로.
						}

						else if (isCode && !flag) { // 그 라인에 { } 둘다 있지 않으면.

							if (line.indexOf("{") != -1) {
								checkBrace.push("{"); // 라인에 {가 있으면 스택에 하나 추가
							}
							if (line.indexOf("}") != -1) {
								checkBrace.pop(); // 라인에 }가 있으면 스택 하나 팝팝
							}

							if (checkBrace.empty()) {
								System.out.println(code);
								System.out.println(c2.cnt++);
								code = "";
								isBrace = false;
								isCode = false;
								keywordOn = false;
								continue;
							}
						} // end if(!flag)

//									isBrace = false;

					} // end if(isCode && isBrace)

					else if (isCode && !isBrace) {
						code += i + ":" + line + "\n";
						if (i <= k) {
//										System.out.println(code);

						}
						if (i == k + 1) {
							System.out.println(code);
							System.out.println(c2.cnt++);
							code = "";
							tenline = false;
							count = 0;
							isCode = false;
							keywordOn = false;
						}
					}

//		-----------------------------------------------------------------------------------------------------------
					continue;
				} // 임포트 방지
			} // line출력 for문 끝
			isComment = false; // 여러줄 주석
			isCode = false; // 검색에 해당되는 코드
			isBrace = false; // 브레이스

			tenline = false; // 열줄만 출력하는 변수
			flag = false; // 한줄에 브레이스 짝이 맞을 때.
			count = 0;
			keywordOn = false;

			br.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	} // subDirList()
}