package tests;

import controllers.CasierieController;
import business.Casierie;
import business.AbonatException;
import business.ConcertException;

import org.junit.Test;
import org.junit.Before;

public class CasierieControllerTest {
	
	Casierie conc = new Casierie();
	CasierieController casierieC = new CasierieController(conc);
	
	@Before
	public void setup() {
	}

	@Test(expected = ConcertException.class)
	public void addConcert() throws ConcertException {
		casierieC.addConcert("Concert1", "1/3/2011", "12", "12");
	}
	

	@Test(expected = AbonatException.class)
	public void addAbonat() throws AbonatException {
		casierieC.addAbonat("Rus Catalin","rus.catalin@yahoo.com");
	}
}