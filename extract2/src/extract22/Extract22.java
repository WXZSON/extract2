package extract22;





/*
 * import org.apache.http.client.HttpClient;  
 
import org.apache.http.client.ResponseHandler;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.impl.client.BasicResponseHandler;  
//import org.apache.http.impl.client.DefaultHttpClient; 
import org.apache.http.impl.client.HttpClientBuilder;
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;  
import java.net.URL;  
import java.util.ArrayList;  
import java.util.List;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;

//import javax.swing.text.Document;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements; 

import org.jsoup.helper.Validate;

class ExtractDoc{
	String author;
	String doc;
	
}

public class Extract22 {
	ArrayList StructDoc;  
	public String getpage(String URL) throws IOException{
		String pattern = "/p/\\d+";
		//中文
		Pattern r = Pattern.compile(pattern);
		//Matcher m = r.matcher(pageurl);
		String filename="C:\\Users\\ruizhewu\\heichuizi\\test.html";
		String savePath="C:\\Users\\ruizhewu\\heichuizi\\";
		File file = new File(filename);
		String tempString = null;
		BufferedReader reader = null;
		String content=null;
		StructDoc= new ArrayList(); 
		try {
			System.out.println("*********************");
			reader = new BufferedReader(new FileReader(file));
			int line = 1;
			while ((tempString = reader.readLine()) != null) {
				content=content+"\n"+tempString;
				//System.out.println("line " + line + ": " + tempString);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		//String c1=getNoHTMLString(content,10000);
		//System.out.println(content);
		Document doc = Jsoup.parse(content);
		Elements fool =doc.getElementsByTag("h3"); //Get the tile of Each post thread
		Elements fool2 =doc.getElementsByClass("d_post_content j_d_post_content "); //Get the tile of Each post thread
		Elements fool3 =doc.getElementsByClass("louzhubiaoshi  j_louzhubiaoshi"); //Get the tile of Each post thread
		Elements fool4 =doc.getElementsByTag("a"); //Get the tile of Each post thread
		//<div class="louzhubiaoshi  j_louzhubiaoshi" author="xl19931215">
		//class="d_post_content j_d_post_content
		//class="d_name"
		 for (Element link : fool) { 
			 System.out.println(link.attr("title").toString());
		 }
		 System.out.println("*1*******************");
		 for (Element link : fool2) { 
			 ExtractDoc tmp=new ExtractDoc();
			 tmp.doc=link.text();
			 StructDoc.add(tmp);
			 System.out.println(link.text());
		 }
		 System.out.println("*3*******************");
		 System.out.println("Size="+fool4.size());
		 int tmpCount=0;
		 Iterator<ExtractDoc> iterator = StructDoc.iterator();
		 for (Element link : fool4) { 
			 if(link.attr("data-field").isEmpty()==false){
				 String tmpId=link.attr("data-field");
				 tmpId=tmpId.substring(0, 5);
				 if(tmpId.equals("{\"un\""))
				 {
					 ExtractDoc tmp =iterator.next();
					 String tmpAuthor=link.attr("data-field");
					 int index=tmpAuthor.indexOf("\"}");
					 tmp.author=tmpAuthor.substring(7, index);
					 System.out.println(tmp.author);
					 tmpCount++;
				 }
			 }
		 }
		 System.out.println("tmpCount="+tmpCount);
		 Iterator<ExtractDoc> iterator1 = StructDoc.iterator();
		 String fileName=savePath+URL;
		 //FileWriter writer = null;
		//try {
		//	writer = new FileWriter(fileName);
		//} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8");
		 while(iterator1.hasNext()){
			 ExtractDoc tmp=iterator1.next();
			 System.out.println(tmp.author+"    "+tmp.doc);
			 out.write("\n\t"+tmp.author+"    "+tmp.doc);
		 }
		 out.close();
		//doc.getElementsByTag();
		return content;
	}
	public static void main(String[] ags){
		Extract22 T=new Extract22();
		try {
			T.getpage("123");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
