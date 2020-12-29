package pattern.java.p04_factory.idcard;

import pattern.java.p04_factory.framework.Factory;
import pattern.java.p04_factory.framework.Product;

import java.util.ArrayList;
import java.util.List;

public class IDCardFactory extends Factory {
    private List<Product> owners = new ArrayList<>();

    @Override
    protected Product createProduct(String owner) {
        return new IDCard(owner);
    }

    @Override
    protected void registerProduct(Product product) {
        owners.add(product);
    }

    public List<Product> getOwners() {
        return owners;
    }
}
