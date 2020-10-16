package rmi.servidor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ssdd.comun.fichero.Fichero;
import ssdd.comun.fichero.ServidorI;

public class Servidor implements ServidorI {

	private static final long serialVersionUID = -791206761184971895L;

	public Servidor() throws RemoteException
	{
		super();
	}

	@Override
	public boolean enviarFichero(Fichero fichero) throws RemoteException {
		OutputStream os;
		File repositorio = new File(fichero.obtenerPropietario());
		if (!repositorio.exists()) 
			repositorio.mkdir();
		
		String nombreFichero = fichero.obtenerPropietario() + "/" + fichero.obtenerNombre();
		
		try {
			os = new FileOutputStream(nombreFichero); //Ponemos .rcb para no confundir con el original
			if (fichero.escribirEn(os)==false)
			{
				os.close();
				return false;
			}
			os.close();
			System.out.println("Fichero "+ fichero.obtenerNombre()  + " ( "+ fichero.obtenerPropietario() +" ) recibido");
		} catch (FileNotFoundException e) {
			return false;
			//e.printStackTrace();
		} catch (IOException e) {
			return false;
			//e.printStackTrace();
		}	
		return true;
	}
	
	@Override
	public Fichero obtenerFichero(String propietario, String nombreFichero) throws RemoteException {
		Fichero fichero= new Fichero(propietario, nombreFichero, propietario);
		return fichero;
	}
}
