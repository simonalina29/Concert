package persistence;

import business.Abonat;
import business.AbonatException;
import business.AbonatValidator;
import business.Concert;
import business.ConcertException;
import business.ConcertValidator;

import java.util.List;

import java.util.ArrayList;
import java.io.*;

public class DataManager {
	private static DataManager instance = null;
	private static final String file_concerte = "data/D.dat"; 
	private static final String file_abonati = "data/C.ser";
	private static final String file_rezervari = "data/A.txt"; 
	private static final String file_concertePline = "data/B.ser"; 
	private List<Concert> concerte = new ArrayList<Concert>();
	private List<Abonat> abonati = new ArrayList<Abonat>();
	List<String> v=new ArrayList<String>();
	private List<Concert> ab;
	ConcertValidator cvali = new ConcertValidator();
	AbonatValidator avali = new AbonatValidator();

	private DataManager() {
		readConcerte();
		readAbonati();
	}

	public static DataManager getInstance() {
		if (instance == null)
			instance = new DataManager();
		return instance;
	}

	private void readConcerte() {		
		try {
			BufferedReader dis = new BufferedReader(new FileReader(file_concerte));

			try {
				while (true) {
					String line = dis.readLine();
					if (line == null) {
						break;
					}
					String[] parts = line.split("[|]");
					String denumire = parts[0];
					String data = parts[1];
					int nri=Integer.parseInt(parts[2]);
					int nrd=Integer.parseInt(parts[3]);
					Concert c = new Concert(denumire,data,nri,nrd);

					concerte.add(c);
				}
			} catch (EOFException e) {
			}
			dis.close();
		} catch (Exception e) {
			System.err.println("Error reading abonati: " + e.getMessage());
			return;
		}

		// validation
		try {
			validateConcerte();
			System.out.println("Concerte successfully loaded!");
		} catch (ConcertException ex) {
			concerte = new ArrayList<Concert>();
			System.out.println("Corrupted concert file: " + ex.getMessage() + " Data not loaded!");
		}
	}
	

	private void validateConcerte() throws ConcertException {

		List<String> errors;

		for (Concert p : concerte) {
			errors = new ArrayList<String>();
			cvali.validate(p, errors);
			if (errors.size() > 0)
				throw new ConcertException("An invalid concert was found.");
		}
	}

	private void readAbonati() {
		ab=new ArrayList<Concert>();
		try {
			FileInputStream fis = new FileInputStream(file_abonati);
			ObjectInputStream dis = new ObjectInputStream(fis);

			try {
				while (true) {
					String line = (String) dis.readObject();
					if (line == null) {
						break;
					}
					String[] parts =  line.split("[|]");
					String nume = parts[0];
					String email = parts[1];
					Abonat a = new Abonat(nume, email);

					abonati.add(a);
				}
			} catch (EOFException e) {
			}
			dis.close();
		} catch (Exception e) {
			System.err.println("Error reading abonati: " + e.getMessage());
			return;
		}

		// validation
		try {
			validateAbonati();
			System.out.println("Abonati successfully loaded!");
		} catch (AbonatException ex) {
			abonati = new ArrayList<Abonat>();
			System.out.println("Corrupted abonat file: " + ex.getMessage() + " Data not loaded!");
		}
	}

	private void validateAbonati() throws AbonatException {

		List<String> errors;

		for (Abonat a : abonati) {
			errors = new ArrayList<String>();
			avali.validate(a, errors);
			if (errors.size() > 0)
				throw new AbonatException("An invalid abonat was found.");
		}
	}

	public void addConcert(Concert concert) throws ConcertException {
		concerte.add(concert);
		try {
			BufferedWriter dos = new BufferedWriter(new FileWriter(file_concerte, true));

			for (Concert s : concerte) {
				dos.write(s.getDenumire() + "|" + s.getData() +"|"+s.getLocuriInitiale()+"|"+s.getLocuriDisponibile()+"\r\n");
			}
			dos.close();
		} catch (FileNotFoundException e) {
			System.err.println(file_concerte + " file does not exist.");
		} catch (IOException e) {
			System.err.println("Error writing abonat: " + e.getMessage() + "\r\n");
		}
		
	}

