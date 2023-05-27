package pl.ksr.calculation.functions;

public interface MembershipFunction {
    double getValue(double x);

    double getAreaFunction();

    double getLeftLimit();

    double getRightLimit();
}
