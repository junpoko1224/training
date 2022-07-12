package com.example.training.repository;

import java.util.Date;
// ↑java.utilパッケージのDateクラスをインポート

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
// ↑@Column,@Entity,@Id,@Tableを使うためのインポート
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.training.validation.Group1;
import com.example.training.validation.Group2;

import lombok.Data;
// 	lombok.Dataをインポートすることで、@Dataをつければgetterやsetterを記述しなくても使える

/**
 * 投稿
 */
@Entity
// エンティティクラスであることを指定
@Table(name = "post")
// エンティティにマッピングされるテーブル名を指定
@Data
// フィールド（id,author,titleなど）にアクセサーを記述しなくてよい
public class Post {
	
	/** ID */
	@Id
	// 「id」が主キーになる
	@Column
	//各フィールドにマッピングされるテーブルのカラム名を指定(フィールド名とデータベースのカラム名が同一であれば省略可能)
	@NotNull
	private String id = null;
	
	/** 投稿者 */
	@Column(length = 20, nullable = false)
	// ↑(カラム上で)最大20文字、nullは不可
	@NotEmpty(groups = Group1.class)
	@Size(min = 1, max = 20, groups = Group2.class)
	// ↑(入力する際)空欄はダメ、、文字数は1～20文字
	private String author = null;
	
	/** タイトル */
	@Column(length = 20, nullable = false)
	@NotEmpty(groups = Group1.class)
	@Size(min = 1, max = 20, groups = Group2.class)
	private String title = null;
	
	/** 内容 */
	@Column(length = 1000, nullable = false)
	@NotEmpty(groups = Group1.class)
	@Size(min = 1, max = 1000, groups = Group2.class)
	private String body = null;
	
	/** 登録日時 */
	private Date createdDate = null;
	
	/** 更新日時 */
	private Date updatedDate = null;
	
	/** 削除済 */
	private boolean deleted = false;
	// ↑削除されていない場合はfalse
	
}