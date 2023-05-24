package pl.ksr.model.calculation.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TriangularFunctionTest {

    @Test
    void getMemberShipValue() {
        TriangularFunction triang = new TriangularFunction(60, 140, 220);
        assertEquals(0,triang.getMemberShipValue(50));
        assertEquals(0,triang.getMemberShipValue(230));
        assertEquals(0,triang.getMemberShipValue(60));
        assertEquals(0,triang.getMemberShipValue(220));
        assertEquals(1,triang.getMemberShipValue(140));
        assertEquals(0.125,triang.getMemberShipValue(70));
        assertEquals(0.25,triang.getMemberShipValue(200));
    }
}