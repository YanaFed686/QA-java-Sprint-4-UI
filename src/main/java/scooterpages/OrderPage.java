package scooterpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OrderPage {
    private final WebDriver webDriver;

// ссылка на форму заказа
        private final By orderForm = By.xpath(".//div[starts-with(@class, 'Order_Form')]");

// Поле ввода "Имя"
        private final By nameInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Имя')]");

// Поле ввода "Фамилия"
        private final By surnameInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Фамилия')]");

// Поле ввода "Адрес: куда привезти заказ"
        private final By addressInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Адрес')]");

// Поле ввода "Станция метро"
        private final By metroInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Станция метро')]");

//  Обертка выпадающего списка "Станция метро"
    private final By metroStationsList = By.className("select-search__select");

// Список доступных для выбора станций метро
    private final By metroStationsSelect = By.xpath(".//div[@class='select-search__select']//div[starts-with(@class,'Order_Text')]");

// Поле ввода "Телефон: на него позвонит курьер"
        private final By phoneInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Телефон')]");

// Кнопка "Далее"
    private final By nextButton = By.xpath(".//div[starts-with(@class, 'Order_NextButton')]/button");

// Поле выбора даты "Когда привезти самокат"
    private final By datePickerInput = By.xpath(".//div[starts-with(@class, 'react-datepicker__input-container')]//input");

// Выбранная дата в календаре поля "Когда привезти самокат"
    private final By selectedDate = By.className("react-datepicker__day--selected");

// Обертка выпадающего списка "Срок аренды"
    private final By dropdownRoot = By.className("Dropdown-root");

// Список доступных для выбора сроков аренды
    private final By dropdownOption = By.className("Dropdown-option");

// Список доступных для выбора цветов самоката
    private final By scooterColorCheckbox = By.xpath(".//div[starts-with(@class, 'Order_Checkboxes')]//label");

// Поле ввода "Комментарий для курьера"
        private final By commentInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Комментарий')]");

// Кнопка "Заказать"
        private final By orderButton = By.xpath(".//div[starts-with(@class, 'Order_Buttons')]/button[not(contains(@class,'Button_Inverted'))]");

// Кнопка "Да" в окне подтверждения заказа
        private final By confirmOrderButton = By.xpath(".//div[starts-with(@class, 'Order_Modal')]//button[not(contains(@class,'Button_Inverted'))]");

// Заголовок всплывающего окна успешного подтверждения заказа
        private final By newOrderSuccessMessage = By.xpath(".//div[starts-with(@class, 'Order_Modal')]//div[(starts-with(@class,'Order_ModalHeader'))]");

// Конструктор OrderPage
        public OrderPage(WebDriver webDriver) {
            this.webDriver = webDriver;
        }

// Ожидание прогрузки формы заказа
        public void waitForLoadingOrderForm() {
            new WebDriverWait(this.webDriver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOf(this.webDriver.findElement(this.orderForm)));
        }

// Ожидание прогрузки элементов страницы
        private void waitForElementLoading(By elementToLoad) {
            new WebDriverWait(this.webDriver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOf(this.webDriver.findElement(elementToLoad)));
        }

// Ввод имени в поле "Имя"
        public void setName(String name) {
            this.webDriver.findElement(this.nameInput).sendKeys(name);
        }

// Ввод фамилии в поле "Фамилия"
        public void setSurname(String surname) {
            this.webDriver.findElement(this.surnameInput).sendKeys(surname);
        }

// Ввод адреса в поле "Адрес: куда привезти заказ"
        public void setAddress(String address) {
            this.webDriver.findElement(this.addressInput).sendKeys(address);
        }

// Выбор станции метро из списка
        public void setMetro(String metro) {
            this.webDriver.findElement(this.metroInput).sendKeys(metro);
            this.waitForElementLoading(this.metroStationsList);
            this.selectElementFromDropdown(this.metroStationsSelect, metro);
        }

// Ввод телефона в поле "Телефон: на него позвонит курьер"
        public void setPhone(String phone) {
            this.webDriver.findElement(this.phoneInput).sendKeys(phone);
        }

// Нажатие на кнопку "Далее"
        public void clickNextButton() {
            this.webDriver.findElement(this.nextButton).click();
        }

// Выбор даты в календаре поля "Когда привезти самокат"
        public void setDate(String date) {
            this.webDriver.findElement(this.datePickerInput).sendKeys(date);
            this.waitForElementLoading(this.selectedDate);
            this.clickSelectedDate();
        }

// Нажатие на нужную дату в календаре поля "Когда привезти самокат"
    private void clickSelectedDate() {
        this.webDriver.findElement(this.selectedDate).click();
    }

// Выбор срока аренды
        public void setPeriod(String periodToChoose) {
            this.clickDropdown();
            this.selectElementFromDropdown(this.dropdownOption, periodToChoose);
        }


// Нажатие на выпадающий список поля "Срок аренды"
    private void clickDropdown() {
        this.webDriver.findElement(this.dropdownRoot).click();
    }

// Выбор цвета
        public void setColor(String colorToChoose) {
            this.selectElementFromDropdown(this.scooterColorCheckbox, colorToChoose);
        }

// Ввод комментария для курьера
        public void setComment(String comment) {
            this.webDriver.findElement(this.commentInput).sendKeys(comment);
        }

// Создание заказа
        public void makeOrder() {
            this.clickOrderButton();
            this.waitForElementLoading(this.confirmOrderButton);
            this.clickConfirmOrderButton();
        }

// Запрос на пролучение сообщения об успешной регистрации заказа
        public String getNewOrderSuccessMessage() {
            return this.webDriver.findElement(this.newOrderSuccessMessage).getText();
        }
// Нажатие на кнопку "Заказать"
        private void clickOrderButton() {
            this.webDriver.findElement(this.orderButton).click();
        }

// Нажатие на кнопку подтверждения заказа
        private void clickConfirmOrderButton() {
            this.webDriver.findElement(this.confirmOrderButton).click();
        }

//  Работа с выпадающим списком-выбор определенного элемента
        private void selectElementFromDropdown(By dropdownElements, String elementToChoose) {
            List<WebElement> elementsFiltered = this.webDriver.findElements(dropdownElements);
            for (WebElement element : elementsFiltered) {
                if (element.getText().equals(elementToChoose)) {
                    element.click();
                    break;
                }
            }
        }
}

