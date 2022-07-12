package com.example.training.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 投稿のリポジトリー.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	/**
     * IDで検索する
	 * 
	 * @param id ID
	 * @return 投稿
	 */
	public Optional<Post> findById(String id);
	
	/**
    * 更新日時の降順ですべての投稿を検索する
    *
    * @return 投稿のリスト
    */
   List<Post> findAllByOrderByUpdatedDateDesc();
   // このメソッド名をクエリと見なしてみると、
   // select * from post order by updatedDate desc;
   // ↑更新日時が新しい順(desc:降順)に投稿を取り出していることが分かる
   // JPA はルールに基づいた命名でメソッドを定義すると、そのメソッドの処理を自動生成する機能がある
   
   /**
   * 更新日時の降順で未削除の投稿を検索する
   *
   * @return 投稿のリスト
   */
   List<Post> findByDeletedFalseOrderByUpdatedDateDesc();
}
