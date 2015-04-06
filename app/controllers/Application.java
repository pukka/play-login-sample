package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import java.util.*;

import views.html.*;

import models.*;

public class Application extends Controller {

    /** indexへのレンダリング */
    public static Result index() {
        return ok(index.render());
    }

    /** loginへのレンダリング、引数でフォームのインスタンス生成 */
    public static Result login() {
	List<User> user = null;
        user = User.find.all();
	if(user.isEmpty()) {
	    return redirect(routes.Application.first());    
        }
	return ok(login.render(Form.form(User.class)));
    }

    /** 最初の画面 */
    public static Result first() {
        return ok(first.render(Form.form(User.class)));
    }

    /** 新規ユーザ追加 */
    public static Result addUser(){
        return TODO;
    }

    /**
     * フォームのバインド後、エラーがあればbadRequest
     * 問題なければセッションにname記録
     */
    public static Result authenticate() {
        Form<User> loginForm = Form.form(User.class).bindFromRequest();
	if(loginForm.hasErrors()) {
	    return badRequest(login.render(loginForm));
	}
	session().clear();
	session("name",loginForm.get().name);
	return redirect(routes.Application.index());
    }
}
