package com.test.dailyloggraph;

public enum DutyStatus {
    OFF_DUTY(1, "Off-Duty"),
    SLEEPER_BERTH(2,"Sleeper Birth"),
    DRIVING(3, "Driving"),
    ON_DUTY(4, "On Duty");

    private final int value;
    private final String text;

    DutyStatus(final int statusValue, final String statusText) {
        value = statusValue;
        text = statusText;
    }

    public int getValue() { return value; }

    public String getText() { return text; }
}

