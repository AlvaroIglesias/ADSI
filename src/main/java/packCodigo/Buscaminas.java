package packCodigo;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import packVentanas.VBuscaminas;

public class Buscaminas extends Observable implements Observer{

	private static Buscaminas miBuscaminas = new Buscaminas();
	private Tablero tablero;
	private int nivel;
	private int contMinas;
	private Timer timer=new Timer();//Aqui va el tiempo
	private boolean juego;
	private float tiempoTrans;
	private int contBanderas=0;
	private int puntuacion;
	private boolean finalizado = false;
	private Jugador j;
	private boolean pausa;
	
	/****************
	 * CONSTRUCTORA	*
	 ****************/
	private Buscaminas(){
	}
	
	/************************
	 * Singleton.			*
	 * @return miBuscaminas	*
	 ************************/
	public static Buscaminas getBuscaminas(){
		return miBuscaminas;
	}
	

	/************************
	 * 						*
	 * @return 				*
	 ************************/
	
	public Tablero getTablero(){
		return tablero;
	}
	
/*	public void anadirBandera(){
		this.contBanderas++;
		setChanged();
		//notifyObservers(tiempoTrans + "," + contBanderas);
		notifyObservers(null + "," + contBanderas);
	}
*/
	private void setContMinas(){
		contMinas = tablero.minas().size();
	}
	
	public float getTiempoTrans(){
		
		return this.tiempoTrans;
	}

	/**Iniciamos el juego**/
	public void inicioJuego(int pNivel){
		setNivel(pNivel);
		setJuego(true);
		iniciarTablero(pNivel);
		setContMinas();
		contBanderas = contMinas;
		crono();
	}
	
	/**Iniciar el tablero**/
	
	private void iniciarTablero(int pNivel){
		if(pNivel == 1){
			tablero = TableroBuilderN1.getTableroBuilderN1().asignarTablero();
		} else if (pNivel == 2){
			tablero = TableroBuilderN2.getTableroBuilderN2().asignarTablero();
			
		} else if (pNivel == 3){
			tablero = TableroBuilderN3.getTableroBuilderN3().asignarTablero();
		}
	}

	
	/************************************************************
	 * Resetea el Buscaminas haciendo una nueva instancia de	*
	 * tablero, casilla, casillasVacias, lCasillasVisitadas 	*
	 * y lCasillasVacias volviendo a calcular el numero de 		*
	 * minas. El tiempo se resetea.								*												*
	 ************************************************************/
	public void reset(VBuscaminas vBuscaminas){
		iniciarTablero(nivel);
		tablero.addObserver(vBuscaminas);
		setContMinas();
		contBanderas=contMinas;
		pausa=false;
		//tiempoTrans = -1;
		//timer.cancel();
		//crono();
		tablero.addObserver(this);
		setJuego(true);
		setFinalizado(false);
	}
	
	public int getNivel(){
		return nivel;
	}
	
	
	public void setPausa(boolean pPausa){
		this.pausa = pPausa;
	}
	
	public Jugador getJugador() {
		return this.j;
	}
	
/*	
	public void anadirCasillaAListaBanderas(int fila, int columna){
		this.getTablero().anadirCasillaBandera(fila, columna);
		
		setChanged();
		notifyObservers(true + ",BANDERA");
	}*/
	
	
	/**SetJuego**/
	private void setJuego(boolean pJuego){
		this.juego = pJuego;
		setChanged();
		notifyObservers(juego);
	}
	
	/********************
	 * @param pNivel	*
	 ********************/
	private void setNivel(int pNivel){
		nivel = pNivel;
	}

	public void descubrirCasilla(int pFila, int pCol){
		tablero.descubrirCasilla(pFila, pCol);
	}
	
	/**
	 * 
	 */
	public void gameOver(){
		timer.cancel();
		tablero.mostrarTablero();
		setJuego(false);
	}

	public int obtenerNumFilas() {
		
		return tablero.obtenerNumFilas();
	}
	
	public int obtenerNumColumnas() {
		
		return tablero.obtenerNumColumnas();
	}

	public boolean getJuego(){
		return juego;
	}
	
