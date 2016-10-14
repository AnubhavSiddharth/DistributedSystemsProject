package kvstore;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class kvclient {
	
	public static final String EMPTY_STRING = "";
	public static final int SUCCESS = 0;
	public static final int NOT_FOUND = 1;
	public static final int ERROR = 2;
	
	/**
	 *  Function to print generalized error.
	 */
	static void printError(){
		System.out.println("Bad command!");
		System.exit(2);
	}
	
	/**
	 * @param result
	 * 
	 * Function to process result object.
	 */
	static void processResult(Result result){
		String errorText = EMPTY_STRING;
		if(null != result){
			ErrorCode errorCode =  result.getError();
			switch(errorCode.getValue()){
				case SUCCESS:
					String value = result.getValue();
					System.out.println(value);
					System.exit(SUCCESS);
					break;
					
				case NOT_FOUND:
					errorText = result.getErrortext();
					if(null != errorText) {
						System.out.println(errorText);
					}
					System.exit(NOT_FOUND);
					break;
					
				case ERROR:
					errorText = result.getErrortext();
					if(null != errorText) {
						System.out.println(errorText);
					}
					System.exit(ERROR);
					break;
			}
		}
		else {
			System.out.println("Empty Result");
			System.exit(2);
		}
	}
	
	 /**
	 * @param args
	 */
	public static void main(String[] args) {
	
	  try {
	   TTransport transport;
	   
	   // Do something
	   
	   //Server Name
	   String server = args[0];
	   
	   //Get the server address
	   String[] address = args[1].split(":");
	   
	   String ip = address[0];
	   String port = address[1];
	   
	   //Open a socket to server
	   transport = new TSocket(ip, Integer.parseInt(port));
	   transport.open();
	
	   TProtocol protocol = new TBinaryProtocol(transport);
	   KVStore.Client client = new KVStore.Client(protocol);
	   
	   String operation = args[2];
	   String key = EMPTY_STRING;
	   Result res = null;	
	   
	   String o1 = (operation.substring(operation.indexOf("-") + 1, operation.length()));
	   System.out.println(o1);
	   switch (o1) {
	   
		   case "get":
			   if(null != args[3]){
				   //To Do 
				   String filename = args[5];
				   key = args[3];
				   res = client.kvget(key);
				   
				   processResult(res);
			   }
			   else{
				  printError();
			   }
			   break;
		   case "set":
			   if(null != args[3] && null != args[4]){
				   key = args[3];
				   String value = args[4];
				   res = client.kvset(key, value);

				   processResult(res);
			   }
			   else{
				   printError();
			   }
			   break;
		   case "del":
			   if(null != args[3]){
				   key = args[3];
				   res = client.kvdelete(key);
				   processResult(res);
			   }
			   else{
				   printError();
			   }
			   break;
			   
			 default:
				 printError();
				 break;
	   }
	   
	
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