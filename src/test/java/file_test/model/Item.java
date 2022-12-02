package file_test.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class Item {
    private String type;
    private String productId;
    private String planId;
    private String productName;
    private String productType;
    private Integer quantity;
    private BigDecimal priceWithVat;
    private Integer vatRate;
    private Supplier supplier;
    private List<Addon> addons;
}
