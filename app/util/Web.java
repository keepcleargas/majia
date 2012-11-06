package util;

public class Web {
	private String webName;
	private String url;
	private long count;
	
	public String getWebName() {
    	return webName;
    }
	public void setWebName(String webName) {
    	this.webName = webName;
    }
	public String getUrl() {
    	return url;
    }
	public void setUrl(String url) {
    	this.url = url;
    }
	public long getCount() {
    	return count;
    }
	public void setCount(long count) {
    	this.count = count;
    }
	
	@Override
    public String toString() {
	    return "Web [webName=" + webName + ", url=" + url + ", count=" + count
	            + "]";
    }	
}
