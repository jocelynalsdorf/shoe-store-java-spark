import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Store {
  private int id;
  private String name;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public  Store(String name) {
    this.name = name;
  }

  public static List<Store> all() {
    String sql = "SELECT id, name FROM stores";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Store.class);
    }
  }

  @Override
  public boolean equals(Object otherStore) {
    if (!(otherStore instanceof Store)) {
      return false;
    } else {
      Store newStore = (Store) otherStore;
      return this.getName().equals(newStore.getName());
    }
  }


   public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stores (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

 

}//end of class
