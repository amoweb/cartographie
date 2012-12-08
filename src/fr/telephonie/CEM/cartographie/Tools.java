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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;



public class Tools {
	/**
	 * Conversion d'un fichier lascar en CVS compatible Aaronia
	 * @param sc
	 * @param dst
	 */
	public static void lascar2aaronia(String sc, String dst) throws IOException
	{
		FileReader fr = new FileReader(sc);
		BufferedReader brIn = new BufferedReader(fr);
		FileWriter fw = new FileWriter(dst);
		BufferedWriter brOut = new BufferedWriter(fw);
		
		brIn.readLine();
		brOut.write("Timestamp;Measure Unit;500\r\n");
		
		String ligneEntree = brIn.readLine();
		while(ligneEntree != null)
		{
			brOut.write(Tools.ligneLascar2aaronia(ligneEntree)+"\r\n");
			ligneEntree = brIn.readLine();
		}
	}
	
	/**
	 * Conversion d'une ligne d'un fichier Lascar en ligne d'un fichier Aaronia
	 * @param lascar
	 * @return String ligne Aaronia
	 */
	private static String ligneLascar2aaronia(String lascar)
	{
		String aaronia = null;
	
		// 1,25/11/2011 14:30:50,5,100,000023510
		Pattern p = Pattern.compile("^([0-9]+),([0-9]{2})/([0-9]{2})/([0-9]{4}) ([0-9]{2}):([0-9]{2}):([0-9]{2}),([0-9]+)(.*)");
		Matcher m = p.matcher(lascar);

		// 2011-11-25T14:30:50.000;9;5
		if(m.matches()) {
			aaronia = m.group(4)+"-"+m.group(3)+"-"+m.group(2)+"T"+m.group(5)+":"+m.group(6)+":"+m.group(7)+".000;9;"+m.group(8);
		}
		
		return aaronia;
	}
	
	/**
	 * Chargement d'un fichier GPX
	 * @param file
	 * @return List<Point>
	 * @throws IOException 
	 * @throws JDOMException 
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	public static List<Point> loadGPX(String file) throws JDOMException, IOException, ParseException
	{
	    org.jdom.Document document;
	    List<Point> l = new LinkedList<Point>();
	    
	    SAXBuilder sxb = new SAXBuilder();
	    document = sxb.build(new File(file));
	    Element racine = document.getRootElement();
	    Element trk = (Element) racine.getChildren().get(2);
	    Element trkseg = (Element) trk.getChildren().get(0);
	    
	    // Parcourt des checkpoints
	    Iterator<Element> i = trkseg.getChildren().iterator();
	    while(i.hasNext())
	    {
		Element trkpt = i.next();
		String time = null;
		for(int j=0; j<trkpt.getChildren().size(); j++)
		{
		    if(((Element) trkpt.getChildren().get(j)).getName() == "time")
			time = ((Element) trkpt.getChildren().get(j)).getValue();
		}
		if(time != null)
		{
		    l.add(new Point(trkpt.getAttributeValue("lon"), trkpt.getAttributeValue("lat"), time));
		}
	    }
	    
	    return l;
	}
	
	/**
	 * Procédure de création d'un fichier KML
	 * @param List<Point>
	 * @param String fichier
	 * @return false en cas d'erreur
	 * @throws IOException 
	 */
	public static boolean generateKML(List<Point> points, String file) throws IOException
	{
	    int i;
	    FileWriter f = new FileWriter(file);
	    BufferedWriter bw = new BufferedWriter(f);
	    
	    bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
		    + "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\r\n"
		    + "<Document>\r\n"
		    + "	<name>Carthographie CEM</name>\r\n"
		    + "	<description>http://amoweb.fr</description>\r\n");
		    
	    bw.write("	<Style id=\"lineBlue\">\r\n"
	    + "		<LineStyle>\r\n"
	    + "			<color>ffff0000</color>\r\n"
	    + "			</LineStyle>\r\n"
	    + "			<PolyStyle>\r\n"
	    + "			<color>7fff0000</color>\r\n"
	    + "		</PolyStyle>\r\n"
	    + "	</Style>\r\n");

	    bw.write("	<Folder>\r\n"
    		    + "		<name>Trajet</name>\r\n"
    		    + "		<visibility>1</visibility>\r\n"
    		    + "		<open>1</open>\r\n"
    		    + "		<Placemark>\r\n"
    		    + "			<name>GPS Elevation Profile</name>\r\n"
    		    + "			<styleUrl>#lineBlue</styleUrl>\r\n");
    	    bw.write("<LineString>\r\n");
    	    bw.write("<extrude>1</extrude>\r\n");
    	    bw.write("<tessellate>1</tessellate>\r\n");
    	    bw.write("<altitudeMode>relativeToGround</altitudeMode>\r\n");
    	    bw.write("<coordinates>\r\n");
    	    
