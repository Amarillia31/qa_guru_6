package com.elena;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

@DisplayName("Wildberries Search Items")

public class ParameterizedWebTest {
    @BeforeAll
    static void setUp() {
        Configuration.holdBrowserOpen = true;
//        Configuration.baseUrl = "https://demoqa.com";
        Selenide.open("https://www.wildberries.ru/");
        Configuration.browserSize = "1920x1080";

    }

    @CsvSource(value = {
            "nike, Куртка",
            "reebok, Кроссовки"

    })
    @ParameterizedTest(name = "Search product {1} of brand {0}")
    void searchProduct(String testData, String expectedResult) {
        $("#searchInput").setValue(testData);
        $("#searchInput").pressEnter();
        $$(".product-card__brand-name").find(text(expectedResult))
                .shouldBe(visible);
    }

    static Stream<Arguments> methodSearchWb() {
        return Stream.of(
                Arguments.of("nike", "Носки"),
                Arguments.of("reebok", "Кроссовки")
        );
    }

    @MethodSource("methodSearchWb")
    @ParameterizedTest(name = "check search of {0} found {1}")
    void searchItemByBrand(String testData, String expectedResult) {
        $("#searchInput").setValue(testData);
        $("#searchInput").pressEnter();
        $$(".product-card__brand-name").find(text(expectedResult))
                .shouldBe(visible);
    }

    @MethodSource("methodSearchWb")
    @ParameterizedTest(name = "check search of {0} and {1}")
    void searchItemByBrandAndCategory(String testData, String expectedResult) {
        $("#searchInput").setValue(testData + " " + expectedResult);
        $("#searchInput").pressEnter();
        $$(".product-card__brand-name").find(text(expectedResult))
                .shouldBe(visible);
    }

}