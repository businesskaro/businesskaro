package com.businesskaro.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the tag_entity database table.
 * 
 */
@Entity
@Table(name="tag_entity")
//@NamedQueries({
	@NamedQuery(name="TagEntity.findAll", query="SELECT t FROM TagEntity t")
	//@NamedQuery(name="TagEntity.deleteByEntityIdAndEntityType", query="delete from tag_entity where entity_id = ?1 and entity_type=?2 and tag_entity_id <> 0")
//}) 
public class TagEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="tag_entity_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int tagEntityId;

	@Column(name="entity_id")
	private int entityId;

	@Column(name="entity_type")
	private String entityType;

	@Column(name="tag_id")
	private int tagId;
	
	@Column(name="created_date")
	private Date createdDate;

	public TagEntity() {
	}

	public int getTagEntityId() {
		return this.tagEntityId;
	}

	public void setTagEntityId(int tagEntityId) {
		this.tagEntityId = tagEntityId;
	}

	public int getEntityId() {
		return this.entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public String getEntityType() {
		return this.entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public int getTagId() {
		return this.tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	

}