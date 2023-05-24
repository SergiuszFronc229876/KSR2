package pl.ksr.model.calculation.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrapezoidalFunctionTest {

    @Test
    void getMemberShipValue() {
        TrapezoidalFunction trap = new TrapezoidalFunction(150, 210, 300, 380);
        assertEquals(0,trap.getMemberShipValue(23));
        assertEquals(0,trap.getMemberShipValue(400));
        assertEquals(0,trap.getMemberShipValue(150));
        assertEquals(0,trap.getMemberShipValue(380));
        assertEquals(0.3333333333333335,trap.getMemberShipValue(170));
        assertEquals(1,trap.getMemberShipValue(230));
        assertEquals(0.125,trap.getMemberShipValue(370));
    }
    @Test
    void getMemberShipValueForTrapezWithoutLeftArm() {
        TrapezoidalFunction trap = new TrapezoidalFunction(210, 210, 300, 380);
        assertEquals(0,trap.getMemberShipValue(200));
        assertEquals(1,trap.getMemberShipValue(210));
    }    @Test
    void getMemberShipValueForTrapezWithoutRightArm() {
        TrapezoidalFunction trap = new TrapezoidalFunction(150, 210, 300, 300);
        assertEquals(0,trap.getMemberShipValue(310));
        assertEquals(1,trap.getMemberShipValue(300));
    }
}