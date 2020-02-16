package pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.NumberUtil;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BasketPage extends pages.BasePage {
//        @FindBy(xpath = "//div[(@class='cart-items__product-thumbnail cart-items__product-thumbnail_product')]")

    @FindBy(xpath = "//div[contains(@class,'cart-items__products')]//span[contains(@class,'price__current')]")
    public List<WebElement> prices;
    final List<Product> products = new ArrayList<>();

    @FindBy(xpath = "//div[@class='cart-items__product-name']")
    public List<WebElement> names;

    @FindBy(xpath = "//div[@class='list-applied-product-services__item']//span[contains(text(),'24')]")
    public WebElement twoYearGuarantee;

    @FindBy(xpath = "//div[@class='cart-items__product']")
    public WebElement items;

    @FindBy(xpath = "//span[@class='restore-last-removed']")
    WebElement returnItemButton;

    @FindBy(xpath = "//span[@class='price__current']")
    WebElement finalPrice;

    public void delete(String name) {
        WebElement deleteButton = items.findElement(By.xpath("//div[@class='cart-items__product-name']//a[contains(text(),'" + name + "')]/../../../../../..//button[contains(text(),'Удалить')]"));
        deleteButton.click();
    }

    public BasketPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public List<Product> getProducts() {
        for (int i = 0; i < prices.size(); i++) {
            final pages.Product product = new pages.Product();
            product.setName(names.get(i).getText());
            product.setPrice(NumberUtil.parseInt(prices.get(i).getText()));
            products.add(product);
        }
        return products;
    }

    public String findElenemtNameByXpath(String name) {
        return "//div[@class='cart-items__product']//div[@class='cart-items__product-name']//a[contains(text(),'" + name + "')]";
    }

    public void addMoreItems(String name) {
        WebElement addButton = items.findElement(By.xpath("//div[@class='cart-items__product-name']//a[contains(text(),'" + name + "')]/../../../../../..//i[(@class='count-buttons__icon-plus')]"));
        addButton.click();
    }

}