package ru.netology.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

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

    @Test
    void  ShouldSendForm() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("input[placeholder='Город']").setValue("Москва");
        $("span[class='menu-item__control']").click();
        $("input[placeholder='Дата встречи']").click();
        $("input[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME),Keys.BACK_SPACE);
        $("input[placeholder='Дата встречи']").setValue(deliveryDate);
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