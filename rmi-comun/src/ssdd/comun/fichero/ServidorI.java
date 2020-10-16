package ssdd.comun.fichero;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServidorI extends Remote{
	public static final String NOMBRE_SERVICIO = "ServidorFicheros"; 
	public static final String host = "localhost";
	public static final int port = 7777;	
	
	public boolean enviarFichero(Fichero fichero) throws RemoteException;
	public Fichero obtenerFichero(String propietario, String nombreFichero) throws RemoteException;
}
