package pl.ksr.logic.calculation.functions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class ComplementMembershipFunction implements MembershipFunction {
    private final MembershipFunction function;

    @Override
    public double getValue(double x) {
        return 1 - function.getValue(x);
    }

    @Override
    public double getAreaFunction() {
        throw new UnsupportedOperationException("Not implemented yet."); // TODO: need implementation
    }

    @Override
    public double getLeftLimit() {
        return function.getLeftLimit();
    }

    @Override
    public double getRightLimit() {
        return function.getRightLimit();
    }
}
