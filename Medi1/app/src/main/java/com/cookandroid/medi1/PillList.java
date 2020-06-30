package com.cookandroid.medi1;

public class PillList{
    //**
    int mediId;
    String mediName;
    String startDate;
    String endDate;
    String oneTime, twoTime, threeTime, fourTime, fiveTime;
    int timesPerDay;

    private int resId;
    private boolean isSelected;

    public PillList() {
    }

    public PillList(String mediName, String startDate, String endDate,int timesPerDay) {
        this.mediName = mediName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timesPerDay = timesPerDay;
    }

    //**
    public int getMediId() {
        return mediId;
    }

    public void setMediId(int mediId) {
        this.mediId = mediId;
    }

    public String getMediName() { return mediName; }

    public void setMediName(String mediName) { this.mediName = mediName; }

    public String getStartDate() { return startDate; }

    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }

    public void setEndDate(String endDate) { this.endDate = endDate; }

    public void setTimesPerDay(int timesPerDay) { this.timesPerDay = timesPerDay; }

    public int getTimesPerDay() { return timesPerDay; }

    public int getResId() { return resId; }

    public void setResId(int resId) { this.resId = resId; }

    public boolean getSelected() { return isSelected; }

    public void setSelected(boolean selected) { isSelected = selected; }

    public String getOneTime() { return oneTime; }

    public String getTwoTime() { return twoTime; }

    public String getThreeTime() { return threeTime; }

    public String getFourTime() { return fourTime; }

    public String getFiveTime() { return fiveTime; }

    public void setOneTime(String oneTime) { this.oneTime = oneTime; }

    public void setTwoTime(String twoTime) { this.twoTime = twoTime; }

    public void setThreeTime(String threeTime) { this.threeTime = threeTime; }

    public void setFourTime(String fourTime) { this.fourTime = fourTime; }

    public void setFiveTime(String fiveTime) { this.fiveTime = fiveTime; }

}
