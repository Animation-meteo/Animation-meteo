package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import getgribbymail.getgribbymail;

class Testgetgribbymail {

	getgribbymail monget = null ;
		
	@Test
	void testsetdonneeenvoye() {
		
		monget = new getgribbymail();
		
		double lgpg = 4.0;
		double lgpd = 2.0;
		double lapg = 3.0;
		double lapd = 1.0;
		
		monget.setdonneeenvoyee(lapg, lapd, lgpg,  lgpd);
		
		assertEquals(monget.getlongitudepg(),lgpg);
		assertEquals(monget.getlattitudepd(),lapd);
		assertEquals(monget.getlongitudepd(),lgpd);
		assertEquals(monget.getlattitudepg(),lapg);
		
		
	}
	
	

}
