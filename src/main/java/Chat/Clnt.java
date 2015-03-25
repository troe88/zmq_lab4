package Chat;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class Clnt {
	public static void main(final String[] args) {
		ZMQ.Context context = ZMQ.context(1);
		Socket sub = context.socket(ZMQ.SUB);
		sub.connect("tcp://localhost:5555");
		sub.subscribe("".getBytes());


		while (!Thread.currentThread().isInterrupted()) {
			System.out.println(sub.recvStr());
			//
		}
		
		context.term();
		sub.close();
	}
}
