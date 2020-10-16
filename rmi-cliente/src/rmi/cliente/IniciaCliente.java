
package rmi.cliente;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import ssdd.comun.fichero.Fichero;
import ssdd.comun.fichero.ServidorI;
import ssdd.comun.rmi.IniciarRmi;

public class IniciaCliente extends IniciarRmi {

	private static String nombre;
	private static Console console = System.console();
	private static BufferedReader reader = new BufferedReader(
											new InputStreamReader(System.in));

    public IniciaCliente() {
        super(IniciaCliente.class);
    }

    @Override
    public void operacionRMI() {
        try {        	
        	System.out.println("Introduzca su nombre:");
    		nombre = leerConcola();	
			String opt = "";			
			do {
			    System.out.println("Elija la operacion:");
			    System.out.println("1 - Enviar");
			    System.out.println("2 - Obtener");
			    System.out.println("3 - Salir");
			    opt = leerConcola();
			    
				switch (opt) {
					case "1": enviar(); break;
					case "2": recibir();  break;					
				}
			}
			while (!opt.equals("3"));
        }
        catch(Exception e) {
            e.printStackTrace();
        }   
    }

    private static void enviar() {
    	try {
	    	System.out.println("Introduzca el nombre del fichero a guardar:");
			String nombreFichero = leerConcola();
			ServidorI servidor;		
			
			Registry registry = LocateRegistry.getRegistry(ServidorI.port);
			servidor = (ServidorI)registry.lookup(ServidorI.NOMBRE_SERVICIO);
			Fichero fichero= new Fichero(nombreFichero, nombre);	
			//Enviamos el fichero
			if (servidor.enviarFichero(fichero)==false)
			{
				System.err.println("Error en el envio (Checksum failed).");
			}
			else{
				System.out.println("Fichero: "+nombreFichero+ " guardado");	
			}
    	} catch (RemoteException e) {			
			System.err.println("Error en el servidor. " + e.getMessage());
		} catch (NotBoundException e) {
			System.err.println("Servidor no encontrado");
		} catch (Exception e) {			
			System.err.println("Error genérico");
		} 
		
	}
	
	private static void recibir() {
		try {
			System.out.println("Introduzca el nombre del fichero a obtener:");
			String nombreFichero = leerConcola();			
			ServidorI servidor;
			Registry registry = LocateRegistry.getRegistry(ServidorI.port);
			servidor = (ServidorI)registry.lookup(ServidorI.NOMBRE_SERVICIO);
			Fichero fichero = servidor.obtenerFichero(nombre, nombreFichero);
			OutputStream os;
			nombreFichero = fichero.obtenerNombre()+".rcb"; 
			//Ponemos .rcb para no confundir con el original			
			os = new FileOutputStream(nombreFichero); 
			if (fichero.escribirEn(os)==false)
			{
				os.close();
			}
			os.close();
			System.out.println("Fichero " + fichero.obtenerNombre() + " recibido");
		} catch (FileNotFoundException e) {			
			System.err.println("Fichero no encontrado");
		} catch (RemoteException e) {			
			System.err.println("Error en el servidor. " + e.getMessage());
		} catch (IOException e) {			
			System.err.println("Error al guardar fichero");
		} catch (NotBoundException e) {
			System.err.println("Servidor no encontrado");
		} catch (NullPointerException e) {			
			System.err.println("Fichero no encontrado");
		} 
		
	}
	
	private static String leerConcola() {	
		if (console != null) return console.readLine();
		try {
			return reader.readLine();
		} 
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
    public static void main(String[] args) {
        new IniciaCliente();
    }
}
