package util;

public class URLHelper {
	// util
	public static String urlConvert(String url) {
		String newUrl = url.toLowerCase().trim();
		if(newUrl.startsWith("https://")){
			newUrl = newUrl.replace("https://", "");
		}
		if(newUrl.startsWith("http://")){
			newUrl = newUrl.replace("http://", "");
		}
		int index = newUrl.indexOf("/");
		if(index!=-1){
			newUrl = newUrl.substring(0,index);
		}
		if((newUrl.startsWith("www."))){
			newUrl = newUrl.replace("www.", "");
		}
		
		if (!newUrl.contains(".")) {
			newUrl = newUrl.concat(".com");
		}
		return newUrl;
	}
}
