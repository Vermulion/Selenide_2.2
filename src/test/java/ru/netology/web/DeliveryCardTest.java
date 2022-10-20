package ru.netology.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.ExactText;
import org.junit.jupiter.api.BeforeEach;
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
    //Calendar elements
    SelenideElement nextMonth = $x("//div[@data-step='1']");
    private String deliveryDate = generateDate(3);
    private String deliveryDate1 = generateDate(14);

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

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSendForm() {
        $("input[placeholder='Город']").setValue("Москва");
        $("input[placeholder='Дата встречи']").click();
        $("input[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
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
        $x(".//input[@placeholder='Город']").val("Мо");
        $(byText("Москва")).click();
        $x(".//button[@role='button']").click();
        //Calendar
        ElementsCollection dates = $$x("//td[@data-day]");
        int days = 14 - 3; //two weeks minus three days which are the minimum for delivery
        int remains;  //(optional) remained days those will have been subtracted from the next month
        int currentWeek = dates.size(); //amount of remained days this month
        if (currentWeek < days) { //in case of dates.size is less than 11
            remains = days - currentWeek;
            nextMonth.click();
            dates.get(remains).click();
        } else {
            dates.get(days).click();
        }
        $x(".//input[@placeholder='Дата встречи']").val(deliveryDate1);
        $x(".//span[@data-test-id='name']//child::input").setValue("Петр Прокофьев");
        $x(".//span[@data-test-id='phone']//child::input").setValue("+79978532542");
        $("[data-test-id='agreement'] span").click();
        $x(".//span[contains(text(), 'Забронировать')]//ancestor::button").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(16)).shouldHave(exactText("Успешно!"));
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(16))
                .shouldHave(exactText("Встреча успешно забронирована на " + deliveryDate1));
    }
}

