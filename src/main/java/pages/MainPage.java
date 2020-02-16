package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends BasePage {

    public MainPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;
    }

    @FindBy(xpath = "//input[@placeholder='Поиск среди более 100 000 товаров']")
    public WebElement searchField;

    @FindBy(xpath = "//div[@class='presearch__suggests']/a[text()='playstation 4 slim']")
    public WebElement playstation;

}
