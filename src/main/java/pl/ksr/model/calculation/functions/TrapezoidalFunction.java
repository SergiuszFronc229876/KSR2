package pl.ksr.model.calculation.functions;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TrapezoidalFunction implements MemberShipFunction {

    @Override
    public double getMemberShipValue(double x) {
        return 0;
    }
}
