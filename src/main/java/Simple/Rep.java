package Simple;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class Rep {

	private static final String TCP_2222 = "tcp://*:2222";
	private static String msg = new String();

	public static void main(final String[] args) throws InterruptedException {
		System.out.println("Server start!");

		Context context = ZMQ.context(1);
		Socket socket = context.socket(ZMQ.PULL);
		socket.bind(TCP_2222);

		while (!Thread.currentThread().isInterrupted()) {
			msg = socket.recvStr();
			System.out.println(msg);

			//Thread.sleep(1000);

			//socket.send("Recv: " + msg);
		}
		
		socket.close();
		context.term();
	}
}
