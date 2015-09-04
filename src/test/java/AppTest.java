// import java.util.ArrayList;

import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.rules.ExternalResource;
import org.sql2o.*;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;


public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Shoe Stores");
  }

  @Test
  public void storeIsDisplayedWhenCreated() {
    goTo("http://localhost:4567/stores");
    fill("#name").with("Osh");
    submit(".btn-info");
    assertThat(pageSource()).contains("Osh");
  }

  @Test
  public void storeIsDeleted() {
    Store myStore = new Store("Target");
    myStore.save();
    String storePath = String.format("http://localhost:4567/stores/%d/update", myStore.getId());
    goTo(storePath);
    submit(".btn-danger");
    click("a", withText("Add or view a store"));
    assertThat(pageSource()).doesNotContain("Target");
  }



}//end of test class
