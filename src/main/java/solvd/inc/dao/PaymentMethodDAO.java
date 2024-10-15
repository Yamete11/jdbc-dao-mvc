package solvd.inc.dao;

import solvd.inc.model.PaymentMethod;

public interface PaymentMethodDAO extends GenericDAO<PaymentMethod> {
    boolean existsByTitle(String title);
}
