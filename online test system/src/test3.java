import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

public class test3 {
	int no_test=1;
	int no_file=7;
	
	void create_output()
	{
		
        try{
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        writer.write("0");
	     writer.write("\n");
        for(int i=1;i<=no_file;i++){
	       
	      BufferedReader brin = new BufferedReader(new FileReader("/home/shaurya/eclipse_files/sbsk/src/file"+i+".txt"));
	      try {
	         int count=1;
	       	 
	         String s,c;
	         for(int j=0;j<12;j++)
	         	s = brin.readLine();
	         while ((s = brin.readLine()) != null) {
	         
	         if(count!=1)
	         	c=s.substring(2);
	         else
	         	c=s;
	         //System.out.println(c);
	         writer.write(c);
	         writer.write("\n");
	            
	            count++;
	            if(count==6*no_test)
	            	break;
	            
	            	
	         }
	      }finally {
	         	 
	            brin.close();
	       	         
	      }
	      }
	      writer.write("1");
	      writer.close();
	}
	catch(Exception e)
	{
	}
        
	}
    public static void main(String[] args) throws IOException {

        test3 p=new test3();
        p.create_output();
        
    }
}
