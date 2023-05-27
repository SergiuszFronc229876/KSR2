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
    private final double leftMin;
    private final double rightMin;

    @Override
    public double getValue(double x) {
        if (x < leftMin || x > rightMin) {
            return 0;
        }
        double exponent = -1 * Math.pow((x - centreMax), 2) / Math.pow(2 * standardDeviation, 2);
        return Math.exp(exponent);
    }

    public double getAreaFunction() {
        int numSteps = 1000; // Number of steps for numerical integration
        double stepSize = (rightMin - leftMin) / numSteps; // Size of each step
        double area = 0.0;

        for (int i = 0; i < numSteps; i++) {
            double x = leftMin + i * stepSize;
            area += getValue(x) * stepSize; // Accumulate the area under the curve
        }

        return area;
    }

    @Override
    public double getLeftLimit() {
        return leftMin;
    }

    @Override
    public double getRightLimit() {
        return rightMin;
    }
}
