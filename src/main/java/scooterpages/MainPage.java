package scooterpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver webDriver;

//Кнопка "да все привыкли" в "И здесь куки! В общем, мы их используем"
    private final By confirmCookiesButton = By.id("rcc-confirm-button");

//Заголовок каждого пункта из выпадающего списка "Вопросы о важном"
    private final By listHeaders = By.className("accordion__heading");

//Текст ответов на вопросы из выпадающего списка
    private final By answersToListHeaders = By.xpath(".//div[@class='accordion__panel']/p");

//Кнопка "Заказать", расположенная на верхней части страницы,в хэдере
    private final By orderButtonInTheHeader = By.xpath(".//div[starts-with(@class, 'Header_Nav')]//button[starts-with(@class, 'Button_Button')]");

//Кнопка "Заказать", расположенная в нижней части страницы
    private final By orderButtonDownThePage = By.xpath(".//div[starts-with(@class, 'Home_RoadMap')]//button[starts-with(@class, 'Button_Button')]");

//Конструктор MainPage
    public MainPage(WebDriver driver) {

        this.webDriver = driver;
    }

//Нажатие на кнопку "да все привыкли" в "И здесь куки! В общем, мы их используем"
    public void clickOnConfirmCookiesButton() {

        this.webDriver.findElement(this.confirmCookiesButton).click();
    }

//нажатие на вопрос из списка "Вопросы о важном"
    public void clickEachListHeader(int index) {

        this.webDriver.findElements(this.listHeaders).get(index).click();
    }

//get-запрос на получение заголовка вопроса в списке "Вопросы о важном"
    public String getEachListHeader(int index) {
        return this.webDriver.findElements(this.listHeaders).get(index).getText();
    }
//Ожидание загрузки ответа 5 сек на вопрос в выпадающем списке "Вопросы о важном"
    public void waitForLoadTheAnswer(int index) {
        new WebDriverWait(this.webDriver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(this.webDriver.findElements(this.answersToListHeaders).get(index)));
    }

//запрос на проверку отображения ответа на вопрос из списка "Вопросы о важном"
    public boolean isAnswersToListHeadersDisplayed(int index) {
        return this.webDriver.findElements(this.answersToListHeaders).get(index).isDisplayed();
    }

//get-запрос на получение текста ответа в списке "Вопросы о важном"
    public String getAnswersText(int index) {
        return this.webDriver.findElements(this.answersToListHeaders).get(index).getText();
    }

// Нажатие на кнопку "Заказать" на верхней части страницы,в хэдере
    public void clickOrderButtonInTheHeader() {

        this.webDriver.findElement(this.orderButtonInTheHeader).click();
    }

// Нажатие на кнопку "Заказать" в нижней части страницы
    public void clickOrderButtonDownThePage() {

        this.webDriver.findElement(this.orderButtonDownThePage).click();
    }
}