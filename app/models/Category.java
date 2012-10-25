package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.GenericModel;

/**
 * 类别类
 * @author  keepcleargas
 * @Date    2012-9-27
 */
@Entity
@Table(name="category")
public class Category extends GenericModel implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="category_id")
	public int categoryId;
	
	@Column(name="category_name")
	public String categoryName;
	/**
	 * 默认构架ID 为1的 ,数据库必须有初始化的数据 1 为其他类别
	 */
	public  Category(){
		
	}
	
	public Category(String categoryName){
		this.categoryName = categoryName;
		this.create();
	}
	
	public Category addId(int categoryId){
		this.categoryId = categoryId;
		return this;
	}
	
	public static Category findByCategoryName(String categoryName){
		return (Category)find("categoryName",categoryName).first();
	}
}
