package Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import Model.Database;
import View.GameGUI;

public class Game {

	public static int wheelsNumber;
	private int guessedNumber;
	private HashMap<Integer, Double> prizes = new HashMap<Integer, Double>();
	private ArrayList<Integer> guesses = new ArrayList<Integer>();
	private Database database;
	private double totalPrize;

	public Game() {
		addPrizes();
		database = new Database();
		totalPrize = 0;
	}

	public void startGame() throws InterruptedException {
		wheelsStartSpinning();
	}

	// Spinning the wheel
	private void wheelsStartSpinning() throws InterruptedException {

		Thread t1 = new Thread(new ThreadsWheels(GameGUI.dialog));
		t1.start();
	}

	public int getwheelsNumber() {
		return wheelsNumber;
	}

	// set the guessedNumber and saves to the database
	public void setGuessedNumber(int guessedNumber) {
		this.guessedNumber = guessedNumber;
		database.InsertValue(guessedNumber);
	}

	public boolean isWinner() {
		if (wheelsNumber == guessedNumber) {
			// USER WIN
			return true;
		} else {
			// USER LOSE
			return false;
		}
	}

	// Prizes
	public double winnerPrize() {

		if (isWinner()) {
			// USER WIN
			double prize = prizes.get(guessedNumber);
			totalPrize += prize;
			return prize;
		} else {
			// USER LOSE
			return 0;
		}
	}

	// Add the prizes to HashMap
	private void addPrizes() {

		prizes.put(1, 1000.00);
		prizes.put(2, 5000.00);
		prizes.put(3, 10000.00);
		prizes.put(4, 20000.00);
		prizes.put(5, 35000.00);
		prizes.put(6, 50000.00);
		prizes.put(7, 60000.00);
		prizes.put(8, 75000.00);
		prizes.put(9, 85000.00);
		prizes.put(10, 100000.00);
	}

	public double getTotalPrize() {
		return this.totalPrize;
	}

	// Retrieve all guesses and select the last ten
	public ArrayList<Integer> lastTenGuesses() {
		ArrayList<Integer> lastGuessesList = new ArrayList<Integer>();

		guesses.addAll(database.RetrieveGuesses());

		if (guesses.size() > 10) {
			for (int i = guesses.size() - 1; i > guesses.size() - 11; i--) {
				lastGuessesList.add(guesses.get(i));
			}
		} else {
			lastGuessesList.addAll(guesses);
			Collections.reverse(lastGuessesList);
		}

		return lastGuessesList;
	}

}
