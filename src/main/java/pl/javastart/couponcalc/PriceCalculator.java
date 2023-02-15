package pl.javastart.couponcalc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PriceCalculator {

    public double calculatePrice(List<Product> products, List<Coupon> coupons) {
        if (products == null || products.isEmpty()) {
            return 0;
        } else if (coupons == null || coupons.isEmpty()) {
            return products.stream()
                    .map(Product::getPrice)
                    .reduce(Double::sum)
                    .get();
        } else {
            return getSumProductsWithCoupons(products, coupons);
        }
    }

    private static double getSumProductsWithCoupons(List<Product> products, List<Coupon> coupons) {
        int sumIndex = 0;
        double min = 0;

        for (Coupon coupon : coupons) {
            double sum = getSum(products, coupon);

            if (sumIndex == 0 || sum < min) {
                min = sum;
            }
            sumIndex++;
        }
        return round(min);
    }

    private static double getSum(List<Product> products, Coupon coupon) {
        Category couponCategory = coupon.getCategory();
        int discountValueInPercents = coupon.getDiscountValueInPercents();
        double discountValueDecimal = discountValueInPercents * 0.01;

        double sum = 0;
        for (Product product : products) {
            double price;
            if (couponCategory == null || couponCategory.equals(product.getCategory())) {
                price = product.getPrice() * (1 - discountValueDecimal);

            } else {
                price = product.getPrice();
            }
            sum += price;
        }
        return sum;
    }

    public static double round(double value) {
        int precision = 2;
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(precision, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}