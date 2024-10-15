package solvd.inc.service;

import solvd.inc.model.PaymentMethod;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodService {
    void createPaymentMethod(PaymentMethod paymentMethod);
    Optional<PaymentMethod> getPaymentMethodById(Long id);
    List<PaymentMethod> getAllPaymentMethods();
    void updatePaymentMethod(PaymentMethod paymentMethod);
    void deletePaymentMethodById(Long id);
}
