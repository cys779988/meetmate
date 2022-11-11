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

import com.spring.common.model.BaseFileEntity;
import com.spring.security.model.UserEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "tb_board_file")
public class BoardFileEntity extends BaseFileEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="FILE_ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "BOARD_ID", referencedColumnName = "BOARD_ID", foreignKey = @ForeignKey(name = "fk_file_board"))
	private BoardEntity board;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "EMAIL", foreignKey = @ForeignKey(name = "fk_file_user"))
	private UserEntity registrant;
	
	@Builder
	public BoardFileEntity(BoardEntity board, String filePath, Long fileSize, String saveFileName, String originalFileName, UserEntity registrant){
		this.board = board;
		this.registrant = registrant;
		super.setFilePath(filePath);
		super.setFileSize(fileSize);
		super.setSaveFileName(saveFileName);
		super.setOriginalFileName(originalFileName);
	}
}
