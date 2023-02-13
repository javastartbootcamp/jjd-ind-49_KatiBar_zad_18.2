package pl.javastart.couponcalc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PriceCalculator {

    public double calculatePrice(List<Product> products, List<Coupon> coupons) {
        if (products == null || products.isEmpty()) {
            return 0;
        }

        if (coupons == null || coupons.isEmpty()) {
            return products.stream()
                    .map(Product::getPrice)
                    .reduce(Double::sum)
                    .get();
        }

        if (coupons.size() == 1) {
            Category couponCategory = coupons.get(0).getCategory();
            int discountValueInPercents = coupons.get(0).getDiscountValueInPercents();
            double discountValueDecimal = discountValueInPercents * 0.01;
            Double productPriceWithDiscount = products.stream()
                    .filter(x -> x.getCategory().equals(couponCategory))
                    .map(Product::getPrice)
                    .map(x -> x - (x * discountValueDecimal))
                    .reduce(Double::sum)
                    .get();

            return round(productPriceWithDiscount);
        }

        return 0;
    }

    public static double round(double value) {
        int precision = 2;
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(precision, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

}