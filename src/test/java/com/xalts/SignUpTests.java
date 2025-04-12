package com.xalts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SignUpTests {

  WebDriver driver;
  private WebDriverWait wait;

  private static void clear(WebElement confirmPassword) {
    confirmPassword.clear();
    String existingValue = confirmPassword.getDomAttribute("value");

    if (existingValue != null && !existingValue.isEmpty()) {
      for (int i = 0; i < existingValue.length(); i++) {
        confirmPassword.sendKeys(Keys.BACK_SPACE);
      }
    }
  }

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

  @Given("the user is on the Sign Up Page")
  public void the_user_is_on_the_Sign_Up_Page() {
    driver = new ChromeDriver();
    driver.manage().window().maximize();

    wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    driver.get("https://xaltsocnportal.web.app/signin");
  }

  @AfterClass
  public void tearDown() {
    driver.quit();
  }

  @When("the user enters valid email Address,Password and confirm password correctly")
  public void the_user_enters_valid_emailaddress_password_confirmPassword_correctly() {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    elements.get(0).sendKeys("test12345678@gmail.com");
    elements.get(1).sendKeys("Test19986@");
    elements.get(2).sendKeys("Test19986@");
  }

  @When("clicks on the SignUp button")
  public void clicks_on_the_Sign_up_button() {
    WebElement signup = driver.findElement(By.xpath("//*[@id='root']/div/main/div[2]/button[1]"));
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.elementToBeClickable(signup));
    signup.click();
    //throw new io.cucumber.java.PendingException();

  }

  @Then("the user should be redirected to the homepage")
  public void the_user_should_beredirected_to_the_page() {
    String currentUrl = driver.getCurrentUrl();
    if (currentUrl != null) {
      assertTrue(currentUrl.contains("xaltsocnportal"));
    }
  }

/*  @Given("the user is on the Sign Up Page")
  public void  the_user_is_on_Sign_Up_Page() {
    driver = new ChromeDriver();
    driver.manage().window().maximize();

    wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    driver.get("https://xaltsocnportal.web.app/signin");
  }*/

  @When("the user enters existing email Address,Password and confirm password")
  public void the_user_enters_existing_email_address_password_confirmPassword() {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    elements.get(0).sendKeys("test12345678@gmail.com");
    elements.get(1).sendKeys("Test19986@");
    elements.get(2).sendKeys("Test19986@");
  }

  /*@When("clicks on the SignUp button")
  public void clicks_on_the_Sign_up_button1() {
    WebElement signup = driver.findElement(By.xpath("//*[@id='root']/div/main/div[2]/button[1]"));
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.elementToBeClickable(signup));
    signup.click();
  }*/
  @Then("the user should get a pop up saying {string}")
  public void the_user_should_get_a_popup_up_saying(String message) {
    try
    {
      Alert alert = wait.until(ExpectedConditions.alertIsPresent());
      assertEquals(alert.getText(), message);
  //assertEquals(alert.getText(), "Provided E-Mail is already in use");
    alert.accept();
  }
    catch (Exception e){
      e.printStackTrace();
    }
  }

  @When("the user enters invalid email Address,Password and confirm password")
  public void the_user_enters_invalid_email_address_Password_and_confirmpassword() throws InterruptedException {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    WebElement emailElement = elements.get(0);

    emailElement.sendKeys("test123#gmail.com");
    elements.get(1).sendKeys("Test1998@");
    elements.get(2).sendKeys("Test1998@");
  }
  @Then("SignUp Button is Disabled")
      public void SignUp_button_disabled() throws InterruptedException {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    WebElement emailElement = elements.get(0);
    //change it to 1 when user want to check for invalid password scenario
   WebElement signup = driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]"));
   assertFalse(signup.isEnabled());

    Thread.sleep(500);  //

    String ariaInvalid = emailElement.getDomAttribute("aria-invalid");
   assertEquals(ariaInvalid, "true");
  }

  @When("the user enters Incomplete email Address,Password and confirm password")
  public void the_user_enters_Incomplete_email_Address_Password_and_confirmPassword() {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    elements.get(0).sendKeys("test123@gmail.c");
    elements.get(1).sendKeys("Test1998@");
    elements.get(2).sendKeys("Test1998@");
  }
  @Then("the user should get a pop up says {string}")
      public void the_user_should_get_a_popup_up_says( String undefined) {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    WebElement emailElement = elements.get(0);
    WebElement signup = driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]"));
    assertFalse(signup.isEnabled());
    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
    assertEquals(undefined, alert.getText());
    //assertEquals(alert.getText(), "undefined");
    alert.accept();
  }

  @When("the user enters valid email Address,Invalid Password and confirm password")
  public void the_user_enters_valid_email_Address_InvalidPassword_and_confirmPassword() {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    elements.get(0).sendKeys("test123@gmail.com");
    WebElement password = elements.get(1);

    password.sendKeys("short");

    /*String ariaInvalid = password.getDomAttribute("aria-invalid");
    assertEquals(ariaInvalid, "true");

    password.clear();
    password.sendKeys("ValidPassword$123");*/


    //ariaInvalid = password.getDomAttribute("aria-invalid");
    //assertEquals(ariaInvalid, "true");
  }

  @When("the user enters Password and  confirm password different")
  public void the_user_enters_Password_and_confirmPassword_different() {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    elements.get(0).sendKeys("test123@gmail.com");
    WebElement password = elements.get(1);
    password.sendKeys("ValidPassword$123");

    WebElement confirmPassword = elements.get(2);

    confirmPassword.sendKeys("short");
  }
  @Then("SignUp Button is Disabled for this scenario")
 public void SignUp_Button_is_isabled_for_this_scenario(){
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    WebElement password = elements.get(1);
    password.sendKeys("ValidPassword$123");

    WebElement confirmPassword = elements.get(2);

    confirmPassword.sendKeys("short");
    String ariaInvalid = confirmPassword.getDomAttribute("aria-invalid");
    assertEquals(ariaInvalid, "true");

    //clear(confirmPassword);

   // confirmPassword.sendKeys("ValidPassword$123");

    //ariaInvalid = confirmPassword.getDomAttribute("aria-invalid");
   // assertEquals(ariaInvalid, "false");
  }

  @When("the user leaves all the fields blank")
  public void the_user_leaves_all_the_fields_blank() {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    WebElement email = elements.get(0);
    WebElement password = elements.get(1);
    WebElement confirmPassword = elements.get(2);
  }
  @Then("SignUp Button Responds disabled")
    public void SignUp_Button_Responds_disabled() {
      List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
      WebElement email = elements.get(0);
      WebElement password = elements.get(1);
      WebElement confirmPassword = elements.get(2);
    WebElement signup = driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]"));

    assertFalse(signup.isEnabled());
    assertEquals(email.getDomAttribute("aria-invalid"), "true");
    assertEquals(password.getDomAttribute("aria-invalid"), "true");
    assertEquals(confirmPassword.getDomAttribute("aria-invalid"), "false");
  }

}
