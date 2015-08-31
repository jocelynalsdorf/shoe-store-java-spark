import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Task {
  private int id;
  private String description;

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public Task(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object otherTask) {
    if(!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) &&
             this.getId() == newTask.getId();
    }
  }

  public static List<Task> all() {
    String sql = "SELECT id, description FROM tasks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks (description) VALUES (:description)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", this.description)
        .executeUpdate()
        .getKey();
    }
  }

  public static Task find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks where id=:id";
      Task task = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Task.class);
          return task;
    }
  }




  // public void update(String description) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "UPDATE tasks SET description = :description WHERE id = :id";
  //     con.createQuery(sql)
  //       .addParameter("description", description)
  //       .addParameter("id", id)
  //       .executeUpdate();
  //   }
  // }

}
