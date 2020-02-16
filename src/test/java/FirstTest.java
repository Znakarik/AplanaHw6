
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.*;
import util.NumberUtil;

import java.util.Arrays;
import java.util.List;

public class FirstTest extends BaseTest {

    @Test
    public void PagesTest() throws InterruptedException {
        final WebDriverWait wait = new WebDriverWait(driver, 30);

        /** 1. открыть dns-shop */
        driver.navigate().to(properties.getProperty("app.url"));
        final MainPage mainPage = new MainPage(driver);
        /** 2. в поиске найти playstation */
        mainPage.searchField.sendKeys("playstation");
        /** 3. кликнуть по playstation 4 slim black */
        mainPage.playstation.click();

        final SearchPage searchPage = new SearchPage(driver);
        wait.until(ExpectedConditions.visibilityOf(searchPage.itemTitle));

        /** Кликаем на title товара и переходим на ItemPage */
        searchPage.itemTitle.click();

        /** 4. запомнить цену */
        final ItemPage itemPage = new ItemPage(driver);

        final Product playstation1 = new Product();
        /** Цена до добавления гарантии */
        playstation1.setPrice(NumberUtil.parseInt(itemPage.price.getText()));
        playstation1.setName(itemPage.name.getText());

        /** 5. Доп.гарантия - выбрать 2 года */
        itemPage.selectGuarantee.sendKeys("2");

        /** 6. дождаться изменения цены и запомнить цену с гарантией */
        final Product playstation2 = new Product();
        playstation2.setPrice(NumberUtil.parseInt(itemPage.price.getText()));
        playstation2.setName(itemPage.name.getText());

        /** 7. Нажать Купить */
        itemPage.buyButton.click();

        Integer playstationPrice3 = NumberUtil.parseInt(itemPage.price.getText());

        /** 8. выполнить поиск Detroit */
        mainPage.searchField.sendKeys("Detroit\n");

        /** 9. запомнить цену */
        final Product detroid = new Product();
        detroid.setPrice(NumberUtil.parseInt(itemPage.price.getText()));
        detroid.setName(itemPage.name.getText());

        /** 10. нажать купить */
        itemPage.buyButton.click();

        /** 11. проверить что цена корзины стала равна сумме покупок */
        Thread.sleep(3000); // ждем, пока динамический обьект цены корзины отрендерится
        /** 11. Проверить что цена корзины стала равна сумме покупок */
        int basketPrice1 = NumberUtil.parseInt(itemPage.basketPrice.getText());
        Assert.assertEquals(basketPrice1, playstation2.getPrice() + detroid.getPrice(), "Prices are not the sane");

        /** 12. перейти в корзину */
        itemPage.basketPrice.click();

        final BasketPage basketPage = new BasketPage(driver);

        final List<Product> productsFromPages = Arrays.asList(playstation1, detroid);
        final List<Product> productsFromBasket = basketPage.getProducts();

        Boolean isPresent = driver.findElements(By.xpath("//div[@class='list-applied-product-services__item']")).size() > 0;

        Assert.assertEquals(productsFromPages.size(), productsFromBasket.size());

        /** 13. проверить, что для приставки выбрана гарантия на 2 года */
        /** Узнаем стоимость гарантии */
        playstation2.setGuarantee(playstation2.getPrice() - playstation1.getPrice());
        /** Проверяем наличие элемента гарантии на странице */
        Assert.assertTrue(basketPage.twoYearGuarantee.isDisplayed());
        /** Смотрим, есть ли на странице наши обьекты */
        Assert.assertTrue(productsFromBasket.contains(playstation1));
        Assert.assertTrue(productsFromBasket.contains(detroid));
        /** 14. проверить цену каждого из товаров и сумму */
        Assert.assertTrue(productsFromBasket.containsAll(productsFromPages));

        driver.findElements(By.cssSelector(".cart-items__product-name-link")).forEach(element -> System.out.println(element.getText()));
        int finalBasketPrice1 = NumberUtil.parseInt(basketPage.getFinalPrice().getText());
        /**15. удалить из корзины Detroit */
        basketPage.delete(detroid.getName());

        /** 16. проверить что Detroit нет больше в корзине и что сумма уменьшилась на цену Detroit */
        Thread.sleep(2000);
        Assert.assertTrue(driver.findElements(By.xpath(basketPage.findElenemtNameByXpath(detroid.getName()))).size() < 1);
        int basketPrice = NumberUtil.parseInt(itemPage.basketPrice.getText());
        Assert.assertEquals((basketPrice1 - detroid.getPrice()), basketPrice);

        /** 17. добавить еще 2 playstation (кнопкой +) и проверить что сумма верна (равна трем ценам playstation) */
        basketPage.addMoreItems(playstation2.getName());
        basketPage.addMoreItems(playstation2.getName());
        int finalBasketPrice2 = NumberUtil.parseInt(basketPage.getFinalPrice().getText());
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[contains(text(),'92 037')]"))));
        Assert.assertEquals((playstation2.getPrice() * 3), finalBasketPrice2);
        /** 18. нажать вернуть удаленный товар, проверить что Detroit появился в корзине и сумма увеличилась на его значение */
        basketPage.getReturnItemButton().click();
        int finalBasketPrice3 = NumberUtil.parseInt(basketPage.getFinalPrice().getText());
        Assert.assertEquals((int) detroid.getPrice(), finalBasketPrice3 - finalBasketPrice1);
    }
}
