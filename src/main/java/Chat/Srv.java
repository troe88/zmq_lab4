package Chat;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class Srv {

	public static void main(final String[] args) {
		System.out.println("Server starting...");
		ZMQ.Context context = ZMQ.context(1);
		Socket pub = context.socket(ZMQ.PUB);
		Socket pull = context.socket(ZMQ.PULL);

		pub.bind("tcp://*:5555");
		pub.bind("ipc://chat");

		pull.bind("tcp://*:5556");

		while (!Thread.currentThread().isInterrupted()) {
			String get_msg = pull.recvStr();

			pub.send(get_msg);
		}
		context.term();
		pub.close();
		pull.close();
	}
}
