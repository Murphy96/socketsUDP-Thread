package ThreadConSocketsUDP;

public class MainCliente {

	public static void main(String[] args) {
		int puerto=8888;
		String host = "localhost";
		System.out.println("Cliente iniciado en el puerto "+puerto);
		ClienteUDP cliente = new ClienteUDP(puerto,host); //Se crea un cliente de tipo clientUDP
		
		cliente.start(); //Se inicia el cliente

	}

}
