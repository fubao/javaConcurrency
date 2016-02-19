package com.concurrency.Phaser;

import java.util.concurrent.Phaser;

import com.concurrency.Phaser.PhaserTest_3.Task_03;

public class PhaserTest_4 {
	public static void main(String[] args) {
        final int phaseToTerminate = 3;
		Phaser phaser = new Phaser(5){
			 protected boolean onAdvance(int phase, int registeredParties) { 
				 System.out.println("ִ��onAdvance����.....");
				 return phase >= phaseToTerminate || registeredParties == 0; 
			 }
		};
		
		for(int i = 0 ; i < 5 ; i++){
			Task_03 task = new Task_03(phaser);
			Thread thread = new Thread(task,"task_" + i);
			thread.start();
		}
		
		phaser.register();
		while(!phaser.isTerminated()){
			phaser.arriveAndAwaitAdvance();
		};
		
		System.out.println("���߳������Ѿ�����....");
	}
	
	static class Task_04 implements Runnable{
		private final Phaser phaser;
		
		public Task_04(Phaser phaser){
			this.phaser = phaser;
		}
		
		@Override
		public void run() {
			do{
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "��ʼִ������...");
				phaser.arriveAndAwaitAdvance();
			}while(!phaser.isTerminated());
		}
		
	}
}
