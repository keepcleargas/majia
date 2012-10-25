package util;

public class Account {
	private long shareId;
	private String username;
	private String password;
	private int top;
	private int down;
	private float rate;

	public long getShareId() {
    	return shareId;
    }

	public void setShareId(long shareId) {
    	this.shareId = shareId;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getDown() {
		return down;
	}

	public void setDown(int down) {
		this.down = down;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	@Override
    public String toString() {
	    return "Account [shareId=" + shareId + ", username=" + username
	            + ", password=" + password + ", top=" + top + ", down=" + down
	            + ", rate=" + rate + "]";
    }

	
}
