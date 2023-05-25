package pl.ksr.model.calculation.functions;

import org.junit.jupiter.api.Test;
import pl.ksr.calculation.functions.TriangularFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TriangularFunctionTest {

    @Test
    void getMemberShipValue() {
        TriangularFunction triang = new TriangularFunction(60, 140, 220);
        assertEquals(0, triang.getValue(50));
        assertEquals(0, triang.getValue(230));
        assertEquals(0, triang.getValue(60));
        assertEquals(0, triang.getValue(220));
        assertEquals(1, triang.getValue(140));
        assertEquals(0.125, triang.getValue(70));
        assertEquals(0.25, triang.getValue(200));
    }
}