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

public class MathParsingException extends Exception
{
    private static final long serialVersionUID = -4799320521550777608L;

    public MathParsingException(String x)
    {
    	super("Erreur de parsing de " + x + ".");
    }
}
