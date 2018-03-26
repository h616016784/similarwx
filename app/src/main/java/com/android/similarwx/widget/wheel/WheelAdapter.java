package com.android.similarwx.widget.wheel;

/**
 * Wheel adapter.
 */
public class WheelAdapter implements WheelInterface {

    private String[] mItems;

    private long[] mValues;

    public WheelAdapter(String[] items) {
        mItems = items;
    }

    public WheelAdapter(String[] items, long[] values) {
        mItems = items;
        mValues = values;
    }

    @Override
    public String getItem(int index) {
        if (index >= 0 && index < getItemsCount()) {
            return mItems[index];
        }
        return null;
    }

    @Override
    public long getValue(int index) {
        if (index >= 0 && index < getValuesCount()) {
            return mValues[index];
        }
        return 0;
    }

    @Override
    public int getItemsCount() {
        return mItems.length;
    }

    @Override
    public int getValuesCount() {
        return mValues != null ? mValues.length : 0;
    }

    @Override
    public int getMaximumLength() {
        return mItems.length;
    }
}
