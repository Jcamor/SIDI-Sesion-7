
package rmi.servidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import ssdd.comun.fichero.ServidorI;
import ssdd.comun.rmi.IniciarRmi;

public class IniciaServidor extends IniciarRmi {
    
    public IniciaServidor() {
        super(Servidor.class);  
    }

    @Override
    public void operacionRMI() {
        try {
        	 Registry registry = LocateRegistry.createRegistry(ServidorI.port);
        	 ServidorI servidor = new Servidor();
        	 ServidorI servidorStub = 
             		(ServidorI)UnicastRemoteObject.exportObject(servidor, ServidorI.port);
        	 registry.rebind(ServidorI.NOMBRE_SERVICIO, servidorStub);
             System.out.println(registry.list().toString());
             System.out.println("Servidor de Ficheros ok, intro para terminar...");
             System.in.read();
             registry.unbind(ServidorI.NOMBRE_SERVICIO);
             UnicastRemoteObject.unexportObject(servidorStub, true);
             UnicastRemoteObject.unexportObject(registry, true);
             
           /* Otra opción: En este caso debemos ejecutar desde consola el rmiregistry en el puerto seleccionado
            * 
            * ServidorI servidor = new Servidor();
            ServidorI servidorStub = (ServidorI)UnicastRemoteObject.exportObject(servidor, ServidorI.port);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(ServidorI.NOMBRE_SERVICIO, servidorStub);
            System.out.println(registry.list().toString());
            System.out.println("Servidor de Ficheros ok, intro para terminar...");
            System.in.read();
            registry.unbind(ServidorI.NOMBRE_SERVICIO);
            UnicastRemoteObject.unexportObject(servidor, true);  */     
           
            
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
  //This method lists the names registered with a Registry object
  	private static void listRegistry(String registryURL) throws RemoteException, MalformedURLException{
  		System.out.println("Registry " + registryURL + " contains: ");
  		String[] names =Naming.list(registryURL);
  		for  (int i=0; i< names.length; i++)
  		{
  			System.out.println(names[i]);
  		}
  	}


    public static void main(String[] args) {
        new IniciaServidor();
    }
}
