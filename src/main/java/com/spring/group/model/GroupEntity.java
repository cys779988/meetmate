package com.spring.group.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.spring.common.model.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "tb_group")
public class GroupEntity extends BaseTimeEntity{
	
	@EmbeddedId
	@Column(name = "GROUP_ID")
	private GroupID id;
	
	private Long divNo;
	
	@Builder
	public GroupEntity(GroupID id, Long divNo) {
		this.id = id;
		this.divNo = divNo;
	}
}
