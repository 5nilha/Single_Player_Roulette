package Model;

public class Player implements CreatePlayer {

	private String name;
	private String nickName;

	public Player(String name, String cassinoNickName) {
		this.name = name;
		this.nickName = cassinoNickName;
	}

	public Player() {
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setNickname(String name) {
		this.nickName = name;
	}

	@Override
	public String getNickname() {
		return this.nickName;
	}

}
