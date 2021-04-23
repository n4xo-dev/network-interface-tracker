package lopezosa.uma.es;

import java.net.SocketException;

public class Main {
    public static void main(String[] args) {   	
        try {
			NetworkInterfaceTracker.showActiveInterfaces();
		} catch (SocketException e) {
			System.out.println("SocketException: " + e.getMessage());
		}
    }
}
