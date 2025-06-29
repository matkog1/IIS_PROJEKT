package hr.algebra.validationservers.xmlrpc;

import org.apache.xmlrpc.webserver.WebServer;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;

public class RpcServer {
    public static void start() {
        try {
            WebServer server = new WebServer(8089);
            XmlRpcServer xmlRpcServer = server.getXmlRpcServer();

            PropertyHandlerMapping phm = new PropertyHandlerMapping();
            phm.addHandler("dhmz", DHMZ.class);

            xmlRpcServer.setHandlerMapping(phm);
            server.start();

            System.out.println("XML-RPC Server radi na portu 8089...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
