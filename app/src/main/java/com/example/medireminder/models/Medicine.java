package com.example.medireminder.models;

public class Medicine {
    private int id;
    private String name;
    private String dosage;
    private String frequency;
    private String time;
    private String startDate;
    private String endDate;
    private boolean isActive;
    private int userId;

    public Medicine() {
    }

    public Medicine(String name, String dosage, String frequency, String time, 
                   String startDate, String endDate, int userId) {
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.time = time;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = true;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

