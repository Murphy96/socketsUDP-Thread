package ThreadConSocketsUDP;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServidorUDP  extends Thread{
	private int puerto;
	
	
	public ServidorUDP(int puerto) {
		super();
		this.puerto = puerto;
	}


	@Override
    public void run()  
    {
		try {
			DatagramSocket socketUDP = new DatagramSocket(puerto); //Socket UDP
			while (true) {
				byte[] buffer = new byte[1000];
				// Construimos el DatagramPacket para recibir peticiones
				DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
				socketUDP.receive(peticion);
				// Si la peticion del cliente es Cliente conectado se envia el menu
				if((new String(peticion.getData()).trim()).equals("Cliente conectado")) 
				{
					String menu = "MENU\n----\n1. Es primo\n2. Es Palíndromo\n3. Máximo de tres números\n4. Salir";
					byte mensajeEnviar[] = new byte[1024];
					mensajeEnviar = menu.getBytes();
					DatagramPacket dmenu = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, peticion.getAddress(), peticion.getPort());
					socketUDP.send(dmenu);
				}
				else {
					//System.out.print("Datagrama recibido del host: " + peticion.getAddress());
					//System.out.println(" desde el puerto remoto: " +  peticion.getPort());
					//Si la peticion no es la de Cliente conectado se comvierte la peticion 
					//a entero para elegir la opcion que se selecciono del menu
					int op = Integer.parseInt(new String(peticion.getData()).trim());
					byte mensajeEnviar[] = new byte[1024];
					switch (op) {
					case 1: //Opcion Es primo
						String numParametros = "1"; //Numero de parametros que necesitara la opcion elegida
						mensajeEnviar = numParametros.getBytes();
						DatagramPacket mensaje = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, peticion.getAddress(), peticion.getPort());
						socketUDP.send(mensaje);
						//Indicaciones de la opcion elegida
						String primo = "ES PRIMO\n--------\nIngrese un numero: ";						
						mensajeEnviar = primo.getBytes();
						mensaje = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, peticion.getAddress(), peticion.getPort());
						socketUDP.send(mensaje);
						socketUDP.receive(peticion);
						int n = Integer.parseInt(new String(peticion.getData()).trim());
						//Algoritmo para verificar si un numero es primo
						boolean sw = true;
						for (int i = 2; i < n; i++) {
							if(n%i==0)
							{
								sw = false;
								break;
							}				
						}
						if(n<2)
							sw = false;
						if (sw)
						{
							mensajeEnviar = "Es primo".getBytes();
							mensaje = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, peticion.getAddress(), peticion.getPort());
							socketUDP.send(mensaje);
						}
						else
						{
							mensajeEnviar = "No es primo".getBytes();
							mensaje = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, peticion.getAddress(), peticion.getPort());
							socketUDP.send(mensaje);
						}
						break;
					case 2: //Opcion Es palindromo
						numParametros = "1"; //Numero de parametros que necesitara la opcion elegida
						mensajeEnviar = numParametros.getBytes();
						mensaje = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, peticion.getAddress(), peticion.getPort());
						socketUDP.send(mensaje);
						//Indicaciones de la opcion elegida
						String palindrome = "ES PALINDROME\n-------------\nIngrese la cadena: ";
						mensajeEnviar = palindrome.getBytes();
						mensaje = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, peticion.getAddress(), peticion.getPort());
						socketUDP.send(mensaje);
						socketUDP.receive(peticion);
						String cad = new String(peticion.getData()).trim();
						//Algoritmo para verificar si una cadena es un palimdromo
						StringBuilder builder=new StringBuilder(cad);
						String cadInv = builder.reverse().toString();
						if(cad.equals(cadInv))
						{
							mensajeEnviar = "Es palíndromo".getBytes();
							mensaje = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, peticion.getAddress(), peticion.getPort());
							socketUDP.send(mensaje);
						}
						else
						{
							mensajeEnviar = "No es palíndromo".getBytes();
							mensaje = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, peticion.getAddress(), peticion.getPort());
							socketUDP.send(mensaje);
						}
						break;
					case 3: //Opcion Máximo de tres números
						numParametros = "3"; //Numero de parametros que necesitara la opcion elegida
						mensajeEnviar = numParametros.getBytes();
						mensaje = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, peticion.getAddress(), peticion.getPort());
						socketUDP.send(mensaje);
						int a = 0, b = 0, c = 0;
						//Indicaciones de la opcion elegida
						String maximo = "Máximo de tres números\n----------------------\nIngrese el primero número: ";
						mensajeEnviar = maximo.getBytes();
						mensaje = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, peticion.getAddress(), peticion.getPort());
						socketUDP.send(mensaje);
						buffer = new byte[1000];
						peticion = new DatagramPacket(buffer, buffer.length);
						socketUDP.receive(peticion);
						a = Integer.parseInt(new String(peticion.getData()).trim());
						mensajeEnviar = "Ingrese el segundo número: ".getBytes();
						mensaje = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, peticion.getAddress(), peticion.getPort());
						socketUDP.send(mensaje);
						buffer = new byte[1000];
						peticion = new DatagramPacket(buffer, buffer.length);
						socketUDP.receive(peticion);
						b = Integer.parseInt(new String(peticion.getData()).trim());
						mensajeEnviar = "Ingrese el tercer número: ".getBytes();
						mensaje = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, peticion.getAddress(), peticion.getPort());
						socketUDP.send(mensaje);
						buffer = new byte[1000];
						peticion = new DatagramPacket(buffer, buffer.length);
						socketUDP.receive(peticion);
						c = Integer.parseInt(new String(peticion.getData()).trim());
						//Algoritmo para hallar el maximo de 3 numeros
						if (a>=b && a>=c)
						{
							mensajeEnviar = ("El máximo de los tres números es "+Integer.toString(a)).getBytes();
							mensaje = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, peticion.getAddress(), peticion.getPort());
							socketUDP.send(mensaje);
						}
						else if (b>=a && b>=c)
						{
							mensajeEnviar = ("El máximo de los tres números es "+Integer.toString(b)).getBytes();
							mensaje = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, peticion.getAddress(), peticion.getPort());
							socketUDP.send(mensaje);
						}
						else
						{
							mensajeEnviar = ("El máximo de los tres números es "+Integer.toString(c)).getBytes();
							mensaje = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, peticion.getAddress(), peticion.getPort());
							socketUDP.send(mensaje);
						}
						break;
					default:
						mensajeEnviar = "Opción no valida".getBytes();
						mensaje = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, peticion.getAddress(), peticion.getPort());
						socketUDP.send(mensaje);
						break;
					}
				}
			}
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}
}