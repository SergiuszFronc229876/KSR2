package pl.ksr.logic.calculation.functions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class TrapezoidalFunction implements MembershipFunction {
    private final double leftMin;
    private final double leftMax;
    private final double rightMax;
    private final double rightMin;

    @Override
    public double getValue(double x) {
        if (x >= leftMax && x <= rightMax) {
            return 1.0;
        } else if (x <= leftMin || x >= rightMin) {
            return 0.0;
        } else if (x > leftMin && x < leftMax) {
            return (x - leftMin) / (leftMax - leftMin);
        } else {
            return (rightMin - x) / (rightMin - rightMax);
        }
    }

    @Override
    public double getAreaFunction() {
        return ((rightMin - leftMin) + (rightMax - leftMax)) / 2;
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
