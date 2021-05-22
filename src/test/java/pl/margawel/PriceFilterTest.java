package pl.margawel;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class PriceFilterTest {
    private WebDriver driver;
    private String labelString;
    private double labelPriceMin, labelPriceMax, priceProduct;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://prod-kurs.coderslab.pl/index.php?id_category=2&controller=category");
//        driver.get("https://prod-kurs.coderslab.pl/index.php?id_category=2&controller=category&q=Price-%E2%82%AC-8-10");

    }

    @Test
    public void priceFilter() {
        List<WebElement> priceCheckList = driver.findElements(By.cssSelector("section.facet:nth-of-type(6) input"));
        List<WebElement> label = driver.findElements(By.cssSelector("section.facet:nth-of-type(6) a"));

        labelString = label.get(0).getText();
        labelPriceMin = Double.parseDouble(labelString.substring(1, 5));
        labelPriceMax = Double.parseDouble(labelString.substring(9, 14));

        priceCheckList.get(0).click();

        //WebDriver waitDriver = new WebDriverWait(driver, 10).until(driver -> driver.findElements(By.xpath("//*[@class='h6 active-filter-title']")));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> productsListElements = driver.findElements(By.xpath("//*[@itemprop='price']"));

        for (WebElement e : productsListElements){

            priceProduct = Double.parseDouble(e.getText().substring(1, 5));
            if ((priceProduct < labelPriceMin) || (priceProduct > labelPriceMax)) {
                System.out.println("Bład");
            }

        }

    }

    @After
    public void tearDown() throws Exception {
       // driver.close();

    }
}
