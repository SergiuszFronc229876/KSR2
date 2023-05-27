package pl.ksr.summarization;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class LinguisticVariable {
    private String name;
    private List<Label> labels;
}
