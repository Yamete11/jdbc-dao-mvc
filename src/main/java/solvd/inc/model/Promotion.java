package solvd.inc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    private Long id;
    private String promotionCode;
    private String description;
    private double discountPercentage;
    private Timestamp expirationDate;
    private boolean isActive;
    private List<CustomerPromotion> customerPromotions;

}
