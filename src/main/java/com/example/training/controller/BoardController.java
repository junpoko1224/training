package com.example.training.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.training.repository.Post;
import com.example.training.repository.PostFactory;
import com.example.training.repository.PostRepository;
import com.example.training.validation.GroupOrder;

/**
 * 掲示板のフロントコントローラー
 */
@Controller
public class BoardController {
	
	/** 投稿の一覧 */
	@Autowired
	private PostRepository repository;
	
	/**
	 * 一覧を表示する
	 * 
	 * @param model モデル
	 * @return テンプレート
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	// ↑value:指定したパス("/")のリクエストを受けると（URLで指定されると）、このメソッドを呼び出す
	//   method:GETやPOSTなどのメソッドを指定でき、URLに加えGETの場合に表示する
	public String index(Model model) {
		// ↑ModelクラスはControllerからView（画面）へ変数を渡すためのもの
		model.addAttribute("form", PostFactory.newPost());
		// ↑ビュー（main.html）のform属性（${form})の箇所に、PostFactory.newPost()の値を渡して置き換える
		model = this.setList(model);
		// setList(model)で投稿一覧を取得・表示するメソッドを取得したのでmodelに代入して
		model.addAttribute("path", "create");
		return "layout";
		// ↑layout.htmlを表示させる
	}
	
	/**
	 * 一覧を設定する
	 * 
	 * @param model モデル
	 * @return 一覧を設定したモデル
	 */
	private Model setList(Model model) {
		Iterable<Post> list = repository.findByDeletedFalseOrderByUpdatedDateDesc();
		// ↑リポジトリーから投稿一覧を取得(findAllメソッド）し、Post型のデータを取り出してlistに代入
		// ↑※findAllからfindByDeletedFalseOrderByUpdatedDateDesc();へ変更(未削除の一覧を取得)
		model.addAttribute("list", list);
		// ↑ビュー（main.html）のlist属性（${list})の箇所に、listの値を渡して置き換える
		return model;
		// ↑投稿一覧を取得して表示させるというmodel(メソッド）を返す
	}
	
	/**
	 * 登録する
	 * 
	 * @param form フォーム
	 * @param model モデル
	 * @return テンプレート
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	// ↑URLが/createになっている状態でPOST（送信ボタン押下）された場合に以下が実行される
	public String create(@ModelAttribute("form") @Validated(GroupOrder.class) Post form, BindingResult result, Model model) {
		// ↑@ModelAttributeでformから受け取った値をPost formに詰めて、
		// ↑@Validatedを付与したPostインスタンスのformにバリデーションチェックをかけ、
		// BindingResultでバリデーション結果を確認(BindResult はバリデーションの対象となる引数の次の引数として定義する必要がある)
		if (!result.hasErrors()) {
			repository.saveAndFlush(PostFactory.createPost(form));
			model.addAttribute("form", PostFactory.newPost());
			// ↑createPostをsaveAndFlushしたらnewPostの画面を表示しろということ？
		}
		model = this.setList(model);
		model.addAttribute("path", "create");
		// ↑createメソッドの作業が終わったらpath(/create)をcreateにする
		return "layout";
	}
		
	/**
	 * 編集する投稿を表示する
	 * 
	 * @param form フォーム
	 * @param model モデル
	 * @return テンプレート
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	// ↑編集ボタン押下(GET)するとURLが/editになり、以下が実行される
	public String edit(@ModelAttribute("form") Post form, Model model) {
		Optional<Post> post = repository.findById(form.getId());
		model.addAttribute("form", post);
		model = setList(model);
		model.addAttribute("path", "update");
		// ↑editメソッドの作業が終わったら(送信ボタン押下したら）path(/edit)をupdateにする
		return "layout";
	}
	
	/**
	 * 更新する
	 *
	 * @param form  フォーム
	 * @param model モデル
     * @return テンプレート
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute("form") @Validated(GroupOrder.class) Post form, BindingResult result, Model model) {
        if (!result.hasErrors()) {
        	Optional<Post> post = repository.findById(form.getId());
        	repository.saveAndFlush(PostFactory.updatePost(post.get(), form));
        }
        model.addAttribute("form", PostFactory.newPost());
        model = setList(model);
        model.addAttribute("path", "create");
     // ↑updateメソッドの作業が終わったらpath(/update)をcreateにする
        return "layout";
    }
    
    /**
     * 削除する
     *
     * @param form  フォーム
     * @param model モデル
     * @return テンプレート
     */
     @RequestMapping(value = "/delete", method = RequestMethod.GET)
     public String delete(@ModelAttribute("form") Post form, Model model) {
         Optional<Post> post = repository.findById(form.getId());
         repository.saveAndFlush(PostFactory.deletePost(post.get()));
         model.addAttribute("form", PostFactory.newPost());
         model = setList(model);
         model.addAttribute("path", "create");
         return "layout";
     }

}
