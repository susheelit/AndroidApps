package com.ceodelhi.filesystemapp.model;

import java.io.Serializable;

public class ModelFiles implements Serializable {

    private int ID;
    private String FileNo;
    private String FileSubject;
    private String Photo;
    private String Photo1;
    private String Photo2;
    private String Photo3;
    private String Photo4;
    private String Photo5;
    private String Status;
    private String EntryDate;
    private String Comment;
    private String PROP_Subject;
    private String PROP_Page;
    private String PA_Instruction;
    private String TotalRecvAsOn;
    private String TotalPendAsOn;
    private String TotalDispAsOn;
    private String TodayReceived;
    private String TodayPending;
    private String TodayDisposed;

    public ModelFiles(int ID, String fileNo, String fileSubject, String photo, String photo1, String photo2, String photo3, String photo4, String photo5, String status, String entryDate, String comment, String PROP_Subject, String PROP_Page, String PA_Instruction, String totalRecvAsOn, String totalPendAsOn, String totalDispAsOn, String todayReceived, String todayPending, String todayDisposed) {
        this.ID = ID;
        FileNo = fileNo;
        FileSubject = fileSubject;
        Photo = photo;
        Photo1 = photo1;
        Photo2 = photo2;
        Photo3 = photo3;
        Photo4 = photo4;
        Photo5 = photo5;
        Status = status;
        EntryDate = entryDate;
        Comment = comment;
        this.PROP_Subject = PROP_Subject;
        this.PROP_Page = PROP_Page;
        this.PA_Instruction = PA_Instruction;
        TotalRecvAsOn = totalRecvAsOn;
        TotalPendAsOn = totalPendAsOn;
        TotalDispAsOn = totalDispAsOn;
        TodayReceived = todayReceived;
        TodayPending = todayPending;
        TodayDisposed = todayDisposed;
    }

    public String getPA_Instruction() {
        return PA_Instruction;
    }

    public void setPA_Instruction(String PA_Instruction) {
        this.PA_Instruction = PA_Instruction;
    }

    public String getTotalRecvAsOn() {
        return TotalRecvAsOn;
    }

    public void setTotalRecvAsOn(String totalRecvAsOn) {
        TotalRecvAsOn = totalRecvAsOn;
    }

    public String getTotalPendAsOn() {
        return TotalPendAsOn;
    }

    public void setTotalPendAsOn(String totalPendAsOn) {
        TotalPendAsOn = totalPendAsOn;
    }

    public String getTotalDispAsOn() {
        return TotalDispAsOn;
    }

    public void setTotalDispAsOn(String totalDispAsOn) {
        TotalDispAsOn = totalDispAsOn;
    }

    public String getTodayReceived() {
        return TodayReceived;
    }

    public void setTodayReceived(String todayReceived) {
        TodayReceived = todayReceived;
    }

    public String getTodayPending() {
        return TodayPending;
    }

    public void setTodayPending(String todayPending) {
        TodayPending = todayPending;
    }

    public String getTodayDisposed() {
        return TodayDisposed;
    }

    public void setTodayDisposed(String todayDisposed) {
        TodayDisposed = todayDisposed;
    }

    public ModelFiles() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFileNo() {
        return FileNo;
    }

    public void setFileNo(String fileNo) {
        FileNo = fileNo;
    }

    public String getFileSubject() {
        return FileSubject;
    }

    public void setFileSubject(String fileSubject) {
        FileSubject = fileSubject;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getPhoto1() {
        return Photo1;
    }

    public void setPhoto1(String photo1) {
        Photo1 = photo1;
    }

    public String getPhoto2() {
        return Photo2;
    }

    public void setPhoto2(String photo2) {
        Photo2 = photo2;
    }

    public String getPhoto3() {
        return Photo3;
    }

    public void setPhoto3(String photo3) {
        Photo3 = photo3;
    }

    public String getPhoto4() {
        return Photo4;
    }

    public void setPhoto4(String photo4) {
        Photo4 = photo4;
    }

    public String getPhoto5() {
        return Photo5;
    }

    public void setPhoto5(String photo5) {
        Photo5 = photo5;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(String entryDate) {
        EntryDate = entryDate;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getPROP_Subject() {
        return PROP_Subject;
    }

    public void setPROP_Subject(String PROP_Subject) {
        this.PROP_Subject = PROP_Subject;
    }

    public String getPROP_Page() {
        return PROP_Page;
    }

    public void setPROP_Page(String PROP_Page) {
        this.PROP_Page = PROP_Page;
    }
}
