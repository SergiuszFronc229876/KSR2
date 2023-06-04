package pl.ksr.logic.calculation.functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrapezoidalFunctionTest {

    @Test
    void getMembershipDegree() {
        TrapezoidalFunction trap = new TrapezoidalFunction(150, 210, 300, 380);
        assertEquals(0, trap.getValue(23));
        assertEquals(0, trap.getValue(400));
        assertEquals(0, trap.getValue(150));
        assertEquals(0, trap.getValue(380));
        assertEquals(0.3333333333333333, trap.getValue(170));
        assertEquals(1, trap.getValue(230));
        assertEquals(0.125, trap.getValue(370));
    }

    @Test
    void getMembershipDegreeForTrapezWithoutLeftArm() {
        TrapezoidalFunction trap = new TrapezoidalFunction(210, 210, 300, 380);
        assertEquals(0, trap.getValue(200));
        assertEquals(1, trap.getValue(210));
    }

    @Test
    void getMembershipDegreeForTrapezWithoutRightArm() {
        TrapezoidalFunction trap = new TrapezoidalFunction(150, 210, 300, 300);
        assertEquals(0, trap.getValue(310));
        assertEquals(1, trap.getValue(300));
    }
}
