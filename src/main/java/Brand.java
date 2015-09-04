import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Brand {
  private int id;
  private String description;
  

  public int getId() {
    return id;
  }

  

  public String getDescription() {
    return description;
  }

  public Brand(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object otherBrand) {
    if(!(otherBrand instanceof Brand)) {
      return false;
    } else {
      Brand newBrand = (Brand) otherBrand;
      return this.getDescription().equals(newBrand.getDescription()) &&
             this.getId() == newBrand.getId();
    }
  }

  public static List<Brand> all() {
    String sql = "SELECT id, description FROM brands";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Brand.class);
    }
  }

  

}//end of class
