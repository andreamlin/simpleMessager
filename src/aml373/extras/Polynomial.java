package aml373.extras;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
	//regex pattern for any single term with an 'x'
	Pattern xTerm = Pattern.compile("[-+]*\\s*(\\d)+x(?:\\^(\\d+))*");
	//regex pattern for any constant
	Pattern constantTerm = Pattern.compile("(\\d+)");

	//maps exponents to coefficients
	Map<Integer, Integer> terms = new TreeMap<>(new Comparator<Integer>(){
		//Descending order
		@Override
		public int compare(Integer o1, Integer o2) {
			return o2 - o1;
		}
	});
	
	private void addTerm(int coef, int exp) {
		if (coef == 0) return;
		if (terms.containsKey(exp)) {
			int newCoef =  terms.get(exp) + coef;
			if (newCoef == 0){
				terms.remove(exp);
			} else {
				terms.put(exp, newCoef);
			}
		} else {
			terms.put(exp, coef);
		}
	}
	
	private void parseSingleTerm(String term){
		int sign = term.contains("-") ? -1 : 1;
		if (!term.contains("x")){
			Matcher m = constantTerm.matcher(term);
			if (!m.find()){
				return;
			}			
			int coef = Integer.parseInt(m.group(1));
			terms.put(0, sign * coef);
		} else {
			Matcher m = xTerm.matcher(term);
			if (!m.find()){
				return;
			}			
			int coef = Integer.parseInt(m.group(1));
			int exp = m.group(2) == null ? 1 : Integer.parseInt(m.group(2));
			terms.put(exp, sign * coef);
		}
	}
	
	/**
	 * Create a Polynomial from a string representation.
	 * @param expr
	 */
	public Polynomial(String expr) {		
		expr = expr.trim();
		int prevToken = 0;
		int nextToken; 
		while (prevToken >= 0){
			int plusIndex = expr.indexOf("+", prevToken + 1);
			int minusIndex = expr.indexOf("-", prevToken + 1);
			if (plusIndex == -1) {
				if (minusIndex == -1) break;
				else nextToken = minusIndex;
			} else if (minusIndex == -1) {
				nextToken = plusIndex;
			} else {
				nextToken = Math.min(plusIndex, minusIndex);
			}

			String term = expr.substring(prevToken, nextToken);
			parseSingleTerm(term);
			prevToken = nextToken;
		}
		
		parseSingleTerm(expr.substring(prevToken));

	}
	
	private Polynomial() {}
	
	/**
	 * Deep-copy constructor.
	 */
	public Polynomial(Polynomial polynomial) {
		Polynomial newPoly = new Polynomial();
		newPoly.terms = new TreeMap<>(newPoly.terms);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Entry<Integer, Integer> term : terms.entrySet()){
			int coef = term.getValue();
			int exp = term.getKey();
			String xTerm = exp == 0 ? "" : (exp == 1) ? "x" : "x^" + exp;
			String sign = coef < 0 ? " - " : " + ";
			String coefString = Math.abs(coef) == 1 ? "" : Integer.toString(Math.abs(coef));
			sb.append(sign + coefString + xTerm);
		}
		
		String tempString = sb.toString();
		if (tempString.startsWith(" + ")) {
			//take off leading plus signs
			tempString = tempString.substring(" + ".length()).trim();
		} else {
			tempString = tempString.trim();
		}
		if (tempString.equals("")) return "0";
		else return tempString;
	}
	
	/**
	 * @return new polynomial that represents the derivate of this one
	 */
	public Polynomial differentiate() {
		Polynomial dx = new Polynomial();
		for (Integer exp : this.terms.keySet()){
			if (exp == 0) continue;
			int newCoef = exp * this.terms.get(exp);
			dx.addTerm(newCoef, exp-1);
		}
		return dx;
	}
	
	//Util method for returning differential of one string representing polynomial
	public static String differentiate(String s){
		return (new Polynomial(s)).differentiate().toString();
	}
	
	//examples
	public static void main(String[] args){
		String[] params = {"3x^2 - 4x + 5", "0", "-5x - 6x^3"};
		for (String s : params) {
			System.out.println(differentiate(s));
		}
	}
	
	
}
