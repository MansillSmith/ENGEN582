import java.io.BufferedInputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Server{

    public class ServerConnection{
        // The socket for the connection
        private Socket socket;

        public ServerConnection(Socket socket){
            this.socket = socket;
        }

        public void SendString(String string){

        }

        public String WaitToRecieveString(){
            try{
                BufferedInputStream bif = new BufferedInputStream(socket.getInputStream());
                while(bif.available() != 0){                    
                        String recievedString = new String(bif.readAllBytes(), StandardCharsets.UTF_8);
                        bif.close();
        
                        return recievedString;

                }
            }
            catch(Exception e){

            }
            return null;
        }
    }

    private class AllowConnections implements Runnable{
        public void run(){
            // TODO: Change loop criteria
            while (true){
                try{
                    Socket client = simpleServer.accept();
                    System.out.print("Connected to" + client.getInetAddress());
                    listServerConnection.add(new ServerConnection(client));
                }
                catch (Exception e){
                    System.err.println("Error: " + e);
                }
            }
        }
    }

    // The socket the server operates on
    public ServerSocket simpleServer;

    // The connections on the server
    public ArrayList<ServerConnection> listServerConnection;

    // The thread for handleing opening the server
    private Thread threadOpenServer;

    public static void main(String args[]){
        Server server = new Server();
        server.Open();

        server.WaitForConnection();

        System.out.println("Received:" + server.listServerConnection.get(0).WaitToRecieveString());
    }

    public Server(){
        listServerConnection = new ArrayList<ServerConnection>();
        try{
            simpleServer = new ServerSocket(0, 1, getLocalHostLANAddress());
            System.out.println("IP: " + simpleServer.getInetAddress().toString().substring(1) + "\nPort: " + simpleServer.getLocalPort());
        }
        catch (Exception e){
            System.err.println("Error: " + e);
        }
    }

    // Opens the server to clients
    public void Open(){
        threadOpenServer = new Thread(new AllowConnections());
        threadOpenServer.start();
    }

    public void WaitForConnection(){
        while(this.listServerConnection.size() == 0){
            try{
                Thread.sleep(500);
            }
            catch(Exception e){
                
            }
        }
    }

    public void CloseServer(){
        try{
            threadOpenServer.join();
            simpleServer.close();
        }
        catch (Exception e){
            System.err.println("Error: " + e);
        }
    }


    //Gets the IP address of the server
    private InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // Iterate all NICs (network interface cards)...
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // Iterate all IP addresses assigned to each card...
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {
    
                        if (inetAddr.isSiteLocalAddress()) {
                            // Found non-loopback site-local address. Return it immediately...
                            return inetAddr;
                        }
                        else if (candidateAddress == null) {
                            // Found non-loopback address, but not necessarily site-local.
                            // Store it as a candidate to be returned if site-local address is not subsequently found...
                            candidateAddress = inetAddr;
                            // Note that we don't repeatedly assign non-loopback non-site-local addresses as candidates,
                            // only the first. For subsequent iterations, candidate will be non-null.
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                // We did not find a site-local address, but we found some other non-loopback address.
                // Server might have a non-site-local address assigned to its NIC (or it might be running
                // IPv6 which deprecates the "site-local" concept).
                // Return this non-loopback candidate address...
                return candidateAddress;
            }
            // At this point, we did not find a non-loopback address.
            // Fall back to returning whatever InetAddress.getLocalHost() returns...
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        }
        catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException("Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }
}