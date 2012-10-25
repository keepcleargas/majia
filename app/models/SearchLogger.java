package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
/**
 * 搜索关键词Log
 * @author  keepcleargas
 * @Date    2012-10-11
 */
@Entity
@Table(name="search_log")
public class SearchLogger extends GenericModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	public long id;
	public String keyword;
	@Column(name="search_time")
	public long searchTime;
	
	public SearchLogger(String keyword,long searchTime){
		this.keyword = keyword;
		this.searchTime = searchTime;
		create();
	}
}
