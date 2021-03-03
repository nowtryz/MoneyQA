import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MoneyTest {
	static Random random;
	
	@BeforeAll
	static void setUpAll() {
		random = new Random();
	}
	
	@ParameterizedTest
	@CsvSource({
		"20, USD",
		"15.9, USD",
		"169846.17, EUR",
		"13451234, EUR",
		"4561.56, EUR"
	})
	void testMoney(double amount, String currency) {
		Money money = new Money(amount, currency);
		assertEquals(amount, money.amount());
		assertEquals(currency, money.currency());
	}
	
	@Test
	void testMoney_NullCurrency_ShouldFail() {
		assertThrows(Exception.class, () -> new Money(random.nextDouble(), null));
	}
	
	@Test
	void testEquals_SameObject() {
		Money money = new Money(random.nextDouble(), "EUR");
		assertTrue(money.equals(money));
	}
	
	@Test
	void testEquals_SameValues() {
		double amount = random.nextDouble();
		Money money = new Money(amount, "EUR");
		Money money2 = new Money(amount, "EUR");
		assertTrue(money.equals(money2));
	}
	
	@Test
	void testEquals_SameAmountDifferentCurrency_ShouldFail() {
		double amount = random.nextDouble();
		Money money = new Money(amount, "EUR");
		Money money2 = new Money(amount, "USD");
		assertEquals(money, money2);
	}
	
	@Test
	void testEquals_SameCurrencyDifferentAmount_ShouldNotBeEqual() {
		Money money = new Money(random.nextDouble(), "EUR");
		Money money2 = new Money(random.nextDouble(), "EUR");
		assertNotEquals(money, money2);
	}

	
	// conversion rate: 1.372
	@Test
	void testAdd_DifferentCurrencies() {
		Money eur = new Money(10, "EUR");
		Money usd = new Money(13.72, "USD"); // 10€
		assertEquals(20d, eur.add(usd).amount(), 0.1);
	}

	
	// conversion rate: 1.372
	@Test
	void testAdd_SameCurrency() {
		Money eur = new Money(10, "EUR");
		Money eur2 = new Money(10, "EUR"); // 10€
		assertEquals(20d, eur.add(eur2).amount(), 0.1);
	}

}
