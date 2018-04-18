import business.Casierie;
import business.Abonat;
import controllers.CasierieController;
import controllers.AbonatController;
import gui.CasierieFrame;
import gui.AbonatFrame;
import persistence.DataManager;

public class StartApp {
	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Casierie casierie = new Casierie();
		CasierieController CTRL = new CasierieController(casierie);
		CasierieFrame frame = new CasierieFrame(casierie, CTRL);
		
		AbonatController aCTRL;
		AbonatFrame abonatWindow;
		int cnt = 0;
		for (Abonat a : DataManager.getInstance().getAbonati()) {
			aCTRL = new AbonatController(casierie, a.getNume());
			abonatWindow = new AbonatFrame(aCTRL);
			abonatWindow.setLocation(20, 240*cnt+50);
			cnt++;
		}
	}
}