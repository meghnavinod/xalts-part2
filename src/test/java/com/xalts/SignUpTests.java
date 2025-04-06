package com.xalts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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
import org.testng.annotations.BeforeMethod;
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

  @BeforeMethod
  public void OpenSignUpPage() {
    driver.get("https://xaltsocnportal.web.app/signin");
  }

  @AfterClass
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testValidSignUP() {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    elements.get(0).sendKeys("test123@gmail.com");
    elements.get(1).sendKeys("Test1998@");
    elements.get(2).sendKeys("Test1998@");
    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]")).click();

    String currentUrl = driver.getCurrentUrl();
    if (currentUrl != null) {
      assertTrue(currentUrl.contains("xaltsocnportal"));
    }
  }

  @Test
  public void testSignupWithExistingEmail() {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    elements.get(0).sendKeys("test123@gmail.com");
    elements.get(1).sendKeys("Test1998@");
    elements.get(2).sendKeys("Test1998@");

    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]")).click();

    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
    assertEquals(alert.getText(), "Provided E-Mail is already in use");
    alert.accept();
  }

  @Test
  public void testSignUpWithInvalidEmail() throws InterruptedException {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    WebElement emailElement = elements.get(0);

    emailElement.sendKeys("test123#gmail.com");
    elements.get(1).sendKeys("Test1998@");
    elements.get(2).sendKeys("Test1998@");

    WebElement signup = driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]"));
    assertFalse(signup.isEnabled());

    Thread.sleep(500);  //

    String ariaInvalid = emailElement.getDomAttribute("aria-invalid");
    assertEquals(ariaInvalid, "true");
  }

  @Test
  public void testSignupWithIncompleteEmailFormat() {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    elements.get(0).sendKeys("test123@gmail.c");
    elements.get(1).sendKeys("Test1998@");
    elements.get(2).sendKeys("Test1998@");

    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]")).click();

    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
    assertEquals(alert.getText(), "undefined");
    alert.accept();
  }

  @Test
  public void testPasswordNotMeetingCriteria() {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    elements.get(0).sendKeys("test123@gmail.com");
    WebElement password = elements.get(1);

    password.sendKeys("short");

    String ariaInvalid = password.getDomAttribute("aria-invalid");
    assertEquals(ariaInvalid, "true");

    password.clear();
    password.sendKeys("ValidPassword$123");

    ariaInvalid = password.getDomAttribute("aria-invalid");
    assertEquals(ariaInvalid, "false");
  }

  @Test
  public void testPasswordAndConfirmPasswordMismatch() {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    elements.get(0).sendKeys("test123@gmail.com");
    WebElement password = elements.get(1);
    password.sendKeys("ValidPassword$123");

    WebElement confirmPassword = elements.get(2);

    confirmPassword.sendKeys("short");

    String ariaInvalid = confirmPassword.getDomAttribute("aria-invalid");
    assertEquals(ariaInvalid, "true");

    clear(confirmPassword);

    confirmPassword.sendKeys("ValidPassword$123");

    ariaInvalid = confirmPassword.getDomAttribute("aria-invalid");
    assertEquals(ariaInvalid, "false");
  }

  @Test
  public void testEmptyFieldsOnSignup() {
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
