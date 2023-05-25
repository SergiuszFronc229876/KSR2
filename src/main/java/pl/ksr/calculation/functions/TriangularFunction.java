package pl.ksr.calculation.functions;


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
        if (x <= leftMin || x >= rightMin) {
            return 0.0;
        } else if (x >= centreMax) {
            return (rightMin - x) / (rightMin - centreMax);
        } else {
            return (x - leftMin) / (centreMax - leftMin);
        }
    }
}
