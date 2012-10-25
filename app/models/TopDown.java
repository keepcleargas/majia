package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
/**
 * 顶踩记录类
 * @author  keepcleargas
 * @Date    2012-10-11
 */
@Entity
@Table(name = "feedback_topdown")
public class TopDown extends GenericModel{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "feedback_id")
	public long feedback_id;
	@Column(name = "ip_address")
	public String ipAddress;
	@Column(name = "web_id")
	public long webId;
	@Column(name = "share_id")
	public long shareId;
	@Column(name = "feedback_time")
	public long feedbackTime;
	
	public TopDown(String ipAddress,long webId,long shareId,long time){
		this.ipAddress = ipAddress;
		this.webId=webId;
		this.shareId = shareId;
		this.feedbackTime = time;
		create();
	}
	/**
	 * 寻找一天内的 该ip对该站点的评价
	 * @author keepcleargas
	 * @Date：   2012-10-15
	 * @param ipAddress
	 * @param webId
	 * @param time
	 * @return
	 * true：24小时内已存在评价.false:针对该网站 该IP的评价
	 */
	public static boolean findTopDown(String ipAddress,long webId,long time){
		long oneDayAgo = time -1000*3600*24;
		TopDown td = TopDown.find("select td from TopDown td where td.ipAddress=? and td.webId =? and td.feedbackTime>?",ipAddress,webId,oneDayAgo).first();
		return td==null?false:true;
	}
}
