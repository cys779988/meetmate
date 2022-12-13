package com.spring.course.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

	@Column(columnDefinition = "TEXT")
	private String content;

	@Column(nullable = false)
	private Long category;

	@Column(nullable = false)
	private LocalDate applyStartDate;
	
	@Column(nullable = false)
	private LocalDate applyEndDate;
	
	@Column(nullable = false)
	private LocalDate startDate;
	
	@Column(nullable = false)
	private LocalDate endDate;
	
	private Integer divclsNo;
	
	@Column(nullable = false)
	private Integer maxNum;

	@ColumnDefault("0")
	@Column(insertable = true, updatable = false)
	private Integer curNum;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "course_id")
	private List<GroupEntity> group = new ArrayList<>();
	
	@Lob
	@Column(name = "SCHEDULES", columnDefinition = "BLOB")
	private String schedules;
	
	@Lob
	@Column(name = "MAP_NODE", columnDefinition = "BLOB")
	private String node;

	@Lob
	@Column(name = "MAP_EDGE", columnDefinition = "BLOB")
	private String edge;
	
	@Builder
    public CourseEntity(Long id, UserEntity registrant, String title, String content, Long category, LocalDate applyStartDate, LocalDate applyEndDate, LocalDate startDate, LocalDate endDate, Integer divclsNo, Integer maxNum, Integer curNum, String schedules, String node, String edge) {
        this.id = id;
        this.registrant = registrant;
        this.title = title;
        this.content = content;
        this.category = category;
        this.applyStartDate = applyStartDate;
        this.applyEndDate = applyEndDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.divclsNo = divclsNo;
		this.maxNum = maxNum;
		this.curNum = curNum;
		this.schedules = schedules;
		this.node = node;
		this.edge = edge;
    }
}
