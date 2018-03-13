import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.*;
public class test {

	/**
	 * @param args
	 */
	public static void main(String args[]) throws IOException {  
	      FileInputStream in = null;
	      FileOutputStream out = null;
	      int count=0;
	      for(int i=1;i<8;i++){
	      in = new FileInputStream("home/shaurya/file"+i+".txt");
	      try {
	         
	         out = new FileOutputStream("output.txt");
	         
	         int c;
	         while ((c = in.read()) != -1) {
	            out.write(c);
	            count++;
	            if(count==4)
	            	break;
	         }
	      }finally {
	         if (in != null) {
	            in.close();
	         }
	         if (out != null) {
	            out.close();
	         }
	      }
	      }
	   }
			
	}
			

	


