package solvd.inc.service.impl;

import solvd.inc.dao.PaymentMethodDAO;
import solvd.inc.dao.implementation.PaymentMethodImpl;
import solvd.inc.model.PaymentMethod;
import solvd.inc.service.PaymentMethodService;

import java.util.List;
import java.util.Optional;

public class PaymentMethodServiceImpl implements PaymentMethodService {

    private final PaymentMethodDAO paymentMethodDAO;

    public PaymentMethodServiceImpl() {
        this.paymentMethodDAO = new PaymentMethodImpl();
    }

    @Override
    public void createPaymentMethod(PaymentMethod paymentMethod) {
        validatePaymentMethod(paymentMethod);

        if (paymentMethodDAO.existsByTitle(paymentMethod.getTitle())) {
            throw new IllegalArgumentException("PaymentMethod with title '" + paymentMethod.getTitle() + "' already exists");
        }

        paymentMethodDAO.create(paymentMethod);
    }

    @Override
    public Optional<PaymentMethod> getPaymentMethodById(Long id) {
        PaymentMethod paymentMethod = paymentMethodDAO.getById(id)
                .orElseThrow(() -> new RuntimeException("PaymentMethod with id " + id + " not found"));
        return Optional.of(paymentMethod);
    }

    @Override
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodDAO.findAll();
    }

    @Override
    public void updatePaymentMethod(PaymentMethod paymentMethod) {
        validatePaymentMethod(paymentMethod);

        if (paymentMethodDAO.existsByTitle(paymentMethod.getTitle())) {
            throw new IllegalArgumentException("PaymentMethod with title '" + paymentMethod.getTitle() + "' already exists");
        }
        paymentMethodDAO.update(paymentMethod);
    }

    @Override
    public void deletePaymentMethodById(Long id) {
        paymentMethodDAO.deleteById(id);
    }

    private void validatePaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod.getTitle() == null || paymentMethod.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("PaymentMethod title cannot be null or empty");
        }
    }
}
