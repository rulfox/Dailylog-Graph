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

    public String getText() {
        return text;
    }

    public static DutyStatus getDutyStatus(int value) {
        switch (value){
            case 1:
                return OFF_DUTY;
            case 2:
                return SLEEPER_BERTH;
            case 3:
                return DRIVING;
            case 4:
                return ON_DUTY;
        }
        return ON_DUTY;
    }
}

