package pl.ksr.model.calculation.functions;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TrapezoidalFunction implements MemberShipFunction {
    private double leftDown;
    private double leftUp;
    private double rightUp;
    private double rightDown;


    @Override
    public double getMemberShipValue(double x) {
        if ((x == leftDown && x == leftUp) || (x == rightUp && x == rightDown)) return 1.0;
        else if (x <= leftDown || x >= rightDown) return 0.0;
        else if (x < leftUp) return leftArm(x);
        else if (x > rightUp) return rightArm(x);
        else return 1.0;
    }

    private double leftArm(double x) {
        return x / (leftUp - leftDown) - leftDown / (leftUp - leftDown);
    }

    private double rightArm(double x) {
        return (x / (rightUp - rightDown)) - (rightDown / (rightUp - rightDown));
    }
}

