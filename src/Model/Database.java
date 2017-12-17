package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {

	private final String DB_NAME = "Roulette.db";
	private final String URL_DB = "jdbc:sqlite:../Single_Player_Roulette/" + DB_NAME;
	private static ResultSet result;
	private Statement statement;
	private Connection conn;

	public Database() {

		try {
			conn = DriverManager.getConnection(URL_DB);

			statement = conn.createStatement();
			createTable();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Create a table
	public void createTable() {
		String sql = "CREATE TABLE IF NOT EXISTS guesses (" + "guess_id  INTEGER PRIMARY Key,\n"
				+ "guess_number INTEGER" + ");";

		try {
			this.statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Insert a guess
	public void InsertValue(int guessedNumber) {
		String sql = "INSERT INTO guesses (" + " guess_number )" + "VALUES (" + guessedNumber + ");";

		try {
			this.statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Retrieve a guess
	public ArrayList<Integer> RetrieveGuesses() {
		ArrayList<Integer> guesses = new ArrayList<Integer>();

		String sql = "SELECT ( guess_number ) FROM guesses;";

		try {
			result = this.statement.executeQuery(sql);

			while (result.next()) {
				guesses.add(result.getInt("guess_number"));
			}

			result.close();
			return guesses;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
