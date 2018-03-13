import java.io.*;
import java.net.*;
import java.util.*;
class Client
{
 Socket s;
 Scanner sc=new Scanner(System.in);
 DataInputStream din;
 DataOutputStream dout;
 Client(String ip, int port) throws Exception
 {
  //request a connection
  s = new Socket(ip, port);
 }


 void interact() throws Exception
 {
  din = new DataInputStream(s.getInputStream());
  dout = new DataOutputStream(s.getOutputStream());
 }
 
 void send_user_pass()
 {
  String username,password;
  try{
  System.out.println("Enter the username");
  username=sc.next();   
  System.out.println("Enter the password");
  password=sc.next();
  dout.writeUTF(username);
  dout.writeUTF(password);
  }
  catch(Exception e)
  {}
 }//interact
 
 void close() throws Exception
 {
   s.close();
 }
 public static void main(String args[])
 {
  try
  {
   Client c = new Client("192.168.144.142", 9876);
   c.interact();
   c.send_user_pass();
   c.close();
  } 
  catch(Exception ex)
  {
   System.out.println(ex);
  }
 }
}
