package com.acko.enums;

public enum RoomName {
    WIMBLEDON(2),
    FEROZSHAHKOTLA(4),
    EDENGARDEN(10),
    LORDS(20);

    private final int capacity;

    RoomName(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        switch (this) {
            case WIMBLEDON -> {
                return "Wimbledon";
            }
            case FEROZSHAHKOTLA -> {
                return "FerozShahKotla";
            }
            case EDENGARDEN -> {
                return "EdenGarden";
            }
            case LORDS -> {
                return "Lords";
            }
            default -> throw new IllegalArgumentException("Unknown Room");
        }
    }
}
