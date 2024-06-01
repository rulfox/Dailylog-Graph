package com.test.dailyloggraph;

public enum DutyStatus {
    OFF_DUTY(1),
    SLEEPER_BERTH(2),
    DRIVING(3),
    ON_DUTY(4);

    private final int value;

    DutyStatus(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}

