package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ItemPage extends BasePage {

    public ItemPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//select[@class='form-control select']")
    public WebElement selectGuarantee;

    @FindBy(xpath = "//div[@class='clearfix']//span[@class='current-price-value']")
    public WebElement price;

    @FindBy(xpath = "//form[@id='u1331d9f923754436b28e0e11f7f60510']//input[@class='ui-input-search__input ui-input-search__input_presearch']")
    public WebElement searchField;

    @FindBy(xpath = "//button[@class='btn btn-cart btn-lg']")
    public WebElement buyButton;

    @FindBy(xpath = "//span[@class='cart-link__price']")
    public WebElement basketPrice;

    @FindBy(xpath = "//h1[@class='page-title price-item-title']")
    public WebElement name;

    public void initItem() {
        Product product = new Product(Integer.parseInt(price.getText().replaceAll(" ", "").trim()), getName());
//        System.out.printf("========\nPRODUCT - PRICE: %s NAME: %s\n========\n", product.price, product.name);
    }

    public String getName() {
        return name.getText();
    }
}