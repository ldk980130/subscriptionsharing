package com.sss.subscriptionsharing.domain.report;

public enum Reason {
    CURSE("욕설"),
    SLANDERING("비방"),
    AD("광고"),
    FALSE("허위");

    private String description;

    Reason(String description) {
        this.description = description;
    }
}
