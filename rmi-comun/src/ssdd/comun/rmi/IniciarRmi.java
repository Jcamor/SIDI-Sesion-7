package ssdd.comun.rmi;

public abstract class IniciarRmi {

    public IniciarRmi(Class<?> clase) {

        System.setProperty("java.rmi.server.codebase", clase
            .getProtectionDomain().getCodeSource().getLocation().toString());

        System.setProperty("java.security.policy", PoliticaSeguridad.getLocationOfPolicyFile());

        if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        operacionRMI();
    }

    // Extenderemos esta clase para hacer las RMI 
    public abstract void operacionRMI();

}
