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
import io.ReadPDF;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.sql.*;

@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })


public class SentenceChunkerDemo_test2 {
	public static void main(String[] args) throws IOException {
		List<String> sentences1= new LinkedList<String>();
		//List<String> sentences11= new LinkedList<String>();
		
		StructureOfQuestion StructureOfQuestionInstance1 = new StructureOfQuestion();
		
		//getXMPInformation();
					
		String FileLocation="C://Users/TAPAN/Desktop/Word file/English Comprehension.pdf";
		try{
			String text= ReadPDF.Read(FileLocation);
			String testString = text;
			
			String[] sentences10 = testString.split("((?<=([u901-u97F]))||(?<=([a-z]))||(0-9))\\.\\s{3,}");
			ystem.out.println(sentences10[3]);*/
			
			int counter=1;
			int counter2 = 0;
			ArrayList<String> sentences11 = new ArrayList<String>();
			
			String sn = "";
			
for (int i=0;i<sentences10.length;i++){
				
				sn=sentences10[i];
				String star = "********************";
				
				if ( (sn.substring(0,4).startsWith("4)")) || (sn.substring(0,4).startsWith("(d)")))
				{
				sentences11.add(i+counter2,sn);
				sentences11.add(i+1+counter2,star);
								
				counter2++;
				}
				else 
				{
					sentences11.add(i+counter2,sn);
					
				}
				
				}

/*************************************************************************************************************************/
for(int i=0;i<sentences11.size();i++){
	sn=sentences11.get(i);
	//System.out.println(sn);
	sentences1.add(sn);
}
try{
	
	int size4=sentences1.size();
	String star = "********************";
	
	int srt_idx=0;
	int nxt_idx=FindNextIndex(star, sentences1, srt_idx);
	
	
	
	StructureOfQuestionInstance1 = new StructureOfQuestion();
	  
 	StructureOfQuestionInstance1.option_4 = sentences1.get(nxt_idx-1);
	StructureOfQuestionInstance1.option_3 = sentences1.get(nxt_idx-2);
	StructureOfQuestionInstance1.option_2 = sentences1.get(nxt_idx-3);
	StructureOfQuestionInstance1.option_1 = sentences1.get(nxt_idx-4);
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
			//System.out.println(concatString1);
			StructureOfQuestionInstance1.question_instruction=concatString1;
	
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
		
		
				
	
		while(((sentences1.get(srt_idx))!=null) && (sentences1.get(srt_idx)).startsWith(star)){
		
				nxt_idx=FindNextIndex(star, sentences1, srt_idx);
				StructureOfQuestionInstance1 = new StructureOfQuestion();
			  
			 	StructureOfQuestionInstance1.option_4 = sentences1.get(nxt_idx-1);
				StructureOfQuestionInstance1.option_3 = sentences1.get(nxt_idx-2);
				StructureOfQuestionInstance1.option_2 = sentences1.get(nxt_idx-3);
				StructureOfQuestionInstance1.option_1 = sentences1.get(nxt_idx-4);
				String clean1=sentences1.get(nxt_idx-5);
				clean1=clean1.replace("\"","'");
				
				StructureOfQuestionInstance1.question_statement =clean1;
					
				//StructureOfQuestionInstance1.question_statement =(sentences1.get(nxt_idx-5)).replaceAll("\"","\'");
				
			
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
				//System.out.println(concatString2);
				StructureOfQuestionInstance1.question_instruction=concatString2;
		
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
				//sb.append(";");
				
                
				//System.out.println(sb);
				Statement stmt1=con1.createStatement();
				stmt1.executeUpdate(sb.toString());		
						
				con1.close();
				
				srt_idx=nxt_idx;
				nxt_idx=FindNextIndex(star, sentences1, srt_idx);
				
			}							 
	}catch(Exception e){ System.out.println(e);}
    
			
/****************************************************************************************************************/				
			try{
				FileInputStream fsIP= new FileInputStream(new File("C://Users/TAPAN/Desktop/3.xls")); //Read the spreadsheet that needs to be updated
				HSSFWorkbook wb = new HSSFWorkbook(fsIP); //Access the workbook
				HSSFSheet worksheet = wb.getSheetAt(0); //Access the worksheet, so that we can update / modify it.
         
		
				FileOutputStream fileOut = new FileOutputStream("C://Users/TAPAN/Desktop/3.xls");
				
				int size=sentences1.size();
				for(int i=0;i<size;i++){
					
					//System.out.println(sentences1.get(i));
					
					HSSFRow row1 = worksheet.createRow((short) i);
					HSSFCell cellA1 = row1.createCell((short) 0);
					cellA1.setCellValue(sentences1.get(i));
					HSSFCell cellA2 = row1.createCell((short) 1);	
											 }
					fsIP.close(); //Close the InputStream
					wb.write(fileOut);
					wb.close();
					fileOut.flush();
					fileOut.close();
				}catch(Exception e){ System.out.println(e);	
					}
			
		}finally{}
			}

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
}