package business;

import java.util.List;

public class AbonatValidator {
	
	public void validate(Abonat e, List<String> errors) {

		if (e.getNume() == null || "".equals(e.getNume()))
			errors.add("Validation error: Numele abonatului nu este valid\r\n");
	}
}
