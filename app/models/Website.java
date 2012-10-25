package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.GenericModel;

/**
 * web信息
 * @author  keepcleargas
 * @Date    2012-9-27
 */
@Entity
@Table(name="website")
public class Website extends GenericModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="web_id")
	public long webId;
	
	@Column(name="web_name")
	public String webName;
	public String url;
	public String description;
	
	@Column(name="ref_website_id")
	public long ref; //母网站 ID -1:主 0:未设置 >0子网站
	public int available; //是否有效的网址
	public int closed;    //是否可以被搜索到
    @ManyToMany
    @JoinTable(name="r_website_category",joinColumns = { 
			@JoinColumn(name = "web_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "category_id") 
	})
    public Set<Category> categories;
	//public Category category;
    
    public Website(String url){
    	this.url = url;
    	//Category category = new Category().addId(1);
    	//this.categories = new HashSet<Category>();
    	//this.category = category;
    	this.create();
    }
    public Website(long webId){
    	super();
    	this.webId = webId;
    }
    /**
     * 查找具有关联 的子域/母域
     * @author keepcleargas
     * @Date：   2012-10-14
     * @param website
     * @return
     */
    public static List<Website> findRelatedWeb(Website website){
    	List<Website> webs = new ArrayList<Website>();
    	long ref = website.ref;
    	if(ref == -1){
    		webs = Website.find("select w from Website w where w.ref =?",website.webId).fetch();
    	}else if(ref ==0){
    		webs = new ArrayList<Website>(0);
    	}else{
    		Website mainWeb = Website.findById(ref);
    		webs = Website.find("select w from Website w where w.ref =?",mainWeb.webId).fetch();
    		webs.remove(website);
    		webs.add(mainWeb);
    	}
    	return webs;
    }
    public static Website findByWebName(String keyword){
    	return (Website)find("url",keyword).first();
    }
	@Override
    public String toString() {
	    return "Website [webId=" + webId + ", webName=" + webName + ", url="
	            + url + ", description=" + description + ", ref=" + ref
	            + ", available=" + available + ", closed=" + closed
	            + ", categories=" + categories + "]";
    }
}
