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

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import org.jdom.JDOMException;

import fr.mathematiques.parseur.MathParsingException;
 

public class Fenetre extends JFrame implements ActionListener
{
    private static final long serialVersionUID = -8081654324471232419L;
    
    private JButton btnConvertir;
    private JPanel panelPrincipal;
    private JTextField editGPX;
    private JButton parcourirGPX;
    
    JFileChooser fileChooser;
    private JTextField editAltitudeMax;
    private JPanel panelGPX;
    private JPanel panelAltitudeMax;
    private JPanel panelFormule;
    private JTextField editFormule;
    
    private JPanel panelDonnees;
    private JTextField editTension;
    private JButton parcourirTension;
    private JLabel copyright;

    private JCheckBox cbColoration;
    private JPanel panelMediumColor;
    private JPanel panelHighColor;
    private JTextField editHighValue;
    private JTextField editMediumValue;
	

    public Fenetre()
    {
	super("Cartographie - amoweb.fr");
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.fileChooser = new JFileChooser();
	
	this.panelPrincipal = new JPanel();
	
	this.panelGPX = new JPanel();
	this.panelGPX.setLayout(new BorderLayout());
	this.editGPX = new JTextField("", 22);
	this.parcourirGPX = new JButton("Parcourir GPX");
	this.panelGPX.add(this.parcourirGPX, BorderLayout.EAST);
	this.panelGPX.add(this.editGPX, BorderLayout.CENTER);
	this.parcourirGPX.addActionListener(this);
	
	this.panelDonnees = new JPanel();
	this.panelDonnees.setLayout(new BorderLayout());
	this.editTension = new JTextField("", 30);
	this.parcourirTension = new JButton("Parcourir données");
	this.panelDonnees.add(this.parcourirTension, BorderLayout.EAST);
	this.panelDonnees.add(this.editTension, BorderLayout.CENTER);
	this.parcourirTension.addActionListener(this);

	this.panelFormule = new JPanel();
	this.panelFormule.setLayout(new BorderLayout());
	this.editFormule = new JTextField("x", 5);
	this.editFormule.setToolTipText("Spécifier une formule à appliquer à la donnée x. Laissez x pour ne rien changer.");
	this.panelFormule.add(new JLabel("Formule : Altitude(x) = "), BorderLayout.WEST);
	this.panelFormule.add(this.editFormule, BorderLayout.CENTER);
	
	this.panelAltitudeMax = new JPanel();
	this.panelAltitudeMax.setLayout(new BorderLayout());
	this.editAltitudeMax = new JTextField("100", 6);
	this.editAltitudeMax.setToolTipText("Altitude maximal de la représentation des données. Indiquer 0 pour utiliser les unit�s comme des m�tre sans modification.");
	this.panelAltitudeMax.add(new JLabel("Altitude maximale (m) : "), BorderLayout.WEST);
	this.panelAltitudeMax.add(this.editAltitudeMax, BorderLayout.CENTER);
	this.panelAltitudeMax.add(new JLabel(" (0 désactive l'adaptation)"), BorderLayout.EAST);
	
	this.btnConvertir = new JButton("Créer KML");
	this.btnConvertir.addActionListener(this);
	
	this.copyright = new JLabel("Copyright 2012, Amaury Graillat");
	this.copyright.setSize(600, 10);

	GridLayout gridLayout = new GridLayout(0,1);
	gridLayout.setVgap(7);
	this.panelPrincipal.setLayout(gridLayout);
	this.panelPrincipal.add(new JLabel("GNU General Public License."));
	this.panelPrincipal.add(new JLabel("Coordonnées GPS :"));
	this.panelPrincipal.add(this.panelGPX);
	this.panelPrincipal.add(new JSeparator());
	
	this.panelPrincipal.add(new JLabel("Données à cartographier (Aaronia, Lascar, Narda) :"));
	this.panelPrincipal.add(this.panelDonnees);
	this.panelPrincipal.add(new JSeparator());
	
	this.panelPrincipal.add(new JLabel("Traitement des données :"));
	this.panelPrincipal.add(this.panelFormule);
	this.panelPrincipal.add(this.panelAltitudeMax);
	this.panelPrincipal.add(new JSeparator());
	
	this.panelPrincipal.add(new JLabel("Coloration :"));
	this.cbColoration = new JCheckBox("activer la coloration par niveau");
	this.cbColoration.addActionListener(this);
	this.panelPrincipal.add(this.cbColoration);
	
	this.panelHighColor = new JPanel();
	this.panelHighColor.setLayout(new BorderLayout());
	this.panelHighColor.add(new JLabel("Rouge (valeur) "), BorderLayout.WEST);
	this.editHighValue = new JTextField("5");
	this.panelHighColor.add(this.editHighValue, BorderLayout.CENTER);
	this.panelHighColor.setVisible(false);
	
	this.panelMediumColor = new JPanel();
	this.panelMediumColor.setLayout(new BorderLayout());
	this.panelMediumColor.add(new JLabel("Orange (valeur) "), BorderLayout.WEST);
	this.editMediumValue = new JTextField("0.2");
	this.panelMediumColor.add(this.editMediumValue, BorderLayout.CENTER);
	this.panelMediumColor.setVisible(false);
	
	this.panelPrincipal.add(this.panelHighColor);
	this.panelPrincipal.add(this.panelMediumColor);
	this.panelPrincipal.add(new JSeparator());
	
	this.panelPrincipal.add(this.btnConvertir);
	this.panelPrincipal.add(this.copyright);
	
	this.setContentPane(this.panelPrincipal);
	this.setSize(500, 490);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(true);
    }
    
