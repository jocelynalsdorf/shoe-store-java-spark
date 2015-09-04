import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;
import java.util.ArrayList;

public class BrandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Brand.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Brand firstBrand = new Brand("Mow");
    Brand secondBrand = new Brand("Mow");
    assertTrue(firstBrand.equals(secondBrand));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Brand myBrand = new Brand("Mow");
    myBrand.save();
    assertTrue(Brand.all().get(0).equals(myBrand));
  }

  @Test
  public void save_assignsIdToObject() {
    Brand myBrand = new Brand("Mow");
    myBrand.save();
    Brand savedBrand = Brand.all().get(0);
    assertEquals(myBrand.getId(), savedBrand.getId());
  }

  @Test
  public void find_findsBrandInDatabase_true() {
    Brand myBrand = new Brand("Mow");
    myBrand.save();
    Brand savedBrand = Brand.find(myBrand.getId());
    assertTrue(myBrand.equals(savedBrand));
  }

  @Test
  public void addStore_addsStoreToBrand() {
    Store myStore = new Store("Hanks");
    myStore.save();
    Brand myBrand = new Brand("Mow");
    myBrand.save();
    myBrand.addStore(myStore);
    Store savedStore = myBrand.getStores().get(0);
    assertTrue(myStore.equals(savedStore));
  }

  @Test
  public void getStores_returnsAllStores_ArrayList() {
    Store myStore = new Store("Hanks");
    myStore.save();
    Brand myBrand = new Brand("Mow");
    myBrand.save();
    myBrand.addStore(myStore);
    List savedStores = myBrand.getStores();
    assertEquals(savedStores.size(), 1);
  }

   @Test
   public void update_updateBrandInfo() {
     Brand savedBrand = new Brand("Khao");
     savedBrand.save();
     savedBrand.update("McDonalds");
     assertTrue(Brand.all().get(0).getDescription().equals("McDonalds"));
   }

  // @Test
  // public void delete_deletesALlBrandsAndListsAssociations() {
  //   Store myStore = new Store("Hanks");
  //   myStore.save();

  //   Brand myBrand = new Brand("Mow");
  //   myBrand.save();

  //   myBrand.addStore(myStore);
  //   myBrand.delete();
  //   assertEquals(myStore.getBrands().size(), 0);
  //   }
  

}//end of class
