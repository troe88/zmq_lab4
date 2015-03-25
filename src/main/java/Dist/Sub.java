package Dist;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class Sub {
	public static void main(final String[] args) throws InterruptedException {
		Context context = ZMQ.context(1);
		Socket socket = context.socket(ZMQ.SUB);
		socket.connect("tcp://localhost:3333");
		System.out.println(args.length);
		socket.subscribe("10000".getBytes());
		socket.subscribe(args[0].getBytes());
		
		while (!Thread.currentThread().isInterrupted()) {
			Thread.sleep(1000);
			System.out.println(socket.recvStr());
		}

		context.term();
		socket.close();
	}
}
