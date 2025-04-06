package com.xalts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

  @BeforeMethod
  public void OpenSignUpPage() {
    driver.get("https://xaltsocnportal.web.app/signin");
  }

  @AfterClass
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testSignInWithValidCredentials() throws InterruptedException {

    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[2]")).click();

    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    elements.get(0).sendKeys("test1234@gmail.com");
    elements.get(1).sendKeys("Test1998@");

    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]")).click();
    Thread.sleep(3000);
    String currentUrl = driver.getCurrentUrl();
    if (currentUrl != null) {
      assertTrue(currentUrl.contains("xaltsocnportal.web.app"));
      WebElement signOut = driver.findElement(By.xpath("//*[@id=\"root\"]/div/header/div/button"));
      assertEquals(signOut.getText(), "SIGN OUT");
    }
  }

  @Test
  public void testLoginFailsWithWrongPassword() {

    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[2]")).click();

    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    elements.get(0).sendKeys("test123@gmail.com");
    WebElement password = elements.get(1);
    password.sendKeys("Test199");

    WebElement signup = driver.findElement(
        By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]"));

    assertFalse(signup.isEnabled());

    String ariaInvalid = password.getDomAttribute("aria-invalid");
    assertEquals(ariaInvalid, "true");
  }

  @Test
  public void testLoginFailsWithInvalidEmailFormat() {

    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[2]")).click();

    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    WebElement email = elements.get(0);
    email.sendKeys("test123#gmail.com");

    WebElement signup = driver.findElement(
        By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]"));

    assertFalse(signup.isEnabled());

    String ariaInvalid = email.getDomAttribute("aria-invalid");
    assertEquals(ariaInvalid, "true");
  }

  @Test
  public void testPasswordNotMeetingCriteria() {

    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[2]")).click();

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
  public void testLoginFailsWithSqlInjectionInEmail() {

    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[2]")).click();

    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    WebElement email = elements.get(0);
    email.sendKeys("test123@gmail.comOR\"1=1\"");
    elements.get(1).sendKeys("ValidPassword$123");

    WebElement signup = driver.findElement(
        By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]"));

    signup.click();

    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
    assertEquals(alert.getText(), "undefined");
    alert.accept();
  }

  @Test
  public void testEmptyFieldsOnSignup() {
    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    WebElement email = elements.get(0);
    WebElement password = elements.get(1);

    WebElement signup = driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]"));

    assertFalse(signup.isEnabled());
    assertEquals(email.getDomAttribute("aria-invalid"), "true");
    assertEquals(password.getDomAttribute("aria-invalid"), "true");
  }

  @Test
  public void testLoginFailsWithUnregisteredEmail() {

    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[2]")).click();

    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    WebElement email = elements.get(0);
    email.sendKeys("testsss123@gmail.com");
    elements.get(1).sendKeys("ValidPassword$123");

    WebElement signup = driver.findElement(
        By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]"));

    signup.click();

    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
    assertEquals(alert.getText(), "User not found");
    alert.accept();
  }
}
