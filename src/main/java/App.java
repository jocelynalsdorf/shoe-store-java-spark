import java.util.HashMap;
// import java.util.ArrayList;
import java.util.List;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;



public class App {
  public static void main(String[] args) {
   staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
     HashMap<String, Object> model = new HashMap<String, Object>();
     model.put("template", "templates/index.vtl");
     return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());

     get("/bBrands", (request, response) -> {
       HashMap<String, Object> model = new HashMap<String, Object>();
       List<Brand> brands = Brand.all();
       model.put("brands", brands);
       model.put("template", "templates/brands.vtl");
       return new ModelAndView(model, layout);
     }, new VelocityTemplateEngine());

     get("/stores", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Store> stores = Store.all();
      model.put("stores", stores);
      model.put("template", "templates/stores.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());




 }//end of main
}//end appclass