	public void addAbonat(Abonat abonat) throws AbonatException {
		abonati.add(abonat);
		
		try {
			ObjectOutputStream dos = new ObjectOutputStream(new FileOutputStream(new File(file_abonati), true));

			for (Abonat s : abonati) {
				dos.writeObject(s.getNume() + " | " + s.getEmail() +"\r\n");
			}
			dos.close();
		} catch (FileNotFoundException e) {
			System.err.println(file_abonati + " file does not exist.");
		} catch (IOException e) {
			System.err.println("Error writing abonat: " + e.getMessage() + "\r\n");
		}
	}

	public void rezervaLocuri(String abonat, String concert, int locuriDorite) throws ConcertException {
		try {
			// update concert list
			for (int i = 0; i < concerte.size(); i++) {
				if (concerte.get(i).getDenumire() == concert) {
					Concert c = new Concert(concert, concerte.get(i).getData(), concerte.get(i).getLocuriInitiale(),
							concerte.get(i).getLocuriDisponibile() - locuriDorite);

					// if we still have places for this concert we update list				
					if (c.getLocuriDisponibile() > 0) {
						concerte.set(i, c);
					} else { // else we move it to another file
						addConcertPlin(c);
						concerte.remove(i);
					}
				}
			}
			v.add(abonat+concert+((Integer)locuriDorite).toString());
			
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(file_rezervari));
				/*dos.writeUTF(abonat);
				dos.writeChar('\t'); // tab between fields
				dos.writeUTF(concert);
				dos.writeChar('\t');
				dos.writeInt(locuriDorite);
				dos.writeChar('\t');		
				dos.writeUTF("\12");
				dos.writeChar('\t');*/
			for(String s:v)
			{
				dos.writeUTF(s);
				dos.writeChar('\t');
				dos.writeChar('\12');
			}
			

			dos.close();
		

			// save updated concerte to file
			saveConcerte();
		} catch (FileNotFoundException e) {
			// we handle file existing above but let the throw be
			throw new ConcertException("data/sales/" + abonat + ".ser" + " file does not exist.");
		} catch (IOException e) {
			System.err.println("Error writing concert: " + e.getMessage() + "\r\n");
		}
	}

	public void addConcertPlin(Concert concert) throws ConcertException {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(file_concertePline), true));
			oos.writeObject(concert);
			oos.close();
		} catch (FileNotFoundException e) {
			throw new ConcertException(file_concertePline + " file does not exist.");
		} catch (IOException e) {
			System.err.println("Error writing concert0 with quantity 0: " + e.getMessage() + "\r\n");
		}
	}

	public void saveConcerte() {		
		try {
			BufferedWriter dos = new BufferedWriter(new FileWriter(file_concerte, true));

			for (Concert s : concerte) {
				dos.write(s.getDenumire() + " | " + s.getData() +"|"+s.getLocuriInitiale()+"|"+s.getLocuriDisponibile() +"\r\n");
			}
			dos.close();
		} catch (FileNotFoundException e) {
			System.err.println(file_concerte + " file does not exist.");
		} catch (IOException e) {
			System.err.println("Error writing concert: " + e.getMessage() + "\r\n");
		}
	}

	public void saveAbonati() {
		try {
			BufferedWriter dos = new BufferedWriter(new FileWriter(file_abonati, true));

			for (Abonat s : abonati) {
				if(abonati.size()==1)
					dos.write(s.getNume() + " | " + s.getEmail() + "\r");
				else
					
				dos.write(s.getNume() + " | " + s.getEmail() + "\r\n");
			}
			dos.close();
		} catch (FileNotFoundException e) {
			System.err.println(file_abonati + " file does not exist.");
		} catch (IOException e) {
			System.err.println("Error writing abonat: " + e.getMessage() + "\r\n");
		}
	}

	public List<Concert> getConcerte() {
		return concerte;
	}

	public Concert getConcert(String denumire) {
		for (Concert c : concerte) {
			if (c.getDenumire() == denumire) {
				return c;
			}
		}
		return null;
	}

	public List<Abonat> getAbonati() {
		return abonati;
	}

	public Abonat getAbonat(String nume, String email) {
		for (Abonat a : abonati) {
			if (a.getNume() == nume && a.getEmail() == email) {
				return a;
			}
		}
		return null;
	}
}
