package business;

import java.util.List;

public class ConcertValidator {
	
	public void validate(Concert e, List<String> errors) {

		if (e.getDenumire() == null || "".equals(e.getDenumire()))
			errors.add("Validation error: Numele concertului nu este valid\r\n");
		if (e.getData() == null || "".equals(e.getDenumire()))
			errors.add("Validation error: Data concertului nu este valida\r\n");
		if (e.getLocuriInitiale() <= 0)
			errors.add("Validation error: Numarul de locuri initiale ale concertului nu este valid\r\n");
		if (e.getLocuriDisponibile() <= 0)
			errors.add("Validation error: Numarul de locuri disponibile ale concertului nu este valid\r\n");
	}
}
