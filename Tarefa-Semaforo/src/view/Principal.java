package view;

import java.util.concurrent.Semaphore;
import controller.Threadcontroller;

public class Principal {

	public static void main(String[] args) {
		Semaphore semaforo = new Semaphore(1);
		for(int j = 1; j < 5; j++) {
			Thread tc = new Threadcontroller(j, semaforo);
			tc.start();
		}
	}

}
