package Chat;

import java.util.Scanner;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Poller;
import org.zeromq.ZMQ.Socket;

public class Clnt {
	static String send_msg = new String();

	public static void main(final String[] args) throws InterruptedException {
		ZMQ.Context context = ZMQ.context(1);
		Socket sub = context.socket(ZMQ.SUB);
		Socket push = context.socket(ZMQ.PUSH);

		push.connect("tcp://localhost:5556");

		sub.connect("tcp://localhost:5555");
		sub.subscribe("".getBytes());

		Poller poller = new Poller(0);
		poller.register(sub, Poller.POLLIN);

		Thread myThready = new Thread(new Runnable() {
			@Override
			public void run() {
				Scanner sc = new Scanner(System.in);
				while (!Thread.currentThread().isInterrupted()) {
					send_msg = sc.nextLine();
					push.send(send_msg);
				}

				sc.close();
				push.close();
			}
		});
		myThready.start();

		while (!Thread.currentThread().isInterrupted() && !myThready.isInterrupted()) {
			Thread.sleep(500);
			if ((int) poller.poll() > 0) {
				String get_msg = sub.recvStr();
				if (!get_msg.equals(send_msg))
					System.out.println("print: " + get_msg);
			}
		}

		context.term();
		sub.close();
	}
}
