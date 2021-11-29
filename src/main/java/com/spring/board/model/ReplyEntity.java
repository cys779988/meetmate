
package com.spring.board.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Table(name = "tb_reply")
public class ReplyEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	
	@Column(nullable = false)
	private String content;

	@ManyToOne
	@JoinColumn(name = "board_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_reply_board"))
	private BoardEntity board;

	@ManyToOne
	@JoinColumn(referencedColumnName = "email", foreignKey = @ForeignKey(name = "fk_reply_user"))
	private UserEntity registrant;

	@Builder
	public ReplyEntity(Long id, String content, BoardEntity board, UserEntity registrant) {
		this.id = id;
		this.content = content;
		this.board = board;
		this.registrant = registrant;
	}
}
