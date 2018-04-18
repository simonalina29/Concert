package tests;

import controllers.AbonatController;
import business.Casierie;
import business.AbonatException;
import business.ConcertException;

import org.junit.Test;
import org.junit.Before;

public class AbonatControllerTest {
	
	Casierie casierie = new Casierie();
	AbonatController abonatC = new AbonatController(casierie, "Test");
	
	@Before
	public void setup() {
	}

	@Test(expected = ConcertException.class)
	public void makeSaleInvalidConcert() throws ConcertException, AbonatException {
		abonatC.rezervaLocuri("Concert1", 0);
	}

	@Test(expected = ConcertException.class)
	public void makeSaleInvalidAbonat() throws AbonatException, ConcertException {
		abonatC.rezervaLocuri("Concert2", 0);
	}
}