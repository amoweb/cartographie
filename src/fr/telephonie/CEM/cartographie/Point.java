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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Objet de repr�sentation d'un point
 * @author Amaury Graillat
 */
public class Point implements Cloneable
{
    private double value;
    private String longitude;
    private String latitude;
    private long timestamp;
    private double altitude;
    
    public int indice;
    
    /**
     * Cr�ation d'un point
     * @param longitude
     * @param latitude
     * @param timestamp
     */
    public Point(String longitude, String latitude, long timestamp)
    {
	this.value = 0;
        this.altitude = 0;
        
	this.longitude = longitude;
	this.latitude = latitude;
	this.setTimestamp(timestamp);
    }
    
    /**
     * Cr�ation d'un point
     * @param longitude
     * @param latitude
     * @param time (ISO 8601)
     * @throws ParseException
     */
    public Point(String longitude, String latitude, String time) throws ParseException
    {
	this.value = 0;
        this.altitude = 0;
        
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = ISO8601Timestamp(time);
    }
    
    /**
     * Cr�ation d'un point
     * @param longitude
     * @param latitude
     * @param time (ISO 8601)
     * @throws ParseException
     */
    public Point(int value, String time) throws ParseException
    {
        this.longitude = "";
        this.latitude = "";
        this.altitude = 0;
        
        this.value = value;
        this.timestamp = ISO8601Timestamp(time);
    }

    /**
     * Cr�ation d'un Point
     */
    public Point()
    {
        this.value = 0;
        this.longitude = "";
        this.latitude = "";
        this.timestamp = 0;
        this.altitude = 0;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }


    public void setValue(double d)
    {
	this.value = d;
    }

    public double getValue()
    {
	return this.value;
    }

    public void setTimestamp(long timestamp2)
    {
	this.timestamp = timestamp2;
    }

    public long getTimestamp()
    {
	return this.timestamp;
    }

    public String getLatitude()
    {
	return this.latitude;
    }

    public String getLongitude()
    {
	return this.longitude;
    }
    
    public void setAltitude(double d)
    {
	this.altitude = d;
    }

    public double getAltitude()
    {
	return altitude;
    }
    
    public Point clone()
    {
	Point p = new Point(this.getLongitude(), this.getLatitude(), this.getTimestamp());
	p.setAltitude(this.getAltitude());
	p.setValue(this.getValue());
	return p;
    }
    
    /**
     * Renvoie les coordonn�es de type 5.068086,45.886459,20.000000
     * @return String
     */
    public String getCoordinates()
    {
	return this.longitude + "," + this.latitude + "," + this.altitude;
    }
    
    /**
     * ISO 8601 en timestamp 
     * @param String heure (ISO 8601) 
     * @return timestamp correspondant � la zone locale
     * @throws ParseException 
     */
    private long ISO8601Timestamp(String time) throws ParseException
    {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	String dateString = time;
	
	// Date UTC (Z)
	if(time.indexOf('Z') != -1)
	{
        	dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        	dateString = time.replace("Z", "");
	}
	
	Date date = dateFormat.parse(dateString);
	return date.getTime();
    }
    
    public String toString()
    {
	return "(" + this.longitude + "," + this.latitude + "," + this.altitude + "):"+this.timestamp+"="+this.value;
    }
}
