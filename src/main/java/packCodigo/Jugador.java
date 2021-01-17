package packCodigo;

public class Jugador {
	private String nombre;
	private int puntuacion;
	
	/**
	 * Funcionalidad Premios:
	 * Añadimos: 
	 *- Un array para las partidas ganadas
     *- Un array para las partidas ganadas seguidas
     *- Un int que indica el nivel máximo (3 en este caso)
	 */
	private int[] ganadas;
	private int[] seguidas;
	private int MAX_NIVEL=3;
	
	public Jugador(String pNombre){
		nombre = pNombre;
	}
	
	public void establecerPuntuacion(int pPuntuacion){
		puntuacion=pPuntuacion;
	}

	public int compareTo(Jugador pivote) {
		if(pivote.obtenerPunt()>this.obtenerPunt()){
			return 1;
		}else{
			if(pivote.obtenerPunt()<this.obtenerPunt()){
				return -1;
			}else{return 0;}
		}
	}
	
	public String obtenerNombre(){
		return nombre;
	}
	
	public int obtenerPunt(){
		return puntuacion;
	}
	
	private void ponerPunt(){
		this.puntuacion=Buscaminas.getBuscaminas().obtenerPuntuacion();
	}
	
	//public para las JUnit
	private boolean mismoJugador(){
		boolean mismo = false;
		if(this.obtenerNombre().equals(Buscaminas.getBuscaminas().obtenerNombreJugador())){
			mismo = true;
		}
		return mismo;
	}
	//public para las JUnit
	private void asignarPuntuacionR(){
		mayorPunt();
	}
	
	private void mayorPunt(){
		if(Buscaminas.getBuscaminas().obtenerPuntuacion()>=this.obtenerPunt()){
			this.ponerPunt();
		}
	}
	
	/**
	 * Funcionalidad Premios:
	 * Método que obtiene el número de partidas ganadas en total por un jugador
	 * teniendo en cuenta el nivel en el que está jugando.
	 */
	public int obtenerGanadas (int nivel) {
		if (nivel>=1 && nivel<=MAX_NIVEL) {
			return ganadas[nivel-1];
		}else {
			return 0;
		}
	}
	/**
	 * Funcionalidad Premios:
	 * Método que obtiene el número de partidas ganadas seguidas por un jugador
	 * teniendo en cuenta el nivel en el que está jugando.
	 */
	public int obtenerSeguidas (int nivel) {
		if (nivel>=1 && nivel<=MAX_NIVEL) {
			return seguidas[nivel-1];
		}else {
			return 0;
		}
	}
	
	/**
	 * Funcionalidad Premios:
	 * Método que incrementa las partidas ganadas totales y seguidas del nivel.
	 */
	public String anadirGanada (int nivel) {
		String sal="";
		if (nivel>=1 && nivel<=MAX_NIVEL) {
			ganadas[nivel-1]++;
			seguidas[nivel-1]++;
			sal=getPremio(nivel);
		}
		return sal;
	}
	
	/**
	 * Funcionalidad Premios:
	 * Método que borra el historial de partidas seguidas, se invoca al perder
	 * una partida.  
	 */
	public boolean borrarSeguidas(int nivel) {
		if (nivel>=1 && nivel<=MAX_NIVEL) {
			seguidas[nivel-1]=0;
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Funcionalidad Premios:
	 * Métodos para la serialización de datos para guardarlos en el XML.
	 */
	public String serializeSeguidas() {
		String sal="";
		for (int i=0; i<MAX_NIVEL; i++) {
			sal+=String.valueOf(seguidas[i])+" ";
		}
		sal.trim();
		return sal;
	}
	public String serializeGanadas() {
		String sal="";
		for (int i=0; i<MAX_NIVEL; i++) {
			sal+=String.valueOf(ganadas[i])+" ";
		}
		sal.trim();
		return sal;
	}
	public boolean extraerGanadas(String fuente) {
		String[] lista=fuente.split(" ");
		if (lista.length>0) {
			for (int i = 0; i < lista.length; i++) {
				if (i<MAX_NIVEL) {
					ganadas[i]=Integer.parseInt(lista[i]);
				}
			}
			return true;
		}else {
			return false;
		}
		       
	}
	public boolean extraerSeguidas(String fuente) {
		String[] lista=fuente.split(" ");
		if (lista.length>0) {
			for (int i = 0; i < lista.length; i++) {
				if (i<MAX_NIVEL) {
					seguidas[i]=Integer.parseInt(lista[i]);
				}
			}
			return true;
		}else {
			return false;
		} 
	}


	/**
	 * Funcionalidad Premios:
	 * Método que comprueba si has llegado justo al número requerido para ganar cada premio, 
	 * y en caso de hacerlo devuelve qué premio se ha ganado.
	 */
	private String getPremio(int nivel) {
		String sal="";
		if (nivel>=1 && nivel<=MAX_NIVEL) {
			if (ganadas[nivel-1]==1) {
				sal="Ganadas en el nivel "+String.valueOf(nivel)+": "+String.valueOf(ganadas[nivel-1]);
			}else if (ganadas[nivel-1]==5){
				sal="Ganadas en el nivel "+String.valueOf(nivel)+": "+String.valueOf(ganadas[nivel-1]);
			}else if (ganadas[nivel-1]==10){
				sal="Ganadas en el nivel "+String.valueOf(nivel)+": "+String.valueOf(ganadas[nivel-1]);
			}
			if (seguidas[nivel-1]==5) {
				sal="Ganadas seguido en el nivel "+String.valueOf(nivel)+": "+String.valueOf(seguidas[nivel-1]);
			}else if (seguidas[nivel-1]==10){
				sal="Ganadas seguido en el nivel "+String.valueOf(nivel)+": "+String.valueOf(seguidas[nivel-1]);
			}else if (seguidas[nivel-1]==15){
				sal="Ganadas seguido en el nivel "+String.valueOf(nivel)+": "+String.valueOf(seguidas[nivel-1]);
			}
		}
		return sal;
	}
}

