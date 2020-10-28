package ThreadConSocketsUDP;
public class MainServidor {

	public static void main(String[] args) {
		int puerto = 8888;
		System.out.println("SERVIDOR INICIADO EN EL PUERTO "+puerto);
		ServidorUDP servidor = new ServidorUDP(puerto); //Se crea un servidor de tipo serverUDP
		servidor.start(); //Se  inicia el servidor
	
	}
}
