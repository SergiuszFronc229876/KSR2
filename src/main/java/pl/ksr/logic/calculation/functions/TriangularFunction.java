package pl.ksr.logic.calculation.functions;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class TriangularFunction implements MembershipFunction {
    private final double leftMin;
    private final double centreMax;
    private final double rightMin;

    @Override
    public double getValue(double x) {
        if (x == centreMax && (centreMax == leftMin || centreMax == rightMin)) {
            return 1.0;
        } else if (x >= centreMax && x <= rightMin) {
            return (rightMin - x) / (rightMin - centreMax);
        } else if (x < centreMax && x >= leftMin) {
            return (x - leftMin) / (centreMax - leftMin);
        } else {
            return 0.0;
        }
    }

    @Override
    public double getAreaFunction() {
        return (rightMin - leftMin) / 2;
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
