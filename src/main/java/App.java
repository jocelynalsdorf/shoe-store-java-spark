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

     get("/tasks", (request, response) -> {
       HashMap<String, Object> model = new HashMap<String, Object>();
       List<Task> tasks = Task.all();
       model.put("tasks", tasks);
       model.put("template", "templates/tasks.vtl");
       return new ModelAndView(model, layout);
     }, new VelocityTemplateEngine());

     get("/categories", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Category> categories = Category.all();
      model.put("categories", categories);
      model.put("template", "templates/categories.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

      get("/tasks/:id", (request,response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        int id = Integer.parseInt(request.params("id"));
        Task task = Task.find(id);
        model.put("task", task);
        model.put("allCategories", Category.all());
        model.put("template", "templates/task.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      get("/categories/:id", (request,response) ->{
        HashMap<String, Object> model = new HashMap<String, Object>();
        int id = Integer.parseInt(request.params("id"));
        Category category = Category.find(id);
        model.put("category", category);
        model.put("allTasks", Task.all());
        model.put("template", "templates/category.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      post("/tasks", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        String description = request.queryParams("description");
        Task newTask = new Task(description);
        newTask.save();
        response.redirect("/tasks");
        return null;
      });

      post("/categories", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        String name = request.queryParams("name");
        Category newCategory = new Category(name);
        newCategory.save();
        response.redirect("/categories");
        return null;
      });

      post("/add_tasks", (request, response) -> {
        int taskId = Integer.parseInt(request.queryParams("task_id"));
        int categoryId = Integer.parseInt(request.queryParams("category_id"));
        Category category = Category.find(categoryId);
        Task task = Task.find(taskId);
        category.addTask(task);
        response.redirect("/categories/" + categoryId);
        return null;
      });

      post("/add_categories", (request, response) -> {
        int taskId = Integer.parseInt(request.queryParams("task_id"));
        int categoryId = Integer.parseInt(request.queryParams("category_id"));
        Category category = Category.find(categoryId);
        Task task = Task.find(taskId);
        task.addCategory(category);
        response.redirect("/tasks/" + taskId);
        return null;
      });
 //mark as "completed-route for tasks"

      post("/tasks/:taskId/complete", (request, response) -> {
        int taskId = Integer.parseInt(request.queryParams("task_id"));
        //int checkedId = Integer.parseInt(request.queryParams("checked_id"));
        //int categoryId = Integer.parseInt(request.queryParams("category_id"));
        //Category category = Category.find(categoryId);
        Task task = Task.find(taskId);
        task.markCompleted();
        //category.addTask(task);
        //response.redirect("/categories/" + categoryId);
        response.redirect("/");
        return null;
      });
//update get form to update delete for tasks:

      get("/categories/:category_id/tasks/:id/update", (request, response) -> {
        HashMap<String, Object> model = new HashMap<String, Object>();
        Category category = Category.find(Integer.parseInt(request.params(":category_id")));
        Task task = Task.find(Integer.parseInt(request.params(":id"))); 
        model.put("category", category);
        model.put("task", task); 
        model.put("template", "templates/edit-task.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());


      post("/:category_id/tasks/:id/update", (request, response) -> {
        int taskId = Integer.parseInt(request.params(":id"));
        int categoryId = Integer.parseInt(request.params(":category_id"));
        Category category = Category.find(categoryId);
        Task task = Task.find(taskId);
        String description = request.queryParams("description");
        task.update(description);
        response.redirect("/categories/" + categoryId);
        //response.redirect("/");
        return null;
      });

      post("/:category_id/tasks/:id/delete", (request, response) -> {
        int taskId = Integer.parseInt(request.params(":id"));
        int categoryId = Integer.parseInt(request.params(":category_id"));
        Category category = Category.find(categoryId);
        Task task = Task.find(taskId);
        task.delete();
         //model.put("categories", Category.all());
        response.redirect("/categories/" + categoryId);
        //response.redirect("/");
        return null;
      });
      




 }//end of main
}//end appclass
