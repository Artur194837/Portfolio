package taschenrechner;

import java.util.ArrayList;

public class InfixZuPostfixUmwandler implements InfixZuPostfixUmwandlerI{
	private String erstesLeerzeichenEntfernen(String s) {
		if(s.charAt(0) == ' ')
			return s.substring(1, s.length());
		else
			return s;
	}
	
	public String infixZuPostfix(String infix) {
		String postfix = "";
		
		int i = 0; //Index mit dem über infix iteriert wird
		
		while(i < infix.length()) {
			if(Character.isDigit(infix.charAt(i))) { //Wenn das erste Zeichen eine Ziffer ist kann dieses einfach in postfix übernommen werden
				postfix += infix.charAt(i);
			}
			else {
				if(infix.charAt(i) != ' ') { //Leerzeichen werden ignoriert
					String infixFuerRekursion = "";
					
					int start = 0;
					int end = 0;
					
					if(infix.charAt(i) == '+' || infix.charAt(i) == '-') {
						start = i + 1;
						end = start;
						
						int klammernGeoffnet = 0; //Variable zur Überwachung von offenen Klammern, denn diese müssen zuerst rekursiv verarbeitet werden
						
						while(end < infix.length() && (klammernGeoffnet > 0 || infix.charAt(end) == '(' || (infix.charAt(end) != '+' && infix.charAt(end) != '-'))) { //Es wird so lange iteriert bis alle Klammern geschlossen sind und das erste + / - auftaucht
							if(infix.charAt(end) == '(')
								klammernGeoffnet ++;
							else
								if(infix.charAt(end) == ')')
									klammernGeoffnet --;
							end++;
						}
						
						end--;
						
						int anzahlAnOperationen = 0; //Anhand der Anzahl der Operationen im Bereich start - end kann man herleiten, wie viele Zahlen in diesem Bereich sind
						
						for(int k = start; k <= end; k++)
							if(infix.charAt(k) == '+' || infix.charAt(k) == '-' || infix.charAt(k) == '*' || infix.charAt(k) == '/')
								anzahlAnOperationen++;
						
						if(anzahlAnOperationen == 0) { //Nur eine Zahl also wird zuerst die Zahl zu postfix hinzugefügt und dann die Operation
							String zahl = erstesLeerzeichenEntfernen(infix.substring(start, end + 1));
							
							postfix += " " + zahl;
							
							postfix += " " + infix.charAt(i);
						}
						else {
							String stringFuerRekursivenAufruf = infix.substring(start, end + 1);
							
							postfix += " " + infixZuPostfix(stringFuerRekursivenAufruf); //Rekursiver Aufruf des Bereichs, der zuerst zu postfix hinzugefügt werden muss
							
							postfix += " " + infix.charAt(i); //Operation
						}
						infix = infix.substring(0, start) + infix.substring(end + 1, infix.length()); //Entfernen des abgearbeiteten Strings aus infix
					}
					else {
						if(infix.charAt(i) == '*' || infix.charAt(i) == '/') {
							start = i + 1;
							end = start;
							
							if(infix.charAt(start) == ' ')
								start++;
							
							int klammernGeoffnet = 0;
							
							while(end < infix.length() && (Character.isDigit(infix.charAt(end)) || klammernGeoffnet > 0 || infix.charAt(end) == '(' || infix.charAt(end) == ' ')) { //Solange iterieren bis alle Klammern geschlossen wurden und keine weitere Operation auftritt
								if(infix.charAt(end) == '(')
									klammernGeoffnet ++;
								else
									if(infix.charAt(end) == ')')
										klammernGeoffnet --;
								end++;
							}
							
							end--;
							
							if(infix.charAt(start) == '(') {
								String stringFuerRekursivenAufruf = infix.substring(start, end + 1);
								
								postfix += " " + infixZuPostfix(stringFuerRekursivenAufruf);
								
								postfix += " " + infix.charAt(i);
							}
							else {
								String zahl = erstesLeerzeichenEntfernen(infix.substring(start, end + 1));
								
								postfix += " " + zahl;
								
								postfix += " " + infix.charAt(i);
							}
							
							infix = infix.substring(0, start) + infix.substring(end + 1, infix.length());
						}
						else { //Erstes Zeichen ist (
							infix = erstesLeerzeichenEntfernen(infix);
							
							int klammernGeoffnet = 1;
							
							int k = 1;
							
							while(klammernGeoffnet > 0) {
								if(infix.charAt(k) == '(')
									klammernGeoffnet++;
								else
									if(infix.charAt(k) == ')')
										klammernGeoffnet--;
								k++;
							}
							
							k--;
							
							String stringFuerRekursivenAufruf = infix.substring(1, k);
							
							postfix += " " + infixZuPostfix(stringFuerRekursivenAufruf);
							
							infix = infix.substring(k + 1, infix.length());
							
							i = -1; //i wieder auf 0 setzen, da danach 1 addiert wird
						}
					}
				}
			}
			i++;
		}
		return postfix;
	}
}
