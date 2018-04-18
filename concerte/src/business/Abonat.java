package business;

public class Abonat {
	private String nume;
	private String email;

	public Abonat(String nume,String email) {
		this.nume = nume;
		this.email=email;
	}

	public String getNume() {
		return nume;
	}

	public String toString() {
		return nume+"   "+email;
	}

	public boolean equals(Object o) {
		if (o instanceof Abonat) {
			Abonat b = (Abonat) o;
			return nume.equals(b.nume);
		}
		return false;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
