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

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO brands (description) VALUES (:description)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", this.description)
        .executeUpdate()
        .getKey();
    }
  }

  public static Brand find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM brands where id=:id";
      Brand brand = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Brand.class);
          return brand;
    }
  }

  public void addStore(Store store) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stores_brands (store_id, brand_id) VALUES (:store_id, :brand_id)";
      con.createQuery(sql)
        .addParameter("store_id", store.getId())
        .addParameter("brand_id", this.getId())
        .executeUpdate();
    }
  }

  public ArrayList<Store> getStores() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT store_id FROM stores_brands WHERE brand_id = :brand_id";
      List<Integer> storeIds = con.createQuery(sql)
        .addParameter("brand_id", this.getId())
        .executeAndFetch(Integer.class);

        ArrayList<Store> stores = new ArrayList<Store>();

        for(Integer storeId : storeIds) {
          String brandQuery = "SELECT * FROM stores WHERE id = :storeId";
          Store store = con.createQuery(brandQuery)
            .addParameter("storeId", storeId)
            .executeAndFetchFirst(Store.class);
          stores.add(store);
        }
      return stores;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM brands WHERE id = :id";
        con.createQuery(deleteQuery)
        .addParameter("id", id)
        .executeUpdate();

      String joinDeleteQuery = "DELETE FROM stores_brands WHERE brand_id = :brandId";
        con.createQuery(joinDeleteQuery)
          .addParameter("brandId", this.getId())
          .executeUpdate();
    }
  }

  public void update(String description) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE brands SET description = :description WHERE id = :id";
      con.createQuery(sql)
        .addParameter("description", description)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

}//end of class
