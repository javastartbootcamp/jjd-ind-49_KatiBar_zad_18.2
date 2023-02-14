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
        int couponsNumber = coupons.size();
        double[] sumForEachCouponArray = new double[couponsNumber];
        int sumIndex = 0;

        for (Coupon coupon : coupons) {
            Category couponCategory = coupon.getCategory();
            int discountValueInPercents = coupon.getDiscountValueInPercents();
            double discountValueDecimal = discountValueInPercents * 0.01;

            for (Product product : products) {
                double price;
                if (couponCategory == null || couponCategory.equals(product.getCategory())) {
                    price = product.getPrice() * (1 - discountValueDecimal);

                } else {
                    price = product.getPrice();
                }
                sumForEachCouponArray[sumIndex] += price;
            }
            sumIndex++;
        }

        double min = sumForEachCouponArray[0];
        for (int i = 0; i < sumForEachCouponArray.length; i++) {
            if (sumForEachCouponArray[i] < min) {
                min = sumForEachCouponArray[i];
            }
        }
        return round(min);
    }

    public static double round(double value) {
        int precision = 2;
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(precision, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}