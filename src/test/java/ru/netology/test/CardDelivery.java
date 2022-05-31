package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class CardDelivery {

    private String date(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldOrderCardDelivery() {
        String actualDate = date(3);

        $("[data-test-id = 'city'] input").setValue("Кемерово");
        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id = 'date'] input").setValue(actualDate);
        $("[data-test-id = 'name'] input").setValue("Гениев Евгений");
        $("[data-test-id = 'phone'] input").setValue("+79998887766");
        $("[data-test-id = 'agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(appear, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(exactText("Встреча успешно забронирована на " + actualDate));
    }
}
