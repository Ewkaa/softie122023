import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MetalShop {

    String password = "test123";
    String username = "test12";
    static WebDriver driver = new ChromeDriver();
    @BeforeAll
    public static void setUp(){
        driver.manage().window().maximize();
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
    }

    @AfterAll
    public static void closeBrowser(){
        driver.quit();
    }

    @BeforeEach
    public void goToHomePage() {
        driver.findElement(By.linkText("Sklep")).click();
    }

    @Test
    @Order(2)
    public void emptyPassword() {
        driver.findElement(By.linkText("Moje konto")).click();
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.name("login")).click();
        String error = driver.findElement(By.cssSelector(".woocommerce-error")).getText();
        Assertions.assertEquals("Błąd: pole hasła jest puste.", error);
    }
    @Test
    @Order(3)
    public void emptyLogin() {
        driver.findElement(By.linkText("Moje konto")).click();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
        String error = driver.findElement(By.cssSelector(".woocommerce-error")).getText();
        Assertions.assertEquals("Błąd: Nazwa użytkownika jest wymagana.", error);
    }
    @Test
    @Order(1)
    public void registerSuccess() {
        driver.findElement(By.linkText("register")).click();
        Faker faker = new Faker();
        String registerUsername = faker.name().username();
        String email = registerUsername + faker.random().nextInt(10000) + "@wp.pl";
        driver.findElement(By.cssSelector("#user_login")).sendKeys(registerUsername);
        driver.findElement(By.cssSelector("#user_email")).sendKeys(email);
        driver.findElement(By.cssSelector("#user_pass")).sendKeys(password);
        driver.findElement(By.cssSelector("#user_confirm_password")).sendKeys(password);
        driver.findElement(By.cssSelector(".ur-submit-button")).click();
        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".user-registration-message")));
        WebElement error = driver.findElement(By.cssSelector(".user-registration-message"));
        Assertions.assertEquals("User successfully registered.", error.getText());
    }
}
