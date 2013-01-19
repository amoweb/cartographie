package fr.telephonie.CEM.cartographie;

public class LangEn {
	final static String WIN_TITLE = "Cartographie - amoweb.fr";
	final static String WIN_BROWSE_GPX = "Browse GPX";
	final static String WIN_BROWSE_DATA = "Browse data";
	final static String WIN_FORMULA_TOOLTIP = "Math formula to apply to the data x. Fill this field with x will not affect the data.";
	final static String WIN_FORMULA_LABEL = "Formula : Altitude(x) = ";
	final static String WIN_ALTITUDE_TOOLTIP = "Max altitude to resize the graph. Fill this field with 0 will not affect the data.";
	final static String WIN_ALTITUDE_LABEL = "Max altitude (m) : ";
	final static String WIN_ALTITUDE_LABEL2 = " (0 disable resizing)";
	final static String WIN_CREATE_BTN = "Generate KML";
	final static String WIN_COORDINATES_LABEL = "GPS coordinates :";
	final static String WIN_DATA_LABEL = "Data (Aaronia, Lascar, Narda) :";
	final static String WIN_PROCESS_LABEL = "Data processing :";
	final static String WIN_COLOR_LABEL = "Color :";
	final static String WIN_COLOR_CHECKBOX = "enable threshold coloration";
	final static String WIN_COLOR_RED_LABEL = "Red (value) ";
	final static String WIN_COLOR_ORANGE_LABEL = "Orange (value) ";	
	final static String FILECHOOSER_TITLE = "Open a Aaronia, Envionic (cvs) or Lascar (txt) file.";
	final static String FILECHOOSER_KML_BTN = "Export KML";
	
	final static String MSG_GENERATED = "KML file generated with success !";
	final static String MSG_INVALID_DATE_FORMAT = "Invalid date format.";
	final static String MSG_IO_EXCEPTION = "Error while reading the file ";
	final static String MSG_PARSE_EXCEPTION = "Invalid file contents.";
	final static String MSG_CORRELATION_ERROR = "Error while making the correlation between the data dates and the location dates. The time zone of the computer must be the same as the mobile phone.";
	final static String MSG_NO_ENOUGHT_POINTS = "No enough samples to generate the file (maybe a file isn't reconized ?) ";
	final static String MSG_NULL_ALTITUDE_EXCEPTION = "The max altitude is null, the data file isn't well formatted file.";
	final static String MSG_JDOM_ERROR = "Bad GPX file.";
	final static String MSG_LOCATION_FORMAT_ERROR = "Bad coordinates format.";
}
