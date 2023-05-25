package pl.ksr.calculation.functions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class GaussianFunction implements MembershipFunction {
    private final double centreMax;
    private final double standardDeviation;

    @Override
    public double getValue(double x) {
        double exponent = -1 * Math.pow((x - centreMax), 2) / Math.pow(2 * standardDeviation, 2);
        return Math.exp(exponent);
    }
}
