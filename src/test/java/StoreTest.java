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

 

}//end of class
