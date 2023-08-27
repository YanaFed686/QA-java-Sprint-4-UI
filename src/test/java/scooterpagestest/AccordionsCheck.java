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

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.fail;

    @RunWith(Parameterized.class)
    public class AccordionsCheck {

        private WebDriver webDriver;

// Номер вопроса в списке "вопросы о важном" по порядку
        private final int numberOfElement;

// Получение ожидаемого текста
        private final String expectedHeaderText;

// Ожидаемый текст ответа
        private final String expectedAnswerText;

// Конструктор AccordionsCheck
        public AccordionsCheck(int numberOfAccordionItem, String expectedHeaderText, String expectedAnswerText) {
            this.numberOfElement = numberOfAccordionItem;
            this.expectedHeaderText = expectedHeaderText;
            this.expectedAnswerText = expectedAnswerText;
        }

// Прописывание заголовков и ответов без орфографических ошибок
        @Parameterized.Parameters(name = "Текст в блоке\"Вопросы о важном\". Проверяемый элемент: {1}")
        public static Object[][] setTestData() {
            return new Object[][] {
                    {0, "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                    {1, "Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                    {2, "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                    {3, "Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                    {4, "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                    {5, "Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                    {6, "Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                    {7, "Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области." },
            };
            // Тест падает на последнем пункте, так как допущена опечатка в заголовке
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
        public void checkAccordionIsCorrect() {
            MainPage mainPage = new MainPage(this.webDriver);

            mainPage.clickOnConfirmCookiesButton();

            mainPage.clickEachListHeader(this.numberOfElement);
            mainPage.waitForLoadTheAnswer(this.numberOfElement);

            if (mainPage.isAnswersToListHeadersDisplayed(this.numberOfElement)) {
                MatcherAssert.assertThat("Problems with text in accordion header #" + this.numberOfElement,
                        this.expectedHeaderText,
                        equalTo(mainPage.getEachListHeader(this.numberOfElement))
                );
                MatcherAssert.assertThat("Problems with text in accordion item #" + this.numberOfElement,
                        this.expectedAnswerText,
                        equalTo(mainPage.getAnswersText(this.numberOfElement))
                );
            }
            else {
                fail("Accordion header item #" + this.numberOfElement + "didn't load");
            }
        }
    }