package com.spring.code.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.spring.common.model.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "tb_code")
public class CommonCodeEntity extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="CODE_ID")
	private Long id;
	
	private String name;
	
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CodeType type;
	
    private String registrant;
    
	@Builder
	public CommonCodeEntity(Long id, String name, CodeType type, String registrant) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.registrant = registrant;
	}
}