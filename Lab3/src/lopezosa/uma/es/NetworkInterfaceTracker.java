package lopezosa.uma.es;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Iterator;

public class NetworkInterfaceTracker {
	
	public static void showActiveInterfaces() throws SocketException {
		for (Iterator<NetworkInterface> it = NetworkInterface.getNetworkInterfaces().asIterator(); it.hasNext(); ) {
	        NetworkInterface net = it.next();
	        if(net.isUp()){
	        	InterfaceAddress address = net.getInterfaceAddresses().get(0);	// First IP address for given interface
	            String ip = address.getAddress().getHostAddress();				
	        	short mask = address.getNetworkPrefixLength();					// Netmask for the previous IP
	        	byte[] rawIP =address.getAddress().getAddress();
	        	
	        	System.out.print(net.getName() + ": MAC = ");
	            
	            try {
	                System.out.print(MacManager.mac2text(net.getHardwareAddress()));
	            } catch (NullPointerException e){
	                System.out.print("No disponible");
	            }
	            
	            System.out.print(" | IP = " + ip + " (" + maskIP(rawIP, mask) + "/" + mask + ")\n");
	        }
	    }	
	}

	private static String maskIP(byte[] ip, short mask) {
		
		String result = "";
		int blockCounter;
		int rest = (mask % 8);
		int block;				// Auxiliary variable to print blocks
		
		// All blocks of 1s are included directly to result
		for(blockCounter = 0; blockCounter < mask / 8; blockCounter++) {
			block = (ip[blockCounter] < 0) ? (256 + ip[blockCounter]) : ip[blockCounter]; // Transform from negative to positive notation
			result += block + ".";
		}
		
		// If rest != 0 there is a block with 1s and 0s
		// Here we need to use the AND operator to find out the correct Net route
		if(rest != 0) {
			block = 0;
			
			for(int i = 0; i < rest; i++) {
				block += Math.pow(2, 7 - i);	// Calculate the mask for this block (e.g., 1111 0000)
			}
			
			result += (block & ip[blockCounter]) + ".";	// Calculate block's route using its mask with the IP
			
			blockCounter++;								// One block has been added to result, this increase block counter
		}
		
		// Print the rest of blocks as 0s
		for(; blockCounter < 4 ; blockCounter++) {
			result += "0.";
		}	
		
		return result.substring(0,result.length()-1); // Remove last .
	}
	
	
	
}
