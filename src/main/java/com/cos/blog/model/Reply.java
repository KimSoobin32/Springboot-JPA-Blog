package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌터패턴!!
@Entity
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false,length=200)
	private String content;
	
	@ManyToOne //하나의개시글이 여러개의 답변이 존재
	@JoinColumn(name="boardId")
	private Board board;
	
	@ManyToOne //하나의유저 여러개의 답변이 존재
	@JoinColumn(name="userId")
	private User user;
	
	@CreationTimestamp //시간이 자동 입력
	private Timestamp createDate;
}
