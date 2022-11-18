package com.spring.course.model;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.spring.common.model.BaseTimeEntity;
import com.spring.security.model.UserEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "tb_course")
@DynamicInsert
@DynamicUpdate
public class CourseEntity extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="COURSE_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "EMAIL", foreignKey = @ForeignKey(name = "fk_course_user"))
	private UserEntity registrant;
	
	@Column(length = 30, nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	private Long category;

	private Integer divclsNo;
	
	private Integer maxNum;

	@ColumnDefault("0")
	private Integer curNum;

	@Lob
	@Column(name = "MAP_NODE", columnDefinition = "BLOB")
	private String node;

	@Lob
	@Column(name = "MAP_EDGE", columnDefinition = "BLOB")
	private String edge;
	
	/*
	 * @PrePersist public void prePersist() { this.curNum = this.curNum == null ? 0
	 * : this.curNum; }
	 */

	@Builder
    public CourseEntity(Long id, UserEntity registrant, String title, String content, Long category, Integer divclsNo, Integer maxNum, Integer curNum, String node, String edge) {
        this.id = id;
        this.registrant = registrant;
        this.title = title;
        this.content = content;
        this.category = category;
        this.divclsNo = divclsNo;
		this.maxNum = maxNum;
		this.curNum = curNum;
		this.node = node;
		this.edge = edge;
    }
}
