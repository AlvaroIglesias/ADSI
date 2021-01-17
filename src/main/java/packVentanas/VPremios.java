package packVentanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import packCodigo.Buscaminas;
import packCodigo.Jugador;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class VPremios extends JFrame {

	private JPanel contentPane;
	
	private JCheckBox chckbx1GanadaN1 = new JCheckBox("1 partida ganada");
	private JCheckBox chckbx5GanadaN1 = new JCheckBox("5 partidas ganadas");
	private JCheckBox chckbx10GanadaN1 = new JCheckBox("10 partidas ganadas");
	private JCheckBox chckbx5SeguidaN1 = new JCheckBox("5 partidas seguidas ganadas");
	private JCheckBox chckbx10SeguidaN1 = new JCheckBox("10 partidas seguidas ganadas");
	private JCheckBox chckbx15SeguidaN1 = new JCheckBox("15 partidas seguidas ganadas");
	private final JLabel lblNivel2 = new JLabel("Nivel 2");
	
	private final JCheckBox chckbx1GanadaN2 = new JCheckBox("1 partida ganada");
	private final JCheckBox chckbx5GanadaN2 = new JCheckBox("5 partidas ganadas");
	private final JCheckBox chckbx10GanadaN2 = new JCheckBox("10 partidas ganadas");
	private final JCheckBox chckbx5SeguidaN2 = new JCheckBox("5 partidas seguidas ganadas");
	private final JCheckBox chckbx10SeguidaN2 = new JCheckBox("10 partidas seguidas ganadas");
	private final JCheckBox chckbx15SeguidaN2 = new JCheckBox("15 partidas seguidas ganadas");
	private final JLabel lblNivel3 = new JLabel("Nivel 3");
	
	private final JCheckBox chckbx1GanadaN3 = new JCheckBox("1 partida ganada");
	private final JCheckBox chckbx5GanadaN3 = new JCheckBox("5 partidas ganadas");
	private final JCheckBox chckbx10GanadaN3 = new JCheckBox("10 partidas ganadas");
	private final JCheckBox chckbx5SeguidaN3 = new JCheckBox("5 partidas seguidas ganadas");
	private final JCheckBox chckbx10SeguidaN3 = new JCheckBox("10 partidas seguidas ganadas");
	private final JCheckBox chckbx15SeguidaN3 = new JCheckBox("15 partidas seguidas ganadas");


	

	/**
	 * Create the frame.
	 */
	public VPremios() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 637);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.setBackground(SystemColor.control);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPremios = new JLabel("PREMIOS");
		lblPremios.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		lblPremios.setBounds(283, 11, 134, 38);
		contentPane.add(lblPremios);
		

		//-------------NIVEL 1-------------
		
		JLabel lblNivel1 = new JLabel("Nivel 1");
		lblNivel1.setBounds(20, 62, 49, 14);
		contentPane.add(lblNivel1);
		
		
		//GANADAS
		chckbx1GanadaN1.setBounds(74, 107, 167, 23);
		contentPane.add(chckbx1GanadaN1);
		chckbx1GanadaN1.setEnabled(false);

		
		chckbx5GanadaN1.setBounds(74, 135, 167, 23);
		contentPane.add(chckbx5GanadaN1);
		chckbx5GanadaN1.setEnabled(false);
		
		
		chckbx10GanadaN1.setBounds(74, 166, 167, 23);
		contentPane.add(chckbx10GanadaN1);
		chckbx10GanadaN1.setEnabled(false);
		
		
		//SEGUIDAS
		chckbx5SeguidaN1.setBounds(355, 107, 230, 23);
		contentPane.add(chckbx5SeguidaN1);
		chckbx5SeguidaN1.setEnabled(false);
		
		
		chckbx10SeguidaN1.setBounds(355, 135, 220, 23);
		contentPane.add(chckbx10SeguidaN1);
		chckbx10SeguidaN1.setEnabled(false);
		
		
		chckbx15SeguidaN1.setBounds(355, 166, 230, 23);
		contentPane.add(chckbx15SeguidaN1);
		chckbx15SeguidaN1.setEnabled(false);
		
		//-------------NIVEL 2-------------
		
		lblNivel2.setBounds(20, 229, 49, 14);			
		contentPane.add(lblNivel2);
		
		//GANADAS
		chckbx1GanadaN2.setEnabled(false);
		chckbx1GanadaN2.setBounds(74, 274, 167, 23);
		contentPane.add(chckbx1GanadaN2);
		
		
		chckbx5GanadaN2.setEnabled(false);
		chckbx5GanadaN2.setBounds(74, 302, 167, 23);
		contentPane.add(chckbx5GanadaN2);
		
		
		chckbx10GanadaN2.setEnabled(false);
		chckbx10GanadaN2.setBounds(74, 333, 167, 23);
		contentPane.add(chckbx10GanadaN2);
		
		//SEGUIDAS
		chckbx5SeguidaN2.setEnabled(false);
		chckbx5SeguidaN2.setBounds(355, 274, 230, 23);
		contentPane.add(chckbx5SeguidaN2);
		
		
		chckbx10SeguidaN2.setEnabled(false);
		chckbx10SeguidaN2.setBounds(355, 302, 220, 23);
		contentPane.add(chckbx10SeguidaN2);
		
		
		chckbx15SeguidaN2.setEnabled(false);
		chckbx15SeguidaN2.setBounds(355, 333, 230, 23);	
		contentPane.add(chckbx15SeguidaN2);
		
		
		//-------------NIVEL 3-------------
		
		lblNivel3.setBounds(20, 413, 49, 14);
		contentPane.add(lblNivel3);
		
		//GANADAS
		chckbx1GanadaN3.setEnabled(false);
		chckbx1GanadaN3.setBounds(74, 458, 167, 23);		
		contentPane.add(chckbx1GanadaN3);
		
		
		chckbx5GanadaN3.setEnabled(false);
		chckbx5GanadaN3.setBounds(74, 486, 167, 23);		
		contentPane.add(chckbx5GanadaN3);
		
		
		chckbx10GanadaN3.setEnabled(false);
		chckbx10GanadaN3.setBounds(74, 517, 167, 23);
		contentPane.add(chckbx10GanadaN3);
		
		
		//SEGUIDAS
		chckbx5SeguidaN3.setEnabled(false);
		chckbx5SeguidaN3.setBounds(355, 458, 230, 23);	
		contentPane.add(chckbx5SeguidaN3);
		
		
		chckbx10SeguidaN3.setEnabled(false);
		chckbx10SeguidaN3.setBounds(355, 486, 220, 23);	
		contentPane.add(chckbx10SeguidaN3);
		
		
		chckbx15SeguidaN3.setEnabled(false);
		chckbx15SeguidaN3.setBounds(355, 517, 230, 23);	
		contentPane.add(chckbx15SeguidaN3);
		
		
		/**
		 * Funcionalidad Premios:
		 * Llamada a los métodos, para obtener los premios.
		 */
		checkNivel1();
		checkNivel2();
		checkNivel3();


	}
	/**
	 * Funcionalidad Premios:
	 * Método que se encarga de marcar los check para el Nivel 1.
	 */
	private void checkNivel1() { 
		int g=Buscaminas.getBuscaminas().getJugador().obtenerGanadas(1);
		int s=Buscaminas.getBuscaminas().getJugador().obtenerSeguidas(1);
		if (g>=1) {
			chckbx1GanadaN1.setSelected(true);
		}
		if (g>=5) {
			chckbx5GanadaN1.setSelected(true);
		}
		if (g>=10) {
			chckbx10GanadaN1.setSelected(true);
		}
		if (s>=5) {
			chckbx5SeguidaN1.setSelected(true);
		}
		if (s>=10) {
			chckbx10SeguidaN1.setSelected(true);
		}
		if (s>=15) {
			chckbx15SeguidaN1.setSelected(true);
		}				
	}
	
	/**
	 * Funcionalidad Premios:
	 * Método que se encarga de marcar los check para el Nivel 2.
	 */
	private void checkNivel2() { 
		int g=Buscaminas.getBuscaminas().getJugador().obtenerGanadas(2);
		int s=Buscaminas.getBuscaminas().getJugador().obtenerSeguidas(2);
		if (g>=1) {
			chckbx1GanadaN2.setSelected(true);
		}
		if (g>=5) {
			chckbx5GanadaN2.setSelected(true);
		}
		if (g>=10) {
			chckbx10GanadaN2.setSelected(true);
		}
		if (s>=5) {
			chckbx5SeguidaN2.setSelected(true);
		}
		if (s>=10) {
			chckbx10SeguidaN2.setSelected(true);
		}
		if (s>=15) {
			chckbx15SeguidaN2.setSelected(true);
		}				
	}
	
	/**
	 * Funcionalidad Premios:
	 * Método que se encarga de marcar los check para el Nivel 3.
	 */
	private void checkNivel3() { 
		int g=Buscaminas.getBuscaminas().getJugador().obtenerGanadas(3);
		int s=Buscaminas.getBuscaminas().getJugador().obtenerSeguidas(3);
		if (g>=1) {
			chckbx1GanadaN3.setSelected(true);
		}
		if (g>=5) {
			chckbx5GanadaN3.setSelected(true);
		}
		if (g>=10) {
			chckbx10GanadaN3.setSelected(true);
		}
		if (s>=5) {
			chckbx5SeguidaN3.setSelected(true);
		}
		if (s>=10) {
			chckbx10SeguidaN3.setSelected(true);
		}
		if (s>=15) {
			chckbx15SeguidaN3.setSelected(true);
		}				
	}

}
