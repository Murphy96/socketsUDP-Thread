package ThreadConSocketsUDP;
import java.net.*;
import java.io.*;
public class ClienteUDP extends Thread{
	private int puerto;
	private String DomHost;



	public ClienteUDP(int puerto, String domHost) {
		super();
		this.puerto = puerto;
		DomHost = domHost;
	}



	@Override
    public void run()  
    {
		try {
			//Buffer de entrada para leer los datos
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String host = DomHost;
			int puertoServidor = puerto;
			DatagramSocket socketUDP = new DatagramSocket(); //Socket UDP
			InetAddress hostServidor = InetAddress.getByName(host);
			boolean bMenu = false; //Bandera para pedir el menu
			while(true) {	
				String cadena = "Cliente conectado";	
				if(!bMenu) //Si aun no se pidio el menu se realiza la peticion
				{
					byte[] mensaje = cadena.getBytes();
					// Construimos un datagrama para enviar el mensaje al servidor
					DatagramPacket peticion = new DatagramPacket(mensaje, cadena.length(), hostServidor, puertoServidor);
					// Enviamos el datagrama
					socketUDP.send(peticion);
					byte[] bufer = new byte[1000];
					DatagramPacket respuesta = new DatagramPacket(bufer, bufer.length);
					socketUDP.receive(respuesta);
					String cadRespuesta = new String(respuesta.getData());
					// Enviamos la respuesta del servidor a la salida estandar
					System.out.println(cadRespuesta);	
					bMenu =  true; //Ya se pidio el menu
				}
				cadena="";
				cadena = stdIn.readLine(); // Se lee la opcion elegida por el cliente 
				if (cadena.equals("4")) // Si la opcion elegida es 4 el servidor se desconecta
				{
					System.out.println("Cliente desconectado del servidor...");
					// Cerramos el socket
					socketUDP.close();
					break;
				}
				else
				{
					byte[] mensaje = cadena.getBytes();
					// Construimos un datagrama para enviar el mensaje al servidor
					DatagramPacket  peticion = new DatagramPacket(mensaje, cadena.length(), hostServidor, puertoServidor);
					// Enviamos el datagrama
					socketUDP.send(peticion);
					// Construimos el DatagramPacket que contendrá la respuesta
					byte[] bufer = new byte[1000];
					DatagramPacket respuesta = new DatagramPacket(bufer, bufer.length);
					socketUDP.receive(respuesta);
					//Se verifica si la opcion mandada por el cliente es valida
					if(!(new String(respuesta.getData()).trim()).equals("Opcion no valida"))
					{
						//Numero de parametros que necesitara la opcion elegida
						int numParametros = Integer.parseInt(new String(respuesta.getData()).trim());
						//Se realiza las peticiones necesarias para las opciones del menu
						for (int i = 0; i < numParametros; i++) {
							// Construimos el DatagramPacket que contendrá la respuesta
							bufer = new byte[1000];
							respuesta = new DatagramPacket(bufer, bufer.length);
							socketUDP.receive(respuesta);
							// Enviamos la respuesta del servidor a la salida estandar
							System.out.println(new String(respuesta.getData()));
							cadena="";
							cadena = stdIn.readLine();
							mensaje = cadena.getBytes();
							// Construimos un datagrama para enviar el mensaje al servidor
							peticion = new DatagramPacket(mensaje, cadena.length(), hostServidor, puertoServidor);
							// Enviamos el datagrama
							socketUDP.send(peticion);
						}					
						// Construimos el DatagramPacket que contendrá la respuesta
						bufer = new byte[1000];
						respuesta = new DatagramPacket(bufer, bufer.length);
						socketUDP.receive(respuesta);
					}
					// Enviamos la respuesta del servidor a la salida estandar
					System.out.println(new String(respuesta.getData()));
					bMenu = false; // Se pone en true la bandera para volver a pedir el menu
				}				
			}
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}
}