	public void ponerBandera(int fila, int col) {
		int aux = contBanderas;
		if(0<=contBanderas){
			tablero.ponerBandera(fila,col);
			if(contBanderas < aux){
				setChanged();
				notifyObservers(fila+","+col+","+"PonerBandera");
			} else if (contBanderas > aux){
				setChanged();
				notifyObservers(fila+","+col+","+"QuitarBandera");
			}
		}
		
		
	}
	
	private void crono(){

	  TimerTask  timerTask = new TimerTask() {
	   @Override
	   public void run() {
	    if(pausa){
	    	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }else{
	    	String texto;
	    	tiempoTrans++;
	    	texto = ""+(int)tiempoTrans;
	    	if(tiempoTrans<10){
	    		setChanged();
	    		notifyObservers("00"+texto+","+contBanderas);
	    	}else if(tiempoTrans<100){
	    		setChanged();
	    		notifyObservers("0"+texto+","+contBanderas);
	    	}else{
	    		setChanged();
	    		notifyObservers(texto+","+contBanderas);
	    	}
	    }
	   }
	  };
	  timer = new Timer();
	  timer.scheduleAtFixedRate(timerTask, 0, 1000);
	  
	 }
	
	public void update(Observable pObservable, Object pObjeto) {
		if(pObservable instanceof Tablero){
			String[]p = pObjeto.toString().split(",");
			if(p[1].equals("BANDERA") && p[0].equals("true")){
				if(contBanderas>0){
					contBanderas--;
				}
			}else if(p[1].equals("BANDERA") && p[0].equals("false")){
				if(contBanderas<contMinas){
					contBanderas++;
				}
			}
		}
	}


	public void anadirObservador(VBuscaminas vBuscaminas) {
		addObserver(vBuscaminas);
		tablero.addObserver(vBuscaminas);
		tablero.addObserver(this);
	}

	public void establecerNombreJugador(String text) {
		boolean esta = false;
		if(text==""){
			esta =  Ranking.getRanking().estaEnRanking("Desconocido");
		}else{
			esta =  Ranking.getRanking().estaEnRanking(text);
		}
		
		if(!esta){
			if(text.equals("")){
				j = new Jugador("Desconocido");
			} else {
				j = new Jugador(text);
			}
			j.establecerPuntuacion(0);
			Ranking.getRanking().anadirLista(j);
		} else{
			if(text.equals("")){
				j = Ranking.getRanking().obtJugador("Desconocido");
			} else {
				j = Ranking.getRanking().obtJugador(text);
			}
		}
	}

	private void establecerNivel(String selectedItem) {
		nivel = Integer.parseInt(selectedItem);
	}
	
	private void establecerPuntuacion(int pPunt){
		puntuacion = pPunt;
	}
	
	public String obtenerNombreJugador(){
		return j.obtenerNombre();
	}
	
	public int obtenerBanderas(){
		return contBanderas;
	}
	
	public int obtenerPuntuacion(){
		return puntuacion;
	}
	public void comprobarJuego(){
		if(tablero.getContadorCasillasDescubrir() == contMinas){
			boolean fin = tablero.comprobarJuego();
			setFinalizado(fin);
		}
		
	}

	private void setFinalizado(boolean fin) {
		this.finalizado = fin;
		if(finalizado){
			timer.cancel();
			setChanged();
			notifyObservers("FINALIZADO");
		}
	}

	public void calcularPuntos() {
		if(!finalizado){
			puntuacion = 0;
		} else {
			puntuacion =(int) ((((6000-tiempoTrans)*Math.sqrt(nivel))/10)-(int)tiempoTrans);			
		}	
		asignarPuntos();
	}
	
	private void asignarPuntos(){
		//if(j.obtenerPunt()<puntuacion){
			j.establecerPuntuacion(puntuacion);
		//}
	}

	public void descubrirTodosLosVecinos(int a, int b) {
		tablero.descubrirTodosLosVecinos(a,b);
	}
	
	/**
	 * Funcionalidad Premios:
	 * Se a�ade una partida ganada al jugador teniendo en cuenta el nivel en el que 
	 * est� jugando.
	 */
	public String nuevaGanada() {
		return j.anadirGanada(this.nivel);
	}
	
	/**
	 * Funcionalidad Premios:
	 * Se borra el historial de partidas ganadas seguidas para el jugador teniendo
	 * en cuenta el nivel en el que est� jugando.
	 */
	public void nuevaPerdida() {
		j.borrarSeguidas(this.nivel);
	}
	

}
