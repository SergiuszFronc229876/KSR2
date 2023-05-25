package pl.ksr.calculation.functions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class UnionMembershipFunction extends CombinedMembershipFunction {
    public UnionMembershipFunction(MembershipFunction function1, MembershipFunction function2) {
        super(function1, function2);
    }

    @Override
    public double getValue(double x) {
        return Math.max(function1.getValue(x), function2.getValue(x));
    }
}
