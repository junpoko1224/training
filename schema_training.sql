DROP TABLE IF EXISTS post;
-- 「post」というテーブルが存在したら削除（テーブル名は「post」とする）

CREATE TABLE IF NOT EXISTS post (
--「newpost」というテーブルが存在しなければ作成する
	id varchar(255) NOT NULL,
	-- idは254文字まで、NULLはエラー
	author varchar(20) NULL,
	-- author（投稿者）の文字数19文字？、テーブル作成としてはNULLでも良い？
	title varchar(20) NULL,
	body varchar(1000) NULL,
	deleted bool NOT NULL,
	-- 削除済かどうか（真偽）はNULLだとエラー
	created_date timestamp NULL,
	-- 投稿した時間
	updated_date timestamp NULL,
	-- 更新した時間
	PRIMARY KEY (id)
	-- idは重複不可
);