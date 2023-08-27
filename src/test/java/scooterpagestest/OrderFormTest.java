package scooterpagestest;


import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import scooterpages.MainPage;
import scooterpages.OrderPage;

import java.io.File;

import static org.hamcrest.CoreMatchers.containsString;

    @RunWith(Parameterized.class)
    public class OrderFormTest {
        private WebDriver webDriver;

        private final String name, surname, address, metro, phone, date, period, color, comment;

        private final String expectedOrderSuccessText = "Заказ оформлен";

// Контруктор класса OrderPageTests

        public OrderFormTest(
                String name,
                String surname,
                String address,
                String metro,
                String phone,
                String date,
                String period,
                String color,
                String comment
        ) {
            this.name = name;
            this.surname = surname;
            this.address = address;
            this.metro = metro;
            this.phone = phone;
            this.date = date;
            this.period = period;
            this.color = color;
            this.comment = comment;
        }

        @Parameterized.Parameters(name = "Оформление заказа. Позитивный сценарий. Пользователь: {0} {1}")
        public static Object[][] setOrderParameters() {
            return new Object[][] {
                    {"Яна", "Федорова", "Москва, ул. Льва Толстого, д. 16", "Парк Культуры", "89991234567", "30.08.2023", "двое суток", "чёрный жемчуг", "Позвоните, пожалуйста, за 5 минут до прибытия"},
                    {"Юля", "Иванова", "Москва, ул. Пресненская Набережная, д. 12", "Деловой центр", "89187634815", "30.08.2023", "шестеро суток", "серая безысходность", "Уточняю. что мне нужен именно серый самокат))"},
            };
        }

        @Before
        public void setUpChrome() {
            System.setProperty("webdriver.http.factory", "jdk-http-client");
            ChromeDriverService service = new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File("/Users/yanafedorova/Downloads/chromedriver-mac-arm64/chromedriver"))
                    .build();

            ChromeOptions options = new ChromeOptions()
                    .setBinary("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");

            webDriver = new ChromeDriver(service, options);
            webDriver.get("https://qa-scooter.praktikum-services.ru/");
        }
        // иными способами webdriver на MacOs Chrome v116 не запускается!

        @After
        public void tearDown() {

            this.webDriver.quit();
        }

        @Test
        public void successfulOrderWithButtonInTheHeader() {
            MainPage mainPage = new MainPage(this.webDriver);
            OrderPage orderPage = new OrderPage(this.webDriver);

            mainPage.clickOnConfirmCookiesButton();
            mainPage.clickOrderButtonInTheHeader();
            makeOrder(orderPage);

            MatcherAssert.assertThat(
                    "Problem with creating a new order",
                    orderPage.getNewOrderSuccessMessage(),
                    containsString(this.expectedOrderSuccessText)
            );
        }

// нажатие кнопки "Заказать" на нижней части страницы
        @Test
        public void successfulOrderWithButtonDownThePage() {
            MainPage mainPage = new MainPage(this.webDriver);
            OrderPage orderPage = new OrderPage(this.webDriver);

            mainPage.clickOnConfirmCookiesButton();
            mainPage.clickOrderButtonDownThePage();
            makeOrder(orderPage);

            MatcherAssert.assertThat(
                    "Problem with creating a new order",
                    orderPage.getNewOrderSuccessMessage(),
                    containsString(this.expectedOrderSuccessText)
            );
        }

        private void makeOrder(OrderPage orderPage) {
            orderPage.waitForLoadingOrderForm();

            orderPage.setName(this.name);
            orderPage.setSurname(this.surname);
            orderPage.setAddress(this.address);
            orderPage.setMetro(this.metro);
            orderPage.setPhone(this.phone);

            orderPage.clickNextButton();

            orderPage.setDate(this.date);
            orderPage.setPeriod(this.period);
            orderPage.setColor(this.color);
            orderPage.setComment(this.comment);

            orderPage.makeOrder();
        }
    }