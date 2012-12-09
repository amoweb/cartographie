/**
 * Copyright 2012 Amaury Graillat
 *
 * This file is part of Foobar.
 *
 *  amoweb Cartographie is free software: you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  amoweb Cartographie is distributed in the hope that it will be
 *  useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 *  of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with amoweb Cartographie.  If not, see 
 *  <http://www.gnu.org/licenses/>.
**/

package fr.telephonie.CEM.cartographie;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.jdom.JDOMException;

import fr.mathematiques.parseur.MathParsingException;
import fr.mathematiques.parseur.Parsing;

public class Generateur
{
    private String destKML;
    private String GPXFile;
    private int altitudeMax;
    
    private List<Point> lpos;
    private List<Point> lval;
    private List<Point> lfinale;
    private String formule;

    /**
     * Création d'un générateur
     * @param destKML
     * @param GPXFile
     * @param altitudeMax
     * @throws ParseException 
     * @throws IOException 
     * @throws JDOMException 
     */
    public Generateur(String destKML, String GPXFile, int altitudeMax, String formule)
	    throws JDOMException, IOException, ParseException
    {
	this.destKML = destKML;
	this.GPXFile = GPXFile;
	this.altitudeMax = altitudeMax;
	this.formule = formule;
	
	if(!this.GPXFile.contentEquals(""))
	    this.lpos = Tools.loadGPX(this.GPXFile);
    }

    
    /**
     * Fusionne les listes de points : données + positions
     * @param lval liste des points données
     * @return liste finale de points complets (données + position)
     * @throws NoEnoughtPointsException 
     * @throws CorrelationErrorException 
     * @throws MathParsingException 
     * @throws NumberFormatException 
     * @throws NullAltitudeException 
     */
    private List<Point> preparePoints(List<Point> lpos, List<Point> lval) throws NoEnoughtPointsException, CorrelationErrorException, NumberFormatException, MathParsingException, NullAltitudeException
    {	
	List<Point> finale = null;
	
	if(lval == null)
	{
	    finale = lpos;
	}
	else
	{
        	if(lpos.size() < 2 || lval.size() < 2)
        		throw new NoEnoughtPointsException(lpos.size(), lval.size());
        	
        	finale = Tools.mergeLists(lpos, lval);
	}
	
	// Aucune modification n'est effectuée sur les données
	if(this.formule.contentEquals("x") && this.altitudeMax == 0)
	{
	    for(Point p : finale)
	    {
		p.setAltitude(p.getValue());
	    }
	}
	// Modifications nécessaires
	else
	{
        	// Calcul l'altitude en fonction de la valeur
        	if(!this.formule.contentEquals("x"))
        	{
        		for(Point p : finale)
        		{
        		    	String exp = this.formule.replace("x", String.valueOf(p.getValue()));
        			p.setAltitude((int) new Parsing().evaluer(exp));
        			System.out.println(p.getValue() + " => " + p.getAltitude());
        		}
        	}
        	
        	// On modifie l'altitude
        	if(this.altitudeMax != 0)
        	{
        		double max = Tools.maxValueOf(finale);
        		if(max == 0)
        			throw new NullAltitudeException();
        		
        		for(Point p : finale)
        		{
        		    // L'altitude a déjà été modifiée par la formule
        		    if(this.formule.contentEquals("x"))
        		        p.setAltitude((int)((float)p.getValue()*((float)this.altitudeMax/(float)max)));
        		    else
        		        p.setAltitude((int)((float)p.getAltitude()*((float)this.altitudeMax/(float)max)));
        		}
        	}
	}
	
	// Calcul le début des points utiles
	int iStart;
	for(iStart=0; iStart < finale.size(); iStart++)
	{
		if(!finale.get(iStart).getLatitude().contentEquals("") && !finale.get(iStart).getLongitude().contentEquals("") && finale.get(iStart).getValue() != 0)
		break;
	}
	
	// Calcul la fin des points utiles
	int iEnd;
	for(iEnd=finale.size()-1; iEnd > 0; iEnd--)
	{
		if(!finale.get(iEnd).getLatitude().contentEquals("") && !finale.get(iEnd).getLongitude().contentEquals("") && finale.get(iEnd).getValue() != 0)
		break;
	}
	
	// Ne garde que les Point utiles
	this.lfinale = new LinkedList<Point>();
	int i;
	for(i=iStart; i<iEnd; i++)
	{
	    this.lfinale.add(finale.get(i));
	}
	
	return lfinale;
    }
    
    /**
     * Génère le KML à partir d'un fichier tension
     * @param fichier de donnée (Aaronia ou Lascar)
     * @throws ParseException 
     * @throws IOException 
     * @throws NumberFormatException 
     * @throws CorrelationErrorException 
     * @throws NoEnoughtPointsException 
     * @throws NullAltitudeException 
     * @throws MathParsingException 
     * @throws LocationFormatException 
     * @throws SemanticCheckException 
     * @throws SyntacticCheckException 
     * @throws EvaluationCheckException 
     */
    public void generateKMLfromData(String dataFile)
    	throws NumberFormatException, IOException, ParseException, CorrelationErrorException,
    	NoEnoughtPointsException, NullAltitudeException, MathParsingException, LocationFormatException
    {
	List<Point> lfinale = null;
	
	if(Tools.filetype(dataFile).contentEquals("narda"))
	{
	    lfinale = this.preparePoints(Tools.loadNarda(dataFile), null);
	}
	else
	{
	    lfinale = this.preparePoints(this.lpos, Tools.loadData(dataFile));	
	}
	
	Tools.generateKML(lfinale, this.destKML);
    }

    /**
     * Génère le KML à partir d'un fichier tension
     * @param file (Aaronia ou Lascar)
     * @throws ParseException 
     * @throws IOException 
     * @throws NullAltitudeException 
     * @throws MathParsingException 
     * @throws CorrelationErrorException 
     * @throws NoEnoughtPointsException 
     * @throws NumberFormatException 
     * @throws ParseException 
     * @throws IOException 
     * @throws NumberFormatException 
     * @throws CorrelationErrorException 
     * @throws NoEnoughtPointsException 
     * @throws NullAltitudeException 
     * @throws MathParsingException 
     * @throws SemanticCheckException 
     * @throws SyntacticCheckException 
     * @throws EvaluationCheckException 
     */
    public void generateKMLfromData(String dataFile, String mediumValue, String highValue) throws NumberFormatException, NoEnoughtPointsException, CorrelationErrorException, MathParsingException, NullAltitudeException, IOException, ParseException
    {
    	double mediumValueDouble = Double.parseDouble(mediumValue);
    	double highValueDouble = Double.parseDouble(highValue);

    	List<Point> lfinale = this.preparePoints(this.lpos, Tools.loadData(dataFile));	
    	Tools.generateKML(lfinale, this.destKML, mediumValueDouble, highValueDouble);
    }
}
