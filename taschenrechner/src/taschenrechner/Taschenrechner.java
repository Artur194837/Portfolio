package taschenrechner;

import java.util.Stack;
import java.util.StringTokenizer;

public class Taschenrechner {
	private InfixZuPostfixUmwandlerI infixZuPostfixUmwandler = new InfixZuPostfixUmwandler();
	
	public double berechne(String infix) {
		String postfix = infixZuPostfixUmwandler.infixZuPostfix(infix);
		
		StringTokenizer tokenizer = new StringTokenizer(postfix);
		
		Stack<Double> stack = new Stack<Double>();
		
		while(tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			
			if(token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/"))
				switch(token) {
				case "+":
					double zahlZwei = stack.pop();
					double zahlEins = stack.pop();
					
					stack.push(zahlEins + zahlZwei);
					break;
				case "-":
					zahlZwei = stack.pop();
					zahlEins = stack.pop();
					
					stack.push(zahlEins - zahlZwei);
					break;
				case "*":
					zahlZwei = stack.pop();
					zahlEins = stack.pop();
					
					stack.push(zahlEins * zahlZwei);
					break;
				case "/":
					zahlZwei = stack.pop();
					zahlEins = stack.pop();
					
					stack.push(zahlEins / zahlZwei);
					break;
				}
			else
				stack.push(Double.parseDouble(token));
		}
		
		return stack.pop();
	}
}
