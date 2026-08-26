package com.sarav.jobtracker.entity.dto;

public class JobStatistics {
    private int totalJobs;
    private int applied;
    private int interview;
    private int selected;
    private int rejected;
    private int offer;
    public JobStatistics(){

    }   
    public JobStatistics(int totalJobs,int applied,int interview,int selected,int rejected,int offer){
        this.totalJobs=totalJobs;
        this.applied=applied;
        this.interview=interview;
        this.selected=selected;
        this.rejected=rejected;
        this.offer=offer;
    }
    public int getTotalJobs(){
        return totalJobs;
    }
    public void setTotalJobs(int totalJobs){
        this.totalJobs=totalJobs;
    }
    public int getApplied(){
        return applied;
    }
    public void setApplied(int applied){
        this.applied=applied;
    }
    public int getInterview(){
        return interview;
    }
    public void setInterview(int interview){
        this.interview=interview;
    }
    public int getSelected(){
        return selected;
    }
    public void setSelected(int selected){
        this.selected=selected;
    }
    public int getRejected(){
        return rejected;
    }
    public void setRejected(int rejected){
        this.rejected=rejected;
    }
    public int getOffer(){
        return offer;
    }
    public void setOffer(int offer){
        this.offer=offer;
    }
}
