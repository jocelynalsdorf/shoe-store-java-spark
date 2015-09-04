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
    assertThat(pageSource()).contains("HardCore Shoe Stores");
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

  @Test
  public void storeIsUpdated() {
    Store myStore = new Store("Target");
    myStore.save();
    String storePath = String.format("http://localhost:4567/stores/%d/update", myStore.getId());
    goTo(storePath);
    fill("#name").with("Tarjay");
    submit(".btn-success");
    assertThat(pageSource()).contains("Tarjay");
  }

  @Test
  public void brandIsDisplayedWhenCreated() {
    goTo("http://localhost:4567/brands");
    fill("#description").with("Nike");
    submit(".btn-info");
    assertThat(pageSource()).contains("Nike");
  }

  @Test
  public void brandIsDeleted() {
    Brand myBrand = new Brand("pony");
    myBrand.save();
    String brandPath = String.format("http://localhost:4567/brands/%d/update", myBrand.getId());
    goTo(brandPath);
    submit(".btn-danger");
    // click("a", withText("Add or view a brand"));
    assertThat(pageSource()).doesNotContain("pony");
  }

  @Test
  public void brandIsUpdated() {
    Brand myBrand = new Brand("adidas");
    myBrand.save();
    String brandPath = String.format("http://localhost:4567/brands/%d/update", myBrand.getId());
    goTo(brandPath);
    fill("#description").with("Adidas");
    submit(".btn-success");
    assertThat(pageSource()).contains("Adidas");
  }

  @Test
  public void allbrandsDisplayDescriptionOnStorePage() {
    Store myStore = new Store("Wally");
    myStore.save();
    Brand firstBrand = new Brand("nike");
    firstBrand.save();
    Brand secondBrand = new Brand("pony");
    secondBrand.save();
    String storePath = String.format("http://localhost:4567/stores/%d", myStore.getId());
    goTo(storePath);
    assertThat(pageSource()).contains("nike");
    assertThat(pageSource()).contains("pony");
  }
  
  @Test
  public void allstoresDisplayDescriptionOnBrandPage() {
    Brand myBrand = new Brand("nike");
    myBrand.save();
    Store firstStore = new Store("target");
    firstStore.save();
    Store secondStore = new Store("wally");
    secondStore.save();
    String brandPath = String.format("http://localhost:4567/brands/%d", myBrand.getId());
    goTo(brandPath);
    assertThat(pageSource()).contains("target");
    assertThat(pageSource()).contains("wally");
  }




}//end of test class
