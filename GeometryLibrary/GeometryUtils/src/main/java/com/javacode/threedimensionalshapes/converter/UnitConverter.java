package com.javacode.threedimensionalshapes.converter;

public class UnitConverter {

    public enum Units {
        INCHES (0.0254001),
        FEET (0.3048),
        MILES (1609.35),
        MILLIMETERS (0.001),
        CENTIMETERS (0.01),
        METERS (1),
        KILOMETERS (1000);

        private final double number;

        Units(double number) {
            this.number = number;
        }

        public double getNumber() {
            return number;
        }

    }


    private final Units unit;

    public UnitConverter(Units unit) {
        this.unit = unit;
    }

    public double convertToMeters(double value) {
        return value * unit.getNumber();
    }

    public double convertFromMeters(double value) {
        return value / unit.getNumber();
    }



}
