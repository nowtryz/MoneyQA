import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class ChangeTest {
	static Random random;
	Change change;
	
	@BeforeAll
	static void setUpAll() {
		random = new Random();
	}

	@BeforeEach
	void setUp() throws Exception {
		change = new Change();
	}

	@RepeatedTest(5)
	void testChange_SameCurrency_EUR_shouldFail() {
		assertThrows(Exception.class, () -> change.change(new Money(random.nextDouble(), "EUR"), "EUR"));
		assertThrows(Exception.class, () -> change.change(new Money(random.nextDouble(), "USD"), "USD"));
	}

	@RepeatedTest(5)
	void testChange_NullCurrency_shouldFail() {
		assertThrows(Exception.class, () -> change.change(new Money(random.nextDouble(), "EUR"), null));
	}

	@ParameterizedTest
	// source: https://en.wikipedia.org/wiki/Currency#Control_and_production
	@ValueSource(strings = {"JPY", "GBP", "AUD", "CAD", "CHF", "CNY", "HKD", "NZD", "SEK", "KRW"})
	void testChange_UnknownCurrency_shouldFail(String currency) {
		assertThrows(Exception.class, () -> change.change(new Money(random.nextDouble(), "EUR"), currency));
		assertThrows(Exception.class, () -> change.change(new Money(random.nextDouble(), "EUR"), currency));
	}

	// conversion rate: 1.372
	@ParameterizedTest
	@CsvSource({
		"10, EUR, 13.72, USD",
		"10, USD, 7.29, EUR"
	})
	void testChange_KnownCurrency_ShouldSuccessWithCorrectValue(double amount, String currency, double expectedAmount, String newCurrency) {
		Money money = change.change(new Money(amount, currency), newCurrency);
		assertEquals(expectedAmount, money.amount(), 0.1);
	}
}
