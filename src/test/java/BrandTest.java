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

  
}//end of class
