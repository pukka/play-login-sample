package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import java.util.*;

import views.html.*;

import models.*;

public class Application extends Controller {

    static Form<User> userForm = Form.form(User.class);

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
	return ok(login.render(userForm));
    }

    /** 最初の画面 */
    public static Result first() {
        return ok(first.render(userForm));
    }

    /** 新規ユーザ追加 */
    public static Result addUser(){
        Form<User> createForm = userForm.bindFromRequest();
	if(createForm.hasErrors()){
	    return badRequest(login.render(createForm));
	}
        User.create(createForm.get());

        session().clear();
	session("name",createForm.get().name);
	return redirect(routes.Application.index());
    }

    /**
     * フォームのバインド後、エラーがあればbadRequest
     * 問題なければセッションにname記録
     */
    public static Result authenticate() {
        Form<User> loginForm = userForm.bindFromRequest();
	if(loginForm.hasErrors()) {
	    return badRequest(login.render(loginForm));
	}
	session().clear();
	session("name",loginForm.get().name);
	return redirect(routes.Application.index());
    }
}
