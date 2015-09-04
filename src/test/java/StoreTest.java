import java.util.Arrays;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;


public class StoreTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Store.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Store firstStore = new Store("Hanks");
    Store secondStore = new Store("Hanks");
    assertTrue(firstStore.equals(secondStore));
  }
  @Test
  public void save_savesIntoDatabase_true() {
    Store myStore = new Store("Hanks");
    myStore.save();
    assertTrue(Store.all().get(0).equals(myStore));
  }

  @Test
  public void find_findsStoreInDatabase_true() {
    Store myStore = new Store("Hanks");
    myStore.save();
    Store savedStore = Store.find(myStore.getId());
    assertTrue(myStore.equals(savedStore));
  }

  @Test
  public void addBrand_addsBrandToStore() {
    Store myStore = new Store("Hanks");
    myStore.save();

    Brand myBrand = new Brand("Mow");
    myBrand.save();

    myStore.addBrand(myBrand);
    Brand savedBrand = myStore.getBrands().get(0);
    assertTrue(myBrand.equals(savedBrand));

  }

  @Test
  public void getBrands_returnsAllBrands_ArrayList() {
    Store myStore = new Store("Hanks");
    myStore.save();

    Brand myBrand = new Brand("Mow");
    myBrand.save();

    myStore.addBrand(myBrand);
    List savedBrands = myStore.getBrands();
    assertEquals(savedBrands.size(), 1);
  }

}//end of class
