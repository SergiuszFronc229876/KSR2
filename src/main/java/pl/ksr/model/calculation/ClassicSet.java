package pl.ksr.model.calculation;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@SuperBuilder
@ToString
public abstract class ClassicSet {

    private double beginOfUniverse;

    private double endOfUniverse;

    public boolean isEmpty() {
        return true;
    }

}

