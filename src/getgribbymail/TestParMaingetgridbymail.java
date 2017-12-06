package getgribbymail;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.mail.MessagingException;

public class TestParMaingetgridbymail {

public static void main (String[] args) throws FileNotFoundException, MessagingException, IOException, InterruptedException {
		
		getgribbymail monget = new getgribbymail();
		
		double lgpg = 4.0;
		double lgpd = 2.0;
		double lapg = 3.0;
		double lapd = 1.0;
		
		String lienversfichier = monget.recuperationfichiergrib(lapg, lapd, lgpg,  lgpd);
		
		System.out.println(lienversfichier);
		
	}
}
