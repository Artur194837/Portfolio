package taschenrechner;

public class Hauptprogramm {
	public static void main(String [] args) {
		Taschenrechner taschenrechner = new Taschenrechner();
		
		double ergebnis = taschenrechner.berechne("((3 - 2) + 2 * (5*2))");
		
		System.out.println(ergebnis);
	}
}
