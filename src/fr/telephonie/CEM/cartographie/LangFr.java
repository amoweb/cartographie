package fr.telephonie.CEM.cartographie;

public class LangFr {
	final static String WIN_TITLE = "Cartographie - amoweb.fr";
	final static String WIN_BROWSE_GPX = "Parcourir GPX";
	final static String WIN_BROWSE_DATA = "Parcourir données";
	final static String WIN_FORMULA_TOOLTIP = "Spécifier une formule à appliquer à la donnée x. Laissez x pour ne rien changer.";
	final static String WIN_FORMULA_LABEL = "Formule : Altitude(x) = ";
	final static String WIN_ALTITUDE_TOOLTIP = "Altitude maximal de la représentation des données. Indiquer 0 pour utiliser les unités comme des mètres sans modification.";
	final static String WIN_ALTITUDE_LABEL = "Altitude maximale (m) : ";
	final static String WIN_ALTITUDE_LABEL2 = " (0 désactive l'adaptation)";
	final static String WIN_CREATE_BTN = "Créer KML";
	final static String WIN_COORDINATES_LABEL = "Coordonnées GPS :";
	final static String WIN_DATA_LABEL = "Données à cartographier (Aaronia, Lascar, Narda) :";
	final static String WIN_PROCESS_LABEL = "Traitement des données :";
	final static String WIN_COLOR_LABEL = "Coloration :";
	final static String WIN_COLOR_CHECKBOX = "activer la coloration par niveau";
	final static String WIN_COLOR_RED_LABEL = "Rouge (valeur) ";
	final static String WIN_COLOR_ORANGE_LABEL = "Orange (valeur) ";	
	final static String FILECHOOSER_TITLE = "Ouvrir fichier Aaronia, Envionic (cvs) ou Lascar (txt)";
	final static String FILECHOOSER_KML_BTN = "Enregistrer KML";
	
	final static String MSG_GENERATED = "Fichier KML généré : tout va bien !";
	final static String MSG_INVALID_DATE_FORMAT = "Format de date invalide.";
	final static String MSG_IO_EXCEPTION = "Erreur lors de la lecture du fichier ";
	final static String MSG_PARSE_EXCEPTION = "Contenu d'un fichier invalide.";
	final static String MSG_CORRELATION_ERROR = "Impossible de corréler les heures des données avec les heures des positions. Vérifiez que l'appareil qui a généré les fichiers de données dispose du même fuseau horaire que cet ordinateur.";
	final static String MSG_NO_ENOUGHT_POINTS = "Pas assez d'échantillon pour générer un fichier (celà peut être dû au format du fichier qui est mal reconnu) : ";
	final static String MSG_NULL_ALTITUDE_EXCEPTION = "Altitude maximale nulle, problème de format du fichier de données ?";
	final static String MSG_JDOM_ERROR = "Mauvais format de fichier GPX.";
	final static String MSG_LOCATION_FORMAT_ERROR = "Problème dans la notation des coordonnées.";
}
