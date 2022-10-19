package ru.netology.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.ExactText;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.remote.tracing.EventAttribute.setValue;

class DeliveryCardTest {

    private String deliveryDate = generateDate(3);

    private String generateDate(int day) {
        return LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    private String today = generateDay();

    private String generateDay() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd"));
    }

    private String nextWeek = generateNextWeekday(7);

    private String generateNextWeekday(int day) {
        return LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd"));
    }

    @Test
    void  shouldSendForm() {
        open("http://localhost:9999");
        $("input[placeholder='Город']").setValue("Москва");
        $("input[placeholder='Дата встречи']").click();
        $("input[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.BACK_SPACE);
        $("input[placeholder='Дата встречи']").setValue(deliveryDate);
        $("input[name='name']").setValue("Петр Корн-Фарен");
        $("input[name='phone']").setValue("+79978532542");
        $("[data-test-id='agreement'] span").click();
        $("span[class='button__text']").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(16)).shouldHave(exactText("Успешно!"));
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(16))
                .shouldHave(exactText("Встреча успешно забронирована на " + deliveryDate));
    }

    @Test
    void shouldWorkDropListAndDatePicker() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("input[placeholder='Город']").setValue("Мо");
        $(byText("Москва")).click();
        $("button[role='button']").click();
        //Calendar
        SelenideElement calendar = $(".calendar__layout");
        ElementsCollection weekRows = $$("calendar__row");
//        SelenideElement todayDay = calendar.$$("[role='gridcell']").find(exactText(today));



        //This are the columns of the from date picker table
        SelenideElement nextMonth = $(".calendar__arrow calendar__arrow_direction_right");
        ElementsCollection columns = $$(By.tagName("td"));
        SelenideElement todayDay = columns.find(exactText(today));
        SelenideElement setDay = columns.find(exactText(nextWeek));

        ElementsCollection previousDays = $$("td[class*='off']");
        SelenideElement previousDay = previousDays.find(exactText(nextWeek));

            for (SelenideElement selenideElement : columns) {
                if (selenideElement.equals(previousDay)){
                    selenideElement.click();
                }
            }


    }
}

//    @Test
//    void shouldRegisterByAccountNumberDOMModification() {
//        open("http://localhost:9999");
//        $$(".tab-item").find(exactText("По номеру счёта")).click();
//        $("[name='number']").setValue("4055 0100 0123 4613 8564");
//        $("[name='phone']").setValue("+792000000000");
//        $$("button").find(exactText("Продолжить")).click();
//        $(withText("Успешная авторизация")).shouldBe(visible, Duration.ofMillis(5000));
//        $(byText("Личный кабинет")).shouldBe(visible, Duration.ofMillis(5000));
//    }
//
//    @Test
//    void shouldRegisterByAccountNumberVisibilityChange() {
//        open("http://localhost:9999");
//        $$(".tab-item").find(exactText("По номеру счёта")).click();
//        $$("[name='number']").last().setValue("4055 0100 0123 4613 8564");
//        $$("[name='phone']").last().setValue("+792000000000");
//        $$("button").find(exactText("Продолжить")).click();
//        $(withText("Успешная авторизация")).shouldBe(visible, Duration.ofSeconds(5));
//        $(byText("Личный кабинет")).shouldBe(visible, Duration.ofSeconds(5));
//    }