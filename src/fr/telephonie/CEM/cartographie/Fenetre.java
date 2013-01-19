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
		super(LangEn.WIN_TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.fileChooser = new JFileChooser();

		this.panelPrincipal = new JPanel();

		this.panelGPX = new JPanel();
		this.panelGPX.setLayout(new BorderLayout());
		this.editGPX = new JTextField("", 22);
		this.parcourirGPX = new JButton(LangEn.WIN_BROWSE_GPX);
		this.panelGPX.add(this.parcourirGPX, BorderLayout.EAST);
		this.panelGPX.add(this.editGPX, BorderLayout.CENTER);
		this.parcourirGPX.addActionListener(this);

		this.panelDonnees = new JPanel();
		this.panelDonnees.setLayout(new BorderLayout());
		this.editTension = new JTextField("", 30);
		this.parcourirTension = new JButton(LangEn.WIN_BROWSE_DATA);
		this.panelDonnees.add(this.parcourirTension, BorderLayout.EAST);
		this.panelDonnees.add(this.editTension, BorderLayout.CENTER);
		this.parcourirTension.addActionListener(this);

		this.panelFormule = new JPanel();
		this.panelFormule.setLayout(new BorderLayout());
		this.editFormule = new JTextField("x", 5);
		this.editFormule.setToolTipText(LangEn.WIN_FORMULA_TOOLTIP);
		this.panelFormule.add(new JLabel(LangEn.WIN_FORMULA_LABEL), BorderLayout.WEST);
		this.panelFormule.add(this.editFormule, BorderLayout.CENTER);

		this.panelAltitudeMax = new JPanel();
		this.panelAltitudeMax.setLayout(new BorderLayout());
		this.editAltitudeMax = new JTextField("100", 6);
		this.editAltitudeMax.setToolTipText(LangEn.WIN_ALTITUDE_TOOLTIP);
		this.panelAltitudeMax.add(new JLabel(LangEn.WIN_ALTITUDE_LABEL), BorderLayout.WEST);
		this.panelAltitudeMax.add(this.editAltitudeMax, BorderLayout.CENTER);
		this.panelAltitudeMax.add(new JLabel(LangEn.WIN_ALTITUDE_LABEL2), BorderLayout.EAST);

		this.btnConvertir = new JButton(LangEn.WIN_CREATE_BTN);
		this.btnConvertir.addActionListener(this);

		this.copyright = new JLabel("Copyright 2012, Amaury Graillat");
		this.copyright.setSize(600, 10);

		GridLayout gridLayout = new GridLayout(0,1);
		gridLayout.setVgap(7);
		this.panelPrincipal.setLayout(gridLayout);
		this.panelPrincipal.add(new JLabel("GNU General Public License."));
		this.panelPrincipal.add(new JLabel(LangEn.WIN_COORDINATES_LABEL));
		this.panelPrincipal.add(this.panelGPX);
		this.panelPrincipal.add(new JSeparator());

		this.panelPrincipal.add(new JLabel(LangEn.WIN_DATA_LABEL));
		this.panelPrincipal.add(this.panelDonnees);
		this.panelPrincipal.add(new JSeparator());

		this.panelPrincipal.add(new JLabel(LangEn.WIN_PROCESS_LABEL));
		this.panelPrincipal.add(this.panelFormule);
		this.panelPrincipal.add(this.panelAltitudeMax);
		this.panelPrincipal.add(new JSeparator());

		this.panelPrincipal.add(new JLabel(LangEn.WIN_COLOR_LABEL));
		this.cbColoration = new JCheckBox(LangEn.WIN_COLOR_CHECKBOX);
		this.cbColoration.addActionListener(this);
		this.panelPrincipal.add(this.cbColoration);

		this.panelHighColor = new JPanel();
		this.panelHighColor.setLayout(new BorderLayout());
		this.panelHighColor.add(new JLabel(LangEn.WIN_COLOR_RED_LABEL), BorderLayout.WEST);
		this.editHighValue = new JTextField("5");
		this.panelHighColor.add(this.editHighValue, BorderLayout.CENTER);
		this.panelHighColor.setVisible(false);

		this.panelMediumColor = new JPanel();
		this.panelMediumColor.setLayout(new BorderLayout());
		this.panelMediumColor.add(new JLabel(LangEn.WIN_COLOR_ORANGE_LABEL), BorderLayout.WEST);
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

			this.fileChooser.setDialogTitle(LangEn.WIN_BROWSE_GPX);
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

			this.fileChooser.setDialogTitle(LangEn.FILECHOOSER_TITLE);
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

			this.fileChooser.setDialogTitle(LangEn.FILECHOOSER_KML_BTN);
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
						JOptionPane.showMessageDialog(null, LangEn.MSG_GENERATED);
					}
					catch (NumberFormatException e)
					{
						JOptionPane.showMessageDialog(null, LangEn.MSG_INVALID_DATE_FORMAT);
					}
					catch (IOException e)
					{
						JOptionPane.showMessageDialog(null, LangEn.MSG_IO_EXCEPTION + e.getMessage() + ".");
					}
					catch (ParseException e)
					{
						JOptionPane.showMessageDialog(null, LangEn.MSG_PARSE_EXCEPTION);
					}
					catch (CorrelationErrorException e)
					{
						JOptionPane.showMessageDialog(null, LangEn.MSG_CORRELATION_ERROR);
					}
					catch (NoEnoughtPointsException e)
					{
						JOptionPane.showMessageDialog(null, LangEn.MSG_NO_ENOUGHT_POINTS + e.getMessage());
					}
					catch (NullAltitudeException e)
					{
						JOptionPane.showMessageDialog(null, LangEn.MSG_NULL_ALTITUDE_EXCEPTION);
					}
					catch (JDOMException e)
					{
						JOptionPane.showMessageDialog(null, LangEn.MSG_JDOM_ERROR);
					}
					catch (MathParsingException e)
					{
						JOptionPane.showMessageDialog(null, e.getMessage());
					} catch (LocationFormatException e) {
						JOptionPane.showMessageDialog(null, LangEn.MSG_LOCATION_FORMAT_ERROR + e.getMessage());
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
