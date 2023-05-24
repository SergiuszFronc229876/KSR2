package pl.ksr.model.calculation.functions;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TriangularFunction implements MemberShipFunction {
    private double leftDown;
    private double mid;
    private double rightDown;

    @Override
    public double getMemberShipValue(double x) {
        if (x <= leftDown || x >= rightDown) return 0.0;
        else if (x < mid) return leftArm(x);
        else if (x > mid) return rightArm(x);
        else return 1.0;
    }

    private double leftArm(double x) {
        return x / (mid - leftDown) - leftDown / (mid - leftDown);
    }

    private double rightArm(double x) {
        return (x / (mid - rightDown)) - (rightDown / (mid - rightDown));
    }

}
