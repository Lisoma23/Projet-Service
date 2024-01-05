package bri;

public class Prog{
	private String login;
	private String password;
	private String ftpUrl;
	
	public Prog(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}
	
	public String getFtpUrl() {
		return ftpUrl;
	}
	
	public void setFtpUrl(String ftpUrl) {
		this.ftpUrl = ftpUrl;
	}
}