package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage extends BasePage {

    public SearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//div[@class='product-info__title-link']//a[@class='ui-link']")
    public WebElement itemTitle;

    //*
    @FindBy(xpath = "//div[@class='product-price__current']")
    public WebElement price;



}
