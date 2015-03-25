package Dist;

import java.util.Scanner;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class Pub {
	public static void main(final String[] args) {
		System.out.println("Publisher starting...");
		
		Scanner sc = new Scanner(System.in);
		
		Context context = ZMQ.context(1);
		Socket socket = context.socket(ZMQ.PUB);
		socket.bind("tcp://*:3333");
		socket.bind("ipc://publisher");
		
		while (!Thread.currentThread().isInterrupted()) {
			String msg = sc.next();
			String tmp[] = msg.split(":");
			String upd = String.format("%d %s", Integer.parseInt(tmp[0]), tmp[1]);
			socket.send(upd);
		}
		
		sc.close();
		context.term();
		socket.close();
	}
}
