package business;

import persistence.DataManager;

import java.util.*;

public class Casierie extends Observable {

	public Casierie() {
	}

	public void addConcert(Concert concert) throws ConcertException {
		List<Concert> concerte = DataManager.getInstance().getConcerte();
		int cnt = -1;
		int index = -1;

		for (Concert c : concerte) {
			cnt++;
			if (c.getDenumire().equals(concert.getDenumire())) {
				index = cnt;
				break;
			}
		}
		
		if (index >= 0)
			throw new ConcertException("Concert " + concert.getDenumire() + " exista deja!");
		DataManager.getInstance().addConcert(concert);
		System.out.println("Concertul adaugat: " + concert);
		setChanged();
		notifyObservers();
	}

	public void addAbonat(Abonat a) throws AbonatException {
		int index = DataManager.getInstance().getAbonati().indexOf(a);
		if (index >= 0)
			throw new AbonatException("Abonatul " + a.getNume() + " exista deja!");
		DataManager.getInstance().addAbonat(a);
		System.out.println("Abonatul adaugat: " + a);
		setChanged();
		notifyObservers();
	}

	public void rezervaLocuri(String abonat, String concert, int locuriDorite) throws ConcertException, AbonatException {
		List<Concert> concerte = DataManager.getInstance().getConcerte();
		List<Abonat> abonati = DataManager.getInstance().getAbonati();
		int cnt = -1;
		int index = -1;

		for (Concert c : concerte) {
			cnt++;
			if (c.getDenumire().equals(concert)) {
				index = cnt;
				break;
			}
		}

		if (index < 0)
			throw new ConcertException("Concertul " + concert + " nu exista!");

		cnt = -1;
		index = -1;

		for (Abonat a : abonati) {
			cnt++;
			if (a.getNume().equals(abonat)) {
				index = cnt;
				break;
			}
		}

		if (index < 0)
			throw new AbonatException("Abonatul " + concert + " nu exista!");

		Concert p = DataManager.getInstance().getConcert(concert);
		if (p.getLocuriDisponibile() < locuriDorite)
			throw new ConcertException("Nu mai exista atatea locuri disponibile!");
		DataManager.getInstance().rezervaLocuri(abonat, concert, locuriDorite);
		setChanged();
		notifyObservers();
	}

	public List<Concert> getConcerte() {
		return DataManager.getInstance().getConcerte();
	}

	public List<Abonat> getAbonati() {
		return DataManager.getInstance().getAbonati();
	}
}