    /**
     * Gestion des actions
     */
    public void actionPerformed(ActionEvent evt)
    {
	// Ouverture d'un fichier GPX
	if(evt.getSource() == this.parcourirGPX)
	{
	    FiltreDeFichiers filter = new FiltreDeFichiers(".gpx");
	    this.fileChooser.addChoosableFileFilter(filter);
            this.fileChooser.setFileFilter(filter);
            
            this.fileChooser.setDialogTitle("Ouvrir GPX");
            int returnVal = this.fileChooser.showOpenDialog(this);
            
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
        	if(fileChooser.getSelectedFile().getAbsolutePath() != null)
        	    this.editGPX.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
            
            this.fileChooser.removeChoosableFileFilter(filter);
	}
	// Ouverture d'un fichier données
	else if(evt.getSource() == this.parcourirTension)
	{
	    FiltreDeFichiers filterTXT = new FiltreDeFichiers(".txt");
	    FiltreDeFichiers filterCVS = new FiltreDeFichiers(".csv");
	    this.fileChooser.addChoosableFileFilter(filterTXT);
	    this.fileChooser.addChoosableFileFilter(filterCVS);
	            
	    this.fileChooser.setDialogTitle("Ouvrir fichier Aaronia (cvs) ou Lascar (txt)");
            int returnVal = this.fileChooser.showOpenDialog(this);
            	            
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                if(fileChooser.getSelectedFile().getAbsolutePath() != null)
                    this.editTension.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
            
            this.fileChooser.removeChoosableFileFilter(filterTXT);
            this.fileChooser.removeChoosableFileFilter(filterCVS);
	}
	// Génération du fichier KML
	else if(evt.getSource() == this.btnConvertir)
	{
	    FiltreDeFichiers filter = new FiltreDeFichiers(".kml");
	    this.fileChooser.addChoosableFileFilter(filter);
            this.fileChooser.setFileFilter(filter);
            
            this.fileChooser.setDialogTitle("Enregistrer KML");
            int returnVal = this.fileChooser.showSaveDialog(this);
            
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
        	if(fileChooser.getSelectedFile().getAbsolutePath() != null)
        	{
        	    try
		    {
        		String KMLfile = fileChooser.getSelectedFile().getAbsolutePath();
        		if(!KMLfile.endsWith(".kml"))
        		    KMLfile = KMLfile + ".kml";
        		
        		// La coloration a été activée
        		if(this.cbColoration.isSelected())
        		{
        		    new Generateur(KMLfile, this.editGPX.getText(), Integer.parseInt(this.editAltitudeMax.getText()), this.editFormule.getText()).generateKMLfromData(this.editTension.getText(), this.editMediumValue.getText(), this.editHighValue.getText());
        		}
        		else
        		{
        		    new Generateur(KMLfile, this.editGPX.getText(), Integer.parseInt(this.editAltitudeMax.getText()), this.editFormule.getText()).generateKMLfromData(this.editTension.getText());
        		}
        		this.fileChooser.removeChoosableFileFilter(filter);
			JOptionPane.showMessageDialog(null, "Fichier KML généré : tout va bien !");
		    }
        	    catch (NumberFormatException e)
		    {
			JOptionPane.showMessageDialog(null, "Format de date invalide.");
		    }
        	    catch (IOException e)
		    {
			JOptionPane.showMessageDialog(null, "Erreur lors de la lecture du fichier " + e.getMessage() + ".");
		    }
        	    catch (ParseException e)
		    {
			JOptionPane.showMessageDialog(null, "Contenu d'un fichier invalide.");
		    }
        	    catch (CorrelationErrorException e)
		    {
        		JOptionPane.showMessageDialog(null, "Impossible de corréler les heures des données avec les heures des positions. Vérifiez que l'appareil qui a généré les fichiers de données dispose du même fuseau horaire que cet ordinateur.");
		    }
        	    catch (NoEnoughtPointsException e)
		    {
			JOptionPane.showMessageDialog(null, "Pas assez d'échantillon pour générer un fichier (celà peut être dû au format du fichier qui est mal reconnu) : " + e.getMessage());
		    }
        	    catch (NullAltitudeException e)
		    {
			JOptionPane.showMessageDialog(null, "Altitude maximale nulle, problème de format du fichier de données ?");
		    }
        	    catch (JDOMException e)
		    {
			JOptionPane.showMessageDialog(null, "Mauvais format de fichier GPX.");
		    }
        	    catch (MathParsingException e)
		    {
			JOptionPane.showMessageDialog(null, e.getMessage());
		    } catch (LocationFormatException e) {
			JOptionPane.showMessageDialog(null, "Problème dans la notation des coordonnées." + e.getMessage());
		    }
        	}
            }
	}
	// L'utilisateur coche la coloration
	else if(evt.getSource() == this.cbColoration)
	{
	    this.panelHighColor.setVisible(this.cbColoration.isSelected());
	    this.panelMediumColor.setVisible(this.cbColoration.isSelected());
	}
    }
}
