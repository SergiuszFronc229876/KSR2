package pl.ksr.logic.summarization;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class MeasureWeights {
    private Map<String, Double> weights;

    public boolean areCorrect() {
        return weights.values()
                .stream()
                .mapToDouble(Double::doubleValue)
                .sum() == 1;
    }
}
