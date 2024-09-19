package controller;

import java.util.concurrent.Semaphore;

public class ThreadCarro extends Thread{
	private static int[] tocha = new int[1], pedra = new int[1], portas = {2, 1, 2, 2};
	private int tid, passo, tot, dist = 500, nump;
	private Semaphore semaforo;
        private Semaphore semaforotocha = new Semaphore(1), semaforopedra = new Semaphore(1);
	
	public ThreadCarro(int tid, Semaphore semaforo) {
		this.tid = tid;
		this.semaforo = semaforo;
	}
	
	public void run() {
		percurso();
		try {
			semaforo.acquire();
			portas();
		}catch(Exception t) {
			t.printStackTrace();
		}finally {
			semaforo.release();
		}
	}
	
	private void percurso() {
		while(tot < dist) {
			passo = (int)(Math.random() * 2.1) + 2;
			if(tocha[0] == tid) {
				passo += 2;
			}
			if(tot >= 100 && tocha[0] == 0) {
                                try{
                                    semaforotocha.acquire();
									if(tot >= 100 && tocha[0] == 0) {
                                    	tocha();
									}
                                }catch(Exception e1){
                                    e1.printStackTrace();
                                }finally{
                                    semaforotocha.release();
                                }
			}
			if(tot >= 250 && pedra[0] == 0 && tocha[0] != tid) {
                                try{
                                    semaforopedra.acquire();
									if(tot >= 250 && pedra[0] == 0 && tocha[0] != tid) {
                                    	pedra[0] = tid;
				    					System.out.println("O cavaleiro "+tid+" pegou a pedra.");
									}
                                }catch(Exception e1){
                                    e1.printStackTrace();
                                }finally{
                                    semaforopedra.release();
                                }
			}
			if(pedra[0] == tid) {
				passo +=2;
			}
			tot += passo;
			if(tot >= 500) {
				System.out.println("O cavaleiro "+tid+" andou "+passo+" metros e já percorreu no total 500 metros.");
			}else {
			System.out.println("O cavaleiro "+tid+" andou "+passo+" metros e já percorreu no total "+tot+" metros.");
			}
			try {
				sleep(50);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("O cavaleiro "+tid+" finalizou o túnel e chegou às portas.");
	}
	
	public void portas() {
		boolean por = true;
		while(por == true) {
			nump = (int)(Math.random() * 4);
			if(portas[nump] == 0) {
				por = true;
			}else {
			    por = false;
			}
		}
		System.out.println("O cavaleiro "+tid+" vai abrir a porta "+(nump+1)+".");
		if(portas[nump] == 1) {
			portas[nump] = 0;
			System.out.println("Parabéns! O cavaleiro "+tid+" conseguiu sair.");
		}else {
			portas[nump] = 0;
			System.out.println("Que pena! O cavaleiro "+tid+" foi devorado.");
		}
		
	}
        
        public void tocha(){
            tocha[0] = tid;
            System.out.println("O cavaleiro "+tid+" pegou a tocha.");
        }
}
