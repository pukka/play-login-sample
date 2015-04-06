package controllers;

import play.*;
import play.mvc.*;
import play.data.*;

import views.html.*;

import models.*;

public class Application extends Controller {

    /** indexへのレンダリング */
    public static Result index() {
        return ok(index.render());
    }

    /** loginへのレンダリング、引数でフォームのインスタンス生成 */
    public static Result login() {
	return ok(login.render(Form.form(User.class)));
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
