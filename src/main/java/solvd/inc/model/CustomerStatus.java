package solvd.inc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerStatus {
    private Long id;
    private Timestamp vipStatusChangeDate;
    private Timestamp vipExpirationDate;
}
