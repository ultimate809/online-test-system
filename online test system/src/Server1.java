import java.net.*;
import java.io.*;

class Server1 implements Runnable
{
 boolean flag;
 Thread connectionThread;
 ServerSocket port;


 Server1(int x) throws Exception
 {
  //open a port
  port = new ServerSocket(x); 
  //success
  //limit accept block time to 5000 milliseconds 
  port.setSoTimeout(5000); 

  connectionThread = new Thread(this);
  connectionThread.start();
 }//Server(int)

 public void run()//connectionThread
 {
  acceptConnections();
  shutDown();
 }

 void acceptConnections()
 {
  Socket clnt;
  flag = true;

  while(flag)
  {
   try
   {
     //accept a client connection
     //1. Blocks the program control until 
     //1.1. A client requests a connection
     //1.2. timeout
     //2. On request, form a connection
     //3. Return a Socket object that represents connection with the client 
 
     System.out.println("Server waiting for client connection request ...");
     clnt = port.accept();
     System.out.println("Server formed a client connection");
     new ProcessConnection(clnt);
   }
   catch(Exception ex)
   {}
  }//while

 }//acceptConnections

 void shutDown() 
 {
   try
   {
    port.close();
   }
   catch(Exception ex)
   {}
 }

 public static void main(String args[])
 {
  try
  {
   new Server1(8000);
   
   //...job ahead 
  }
  catch(Exception ex)
  {
   System.out.println(ex); 
  }
 }//main

}//Server


class ProcessConnection extends Thread
{
 Socket clnt;
 DataInputStream din;
 DataOutputStream dout;
 MysqlCon sql=new MysqlCon();
 
 ProcessConnection(Socket s)
 {
   clnt = s;
   sql.connect();
   start();//activate the thread
 }
 void profile()
 {
	 String username,password;
	 int score;
	 char ch='1';
	 try{
		 while(ch=='1'){
		 username=din.readUTF();
		 password=din.readUTF();
		 int service_no=din.readInt();
		 switch(service_no){
			 case 1 : 
				 test1 t1=new test1();
				 t1.create_output();
				 send_file();
				 score=comp_score(1);
				 send_score(username,password);
				 break;
			 case 2 : 
				 test2 t2=new test2();
				 t2.create_output();
				 send_file();
				 score=comp_score(2);
				 send_score(username,password);
				 break;
			 case 3 : 
				 test3 t3=new test3();
				 t3.create_output();
				 send_file();
				 score=comp_score(3);
				 send_score(username,password);
				 break;
			 case 4:
				 send_score(username,password);
				 break;
			 case 5: 
				 preform(username,password);
			 case 6:
				 ch='0';
				 
		 }
		 }		 
	 }
	 catch(Exception e)
	 {
		 
	 }
 }
 void preform(String username,String password)
 {
	 try{
		 Process p = Runtime.getRuntime().exec("python3 graph.py "+username + " "+ password);
		 }
		 catch(Exception ex)
		 {}
 }
 void send_score(String username,String password)
 {
	 try{
		 String score1=sql.get_score(1,username,password);
		 String score2=sql.get_score(2,username,password);
		 String score3=sql.get_score(3,username,password);
		 dout.writeUTF(score1);
		 dout.writeUTF(score2);
		 dout.writeUTF(score3);
	 }
	 catch(Exception e){}
 }
 void send_file()
	{
		try{
			BufferedReader brin = new BufferedReader(new FileReader("output.txt"));
			BufferedWriter writer = new BufferedWriter(new FileWriter("response.txt"));
			String s,r;
			int count=1;
			s = brin.readLine();
			dout.writeUTF(s);
			for(int i=0;i<7;i++)
			{
				s = brin.readLine();
				dout.writeUTF(s);
				s = brin.readLine();
				dout.writeUTF(s);
				s = brin.readLine();
				dout.writeUTF(s);
				s = brin.readLine();
				dout.writeUTF(s);
				s = brin.readLine();
				dout.writeUTF(s);
				r=din.readUTF();
				writer.write(r);
			}
			s = brin.readLine();
			dout.writeUTF(s);
			brin.close();
		}
		catch(Exception e)
		{
			
		}
	}
 int comp_score(int test)
 {
	 int count=0;
	 try{
			BufferedReader brin = new BufferedReader(new FileReader("response.txt"));
			BufferedReader brin2 = new BufferedReader(new FileReader("sol"+test+".txt"));
			String s,r;
			
			for(int i=0;i<7;i++){
			r = brin.readLine();
			s=  brin2.readLine();
			if(r.contains(s))
				count++;
			}
			
			brin.close();
		}
		catch(Exception e)
		{
			
		}
	 return count;
 }
 void recieve_user_pass()
 {
   String username,password;
   String flag;
   try{
   flag=din.readUTF();
   username=din.readUTF();
   password=din.readUTF();
   System.out.println(flag);
   if(flag.contains("new_user"))
   {
	sql.put_new(username,password);
   }
   else if(flag.contains("login"))
   {
	   
	   boolean check=sql.check_old(username,password);
	   System.out.print(check);
	   dout.writeBoolean(check);
   }
   else
   {
	   System.out.print("Invalid Request from client");
   }
   }
   catch(Exception e)
   {
   }
 }
 
 

 
 
 public void run()
 {
  try
  {
   //fetch the i/o streams
   din = new DataInputStream( clnt.getInputStream() );
   dout = new DataOutputStream( clnt.getOutputStream() );
   System.out.print("going inside userpass");
   recieve_user_pass();
   clnt.close();

  }//try
  catch(IOException ex)
  {
   ex.printStackTrace();
   System.out.println(ex);
  }

 }//run

}//ProcessConnection	