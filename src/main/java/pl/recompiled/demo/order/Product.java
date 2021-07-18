package pl.recompiled.demo.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Product {

    private UUID id;
    private String name;
    private Category category;
    private Integer price;

    static Product of(String name, Category category, Integer price) {
        final Product product = new Product();
        product.id = UUID.randomUUID();
        product.name = name;
        product.category = category;
        product.price = price;
        return product;
    }

    public void accept(Visitor visitor) {
        visitor.doForProduct(this);
    }

}
