package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import java.util.*;

import views.html.*;

import models.*;

public class Application extends Controller {

    static Form<User> userForm = Form.form(User.class);
    static Form<Login> loginForm = Form.form(Login.class);

    /** indexへのレンダリング */
    @Security.Authenticated(Secured.class)
    public Result index() {
        return ok(index.render());
    }

    /** loginへのレンダリング、引数でフォームのインスタンス生成 */
    public Result login() {
	List<User> user = null;
        user = User.find.all();
	if(user.isEmpty()) {
	    return redirect(routes.Application.first());    
        }
	return ok(login.render(loginForm));
    }

    /** 最初の画面 */
    public Result first() {
        return ok(first.render(userForm));
    }

    /** 新規ユーザ追加 */
    public Result addUser(){
        Form<User> createForm = userForm.bindFromRequest();
	if(createForm.hasErrors()){
	    return badRequest(first.render(createForm));
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
    public Result authenticate() {
        Form<Login> filledForm = loginForm.bindFromRequest();
	if(filledForm.hasErrors()) {
	    return badRequest(login.render(filledForm));
	}
	session().clear();
	session("name",filledForm.get().name);
	return redirect(routes.Application.index());
    }

    public Result logout() {
        session().clear();
        return redirect(routes.Application.login());
    }

    public static class Login {
        public String name;
	public String password;

	public String validate() {
	    if (User.authenticate(name, password) == null) {
                return "パスワード、またはユーザ名が有効ではありません。";
            }
	    return null;
	}
    }
}
