package controllers;

import business.Casierie;
import business.AbonatException;
import business.Concert;
import business.ConcertException;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;

public class AbonatController extends Observable implements Observer {
	private Casierie concerte;
	private String nume;
	private DefaultListModel<Concert> pdlm = new DefaultListModel<>();

	public AbonatController(Casierie agency, String name) {
		this.concerte = agency;
		this.nume = name;
		agency.addObserver(this);
		updateConcertListModel();
	}

	public String getName() {
		return nume;
	}

	public void rezervaLocuri(String concert, int locuriDorite) throws ConcertException, AbonatException {
		concerte.rezervaLocuri(nume, concert, locuriDorite);
	}

	public List<Concert> getProducts() {
		return concerte.getConcerte();
	}

	public DefaultListModel<Concert> getConcertListModel() {
		return pdlm;
	}

	public void updateConcertListModel() {
		pdlm.clear();
		List<Concert> products = concerte.getConcerte();
		if (!products.isEmpty())
			for (Concert p : products)
				pdlm.addElement(p);
	}

	public void update(Observable o, Object arg) {		
		setChanged();
		notifyObservers();
	}
}
