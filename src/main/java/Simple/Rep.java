package Simple;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class Rep {
	private static String request = new String();

	public static void main(final String[] args) throws Exception {
		System.out.println("Server start");
		
		Context context = ZMQ.context(1);
		
		Socket socket = context.socket(ZMQ.REP);
		socket.bind("tcp://*:5555");

		while (!request.equals("exit")) {
			request = new String(socket.recv(0));
			System.out.println("Received " + request);

			Thread.sleep(1000);

			String reply = Integer.toString(request.hashCode());
			socket.send(reply.getBytes(), 0);
		}
		socket.close();
		context.term();
	}
}
