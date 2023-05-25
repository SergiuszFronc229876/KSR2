package pl.ksr.calculation.functions;

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
}
