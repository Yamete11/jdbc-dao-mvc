package solvd.inc.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private CustomerStatus customerStatus;
    private List<Trip> trips;
    private List<CustomerPromotion> customerPromotions;
}

