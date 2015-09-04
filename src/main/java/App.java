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

      get("/brands", (request, response) -> {
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

//post routes to make a new brand or store
      post("/brands", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        String description = request.queryParams("description");
        Brand newBrand = new Brand(description);
        newBrand.save();
        response.redirect("/brands");
        return null;
      });

      post("/stores", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        String name = request.queryParams("name");
        Store newStore = new Store(name);
        newStore.save();
        response.redirect("/stores");
        return null;
      });

//get routes to individual store/brand pages 
      get("/brands/:id", (request,response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        int id = Integer.parseInt(request.params("id"));
        Brand brand = Brand.find(id);
        model.put("brand", brand);
        model.put("allStores", Store.all());
        model.put("template", "templates/brand.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("/stores/:id", (request,response) ->{
        HashMap<String, Object> model = new HashMap<String, Object>();
        int id = Integer.parseInt(request.params("id"));
        Store store = Store.find(id);
        model.put("store", store);
        model.put("allBrands", Brand.all());
        model.put("template", "templates/store.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

//post routes to add brand to stores and add stores to brands
       post("/add_brands", (request, response) -> {
        int brandId = Integer.parseInt(request.queryParams("brand_id"));
        int storeId = Integer.parseInt(request.queryParams("store_id"));
        Store store = Store.find(storeId);
        Brand brand = Brand.find(brandId);
        store.addBrand(brand);
        response.redirect("/stores/" + storeId);
        return null;
      });

      post("/add_stores", (request, response) -> {
        int brandId = Integer.parseInt(request.queryParams("brand_id"));
        int storeId = Integer.parseInt(request.queryParams("store_id"));
        Store store = Store.find(storeId);
        Brand brand = Brand.find(brandId);
        brand.addStore(store);
        response.redirect("/brands/" + brandId);
        return null;
      });
//get form to update/delete stores 
      get("/stores/:id/update", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        Store store = Store.find(Integer.parseInt(request.params(":id")));
        model.put("store", store);
        model.put("template", "templates/edit-store.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

//post routes to update/delete stores 
      post("/stores/:id/update", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        Store store = Store.find(Integer.parseInt(request.params(":id")));
        String name = request.queryParams("name");
        store.update(name);
        response.redirect("/stores/" + store.getId());
        return null;
      });

      post("/stores/:id/delete", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        Store store = Store.find(Integer.parseInt(request.params(":id"))); 
        store.delete();
        response.redirect("/");
        return null;
      });  

//get form to update/delete brands
      get("/brands/:id/update", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        Brand brand = Brand.find(Integer.parseInt(request.params(":id"))); 
        model.put("brand", brand); 
        model.put("template", "templates/edit-brand.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

//post routes to update/delete a brand
      post("/brands/:id/delete", (request, response) -> {
        int brandId = Integer.parseInt(request.params(":id"));
        Brand brand = Brand.find(brandId);
        brand.delete();
        response.redirect("/brands");
        return null;
      });

      post("/brands/:id/update", (request, response) -> {
        int brandId = Integer.parseInt(request.params(":id"));
        Brand brand = Brand.find(brandId);
        String description = request.queryParams("description");
        brand.update(description);
        response.redirect("/brands/" + brandId);
        return null;
      });

 }//end of main
}//end appclass
