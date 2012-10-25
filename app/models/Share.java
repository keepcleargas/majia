package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
import util.Account;

/**
 * 匿名(公共)分享
 * 
 * @author keepcleargas
 * @Date 2012-9-27
 */
@Entity
@Table(name = "share")
public class Share extends GenericModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "share_id")
	public long shareId;

	public String username;
	public String password;
	/** 顶 踩 **/
	public int top;
	public int down;
	/** 0:可用 1:不可用 **/
	@Column(name = "delete_mark")
	public int deleteMark;
	/**添加的时间戳**/
	@Column(name = "add_time")
	public long addTime;
	@ManyToOne
	@JoinColumn(name = "web_id")
	public Website website;

	public Share(String username,String password,Website website){
		this.username = username;
		this.password = password;
		this.website = website;
		this.top =1;
		this.addTime = System.currentTimeMillis();
		this.save();
	}
	/**
	 * 顶踩
	 * @author keepcleargas
	 * @Date：   2012-9-28
	 */
	public void addTop(){
		this.top = this.top +1;
		save();
	}
	public void addDown(){
		this.down = this.down +1;
		save();
	}
	
	public static void markDelete(long shareId){
		Share s = Share.findById(shareId);
		s.deleteMark = 1;
		s.save();
	}

	/**
	 * 判断是否已经存咋记录
	 * @author keepcleargas
	 * @Date：   2012-9-28
	 * @param webId 网站ID
	 * @param username 马甲名
	 * @param password 马甲密码
	 * @return
	 */
	public static Share getShare(long webId,String username,String password){
		Share s = Share.find("select s from Share s where s.website.webId =? and s.username=? and s.password=? and deleteMark = 0", webId,username,password).first();
		return s==null?null:s;
	}
	/** 
	 * 通过webID 获取其 前50的数据
	 * @author keepcleargas
	 * @Date：   2012-9-30
	 * @param webId
	 * @return
	 */
	public static List<Share> getShares(long webId){
		List<Share> shares = Share.find("select s from Share s where s.website.webId = ? and deleteMark = 0",webId).fetch(50);
		return shares==null?new ArrayList<Share>(0):shares;
	}
	
	public static List<Share> getShares(long webId,String username){
		List<Share> shares =  Share.find("select s from Share s where s.website.webId =? and s.username=? and deleteMark = 0",webId,username).fetch(20);
		return shares==null?new ArrayList<Share>(0):shares;
	}
	
	/**
	 * 查询webId的马甲列表
	 * 
	 * @author keepcleargas
	 * @Date： 2012-9-27
	 * @param webId
	 * @return
	 */
	public static List<Account> getAccounts(long webId) {
		List<Account> accounts = new ArrayList<Account>();
		List<Object[]> items = Share
		        .find("select s.shareId,s.username,s.password,s.top,s.down,s.top*1.0f/((s.top)+(s.down)) as rate from Share s where s.website.webId=? and s.deleteMark=0 order by rate desc",
		                webId).fetch(20);
		for (Object[] rows : items) {
			long shareId = (long) rows[0];
			String username = (String) rows[1];
			String password = (String) rows[2];
			int top = (int) rows[3];
			int down = (int) rows[4];
			float rate = 0.0f;
			if (rows[5] != null) {
				rate = (float) rows[5];
			}

			Account account = new Account();
			account.setShareId(shareId);
			account.setUsername(username);
			account.setPassword(password);
			account.setTop(top);
			account.setDown(down);
			account.setRate(rate);

			accounts.add(account);
		}
		return accounts == null ? new ArrayList<Account>(0) : accounts;
	}
}
