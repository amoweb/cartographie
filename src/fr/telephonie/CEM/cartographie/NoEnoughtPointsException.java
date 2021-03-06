/**
 * Copyright 2012 Amaury Graillat
 *
 * This file is part of amowebCartographie.
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

public class NoEnoughtPointsException extends Exception
{
    private static final long serialVersionUID = 1L;
    
    private int nbValPoints;
    private int nbPosPoints;

    public NoEnoughtPointsException(int nbPosPoints, int nbPosVal) {
	this.nbPosPoints = nbPosPoints;
	this.nbValPoints = nbPosVal;
    }
    
    public String getMessage()
    {
	return this.nbPosPoints + " points de positions et " + this.nbValPoints + " points de valeur.";
    }
}
