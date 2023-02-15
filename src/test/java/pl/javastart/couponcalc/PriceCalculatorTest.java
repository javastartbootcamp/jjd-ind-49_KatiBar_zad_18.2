package pl.javastart.couponcalc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalculatorTest {

    @Test
    public void shouldReturnZeroForNoProducts() {
        // given
        PriceCalculator priceCalculator = new PriceCalculator();

        // when
        double result = priceCalculator.calculatePrice(null, null);

        // then
        assertThat(result).isEqualTo(0.);
    }

    @Test
    public void shouldReturnPriceForSingleProductAndNoCoupons() {

        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));

        // when
        double result = priceCalculator.calculatePrice(products, null);

        // then
        assertThat(result).isEqualTo(5.99);
    }

    @Test
    public void shouldReturnPriceForMoreProductAndNoCoupons() {

        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        products.add(new Product("Jogurt", 2.50, Category.FOOD));
        products.add(new Product("Bilety do kina", 115.23, Category.ENTERTAINMENT));
        products.add(new Product("Ręcznik", 25.99, Category.HOME));

        // when
        double result = priceCalculator.calculatePrice(products, null);

        // then
        assertThat(result).isEqualTo(149.71);
    }

    @Test
    public void shouldReturnPriceForSingleProductAndOneCoupon() {

        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 20));

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(4.79);
    }

    @Test
    public void shouldReturnPriceForMoreProductAndCouponsWithCategory() {

        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        products.add(new Product("Jogurt", 2.50, Category.FOOD));
        products.add(new Product("Bilety do kina", 115.23, Category.ENTERTAINMENT));
        products.add(new Product("Ręcznik", 25.99, Category.HOME)); // whole sum = 149.71

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 20)); //discount = 1.698, sum = 148.01
        coupons.add(new Coupon(Category.ENTERTAINMENT, 50)); // discount = 57.615, sum = 92.10
        coupons.add(new Coupon(Category.HOME, 10)); // discount = 23.391, sum = 147.11

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(92.09);
    }

    @Test
    public void shouldReturnPriceForMoreProductAndCouponsWithoutCategory() {

        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        products.add(new Product("Jogurt", 2.50, Category.FOOD));
        products.add(new Product("Bilety do kina", 115.23, Category.ENTERTAINMENT));
        products.add(new Product("Ręcznik", 25.99, Category.HOME));

        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 20)); //discount = 1.698, sum = 148.01
        coupons.add(new Coupon(null, 35)); // discount = 52.4, sum = 97.31
        coupons.add(new Coupon(Category.ENTERTAINMENT, 80)); //discount = 92.184, sum = 57.53

        // when
        double result = priceCalculator.calculatePrice(products, coupons);

        // then
        assertThat(result).isEqualTo(57.53);
    }
}