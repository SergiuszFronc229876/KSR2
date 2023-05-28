package pl.ksr.model.calculation.functions;

import org.junit.jupiter.api.Test;
import pl.ksr.logic.calculation.functions.GaussianFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GaussianFunctionTest {

    @Test
    public void testCalculateMembership() {
        GaussianFunction gaussianFunction = new GaussianFunction(125.0, 15.0, 50, 200);
        assertEquals(0.0, gaussianFunction.getValue(49.0),0.000000001);
        assertEquals(0.001930454, gaussianFunction.getValue(50.0),0.000000001);
        assertEquals(0.009145947, gaussianFunction.getValue(60.0), 0.000000001);
        assertEquals(0.034696686, gaussianFunction.getValue(70.0), 0.000000001);
        assertEquals(0.105399225, gaussianFunction.getValue(80.0), 0.000000001);
        assertEquals(0.256375757, gaussianFunction.getValue(90.0), 0.000000001);
        assertEquals(0.499351789, gaussianFunction.getValue(100.0), 0.000000001);
        assertEquals(0.778800783, gaussianFunction.getValue(110.0), 0.000000001);
        assertEquals(0.972604477, gaussianFunction.getValue(120.0), 0.000000001);
        assertEquals(0.972604477, gaussianFunction.getValue(130.0), 0.000000001);
        assertEquals(0.778800783, gaussianFunction.getValue(140.0), 0.000000001);
        assertEquals(0.499351789, gaussianFunction.getValue(150.0), 0.000000001);
        assertEquals(0.256375757, gaussianFunction.getValue(160.0), 0.000000001);
        assertEquals(0.105399225, gaussianFunction.getValue(170.0), 0.000000001);
        assertEquals(0.034696686, gaussianFunction.getValue(180.0), 0.000000001);
        assertEquals(0.009145947, gaussianFunction.getValue(190.0), 0.000000001);
        assertEquals(0.001930454, gaussianFunction.getValue(200.0), 0.000000001);
        assertEquals(0.0, gaussianFunction.getValue(201.0), 0.000000001);
    }

    @Test
    public void testCalculateMembershipOfLeftHalfVut() {
        GaussianFunction gaussianFunction = new GaussianFunction(125.0, 15.0, 50, 125);
        assertEquals(0.0, gaussianFunction.getValue(49.0),0.000000001);
        assertEquals(0.001930454, gaussianFunction.getValue(50.0),0.000000001);
        assertEquals(0.009145947, gaussianFunction.getValue(60.0), 0.000000001);
        assertEquals(0.034696686, gaussianFunction.getValue(70.0), 0.000000001);
        assertEquals(0.105399225, gaussianFunction.getValue(80.0), 0.000000001);
        assertEquals(0.256375757, gaussianFunction.getValue(90.0), 0.000000001);
        assertEquals(0.499351789, gaussianFunction.getValue(100.0), 0.000000001);
        assertEquals(0.778800783, gaussianFunction.getValue(110.0), 0.000000001);
        assertEquals(1.0, gaussianFunction.getValue(125.0), 0.000000001);
        assertEquals(0.0, gaussianFunction.getValue(126.0), 0.000000001);
    }

    @Test
    public void testCalculateMembershipOfRightHalfVut() {
        GaussianFunction gaussianFunction = new GaussianFunction(125.0, 15.0, 125, 200);
        assertEquals(0.0, gaussianFunction.getValue(124.0), 0.000000001);
        assertEquals(1.0, gaussianFunction.getValue(125.0), 0.000000001);
        assertEquals(0.972604477, gaussianFunction.getValue(130.0), 0.000000001);
        assertEquals(0.778800783, gaussianFunction.getValue(140.0), 0.000000001);
        assertEquals(0.499351789, gaussianFunction.getValue(150.0), 0.000000001);
        assertEquals(0.256375757, gaussianFunction.getValue(160.0), 0.000000001);
        assertEquals(0.105399225, gaussianFunction.getValue(170.0), 0.000000001);
        assertEquals(0.034696686, gaussianFunction.getValue(180.0), 0.000000001);
        assertEquals(0.009145947, gaussianFunction.getValue(190.0), 0.000000001);
        assertEquals(0.001930454, gaussianFunction.getValue(200.0), 0.000000001);
        assertEquals(0.0, gaussianFunction.getValue(201.0),0.000000001);
    }
}
