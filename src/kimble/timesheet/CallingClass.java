package kimble.timesheet;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;

public class CallingClass {

	
	public static void main(String[] args) throws InterruptedException, IOException {
	kimble k=new kimble();
	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	Console c=System.console();
	
	System.out.print("Enter Username: ");
	String uname=br.readLine();
//	System.out.println("Enter Password: ");
//	String pass=br.readLine();
	if (c == null) {
	    System.err.println("No console.");
//	    System.exit(1);
	}
	char [] p = c.readPassword("Enter password: ");

	
//	String pass=Arrays.toString(p);
	String pass=String.valueOf(p);

//	c.printf("Password entered was: %s%n",pass);
	System.out.print("Enter date: ");
	String date = br.readLine();
	System.out.print("Enter month: ");
	String month = br.readLine();
	System.out.print("Enter Project: ");
	String Proj = br.readLine();
	System.out.println(month+", "+date);
	System.out.println(Proj);
	
	k.Kimble(uname,pass,month,date,Proj);

//			k.Kimble("ravindra.yadav@makepositive.com", "Ravi#126", "september", "30", "Make Positive (Products)-Provar (Test Analyst124 )");
	
		return;
	}

}
