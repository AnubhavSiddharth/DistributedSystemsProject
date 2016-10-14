package kvstore;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class kvclient {

 public static void main(String[] args) {

  try {
   TTransport transport;

   transport = new TSocket("localhost", 9090);
   transport.open();

   TProtocol protocol = new TBinaryProtocol(transport);
   KVStore.Client client = new KVStore.Client(protocol);

   // Do something

   transport.close();
  } 

  catch (TTransportException e) {
   e.printStackTrace();
  } 

  catch (TException x) {
   x.printStackTrace();
  }
 }

}