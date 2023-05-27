package pl.ksr.summarization;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Quantifier {

    private String name;
    private Label label;
}
