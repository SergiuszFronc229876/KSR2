package pl.ksr.summarization;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class LinguisticVariable {
    private String name;
    private List<Label> labels;
}
