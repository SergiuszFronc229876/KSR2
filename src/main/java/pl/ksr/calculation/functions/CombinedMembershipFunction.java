package pl.ksr.calculation.functions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public abstract class CombinedMembershipFunction implements MembershipFunction {
    protected final MembershipFunction function1;
    protected final MembershipFunction function2;
}
