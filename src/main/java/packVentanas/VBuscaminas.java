package packVentanas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import packCodigo.Buscaminas;
import packCodigo.Casilla;
import packCodigo.Casilla50;
import packCodigo.CasillaMina;
import packCodigo.CasillaReset;
import packCodigo.NoArchivoAudioException;
import packCodigo.Ranking;
import packCodigo.Tablero;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;

@SuppressWarnings({ "serial", "deprecation" })
public class VBuscaminas extends JFrame implements ActionListener, Observer{

	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu menu1, menu2;
	private JMenuItem item1, item2, item3, item4;
	private JPanel panel_2;
	private JLabel lblNewLabel;
	private JLabel[] Banderas = new JLabel[3];
	private JLabel[] Tiempo = new JLabel[3];
	private JPanel panel;
	private int fil;
	private int col;
	private JLabel[] lcasillas;
	private VBuscaminas vBusca = this;
	private Boolean juego = true;
	private Boolean finalizado = false;
	private Clip clip;
	private AudioInputStream ais;
	private int bomba = 0;
	
	private ArrayList<Integer> lminas = new ArrayList<Integer>();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VBuscaminas frame = new VBuscaminas(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VBuscaminas(int nivel) {
		Image icon = new ImageIcon(getClass().getResource("/icono.png")).getImage();
		setIconImage(icon);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(nivel == 1){
			setBounds(100, 100, 500, 450);
		}else if(nivel == 2){
			setBounds(100, 100, 730, 600);
		}else if(nivel == 3){
			setBounds(100, 100, 1150, 710);
		}
		setTitle("Buscaminas");
		setResizable(false); 
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menu1 = new JMenu("Juego");
		menuBar.add(menu1);
		
		menu2 = new JMenu("Ayuda");
		menuBar.add(menu2);
		
		item1 = new JMenuItem("Nuevo");
		item1.addActionListener(this);
		menu1.add(item1);
		
		
		item2 = new JMenuItem("Ver");
		item2.addActionListener(this);
		menu2.add(item2);
		
		item3 = new JMenuItem("Ranking");
		item3.addActionListener(this);
		menu1.add(item3);
		
		/**
		 * Funcionalidad Premios:
		 * Nuevo botón para ver la lista de premios ganados.
		 */
		item4 = new JMenuItem("Premios");
		item4.addActionListener(this);
		menu1.add(item4);
		
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(new MigLayout("", "[200.00]", "[40.00][204.00]"));
		
		panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panel_2, "cell 0 0,grow");
	
		panel_2.setLayout(new MigLayout("", "[20.00][20.00][17.00][][20][][]", "[]"));
		
		for(int i=0; i<3; i++){
			JLabel j1 = new JLabel();
			Banderas[i] = j1;
			panel_2.add(j1, "cell "+i+" 0, grow");
			j1.setHorizontalAlignment(SwingConstants.LEFT);
		}
		
		lblNewLabel = new JLabel();
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBackground(new Color(255, 255, 0));
		panel_2.add(lblNewLabel, "cell 3 0,growx");
		lblNewLabel.setIcon(new ImageIcon(VBuscaminas.class.getResource("/Reset.png")));
		
		
		lblNewLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				Buscaminas.getBuscaminas().reset(vBusca);
				lblNewLabel.setIcon(new ImageIcon(VBuscaminas.class.getResource("/Reset.png")));
			}
		});
		
		for(int i=4; i<7; i++){
			JLabel j1 = new JLabel();
			Tiempo[i-4] = j1;
			panel_2.add(j1, "cell "+i+" 0, grow");
			j1.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		
		
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panel, "cell 0 1,grow");
		
		iniciarCasillas(nivel);
		Buscaminas.getBuscaminas().inicioJuego(nivel);
		Buscaminas.getBuscaminas().anadirObservador(this);
		fil=Buscaminas.getBuscaminas().obtenerNumFilas();
		col=Buscaminas.getBuscaminas().obtenerNumColumnas();
		mostrarTablero();
		anadirCasillas();
	}


	
	private void iniciarCasillas(int pNivel) {
		if(pNivel == 1){
			lcasillas = new JLabel[70];
		}else if(pNivel == 2){
			lcasillas = new JLabel[150];
		}else if(pNivel == 3){
			lcasillas = new JLabel[300];
		}
		
	}

	private void mostrarTablero(){
		
		String SFila = "";
		String SCol = "";
		for(int i=0;i<=fil;i++){
			SFila=SFila+"[]";
			for(int j=0;j<=col;j++){
				SCol=SCol+"[]";
			}
		}
		panel.setLayout(new MigLayout("", SCol, SFila));
	}
	
	public void anadirCasillas(){
		String f="";
		String c="";
		int cont=0;
		for(int i=0; i<=col; i++){
			f= Integer.toString(i);
			for(int j=0; j<=fil; j++){
				c= Integer.toString(j);
				JLabel l1 = new JLabel();
				lcasillas[cont]=l1;
				cont++;
				l1.setFont(new Font("Tahoma", Font.PLAIN, 11));
				l1.setHorizontalAlignment(SwingConstants.CENTER);
				l1.setBackground(new Color(255, 255, 255));
				panel.add(l1, "cell"+f+" "+c);
								
				l1.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e){
						 if (e.getButton() == MouseEvent.BUTTON3 && juego && !finalizado) {
							 int a;
							 int b;
							 a=getx(buscarPosCasilla((JLabel)e.getSource()));
							 b=gety(buscarPosCasilla((JLabel)e.getSource()));
		                     Buscaminas.getBuscaminas().ponerBandera(a,b);
		                     Buscaminas.getBuscaminas().comprobarJuego();
		                  }
						 else if(e.getButton() == MouseEvent.BUTTON1 && juego && !finalizado){
							 int a;
							 int b;
							 a=getx(buscarPosCasilla((JLabel)e.getSource()));
							 b=gety(buscarPosCasilla((JLabel)e.getSource()));
							 Casilla c = Buscaminas.getBuscaminas().getTablero().buscarCasilla(a, b);
							 if(!c.estaDesvelada()){
								 	Buscaminas.getBuscaminas().descubrirCasilla(a,b);
								 	Buscaminas.getBuscaminas().comprobarJuego();

							 }
									 
		                     
					} else
						if(e.getButton() == MouseEvent.BUTTON2 && juego && !finalizado){
							int a;
							int b;
							a=getx(buscarPosCasilla((JLabel)e.getSource()));
							b=gety(buscarPosCasilla((JLabel)e.getSource()));
							Buscaminas.getBuscaminas().descubrirTodosLosVecinos(a,b);
							Buscaminas.getBuscaminas().comprobarJuego();
					}
				}
					});
				
				//System.out.println("fila: " + j);
				//System.out.println("columna: " + i);
				
				if((Buscaminas.getBuscaminas().getTablero().buscarCasilla(j, i)) instanceof CasillaReset){
					l1.setIcon(new ImageIcon(VBuscaminas.class.getResource("/CasillaReset.png")));
				}else if((Buscaminas.getBuscaminas().getTablero().buscarCasilla(j, i)) instanceof Casilla50){
					l1.setIcon(new ImageIcon(VBuscaminas.class.getResource("/Casilla50.png")));
					
				}else if((Buscaminas.getBuscaminas().getTablero().buscarCasilla(j, i)) instanceof CasillaMina){
					l1.setIcon(new ImageIcon(VBuscaminas.class.getResource("/CasillaMina.png")));
					
				}else{
					l1.setIcon(new ImageIcon(VBuscaminas.class.getResource("/Casilla.png")));
				}
			}
		}
	}
	
	private int gety(int pPos) {
		return (pPos/(fil+1));

	}

	private int getx(int pPos) {
		if(pPos>10){
				return pPos%(fil+1);
			}
			else{
				return (pPos%(fil+1));
			}
	}

	private int buscarPosCasilla(JLabel source) {
		int pos=0;
		while(lcasillas[pos]!=source){
			pos++;
		}
		return pos;
	}

	public void compartirTweet() {
		if(java.awt.Desktop.isDesktopSupported()) {
			java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
			
			int puntos = Buscaminas.getBuscaminas().obtenerPuntuacion();
			String enlace = "http://twitter.com/share?text=Mira+mi+puntuación+de+"+String.valueOf(puntos)+"+puntos+que+logre+en+buscaminas&url=#buscaminas";
			if(desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
				try {
					java.net.URI uri= new java.net.URI(enlace);
					desktop.browse(uri);
				}catch(URISyntaxException | IOException ex) {}
			}
		}
	}
	
	public void update(Observable o, Object arg) {
		String[]p = arg.toString().split(",");
		if(o instanceof Buscaminas){ 
			   if(p.length==2){
				   if(p[1]!=null){
					   int aux;
					   int num = Integer.parseInt(p[1]);
					   for(int i=2; i>=0; i--){
						   aux = num%10;
						   num = num/10;
							Banderas[i].setIcon(new ImageIcon(VBuscaminas.class.getResource("/Crono"+aux+".png")));			
						}
				   }
				   if(p[0]!=null){
					   int aux;
					   int num = Integer.parseInt(p[0]);
					   for(int i=2; i>=0; i--){
						   aux = num%10;
						   num = num/10;
							Tiempo[i].setIcon(new ImageIcon(VBuscaminas.class.getResource("/Crono"+aux+".png")));			
						}
				   }
			   }else if(arg instanceof Boolean){
				   if(arg.toString().equals("false")){
					   juego = false;
					   try {
						   play(juego);
					   } catch (NoArchivoAudioException e) {
						   e.printStackTrace();
					   }
					   lblNewLabel.setIcon(new ImageIcon(VBuscaminas.class.getResource("/Perder.png")));
					   //JOptionPane.showMessageDialog(null, "OOOHHHHH QUE PENA, HAS ENCONTRADO UNA MINA!!!");
					   String[] opciones = {"OK", "Compartir"};
					   int compartir = JOptionPane.showOptionDialog(null, "OOOHHHHH QUE PENA, HAS ENCONTRADO UNA MINA!!!", null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, null);
					   if(compartir == 1) {
						   compartirTweet(); //tweet
					   }
					   
					   /**
						 * Funcionalidad Premios:
						 * Se notifica que se ha perdido una partida para borrar
						 * el historial de partidas ganadas seguidas de ese jugador en 
						 * ese nivel.
						 */
					   Buscaminas.getBuscaminas().nuevaPerdida();
					   
					   Ranking.getRanking().guardarLista();
				   }
				   else {
					   juego = true;
					   finalizado = false;
					   bomba = 0;
					   habilitarCasillas();
				   }
			   } else if(p.length ==3){
				   int pos = calcularPosicion(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
				   if(p[2].toString().equals("PonerBandera")){
					   lcasillas[pos].setIcon(new ImageIcon(VBuscaminas.class.getResource("/CasillaBandera.png")));
				   } else {
					   lcasillas[pos].setIcon(new ImageIcon(VBuscaminas.class.getResource("/Casilla.png"))); 
				   } 

			   } else if(arg.equals("FINALIZADO")){
				   finalizado = true;
				   try {
					   play(finalizado);
				   } catch (NoArchivoAudioException e) {
					   e.printStackTrace();
				   }
				   lblNewLabel.setIcon(new ImageIcon(VBuscaminas.class.getResource("/Victoria.png"))); 
				   
				   /**
					 * Funcionalidad Premios:
					 * Se notifica que se ha ganado una partida, y en caso de ganar un premio
					 * se muestra una ventana con un mensaje para que el jugador lo sepa.
					 */

				   String premio=Buscaminas.getBuscaminas().nuevaGanada();
				   if (!premio.equals("")) {
					   JOptionPane.showMessageDialog(null, "¡NUEVO PREMIO!\n"+premio);
				   }
				   
				   mostrarRanking();
				   Ranking.getRanking().guardarLista();
				   //JOptionPane.showMessageDialog(null, "HAS RESUELTO CORRECTAMENTE!!!");
				   String[] opciones = {"OK", "Compartir"};
				   int compartir = JOptionPane.showOptionDialog(null, "HAS RESUELTO CORRECTAMENTE!!!", null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, null);
				   if(compartir == 1) {
					   compartirTweet();
				   }

			   }
			} else if(o instanceof Tablero){
				if (p.length == 3){
				int pos = calcularPosicion(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
				  if(1<=Integer.parseInt(p[2]) && Integer.parseInt(p[2])<=8){
					  System.out.println("1.- " + p[0] + p[1] + p[2]);
					  lcasillas[pos].setIcon(new ImageIcon(VBuscaminas.class.getResource("/Casilla"+Integer.parseInt(p[2])+".png")));
				    }else if(Integer.parseInt(p[2])==0){
				    	System.out.println("2.- " + p[0] + p[1] + p[2]);
				    	   lcasillas[pos].setIcon(new ImageIcon(VBuscaminas.class.getResource("/CasillaVacia.png")));
				    	   
				    }else if(Integer.parseInt(p[2])==10){
				    	System.out.println("3.- " + p[0] + p[1] + p[2]);
				    	if(bomba == 0){
				    		System.out.println("3.1.- " + p[0] + p[1] + p[2]);
				    		 lcasillas[pos].setIcon(new ImageIcon(VBuscaminas.class.getResource("/CasillaPrimeraMina.png")));
				    		 bomba++;
				    	} else {
				    		System.out.println("3.2..- " + p[0] + p[1] + p[2]);
				    		 lcasillas[pos].setIcon(new ImageIcon(VBuscaminas.class.getResource("/CasillaMina.png")));	  
				    	}
				    }else if(Integer.parseInt(p[2])==33){
				    	if(bomba == 0){
				    		 lcasillas[pos].setIcon(new ImageIcon(VBuscaminas.class.getResource("/CasillaReset.png")));
				    		 bomba++;
				    	} else {
				    		 lcasillas[pos].setIcon(new ImageIcon(VBuscaminas.class.getResource("/CasillaResetNP.png")));	  
				    	}
				    }else if(Integer.parseInt(p[2])==55){
				    	if(bomba == 0){
				    		 lcasillas[pos].setIcon(new ImageIcon(VBuscaminas.class.getResource("/Casilla50.png")));
				    		 bomba++;
				    	} else {
				    		 lcasillas[pos].setIcon(new ImageIcon(VBuscaminas.class.getResource("/Casilla50NP.png")));	  
				    	}
				    
					}else if(Integer.parseInt(p[2])==11){
				    	System.out.println("4.- " + p[0] + p[1] + p[2]);
				    	lcasillas[pos].setIcon(new ImageIcon(VBuscaminas.class.getResource("/CasillaNoMina.png")));
				    	
				    }else if(Integer.parseInt(p[2])==30){	//MINA RESET
				    	lcasillas[pos].setIcon(new ImageIcon(VBuscaminas.class.getResource("/CasillaReset.png")));
				    	System.out.println("MINA RESET DESCUBIERTA");
				    	
				    	Buscaminas.getBuscaminas().setPausa(true);

				    	JOptionPane.showMessageDialog(null, "OHH! Has destapado una casilla reset. Se reiniciarÃ¡ el\ntablero cubriendo de nuevo "
				    			+ "todas las casillas.  Pero,\nÂ¡corre!, que el tiempo sigue contando.");
				    	System.out.println(Buscaminas.getBuscaminas().getTiempoTrans());
				    	
				    	Buscaminas.getBuscaminas().reset(this);
				    	
				    	

				    	
				    }
				    else if(Integer.parseInt(p[2])==50){	//MINA 50
				    	lcasillas[pos].setIcon(new ImageIcon(VBuscaminas.class.getResource("/Casilla50.png")));
				    	System.out.println("MINA 50 DESCUBIERTA");
				    	
				    	Buscaminas.getBuscaminas().setPausa(true);
				    	
				    	JOptionPane.showMessageDialog(null, "Hoy es tu dÃ­a de suerte! Has abierto una mina, \npero no te preocupes."
				    			+ " Se marcarÃ¡n el 50% de las\nminas que aÃºn estaban sin marcar.");
				    	
				    	Buscaminas.getBuscaminas().setPausa(false);

				    	Buscaminas.getBuscaminas().getTablero().anadirBandera();

				    	marcar50();
				    	
				    	
				    }
				}
			}
	}
	
	
	public void marcar50(){
		
		
		int a = getx(0);
		int b = gety(0);
		int r = calcularPosicion(a, b);
		System.out.println(r);
		
		//
		
		for(int i=0; i<=col; i++){
			
			for(int j=0; j<=fil; j++){
				
				Casilla c = Buscaminas.getBuscaminas().getTablero().buscarCasilla(j, i);
				
				if((c instanceof CasillaMina || c instanceof CasillaReset) && (!c.tieneBandera())){
					System.out.println(j + "," + i);
					lminas.add(calcularPosicion(j, i));
				}
				
			}
		}
		
		//Obtenemos la mitad del numero de minas sin marcar que queden y redondeamos para abajo
		int mitad = (int) Math.floor(lminas.size()/2);
		System.out.println(mitad);
		
		for(int x = 0; x < mitad; x++){
			
			int z = lminas.get(x);
			
			//Casilla c = Buscaminas.getBuscaminas().getTablero().buscarCasilla(getx(z), gety(z));
			Buscaminas.getBuscaminas().ponerBandera(getx(z), gety(z));
			
			
		}
		
	}

	public void actionPerformed(ActionEvent e) {
        if (e.getSource()==item1) {
        	Buscaminas.getBuscaminas().reset(vBusca);
        } else if (e.getSource() == item2){
        	VAyuda vA = new VAyuda();
			vA.setVisible(true);
        }else if (e.getSource() == item3){
        	mostrarRanking();
        	/**
        	 * Funcionalidad Premios:
        	 * Añadimos el item4, que es el de la ventana de premios.
        	 */
        }else if (e.getSource() == item4){
        	mostrarPremios();
        }
        
        
   }
	
	private void habilitarCasillas(){
		for(int i=0;i<lcasillas.length;i++){
			lcasillas[i].setEnabled(true);
			lcasillas[i].setIcon(new ImageIcon(VBuscaminas.class.getResource("/Casilla.png")));
		}
	}
	
	private int calcularPosicion(int pFila, int pCol){
		int pos = 0; 
		pos = (pCol*(fil+1))+pFila;
		return pos;	
	}
	
	private void mostrarRanking(){
		Buscaminas.getBuscaminas().calcularPuntos();
    	VRanking vR = new VRanking();
		vR.setVisible(true);
	}
	
	/**
	 * Funcionalidad Premios:
	 * Método para mostrar la pantalla de premios.
	 */
	private void mostrarPremios() {
		VPremios vP = new VPremios();
		vP.setVisible(true);
	}
		
	private void autoGuardadoRank(){
		Timer timer;
		TimerTask  timerTask = new TimerTask() {
			@Override
			public void run() {
				try{
		    		 Thread.sleep(10000); 
		    	  }catch (Exception e) {}
				
			}
		};
		timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 0, 50);
	}
	
	private void play(boolean pB) throws NoArchivoAudioException{
		if (pB==false){
			if (new File("resources/lose.wav").getAbsoluteFile() != null){
				try {
					ais = AudioSystem.getAudioInputStream(new File("src/main/resources/lose.wav").getAbsoluteFile());
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					clip = AudioSystem.getClip();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
				try {
					clip.open(ais);
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				throw new NoArchivoAudioException();
			}
		}else{
			if (new File("resources/win.wav").getAbsoluteFile() != null){
				try {
					ais = AudioSystem.getAudioInputStream(new File("src/main/resources/win.wav").getAbsoluteFile());
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					clip = AudioSystem.getClip();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
				try {
					clip.open(ais);
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				throw new NoArchivoAudioException();
			}
		}
		clip.start();
	}
}

