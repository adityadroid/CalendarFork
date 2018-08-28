// Copyright 2012 Square, Inc.

package com.squareup.timessquare;

import java.util.Date;

/**
 * Describes the state of a particular date cell in a {@link MonthView}.
 */
class MonthCellDescriptor {

    private final Date date;
    private final int value;
    private final boolean isCurrentMonth;
    private boolean isSelected;
    private final boolean isToday;
    private final boolean isSelectable;
    private boolean isHighlighted;
    private boolean isBlocked, isConfirmed, isPending;
    private RangeState rangeState;

    MonthCellDescriptor(Date date, boolean currentMonth, boolean selectable, boolean selected,
                        boolean today, boolean highlighted, boolean isBlocked, boolean isConfirmed, boolean isPending, int value, RangeState rangeState) {
        this.date = date;
        isCurrentMonth = currentMonth;
        isSelectable = selectable;
        isHighlighted = highlighted;
        isSelected = selected;
        isToday = today;
        this.value = value;
        this.rangeState = rangeState;
        this.isBlocked = isBlocked;
        this.isConfirmed = isConfirmed;
        this.isPending = isPending;
    }

    public Date getDate() {
        return date;
    }

    public boolean isCurrentMonth() {
        return isCurrentMonth;
    }

    public boolean isSelectable() {
        return isSelectable;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    boolean isHighlighted() {
        return isHighlighted;
    }

    void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    public boolean isPending() {
        return isPending;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean isToday() {
        return isToday;
    }

    public RangeState getRangeState() {
        return rangeState;
    }

    public void setRangeState(RangeState rangeState) {
        this.rangeState = rangeState;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "MonthCellDescriptor{"
                + "date="
                + date
                + ", value="
                + value
                + ", isCurrentMonth="
                + isCurrentMonth
                + ", isSelected="
                + isSelected
                + ", isToday="
                + isToday
                + ", isSelectable="
                + isSelectable
                + ", isHighlighted="
                + isHighlighted
                + ", isBlocked="
                + isBlocked
                + ", isPending="
                + isPending
                + ", isConfirmed="
                + isConfirmed
                + ", rangeState="
                + rangeState
                + '}';
    }
}
