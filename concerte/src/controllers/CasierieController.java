package controllers;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;

import business.Casierie;
import business.Abonat;
import business.AbonatException;
import business.ConcertException;
import business.Concert;

public class CasierieController extends Observable implements Observer {
	private Casierie concerte;
	private DefaultListModel<Concert> clm = new DefaultListModel<>();
	private DefaultListModel<Abonat> alm = new DefaultListModel<>();

	public CasierieController(Casierie agency) {
		this.concerte = agency;
		agency.addObserver(this);
		updateConcertListModel();
		updateAbonatListModel();
	}

	public void addConcert(String denumire, String data, String loc_init, String loc_disp) throws ConcertException {
		Concert concert = new Concert(denumire, data, Integer.parseInt(loc_init), Integer.parseInt(loc_disp));
		concerte.addConcert(concert);
		updateConcertListModel();
	}

	public void addAbonat(String name, String email) throws AbonatException {
		Abonat agent = new Abonat(name, email);
		concerte.addAbonat(agent);
		updateAbonatListModel();
	}

	public DefaultListModel<Concert> getConcertListModel() {
		return clm;
	}

	public DefaultListModel<Abonat> getAbonatListModel() {
		return alm;
	}

	public void updateConcertListModel() {
		clm.clear();
		List<Concert> concer = concerte.getConcerte();
		if (!concer.isEmpty())
			for (Concert p : concer)
				clm.addElement(p);
	}

	public void updateAbonatListModel() {
		alm.clear();
		List<Abonat> abonati = concerte.getAbonati();
		if (!abonati.isEmpty())
			for (Abonat a : abonati)
				alm.addElement(a);
	}

	public List<Concert> getConcerte() {
		return concerte.getConcerte();
	}

	public List<Abonat> getAbonati() {
		return concerte.getAbonati();
	}

	@Override
	public void update(Observable arg0, Object arg1) {        
        setChanged();
        notifyObservers();
	}
}
