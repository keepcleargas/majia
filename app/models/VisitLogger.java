package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.GenericModel;

@Entity
@Table(name="visit_log")
public class VisitLogger  extends GenericModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	public long id;
	
	@Column(name="ip_address")
	public String ipAddress;
	@Column(name="visit_time")
	public long visitTime;
	
	public VisitLogger(String ip,long time){
		this.ipAddress = ip;
		this.visitTime =time;
		this.create();
	}
}
