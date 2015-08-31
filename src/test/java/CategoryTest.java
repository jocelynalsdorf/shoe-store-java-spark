import java.util.Arrays;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;


public class CategoryTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void all_emptyAtFirst() {
      assertEquals(Category.all().size(), 0);
    }

    @Test
    public void equals_returnsTrueIfNamesAretheSame() {
      Category firstCategory = new Category("Household Shores");
      Category secondCategory = new Category("Household Shores");
      assertTrue(firstCategory.equals(secondCategory));
    }

    @Test
    public void save_savesIntoDatabase_true() {
      Category myCategory = new Category("Household Shores");
      myCategory.save();
      assertTrue(Category.all().get(0).equals(myCategory));
    }

    @Test
    public void find_findsCategoryInDatabase_true() {
      Category myCategory = new Category("Household Shores");
      myCategory.save();
      Category savedCategory = Category.find(myCategory.getId());
      assertTrue(myCategory.equals(savedCategory));
    }

    @Test
    public void addTask_addsTaskToCategory() {
      Category myCategory = new Category("Household Shores");
      myCategory.save();

      Task myTask = new Task("Mow the lawn");
      myTask.save();

      myCategory.addTask(myTask);
      Task savedTask = myCategory.getTasks().get(0);
      assertTrue(myTask.equals(savedTask));

    }

    @Test
    public void getTasks_returnsAllTasks_ArrayList() {
      Category myCategory = new Category("Household chores");
      myCategory.save();

      Task myTask = new Task("Mow the lawn");
      myTask.save();

      myCategory.addTask(myTask);
      List savedTasks = myCategory.getTasks();
      assertEquals(savedTasks.size(), 1);
    }


}
