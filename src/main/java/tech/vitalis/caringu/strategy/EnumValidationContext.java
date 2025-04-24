package tech.vitalis.caringu.strategy;

public class EnumValidationContext {

    private EnumValidationStrategy strategy;

    public void setStrategy(EnumValidationStrategy strategy) {
        this.strategy = strategy;
    }

    public void validar(String valorEnum) {
        strategy.validar(valorEnum);
    }
}
