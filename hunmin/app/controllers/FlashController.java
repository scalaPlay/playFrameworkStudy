package controllers;


import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by kohunmin on 2016. 5. 14..
 */
public class FlashController extends Controller {

    public Result save() {
        flash("success", "The item has been created");
        return redirect("/home");
    }

    public Result index() {
    String message = flash("success");
        if(message == null) {
            message = "Welcome!";
        }
        return ok(message);
    }


}
