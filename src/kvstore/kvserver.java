package kvstore;

import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;

public class kvserver {

 public static void StartServer(KVStore.Processor<KVStoreHandler> processor) {
  try {
   TServerTransport serverTransport = new TServerSocket(9090);
   TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

   System.out.println("Starting the Java server...");
   server.serve();
  } 

  catch (Exception e) {
	  e.printStackTrace();
  }
 }
 
 public static void main(String[] args) {
  StartServer(new KVStore.Processor<KVStoreHandler>(new KVStoreHandler()));
 }

}