    	    for(Point p : points)
    	    {
    		bw.write(p.getCoordinates()+"\r\n");
    	    }
    	    
    	    bw.write("</coordinates>");
    	    bw.write("</LineString>");
    	    
    	    bw.write("</Placemark>");
    	    bw.write("</Folder>");
	    
	    // Waypoints
	    bw.write("	<Folder>\r\n"
		    + "		<name>GPS Waypoints</name>\r\n"
		    + "		<visibility>0</visibility>\r\n"
		    + "		\r\n");
	    
	    i = 0;
	    for(Point p : points)
	    {
		i++;
		bw.write("		<Placemark>\r\n"
        	    + "			<visibility>0</visibility>\r\n"
        	    + "			<name>" + i + "</name>\r\n"
        	    + "			<description>"+p.getValue()+"</description>\r\n"
        	    + "			<styleUrl>#dataPoint</styleUrl>\r\n"
        	    + "			<Point>\r\n"
        	    + "				<coordinates>"+p.getCoordinates()+"</coordinates>\r\n"
        	    + "				<altitudeMode>relativeToGround</altitudeMode>\r\n"
        	    + "			</Point>\r\n"
        	    + "		</Placemark>\r\n");
	    }
	    
	    bw.write("	</Folder>\r\n"
	    + "</Document>\r\n"
	    + "</kml>");
	    
	    bw.flush();
	    
