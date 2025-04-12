package com.xalts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SignInPageTests {

  WebDriver driver;
  private WebDriverWait wait;

  @BeforeClass
  public void setUp() {
    WebDriverManager.chromedriver().clearDriverCache().setup();
    WebDriverManager.chromedriver().clearResolutionCache().setup();

    WebDriverManager.chromedriver()
        .driverVersion("135.0.7049.41") // pin to exact version
        .setup();

    driver = new ChromeDriver();
    driver.manage().window().maximize();
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  @Given("the user is on the Sign In Page")
  public void the_user_is_on_the_Sign_In_Page() {
    driver = new ChromeDriver();
    driver.manage().window().maximize();

    wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    driver.get("https://xaltsocnportal.web.app/signin");
  }

  @AfterClass
  public void tearDown() {
    driver.quit();
  }

  @When("the user enters valid email Address,Password correctly")
  public void the_user_enters_valid_email_Address_Password_correctly() throws InterruptedException {

    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[2]")).click();

    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    elements.get(0).sendKeys("test1234@gmail.com");
    elements.get(1).sendKeys("Test1998@");
  }

  @When("user clicks on the Sign In button")
  public void user_clicks_on_the_Sign_In_button() throws InterruptedException {
    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]")).click();
    Thread.sleep(3000);
  }

  @Then("the user has to be redirected to the homepage")
  public void the_user_should_beredirected_to_the_page() {

    String currentUrl = driver.getCurrentUrl();
    if (currentUrl != null) {
      assertTrue(currentUrl.contains("xaltsocnportal.web.app"));
      WebElement signOut = driver.findElement(By.xpath("//*[@id=\"root\"]/div/header/div/button"));
      assertEquals(signOut.getText(), "SIGN OUT");
    }
  }


  @When("the user enters valid email Address but Wrong Password")
  public void the_user_enters_valid_email_Address_Wrong_Password() {

    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[2]")).click();

    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    elements.get(0).sendKeys("test123@gmail.com");
    WebElement password = elements.get(1);
    password.sendKeys("Test1999@");
  }
  @When("When  user clicks on the Sign In button")
    public void when_user_clicks_on_the_Sign_In_button() {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    elements.get(0).sendKeys("test123@gmail.com");
    WebElement password = elements.get(1);
    password.sendKeys("Test1999@");
    WebElement signup = driver.findElement(
        By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]"));
    signup.click();
}
  @Then("Pop message is  displayed saying {string}")
      public void the_user_should_get_a_popup_up_saying_incorrect_email_or_password(String message) {

    try {
      Alert alert = wait.until(ExpectedConditions.alertIsPresent());
      //assertEquals("Incorrect E-Mail or Password", alert.getText());
      assertEquals(message, alert.getText());
      alert.accept();
    } catch (TimeoutException e) {
      Assert.fail("Expected alert did not appear.");
    }

    // Now safe to interact with the DOM
   /* String ariaInvalid = password.getDomAttribute("aria-invalid");
    assertEquals("true", ariaInvalid);
    assertFalse(signup.isEnabled());
  }*/



  }

  @When("the user enters invalid email Address but Right Password")
  public void the_user_enters_invalid_email_Address_Right_Password() {

    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[2]")).click();

    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    WebElement email = elements.get(0);
    email.sendKeys("test123#gmail.com");
  }
  @Then("SignIn Button is Disabled")
      public void SignInButtonIsDisabled() {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    WebElement email = elements.get(0);
    WebElement signup = driver.findElement(
        By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]"));

    assertFalse(signup.isEnabled());

    String ariaInvalid = email.getDomAttribute("aria-invalid");
    assertEquals(ariaInvalid, "true");
  }

  @When("the user enters valid email Address but  Password is invalid")
  public void testPasswordNotMeetingCriteria() {

    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[2]")).click();

    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    elements.get(0).sendKeys("test123@gmail.com");
    WebElement password = elements.get(1);

    password.sendKeys("short");
  }
  @Then("SignIn Button is Disabled for Password")
   public void SignInButtonIsDisabledForPassword() {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    WebElement password = elements.get(1);

    password.sendKeys("short");
    String ariaInvalid = password.getDomAttribute("aria-invalid");
    assertEquals(ariaInvalid, "true");

    //password.clear();
    //password.sendKeys("ValidPassword$123");

    //ariaInvalid = password.getDomAttribute("aria-invalid");
    //assertEquals(ariaInvalid, "false");
  }

  @When("the user enters valid email Address with OR and enters Password")
  public void the_user_enters_valid_email_Address_with_ORAnd_enters_Password() {

    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[2]")).click();

    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    WebElement email = elements.get(0);
    email.sendKeys("test123@gmail.comOR\"1=1\"");
    elements.get(1).sendKeys("ValidPassword$123");

    /*WebElement signup = driver.findElement(
        By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]"));

    signup.click();*/
  }
  @Then("Alert Message shows {string}")
      public void alertMessageShowsUndefined( String message1) {
    try {
      Alert alert = wait.until(ExpectedConditions.alertIsPresent());
      //assertEquals("Incorrect E-Mail or Password", alert.getText());
      assertEquals(message1, alert.getText());
      alert.accept();
    } catch (TimeoutException e) {
      Assert.fail("Expected alert did not appear.");
    }
  }
  @When("the user leaves all the fields blank in SignIn Page")
  public void the_user_leaves_all_the_fields_blank_in_SignIn_Page() {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    WebElement email = elements.get(0);
    WebElement password = elements.get(1);

   // WebElement signup = driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]"));

    /*assertFalse(signup.isEnabled());
    assertEquals(email.getDomAttribute("aria-invalid"), "true");
    assertEquals(password.getDomAttribute("aria-invalid"), "true");*/
  }

  @When("the user enters valid email Address and Password which is not registered")
  public void testLoginFailsWithUnregisteredEmail() {

    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[2]")).click();

    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    WebElement email = elements.get(0);
    email.sendKeys("testsss123@gmail.com");
    elements.get(1).sendKeys("ValidPassword$123");

    /*WebElement signup = driver.findElement(
        By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]"));

    signup.click();*/
  }
    @Then("Pop message displayed saying {string}")
        public void Pop_message_displayed_saying(String message) {
    try{

    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
    assertEquals(message, alert.getText());
  // assertEquals(alert.getText(), "User not found");
    alert.accept();
  }
    catch(TimeoutException e){
    Assert.fail("Expected alert did not appear.");}
    }
}

