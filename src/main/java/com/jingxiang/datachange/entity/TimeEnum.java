package com.jingxiang.datachange.entity;

import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;


public enum TimeEnum {
    Hour("HALFHOUR", 1800000, DateHistogramInterval.MINUTE),
    SixHour("SIXHOUR", 21600000, DateHistogramInterval.HOUR),
    Day("DAY", 86400000, DateHistogramInterval.HOUR),
    Week("WEEK", 604800000, DateHistogramInterval.DAY),
    Month("MONTH", 1, DateHistogramInterval.DAY),
    ThreeMonth("THREEMONTH", 3, DateHistogramInterval.DAY),
    SixMonth("SIXMONTH", 6, DateHistogramInterval.MONTH),
    Year("YEAR", 12, DateHistogramInterval.MONTH);

    //**传递的参数*/
    String mTime;
    //**时间差*/
    long mDValue;
    //**时间维度*/
    DateHistogramInterval mDateHistogramInterval;

    TimeEnum(String hour, long dValue, DateHistogramInterval dateHistogramInterval) {
        mTime = hour;
        mDValue = dValue;
        mDateHistogramInterval = dateHistogramInterval;
    }

    public static TimeEnum getTimeEnum(String time) {
        for (TimeEnum task : values()) {
            if (time.equals(task.mTime)) {
                return task;
            }
        }
        return Hour;
    }


    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public long getmDValue() {
        return mDValue;
    }

    public void setmDValue(long mDValue) {
        this.mDValue = mDValue;
    }

    public DateHistogramInterval getmDateHistogramInterval() {
        return mDateHistogramInterval;
    }

    public void setmDateHistogramInterval(DateHistogramInterval mDateHistogramInterval) {
        this.mDateHistogramInterval = mDateHistogramInterval;
    }
}
