/**
 * Copyright 2012 Amaury Graillat
 *
 * This file is part of amoweb Math Parser.
 *
 *  amoweb Math Parser is free software: you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation, either version 3 of
 *  the License, or (at your option) any later version.
 *
 *  amoweb Math Parser is distributed in the hope that it will be
 *  useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 *  of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with amoweb Math Parser.  If not, see
 *  <http://www.gnu.org/licenses/>.
 *
 **/
package fr.mathematiques.parseur;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe d'évaluation d'expression arithmétique
 * @author Amaury Graillat
 */
public class Parsing
{
    public Matcher regex(String string, String regex)
    {
        Pattern p = Pattern.compile(regex);
        return p.matcher(string);
    }
    
    
    /**
     * Calcul la valeur d'une expression
     * @return double : résultat
     * @throws MathParsingException 
     * @throws NumberFormatException 
     */
    public double evaluer(String exp) throws NumberFormatException, MathParsingException
    {
	exp = exp.replaceAll("[ ]+", " ");
	return Double.parseDouble(this.calculate(exp.replaceAll("([-]?[0-9.]+)", " $1 "))); 
    }
    
    /**
     * Calcul recurcif
     * @param chaine formatée (espace entre les tokens)
     * @return String chaine représentant le résultat
     */
    private String calculate(String x) throws MathParsingException
    {
		Matcher m;
		
		//System.out.println("#" + x + "#");
		x = x.replaceAll("[ ]+", " ");
		
		// Cas de base : un chiffre
		if(this.regex(x, "^ [-]?[0-9.]+ $").matches())
		    return " " + x + " ";
		
		// Traitement des log
		m = this.regex(x, "^(.*)log ([-]?[0-9.]+)(.*)$");
		if(m.matches())
		    return calculate(m.group(1) +  " "  + String.valueOf(Math.log10(Double.parseDouble(m.group(2)))) +  " "  + m.group(3));
		
		// Traitement des parenthèses en premier
		m = this.regex(x, "^(.*)\\(([^)]*)\\)(.*)$");
		if(m.matches())
		    return calculate(m.group(1) +  " "  + calculate(m.group(2)) +  " "  + m.group(3));
		// Traitement des ^
		m = this.regex(x, "^(.*) ([-]?[0-9.]+) \\^ ([-]?[0-9.]+)(.*)$");
		if(m.matches())
		    return calculate(m.group(1) +  " "  + String.valueOf(Math.pow(Double.parseDouble(m.group(2)),Double.parseDouble(m.group(3)))) +  " "  + m.group(4));
		// Traitement des *
			m = this.regex(x, "^(.*) ([-]?[0-9.]+) \\* ([-]?[0-9.]+)(.*)$");
			if(m.matches())
			    return calculate(m.group(1) +  " "  + String.valueOf(Double.parseDouble(m.group(2))*Double.parseDouble(m.group(3))) +  " "  + m.group(4));
		// Traitement des /
		m = this.regex(x, "^(.*) ([-]?[0-9.]+) \\/ ([-]?[0-9.]+)(.*)$");
		if(m.matches())
		    return calculate(m.group(1) +  " "  + String.valueOf(Double.parseDouble(m.group(2))/Double.parseDouble(m.group(3))) +  " "  + m.group(4));
			
		// Somme
		m = this.regex(x, "^[-0-9.*/+ ]+$");
		if(m.matches())
		{
		    return somme(x);
		}
		
		throw new MathParsingException(x);
    }

    	/**
    	 * Effectue une somme de gauche à droite
    	 * @param String somme formatée (espace entre les tokens)
    	 * @return String
    	 * @throws MathParsingException 
    	 */
	private String somme(String x) throws MathParsingException {
	    x = x.replaceAll("[ ]+", " ");
	    String[] tokens = x.split(" ");
	       
	    double sum = 0;
	    String operateur = "+"; 
	    for(int i=1; i<tokens.length; i++)
	    {
		// Opérateur
		if(tokens[i].matches("^[+-]$"))
		    operateur = tokens[i];
		
		// Nombre
		else if(tokens[i].matches("^[+-]?[0-9.]+$"))
		{
		    if(operateur.contentEquals("+"))
			sum = sum + Double.parseDouble(tokens[i]);
		    else if(operateur.contentEquals("-"))
			sum = sum - Double.parseDouble(tokens[i]);
		    else throw new MathParsingException(x);
		}
	    }
		
	    return String.valueOf(sum);
	}
}
