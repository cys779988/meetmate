package com.spring.board.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.spring.common.model.BaseTimeEntity;
import com.spring.security.model.UserEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "tb_board")
public class BoardEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "email", foreignKey = @ForeignKey(name = "fk_board_user"))
	private UserEntity registrant;

	@Column(length = 30, nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
	private List<ReplyEntity> reply = new ArrayList<>();

	@Builder
	public BoardEntity(Long id, UserEntity registrant, String title, String content) {
		this.id = id;
		this.registrant = registrant;
		this.title = title;
		this.content = content;
	}
}