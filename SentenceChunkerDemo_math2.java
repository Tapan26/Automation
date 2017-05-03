
//import org.apache.pdfbox.pdmodel.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDMetadata;

import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import io.ReadPDF;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import java.io.*;


@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })


public class SentenceChunkerDemo_math2 {
	public static void main(String[] args) throws IOException {
		
		StructureOfQuestion StructureOfQuestionInstance1 = new StructureOfQuestion();
					
		String FileLocation="C://Users/TAPAN/Desktop/Wordfile/Quantitative_Aptitude.pdf";
		try{
			String text= ReadPDF.Read(FileLocation);
			String testString = text;
			String[] sentences10 = testString.split("((?<=([u901-u97F]))||(?<=([a-z]))||(0-9)||(\\[a-z]{1,6}>))\\.\\s{3,}");
			
			int counter=1;
			int counter2 = 0;
			
			List<String> sentences11= new LinkedList<String>();		
			
			
for (int i=0;i<sentences10.length;i++){

	
	String sn=sentences10[i];
	String star = "********************";
		
	if(sn.contains("[<b>]")){
		sn=sn.replaceAll("\\[<b>]", "<b>.");
		if(sn.contains("[]")){
			sn=sn.replaceAll("\\[]", "</b>.");
		}
		else
			sn = sn+"</b>.";
		
		
		}
	if(sn.contains("[<i>]")){
		sn=sn.replaceAll("\\[<i>]", "<i>.");
		if(sn.contains("[]")){
			sn=sn.replaceAll("\\[]", "</i>.");
		}
		else
		   sn = sn+"</i>.";
		//System.out.println(sn);
	}
	if(sn.contains("[<u>]")){
		sn=sn.replaceAll("\\[<u>]", "<u>.");
		if(sn.contains("[]")){
			sn=sn.replaceAll("\\[]", "</u>.");
		}
		else
			sn = sn+"</u>.";
	}
	if(sn.contains("[<strike>]")){
		sn=sn.replaceAll("\\[<strike>]", "<strike>.");
		if(sn.contains("[]")){
			sn=sn.replaceAll("\\[]", "</strike>.");
		}
		else
			sn = sn+"</strike>.";
	}
	
	if ( (sn.contains("4)")) || (sn.contains("(d)"))||(sn.contains("(4)"))||(sn.contains("d)"))){
	sentences11.add(i+counter2,sn);
	sentences11.add(i+1+counter2,star);
					
	counter2++;
	}
				
	else 
	{
		sentences11.add(i+counter2,sn);
		
	}
}

/*****************************copy to new list******************************************/
List<String> sentences1= new LinkedList<String>();
String sn="";
for(int i=0;i<sentences11.size();i++){
	sn=sentences11.get(i);
	sn=sn.replaceAll("[\\[\\]]","");
	//System.out.println(sn);
	//System.out.println("####");
	sentences1.add(sn);
}

/*****************************image locations**********************************/
List<String> imagelcos = new LinkedList<String>();
String pos="";
for(int j=0;j<sentences1.size();j++){
	pos=sentences1.get(j);
	if(pos.contains("img-")){
		imagelcos.add(pos);
	}
}
/****************************************************************************/
List<String> imagename = new LinkedList<String>();
for(int l=0;l<imagelcos.size();l++){
	  String sn1="";
	   sn1 = imagelcos.get(l);
	   
	    Pattern regex = Pattern.compile("[a-z]{3,20}-[0-9]{2}-[0-9]");
	    Matcher m = regex.matcher(sn1);
	    
	    while(m.find()) {
	        String s = m.group(0);
	        imagename.add(s);
	    }
}

/****************************database connect***************************************/
try{

	

	String star = "********************";
	
	int srt_idx=0;
	int nxt_idx=FindNextIndex(star, sentences1, srt_idx);
	
	StructureOfQuestionInstance1 = new StructureOfQuestion();
	  
 	StructureOfQuestionInstance1.option_4 = sentences1.get(nxt_idx-1);
	StructureOfQuestionInstance1.option_3 = sentences1.get(nxt_idx-2);
	StructureOfQuestionInstance1.option_2 = sentences1.get(nxt_idx-3);
	StructureOfQuestionInstance1.option_1 = sentences1.get(nxt_idx-4);
	
	if(srt_idx < nxt_idx-5){
	String clean=sentences1.get(nxt_idx-5);
	
	clean=clean.replace("\" ","'");
	StructureOfQuestionInstance1.question_statement =clean;
		
/********************************************************************************************/	
		
			int temp_idx1=srt_idx+1;
			StringBuilder sb1 = new StringBuilder();
		
			while(temp_idx1!=nxt_idx-5)
			{
				
				String s=sentences1.get(temp_idx1);
				sb1.append(s);
			    sb1.append("\t");
				temp_idx1++;
			}
		
			String concatString1=sb1.toString();
			concatString1=concatString1.replace("\"","'");
			StructureOfQuestionInstance1.question_instruction=concatString1;
	}
/*******************************************************************************************/
			
		Class.forName("com.mysql.jdbc.Driver");
		
		Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/spdb","root","");
	
		String S1 = "INSERT INTO master_question_description_table(question_instruction,question_statement, option_1, option_2, option_3, option_4) VALUES (' "+StructureOfQuestionInstance1.question_instruction
			+ " ',' " + StructureOfQuestionInstance1.question_statement
			
			+ " ',' " + StructureOfQuestionInstance1.option_1
			+ " ',' " + StructureOfQuestionInstance1.option_2
			+ " ',' " + StructureOfQuestionInstance1.option_3
			+ " ',' " + StructureOfQuestionInstance1.option_4			
			+ " ')";
		
		
		Statement stmt=con.createStatement();
		stmt.executeUpdate(S1);	
				
		con.close();
		
		srt_idx=nxt_idx;
		
		
/**************************************passage_func_start***************************************************/	
		
		int counter3=0;
		String s1="#START";
		String s2="#END";
		while(((sentences1.get(srt_idx+1))!=null) && (sentences1.get(srt_idx)).startsWith(star)) {
			
			if((sentences1.get(srt_idx+1)).matches(s1)){
				counter3++;
				
				while(!((sentences1.get(nxt_idx+1)).matches(s2))){
					
					StructureOfQuestionInstance1 = new StructureOfQuestion();
				  
					StructureOfQuestionInstance1.question_group = counter3;
					
				 	StructureOfQuestionInstance1.option_4 = sentences1.get(nxt_idx-1);
					StructureOfQuestionInstance1.option_3 = sentences1.get(nxt_idx-2);
					StructureOfQuestionInstance1.option_2 = sentences1.get(nxt_idx-3);
					StructureOfQuestionInstance1.option_1 = sentences1.get(nxt_idx-4);
					
					if(srt_idx<nxt_idx-5){
					String clean1=sentences1.get(nxt_idx-5);
					clean1=clean1.replace("\"","'");
					
					StructureOfQuestionInstance1.question_statement =clean1;					
				
	/********************************************************************************************/	
					
					int temp_idx2=srt_idx+1;
					StringBuilder sb2 = new StringBuilder();
				
					while(temp_idx2!=nxt_idx-5)
					{
						
						String s=sentences1.get(temp_idx2);
						sb2.append(s);
					    sb2.append("\t");
						temp_idx2++;
					}
				
					String concatString2=sb2.toString();
					concatString2=concatString2.replace("\"","'");
					StructureOfQuestionInstance1.question_instruction=concatString2;
					}
	/*******************************************************************************************/				
					
			 		
					Class.forName("com.mysql.jdbc.Driver");
					
					Connection con1=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/spdb","root","");
				
			
					
					String S  = "INSERT INTO master_question_description_table(question_instruction,question_statement, option_1, option_2, option_3, option_4,question_group)"
							+"VALUES ( ";
					StringBuilder sb = new StringBuilder();
					sb.append(S);
					sb.append('"');
					sb.append(StructureOfQuestionInstance1.question_instruction);
					sb.append('"');
					sb.append(",");
					sb.append('"');
					sb.append(StructureOfQuestionInstance1.question_statement);
					sb.append('"');
					sb.append(",");
					sb.append('"');
					sb.append(StructureOfQuestionInstance1.option_1);
					sb.append('"');
					sb.append(",");
					sb.append('"');
					sb.append(StructureOfQuestionInstance1.option_2);
					sb.append('"');
					sb.append(",");
					sb.append('"');
					sb.append(StructureOfQuestionInstance1.option_3);
					sb.append('"');
					sb.append(",");
					sb.append('"');
					sb.append(StructureOfQuestionInstance1.option_4);
					sb.append('"');
					sb.append(",");
					sb.append('"');
					sb.append(StructureOfQuestionInstance1.question_group);
					sb.append('"');
					sb.append(")");
					//sb.append(";");
					
	                
					//System.out.println(sb);
					Statement stmt1=con1.createStatement();
					stmt1.executeUpdate(sb.toString());		
							
					con1.close();
					
					srt_idx=nxt_idx;
					nxt_idx=FindNextIndex(star, sentences1, srt_idx);	
				} 
				
			}
			
		/**************************************passage_func_end***************************************************/
		
	else{
		
				srt_idx=nxt_idx;
				nxt_idx=FindNextIndex(star, sentences1, srt_idx);
				
				StructureOfQuestionInstance1 = new StructureOfQuestion();
			  
			 	StructureOfQuestionInstance1.option_4 = sentences1.get(nxt_idx-1);
				StructureOfQuestionInstance1.option_3 = sentences1.get(nxt_idx-2);
				StructureOfQuestionInstance1.option_2 = sentences1.get(nxt_idx-3);
				StructureOfQuestionInstance1.option_1 = sentences1.get(nxt_idx-4);
				
				if(srt_idx<nxt_idx-5){
				String clean1=sentences1.get(nxt_idx-5);
				clean1=clean1.replace("\"","'");
				
				StructureOfQuestionInstance1.question_statement =clean1;
				
/********************************************************************************************/	
				
				int temp_idx2=srt_idx+1;
				StringBuilder sb2 = new StringBuilder();
			
				while(temp_idx2!=nxt_idx-5)
				{
					
					String s=sentences1.get(temp_idx2);
					sb2.append(s);
				    sb2.append("\t");
					temp_idx2++;
				}
			
				String concatString2=sb2.toString();
				concatString2=concatString2.replace("\"","'");
				StructureOfQuestionInstance1.question_instruction=concatString2;
				}
/*******************************************************************************************/				
		 		
				Class.forName("com.mysql.jdbc.Driver");
				
				Connection con1=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/spdb","root","");
			
				String S  = "INSERT INTO master_question_description_table(question_instruction,question_statement, option_1, option_2, option_3, option_4)"
						+"VALUES ( ";
				StringBuilder sb = new StringBuilder();
				sb.append(S);
				sb.append('"');
				sb.append(StructureOfQuestionInstance1.question_instruction);
				sb.append('"');
				sb.append(",");
				sb.append('"');
				sb.append(StructureOfQuestionInstance1.question_statement);
				sb.append('"');
				sb.append(",");
				sb.append('"');
				sb.append(StructureOfQuestionInstance1.option_1);
				sb.append('"');
				sb.append(",");
				sb.append('"');
				sb.append(StructureOfQuestionInstance1.option_2);
				sb.append('"');
				sb.append(",");
				sb.append('"');
				sb.append(StructureOfQuestionInstance1.option_3);
				sb.append('"');
				sb.append(",");
				sb.append('"');
				sb.append(StructureOfQuestionInstance1.option_4);
				sb.append('"');
				sb.append(")");
				
				Statement stmt1=con1.createStatement();
				stmt1.executeUpdate(sb.toString());		
						
				con1.close();
				
				} 
			
			}								 
	
}catch(Exception e){ System.out.println(e);}

/****************************fetching data from db*************************************/
	
try {
	
	ArrayList<Fetch_db> quesnList = new ArrayList<>();
	quesnList=getAllQuestion();
	System.out.println(quesnList.toString());
	processData(quesnList);
	//System.out.println(quesnList);
	
	
} catch (ClassNotFoundException e1) {
	e1.printStackTrace();
} catch (SQLException e1) {
	e1.printStackTrace();
}

/****************************rename file***************************************************/
File dir = new File("C:/Users/TAPAN/Desktop/Images/");
int counter3=1;
if (dir.isDirectory()) { // make sure it's a directory
    for (final File f : dir.listFiles()) {
        try {
        	
        	String sn3="";
            sn3=imagename.get(counter3);
          
        	File newfile =new File(sn3+".jpg");
 
        } catch (Exception e) {
         
        }
        counter3++;
    }		
	String S = new String();			
		try{
			FileInputStream fsIP= new FileInputStream(new File("C://Users/TAPAN/Desktop/3.xls")); //Read the spreadsheet that needs to be updated
			HSSFWorkbook wb = new HSSFWorkbook(fsIP); //Access the workbook
			HSSFSheet worksheet = wb.getSheetAt(0); //Access the worksheet, so that we can update / modify it.
     
	
			FileOutputStream fileOut = new FileOutputStream("C://Users/TAPAN/Desktop/3.xls");
			
				int size=sentences1.size();
				for(int i=0;i<size;i++){
					
					HSSFRow row1 = worksheet.createRow((short) i);
					HSSFCell cellA1 = row1.createCell((short) 0);
					cellA1.setCellValue(sentences1.get(i));
					HSSFCell cellA2 = row1.createCell((short) 1);	
											 }
					fsIP.close(); //Close the InputStream
					wb.write(fileOut);
					//wb.close();
					fileOut.flush();
					fileOut.close();
				}catch(Exception e){ System.out.println(e);	
					}
}	
		}finally{}
			}

/**************************************renaming function*********************/
static void renameFiles(String oldName, String newName)
{
    String sCurrentLine = "";

    try
    {
        BufferedReader br = new BufferedReader(new FileReader(oldName));
        BufferedWriter bw = new BufferedWriter(new FileWriter(newName));

        while ((sCurrentLine = br.readLine()) != null)
        {
            bw.write(sCurrentLine);
            bw.newLine();
        }

        br.close();
        bw.close();

        File org = new File(oldName);
        org.delete();

    }
    catch (FileNotFoundException e)
    {
        e.printStackTrace();
    }
    catch (IOException e)
    {
        e.printStackTrace();
    }

}
/**********************************find next index**************************************************/
	    
public static int FindNextIndex(String s, List<String> list, int srt_idx){
	while(true){
		if(list.get(srt_idx+1).startsWith(s)){
			//System.out.println(srt_idx);
			return srt_idx+1;
		}
		else 
			srt_idx++;
	}
	
}
/*************************************************************************************************/
public  static  ArrayList<Fetch_db> getAllQuestion() throws ClassNotFoundException, SQLException {
	Class.forName("com.mysql.jdbc.Driver");
	
	Connection conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/spdb","root","");
    Statement stm;
    stm = conn.createStatement();
    String sql = "Select * From master_question_description_table";
    ResultSet rst;
    rst = stm.executeQuery(sql);
    ArrayList quesList = new ArrayList();
    
    ArrayList list = new ArrayList();  
    
  while (rst.next()) {
    	
    	Fetch_db ques = new Fetch_db();
    	ques.setId(rst.getInt("id"));
    	ques.setQuestion_instruction(rst.getString("question_instruction"));
    	ques.setQuestion_statement(rst.getString("question_statement"));
    	ques.setOption_1(rst.getString("option_1"));
    	ques.setOption_2(rst.getString("option_2"));
    	ques.setOption_3(rst.getString("option_3"));
    	ques.setOption_4(rst.getString("option_4"));
    	//System.out.println(ques);
    	quesList.add(ques);
    }

    //System.out.println(quesList);
    return quesList;
  
   
}

public static void processData(ArrayList<Fetch_db> cars){
   
    for (int i=0; i<cars.size(); i++){
        int id= cars.get(i).id;
        String instruct = "inst";
        String statement = "stmt";
        String option1 = "opt1";
        String option2 = "opt2";
        String option3 = "opt3";
        String option4 = "opt4";
        
        
        //System.out.println(id);
        String inst= cars.get(i).question_instruction;
       //System.out.println(inst+id);
        String stmt=cars.get(i).question_statement;
        String opt1=cars.get(i).option_1;
        
        String opt2=cars.get(i).option_2;
        String opt3=cars.get(i).option_3; 
        String opt4=cars.get(i).option_4;
	
        /******************update string****************************/
        String instupd= RewriteImage(inst,id,instruct);
        
        String stmtupd=RewriteImage(stmt,id,statement);
        String opt1upd=RewriteImage(opt1,id,option1);
        String opt2upd=RewriteImage(opt2,id,option2);
        String opt3upd=RewriteImage(opt3,id,option3); 
        String opt4upd=RewriteImage(opt4,id,option4);
			
        try {
			Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/spdb","root","");
			String S1  = "UPDATE master_question_description_table SET question_instruction=";
			StringBuilder sb = new StringBuilder();
			sb.append(S1);
			sb.append("'");
			sb.append(instupd);
			sb.append("', ");
			sb.append("question_statement=");
			sb.append('"');
			sb.append(stmtupd);
			sb.append('"');
			sb.append(",");
			sb.append("option_1=");
			sb.append('"');
			sb.append(opt1upd);
			sb.append('"');
			sb.append(",");
			sb.append("option_2=");
			sb.append('"');
			sb.append(opt2upd);
			sb.append('"');
			sb.append(",");
			sb.append("option_3=");
			sb.append('"');
			sb.append(opt3upd);
			sb.append('"');
			sb.append(",");
			sb.append("option_4=");
			sb.append('"');
			sb.append(opt4upd);
			sb.append('"');
			sb.append(" WHERE id=");
			sb.append(id);
			
			//System.out.println(sb.toString());
			Statement stmt1;
			
			stmt1 = con.createStatement();
		    stmt1.executeUpdate(sb.toString());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		
        	
        }
}
		
	public static String RewriteImage(String s,int id, String key){

		Pattern regex = Pattern.compile("[a-z]{3,20}-[0-9]{2}-[0-9]");
			  Matcher k = regex.matcher(s);
	   
			   int i = 0;
			 	if(k.find()){
			 		String s3=k.group(0);
			 		
			 		File sourceimage = new File("C:/Users/TAPAN/Desktop/Images/"+s3+".jpg");
			 		BufferedImage image = null;
			 	    File f = null;
			 		try {
			 			
			 		       image = ImageIO.read(sourceimage);
			 		      //System.out.println(image);
			 		      System.out.println("Reading complete.");
			 		    }catch(IOException e){
			 		      System.out.println("Error: "+e);
			 		    }
			 		
			 		String str1 =id+"-"+key+i;
			 		
			 		
			 		
			 		try{
			 		      f = new File("D:/Image/"+str1+".jpg"); 
			 		   
						ImageIO.write(image, "jpg", f);
			 		      System.out.println("Writing complete.");
			 		    }catch(IOException e){
			 		      System.out.println("Error: "+e);
			 		    }
			 		
			 		  String imgsrc="< img src ="+str1+".jpg />";
			 		  s=s.replaceAll(s3,imgsrc);
			 		  
			      i++;
			     }
			 	  
			 	return s;    
		}
		}
