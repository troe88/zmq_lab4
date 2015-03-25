package Simple;

import java.util.Scanner;

import org.zeromq.ZMQ;

public class Req {
	private static final String TCP_LOCALHOST_5555 = "tcp://localhost:5555";

	public static void main(final String[] args) {

		Scanner scanner = new Scanner(System.in);
		
		ZMQ.Context context = ZMQ.context(1);

		System.out.println("Connecting to " + TCP_LOCALHOST_5555);

		ZMQ.Socket requester = context.socket(ZMQ.REQ);
		requester.connect(TCP_LOCALHOST_5555);

		String request = new String();
		while(!request.equals("exit")){
			request = scanner.next();
			System.out.println("Sending " + request);
			requester.send(request.getBytes(), 0);

			byte[] reply = requester.recv(0);
			System.out.println("Received " + new String(reply));
		}
		
		scanner.close();
		requester.close();
		context.term();
	}
}
