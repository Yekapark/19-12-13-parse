package org.parse.contoller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

public class ZipTest {

	public static void main(String[] args) {

		String gitId = "seung0922";
		
		String saveDir = "C:\\ezcode";
		
		List<String> result = getRepositories(gitId);
		
		
		for(String repo : result) {
			
			System.out.println("repo.................>" + repo);
			
			String fileURL = "https://github.com/" + gitId + "/" + repo + "/archive/master.zip";
			
			System.out.println(fileURL);
			
			try {
				// zip 파일 다운로드
				downloadFile(fileURL, saveDir);
				
				// zip 파일 압축 풀기
				decompress(saveDir + "\\" + repo + "-master.zip", saveDir);
				
				File file = new File(saveDir + "\\" + repo + "-master.zip");
				
				file.delete();
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		System.out.println(result);

		
	}
	
	// github repository 목록 가져오기
	public static List<String> getRepositories(String gitName) {
		
		List<String> repositories = new ArrayList<String>();
		
		GitHubClient client = new GitHubClient();
		
		client.setOAuth2Token("9a7545d6057560416cc35af4c375c6f3d23a6a7a");
		
		RepositoryService service = new RepositoryService();
		
		try {
			
			for (Repository repo : service.getRepositories(gitName)) {
				
				repositories.add(repo.getName());
				
				//System.out.println(repo.getName());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return repositories;
	}

	/**
	 * 압축풀기 메소드
	 * 
	 * @param zipFileName 압축파일
	 * @param directory   압축 풀 폴더
	 */
	public static void decompress(String zipFileName, String directory) throws Throwable {
		
		File zipFile = new File(zipFileName);
		
		FileInputStream fis = null;
		ZipInputStream zis = null;
		
		ZipEntry zipentry = null;
		
		try {
			// 파일 스트림
			fis = new FileInputStream(zipFile);
			// Zip 파일 스트림
			zis = new ZipInputStream(fis);
			
			// entry가 없을때까지 뽑기
			while ((zipentry = zis.getNextEntry()) != null) {

				String filename = zipentry.getName();
				File file = new File(directory, filename);
				
				// entiry가 폴더면 폴더 생성
				if (zipentry.isDirectory()) {
					file.mkdirs();
				} else {
					// 파일이면 파일 만들기
					createFile(file, zis);
				}
			}
		} catch (Throwable e) {
			return;
		} finally {
			if (zis != null)
				zis.close();
			if (fis != null)
				fis.close();
		}
	}

	/**
	 * 파일 만들기 메소드
	 * 
	 * @param file 파일
	 * @param zis  Zip스트림
	 */
	private static void createFile(File file, ZipInputStream zis) throws Throwable {
		// 디렉토리 확인
		File parentDir = new File(file.getParent());
		// 디렉토리가 없으면 생성하자
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		// 파일 스트림 선언
		try (FileOutputStream fos = new FileOutputStream(file)) {
			byte[] buffer = new byte[256];
			int size = 0;
			// Zip스트림으로부터 byte뽑아내기
			while ((size = zis.read(buffer)) > 0) {
				// byte로 파일 만들기
				fos.write(buffer, 0, size);
			}
		} catch (Throwable e) {
			throw e;
		}
	}

	public static void downloadFile(String fileURL, String saveDir) throws IOException {
		URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			
			String fileName = "";
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			
			int contentLength = httpConn.getContentLength();

			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 9, disposition.length());
				}
			} else {
				// extracts file name from URL
				fileName = fileURL.substring(fileURL.lastIndexOf("/"), fileURL.length());
				return;
			}

			System.out.println("Content-Type = " + contentType);
			System.out.println("Content-Disposition = " + disposition);
			System.out.println("Content-Length = " + contentLength);
			System.out.println("fileName = " + fileName);

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			
			String saveFilePath = saveDir + File.separator + fileName;

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			
			byte[] buffer = new byte[1024];
			
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();

			System.out.println("File downloaded");
		} else {
			System.out.println("No file to download. Server replied HTTP code: " + responseCode);
		}
		httpConn.disconnect();
	}

}