	    f.close();
	    return true;
	}
	
	/**
	 * Procédure de création d'un fichier KML
	 * @param mediumValueDouble
	 * @param highValueDouble
	 * @param List<Point>
	 * @param String fichier
	 * @return false en cas d'erreur
	 * @throws IOException 
	 */
	public static boolean generateKML(List<Point> points, String file, double mediumValueDouble, double highValueDouble) throws IOException
	{
	    int i;
	    FileWriter f = new FileWriter(file);
	    BufferedWriter bw = new BufferedWriter(f);
	    
	    bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
		    + "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\r\n"
		    + "<Document>\r\n"
		    + "	<name>Carthographie CEM</name>\r\n"
		    + "	<description>http://amoweb.fr</description>\r\n");
		    
	    bw.write("	<Style id=\"lineGreen\">\r\n"
        	    + "		<LineStyle>\r\n"
        	    + "			<color>ff00ff00</color>\r\n"
        	    + "			</LineStyle>\r\n"
        	    + "			<PolyStyle>\r\n"
        	    + "			<color>7f00ff00</color>\r\n"
        	    + "		</PolyStyle>\r\n"
        	    + "	</Style>\r\n"
        	    + "	<Style id=\"lineRed\">\r\n"
        	    + "		<LineStyle>\r\n"
        	    + "			<color>ff0000ff</color>\r\n"
        	    + "			</LineStyle>\r\n"
        	    + "			<PolyStyle>\r\n"
        	    + "			<color>7f0000ff</color>\r\n"
        	    + "		</PolyStyle>\r\n"
        	    + "	</Style>\r\n"
        	    + "	<Style id=\"lineOrange\">\r\n"
        	    + "		<LineStyle>\r\n"
        	    + "			<color>ff00ffff</color>\r\n"
        	    + "			</LineStyle>\r\n"
        	    + "			<PolyStyle>\r\n"
        	    + "			<color>7f00ffff</color>\r\n"
        	    + "		</PolyStyle>\r\n"
        	    + "	</Style>\r\n");
	    
	    bw.write("<Folder>\r\n"
   		    + "	<name>Trajet</name>\r\n"
   		    + "	<visibility>1</visibility>\r\n"
   		    + "	<open>1</open>\r\n");
	    
	    i = 0;
	    String currentLineColor;
	    String previousLineColor;
	    
	    if(points.get(i).getValue() < mediumValueDouble)
		currentLineColor = "#lineGreen";
	    else if(points.get(i).getValue() >= highValueDouble && points.get(i).getValue() < highValueDouble)
		currentLineColor = "#lineOrange";
	    else
		currentLineColor = "#lineRed";
	    
	    previousLineColor = currentLineColor;
	    
	    bw.write("	<Placemark>\r\n"
   		    + "		<name>GPS Elevation Profile</name>\r\n"
   		    + "		<styleUrl>" + currentLineColor + "</styleUrl>\r\n");
   	    bw.write("		<LineString>\r\n");
   	    bw.write("			<extrude>1</extrude>\r\n");
   	    bw.write("			<tessellate>1</tessellate>\r\n");
   	    bw.write("			<altitudeMode>relativeToGround</altitudeMode>\r\n");
   	    bw.write("			<coordinates>\r\n");
   	    
   	    bw.write(points.get(i).getCoordinates()+"\r\n");
   	    
   	    while(i < points.size())
   	    { 		
   		if(points.get(i).getValue() < mediumValueDouble)
   		    currentLineColor = "#lineGreen";
   		else if(points.get(i).getValue() >= mediumValueDouble && points.get(i).getValue() < highValueDouble)
   		    currentLineColor = "#lineOrange";
   		else
   		    currentLineColor = "#lineRed";
   		
   		// Changement de couleur
   		if(!currentLineColor.contentEquals(previousLineColor))
   		{
   		    // Ajout d'un point de lien
   		    Point lien = points.get(i).clone();
   		    lien.setAltitude(points.get(i-1).getAltitude());
   		    lien.setValue(points.get(i-1).getValue());
   		    bw.write(lien.getCoordinates()+"\r\n");
   		    
   		    // Fermeture de la couleur
   		    bw.write("			</coordinates>");
   	   	    bw.write("		</LineString>");
   	   	    bw.write("	</Placemark>");
   	   	    
   	   	    // Nouvelle couleur
   	   	    bw.write("	<Placemark>\r\n"
   	   		    + "		<name>GPS Elevation Profile</name>\r\n"
   	   		    + "		<styleUrl>" + currentLineColor + "</styleUrl>\r\n");
   	   	    bw.write("		<LineString>\r\n");
   	   	    bw.write("			<extrude>1</extrude>\r\n");
   	   	    bw.write("			<tessellate>1</tessellate>\r\n");
   	   	    bw.write("			<altitudeMode>relativeToGround</altitudeMode>\r\n");
   	   	    bw.write("			<coordinates>\r\n");
   		}

   		bw.write(points.get(i).getCoordinates()+"\r\n");
   		
   		previousLineColor = currentLineColor;
   		i++;
   	    }
   	    
   	    bw.write("			</coordinates>");
   	    bw.write("		</LineString>");
   	    bw.write("	</Placemark>");
   	    
   	    
   	    // Terminaison des lignes
   	    bw.write("</Folder>");
	    
	    // Waypoints
	    bw.write("	<Folder>\r\n"
		    + "		<name>GPS Waypoints</name>\r\n"
		    + "		<visibility>0</visibility>\r\n"
		    + "		\r\n");
	    
	    int id = 0;
	    for(Point p : points)
	    {
		id++;
		bw.write("		<Placemark>\r\n"
       	    + "			<visibility>0</visibility>\r\n"
       	    + "			<name>" + id + "</name>\r\n"
       	    + "			<description>"+p.getValue()+"</description>\r\n"
       	    + "			<styleUrl>#dataPoint</styleUrl>\r\n"
       	    + "			<Point>\r\n"
       	    + "				<coordinates>"+p.getCoordinates()+"</coordinates>\r\n"
       	    + "				<altitudeMode>relativeToGround</altitudeMode>\r\n"
       	    + "			</Point>\r\n"
       	    + "		</Placemark>\r\n");
	    }
	    
	    bw.write("	</Folder>\r\n"
	    + "</Document>\r\n"
	    + "</kml>");
	    
	    bw.flush();
	    
	    f.close();
	    return true;
	}

	/**
	 * Chargement d'un fichier Aaronia
	 * @param string
	 * @return List<Point>
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	public static List<Point> loadData(String fichier) throws IOException, NumberFormatException, ParseException
	{
	    List<Point> l = new LinkedList<Point>();
	    FileReader f = new FileReader(fichier);
	    BufferedReader br = new BufferedReader(f);
	    
	    String ligne = br.readLine();
	    while(ligne != null)
	    {
		// Lascar
		if(fichier.endsWith(".txt"))
		    ligne = Tools.ligneLascar2aaronia(ligne);
		
		// Ligne lisible
		if(ligne != null)
		{
        		Pattern p = Pattern.compile("^([0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]+);[0-9]+;([0-9]+)$");
        		Matcher m = p.matcher(ligne);
        
        		// 2011-11-25T14:30:50.000;9;5
        		if(m.matches()) {
        			l.add(new Point(Integer.parseInt(m.group(2)), m.group(1)));
        		}
		}
    
    		ligne = br.readLine();
	    }
	    
	    f.close();
	    return l;
	}

	/**
	 * Fonction qui renvoie le Point de la liste le plus près d'un timestamp
	 * @param List<Point>
	 * @param long timestamp
	 * @param start id
	 * @return Point
	 */
	private static Point nearestPoint(List<Point> l, long timestamp, int start)
	{    
	    int i = start;
	    while(i < l.size() && l.get(i).getTimestamp() < timestamp)
	    {
		i++;
	    }
	    
	    if(i == l.size())
		return null;
	    
	    if(i+1 >= l.size())
	    {
		l.get(i).indice = i;
		return l.get(i);
	    }
		
	    long deltaI = Math.abs(l.get(i).getTimestamp() - timestamp);
	    long deltaIapres = Math.abs(l.get(i+1).getTimestamp() - timestamp);

	    if(deltaI <= deltaIapres)
	    {
		l.get(i).indice = i;
		return l.get(i);
	    }
	    else
	    {
		l.get(i+1).indice = i+1;
		return l.get(i+1);
	    }
	}
	
	/**
	 * Place les valeurs de la première liste dans la seconde
	 * @param points contenant les positions
	 * @param points contenants les valeurs
	 * @return 
	 * @throws FreqMesuresDifferentesException
	 * @throws CorrelationErrorException 
	 */
	public static List<Point> mergeLists(List<Point> loc, List<Point> val) throws CorrelationErrorException
	{
	    long deltaLoc = loc.get(1).getTimestamp() - loc.get(0).getTimestamp();
	    long deltaVal = val.get(1).getTimestamp() - val.get(0).getTimestamp();
	   
	    // On se cale sur la plus grande granularite de mesure
	    // Complete les Point emplacements
	    if(deltaLoc >= deltaVal)
	    {
		Point p = new Point();
		p.indice = 0;
		for(Point pLoc : loc)
		{
		    p = Tools.nearestPoint(val, pLoc.getTimestamp(), p.indice);
		    if(Math.abs(pLoc.getTimestamp()-p.getTimestamp()) <= deltaLoc)
		    {
			pLoc.setValue(p.getValue());
		    }
		}
		
		return loc;
	    }
	    // Complète les Point valeurs 
	    else
	    {
		for(Point pVal : val)
		{
		    Point p = Tools.nearestPoint(loc, pVal.getTimestamp(), 0);
		    if(p != null)
		    {
			if(Math.abs(pVal.getTimestamp()-p.getTimestamp()) <= deltaVal)
			{
			    pVal.setLongitude(p.getLongitude());
			    pVal.setLatitude(p.getLatitude());
			}
		    }
		}
		
		return val;
	    }
	}
	
	/**
	 * Max value
	 * @param List<Point>
	 * @return int
	 */
	public static double maxValueOf(List<Point> val)
	{
	    if(val == null)
		return 0;
	    
	    double maxValue = 0;
	    for(Point p : val)
	    {
		if(p.getValue() > maxValue)
		    maxValue = p.getValue();
	    }
	    
	    return maxValue;
	}
	
	/**
	 * Détecte le type de fichier
	 * @return String
	 * @throws IOException 
	 */
	public static String filetype(String dataFile) throws IOException
	{
	    if(dataFile.toLowerCase().endsWith(".txt"))
	    {
		return "lascar";
	    }
	    else if(dataFile.toLowerCase().endsWith(".csv"))
	    {
		FileReader fr = new FileReader(dataFile);
		BufferedReader br = new BufferedReader(fr);
		if(br.readLine().startsWith("Begin;"))
		{
		    return "narda";
		}
		else
		{
		    return "aaronia";
		}
	    }
	    return "";
	}

	/**
	 * Charge le contenu du fichier Narda (coordonnées et tensions)
	 * @param dataFile
	 * @return
	 * @throws IOException 
	 * @throws LocationFormatException 
	 */
	public static List<Point> loadNarda(String dataFile) throws IOException, LocationFormatException {
	    List<Point> l = new LinkedList<Point>();
	    FileReader f = new FileReader(dataFile);
	    BufferedReader br = new BufferedReader(f);

	    String ligne = br.readLine();
	    
	    Point point = new Point(); 
	    
	    while(ligne != null)
	    {
		
		if(ligne.startsWith("Latitude;"))
		{
		    point.setLatitude(Tools.GPSNotation(ligne.split(";")[1]));
		}
		else if(ligne.startsWith("Longitude;"))
		{
		    point.setLongitude(Tools.GPSNotation(ligne.split(";")[1]));
		}
		else if(ligne.startsWith("Total Value[V/m];"))
		{
		    point.setValue(Double.parseDouble(ligne.split(";")[5]));
		    l.add(point.clone());
		}
		
		ligne = br.readLine();
	    }
	    
	    return l;
	}

	/**
	 * Converti la coordonnée en notation standard
	 * @param string
	 * @return
	 * @throws LocationFormatException 
	 */
	private static String GPSNotation(String coordonnee) throws LocationFormatException {
	    Pattern p = Pattern.compile("^([0-9.]+)°([0-9]+)'([0-9.]+)'' [NE](.*)");
	    Matcher m = p.matcher(coordonnee);
	
	    if(m.matches())
	    {
		return new Double(Double.parseDouble(m.group(1)) + Double.parseDouble(m.group(2))/60.0 + Double.parseDouble(m.group(3))/3600.0).toString();
	    }
	    else
	    {
		throw new LocationFormatException(coordonnee);
	    }
	}
}

