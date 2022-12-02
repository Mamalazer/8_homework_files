package file_test.model;

import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class Order {
    private String userId;
    private String referenceId;
    private List<Item> items;
    private PromoCode promoCode;
    private Date validTill;
    private RetryPolicy retryPolicy;
    private String paymentMethodId;
    private Requisites requisites;
    private String orderOrigin;
}
