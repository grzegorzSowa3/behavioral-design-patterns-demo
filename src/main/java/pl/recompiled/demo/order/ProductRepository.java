package pl.recompiled.demo.order;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(UUID productId);

}
