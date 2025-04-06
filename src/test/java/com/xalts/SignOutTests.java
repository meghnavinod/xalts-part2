package com.xalts;

import static org.testng.Assert.assertEquals;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SignOutTests {

  public static final int DELAY = 2000;
  WebDriver driver;

  @BeforeClass
  public void setUp() {
    WebDriverManager.chromedriver().clearDriverCache().setup();
    WebDriverManager.chromedriver().clearResolutionCache().setup();

    WebDriverManager.chromedriver()
        .driverVersion("135.0.7049.41") // pin to exact version
        .setup();

    driver = new ChromeDriver();
    driver.manage().window().maximize();
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
  public void testSignOutFromMainPage() throws InterruptedException {
    signIn();
    verifySignOut();
  }

  @Test
  public void testSignOutFromGetStartedPage() throws InterruptedException {
    signIn();
    getStartedPage();
    verifySignOut();
  }

  @Test
  public void testSignOutFromOnboardOcnPage() throws InterruptedException {
    signIn();
    getStartedPage();
    onboardOcn();
    verifySignOut();
  }

  @Test
  public void testSignOutFromLaunchOcnPage() throws InterruptedException {
    signIn();
    getStartedPage();
    launchOcn();
    verifySignOut();
  }


  private void getStartedPage() throws InterruptedException {
    navigate("//*[@id=\"root\"]/div/main/div/div/div/button");
  }

  private void onboardOcn() throws InterruptedException {
    navigate("//*[@id=\"root\"]/div/main/section[2]/div/div[1]");
  }

  private void launchOcn() throws InterruptedException {
    navigate("//*[@id=\"root\"]/div/main/section[2]/div/div[2]");
  }

  private void navigate(String xpathExpression) throws InterruptedException {
    WebElement onboardOcn = driver.findElement(
        By.xpath(xpathExpression));
    onboardOcn.click();
    Thread.sleep(DELAY);
  }

  private void verifySignOut() throws InterruptedException {
    WebElement button = driver.findElement(By.xpath("//*[@id=\"root\"]/div/header/div/button"));
    String buttonText = button.getText();
    assertEquals(buttonText, "SIGN OUT");
    button.click();
    Thread.sleep(DELAY);
    button = driver.findElement(By.xpath("//*[@id=\"root\"]/div/header/div/button"));
    buttonText = button.getText();
    assertEquals(buttonText, "SIGN IN");
  }

  private void signIn() throws InterruptedException {
    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[2]")).click();

    List<WebElement> elements = driver.findElements(By.id("outlined-basic"));
    elements.get(0).sendKeys("test1234@gmail.com");
    elements.get(1).sendKeys("Test1998@");

    driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div[2]/button[1]")).click();
    Thread.sleep(DELAY);
  }
}
