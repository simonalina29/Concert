package business;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Concert implements Serializable {
	private String denumire;
	private String data;
	private int locuri_init;
	private int locuri_disp;

	public Concert(String denumire, String data, int locuri_init, int locuri_disp) {
		this.denumire = denumire;
		this.data = data;
		this.locuri_init = locuri_init;
		this.locuri_disp = locuri_disp;
	}

	public String getDenumire() {
		return denumire;
	}

	public String getData() {
		return data;
	}

	public int getLocuriInitiale() {
		return locuri_init;
	}

	public int getLocuriDisponibile() {
		return locuri_disp;
	}

	public String toString() {
		return denumire + " - " + data + " - " + locuri_init + " - " + locuri_disp;
	}

	public boolean equals(Object o) {
		if (o instanceof Concert) {
			Concert b = (Concert) o;
			return denumire.equals(b.denumire) && data.equals(b.data) && locuri_init == b.locuri_init
					&& b.locuri_disp == b.locuri_disp;
		}
		return false;
	}
}
