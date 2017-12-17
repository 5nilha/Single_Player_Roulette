package Controller;

import java.util.Random;
import View.GameGUI;
import javafx.scene.control.Dialog;

public class ThreadsWheels implements Runnable {

	Dialog d;

	public ThreadsWheels(Dialog dialog) {
		this.d = dialog;
	}

	@Override
	public void run() {

		Random rand = new Random();

		try {
			Thread.sleep(10000);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("wheels Stopped to run");
		Game.wheelsNumber = rand.nextInt(10) + 1;
		GameGUI.closeDialog();

	}

}
