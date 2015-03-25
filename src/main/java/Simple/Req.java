package Simple;

import java.util.Scanner;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class Req {
	private static final String TCP_LOCALHOST_2222 = "tcp://localhost:2222";
	private static String msg_from_server = new String();
	private static String msg_to_server = new String();

	public static void main(final String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		Context context = ZMQ.context(1);
		Socket socket = context.socket(ZMQ.REQ);
		socket.connect(TCP_LOCALHOST_2222);
		
		while (!msg_to_server.equals("exit")) {
			msg_to_server = scanner.next();
			socket.send(msg_to_server);
			
			msg_from_server  = socket.recvStr();
			System.out.println(msg_from_server );
		}
		
		scanner.close();
		socket.close();
		context.term();
		
	}
}
