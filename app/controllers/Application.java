package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;
import util.Account;
import util.URLHelper;
import util.Web;

import java.util.*;

import models.*;

public class Application extends Controller {

	public static void index(int index) {
		if (index == 0) { // 如果直接通过www.majiahao.com访问的 访问+1
			visitLog();
		}
		render();
	}

	/** 添加马甲页 **/
	public static void addIndex() {
		render();
	}

	/**   马甲广场    **/
	public static void plaza(){
		List<Web> webs = new ArrayList<Web>();
		webs  = Share.getAvailableWebs();
		render(webs);
	}
	public static void search(@Required String keyword) {
		if (validation.hasErrors()) {
			validation.keep();
			flash.error("请输入网址如:qq.com");
			index(1);
		} else if (keyword.length() > 200) {
			flash.error("网址长度请小于200位");
			index(1);
		} else {
			String newKeyword = URLHelper.urlConvert(keyword);
			searchLog(newKeyword);
			view(newKeyword);
		}
	}

	public static void view(String keyword) {
		Website website = Website.findByUrl(keyword);
		if (website == null) {
			render(keyword);
		} else {
			List<Account> accounts = Share.getAccounts(website.webId);
			render(website, accounts);
		}
	}

	public static void tutorial() {
		render();
	}

	public static void mianze() {
		render();
	}

	public static void aboutUs() {
		render();
	}

	public static String top(long shareId) {
		Share s = Share.findById(shareId);
		feedbackTopDown(s,1);
		return "success";
	}

	public static String down(long shareId) {
		Share s = Share.findById(shareId);
		feedbackTopDown(s,0);
		return "success";
	}

	public static String modifyWebsite(String url,String webName,String des){
		if(webName.trim() ==""){
			return "WEBNAME_IS_BLANK";
		}else if(webName.trim().length()>10){
			return "WEBNAME_IS_TOO_LONG";
		}
		Website web = Website.findByUrl(url);
		web.webName = webName;
		web.description = des;
		web.save();
		return "success";
	}
	public static void AddMaJia(@Required String url, @Required String majiaID,
	        @Required String majiaPwd) {
		if (validation.hasErrors()) {
			validation.keep();
			params.flash();
			flash.error("请输入网址:马甲帐号:密码");
			addIndex();
		}
		String newUrl = URLHelper.urlConvert(url);
		if (majiaID.trim().length() > 50) {
			params.flash();
			flash.error("马甲号须小于50位");
			view(newUrl);
		}
		if (majiaPwd.trim().length() > 50) {
			params.flash();
			flash.error("密码须小于50位");
			view(newUrl);
		}
		Website website = Website.findByUrl(newUrl);
		if (website == null) {
			website = new Website(newUrl);
			new Share(majiaID, majiaPwd, website);
			flash.success("添加新的马甲号成功");
			view(newUrl);
		} else {
			if (Share.getShares(website.webId, majiaID).size() == 0) {
				new Share(majiaID, majiaPwd, website);
				flash.success("添加新的马甲号成功");
				view(newUrl);
			} else {
				params.flash();
				flash.error("这个马甲号已经存在");
				view(newUrl);
			}
		}
	}

	/**
	 * 可改成ajax 异步提交 可节省流量 view(url)--->这样要刷一次页面，最新更新会及时显示
	 * 
	 * @author keepcleargas
	 * @Date： 2012-9-28
	 * @param url
	 * @param webId
	 * @param majiaID
	 * @param majiaPwd
	 */
	public static void add(String url, long webId, @Required String majiaID,
	        @Required String majiaPwd) {
		if (validation.hasErrors()) {
			validation.keep();
			params.flash();
			flash.error("请输入马甲帐号:密码");
			view(url);
		}
		if (majiaID.trim().length() > 50) {
			params.flash();
			flash.error("马甲号须小于50位");
			view(url);
		}
		if (majiaPwd.trim().length() > 50) {
			params.flash();
			flash.error("密码须小于50位");
			view(url);
		}
		// System.out.println("【url:"+url+"] [webId:"+webId+"] majiaID:"+majiaID+"majiaPwd"+majiaPwd);
		if (webId == 0) {
			Website website = new Website(url);
			new Share(majiaID, majiaPwd, website);
			flash.success("添加新的马甲号成功");
			view(url);
		} else {
			if (Share.getShares(webId, majiaID).size() == 0) {
				Website website = new Website(webId);
				new Share(majiaID, majiaPwd, website);
				flash.success("添加新的马甲号成功");
				view(url);
			} else {
				params.flash();
				flash.error("这个马甲号已经存在");
				// to do 重复输入 即 投正票
				view(url);
			}
		}
	}

	/**
	 * 访问日志
	 * 
	 * @author keepcleargas
	 * @Date： 2012-10-1
	 */
	static void visitLog() {
		String ip = Http.Request.current.get().remoteAddress;
		long time = System.currentTimeMillis();
		new VisitLogger(ip, time);
	}

	static void searchLog(String keyword) {
		long time = System.currentTimeMillis();
		new SearchLogger(keyword, time);
	}

	/**
	 * 
	 * @author keepcleargas
	 * @Date： 2012-10-15
	 * @param s
	 * @param type
	 *            0:down 1:top
	 */
	static void feedbackTopDown(Share s, int type) {
		String ip = Http.Request.current.get().remoteAddress;
		long time = System.currentTimeMillis();
		long webId = s.website.webId;
		if (!TopDown.findTopDown(ip, webId, time)) {
			new TopDown(ip, webId, s.shareId, time);
			if (type == 0) {
				s.addDown();
			} else {
				s.addTop();
			}
		}
	}
}