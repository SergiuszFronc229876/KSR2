package pl.ksr.model.calculation;

import lombok.*;
import pl.ksr.model.calculation.functions.MemberShipFunction;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class FuzzySet {
    private MemberShipFunction memberShipFunction;
    private ClassicSet set;
}
