package com.ceodelhi.filesystemapp.model;

import java.io.Serializable;

public class ModelLetters implements Serializable {

    private int ID;
    private String LetterNo;
    private String LetterSubject;
    private String Photo;
    private String Photo1;
    private String Photo2;
    private String Photo3;
    private String Photo4;
    private String Photo5;
    private String Status;
    private String EntryDate;
    private String Comment;
    private String Source_Subject;
    private String PA_Instruction;
    private String TotalRecvAsOn;
    private String TotalPendAsOn;
    private String TotalDispAsOn;
    private String TodayReceived;
    private String TodayPending;
    private String TodayDisposed;
    private String MarkedTo;
    private String ReadStatus;

    public ModelLetters(int ID, String letterNo, String letterSubject, String photo, String photo1, String photo2, String photo3, String photo4, String photo5, String status, String entryDate, String comment, String source_Subject, String PA_Instruction, String totalRecvAsOn, String totalPendAsOn, String totalDispAsOn, String todayReceived, String todayPending, String todayDisposed, String markedTo, String readStatus) {
        this.ID = ID;
        LetterNo = letterNo;
        LetterSubject = letterSubject;
        Photo = photo;
        Photo1 = photo1;
        Photo2 = photo2;
        Photo3 = photo3;
        Photo4 = photo4;
        Photo5 = photo5;
        Status = status;
        EntryDate = entryDate;
        Comment = comment;
        Source_Subject = source_Subject;
        this.PA_Instruction = PA_Instruction;
        TotalRecvAsOn = totalRecvAsOn;
        TotalPendAsOn = totalPendAsOn;
        TotalDispAsOn = totalDispAsOn;
        TodayReceived = todayReceived;
        TodayPending = todayPending;
        TodayDisposed = todayDisposed;
        MarkedTo = markedTo;
        ReadStatus = readStatus;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLetterNo() {
        return LetterNo;
    }

    public void setLetterNo(String letterNo) {
        LetterNo = letterNo;
    }

    public String getLetterSubject() {
        return LetterSubject;
    }

    public void setLetterSubject(String letterSubject) {
        LetterSubject = letterSubject;
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

    public String getSource_Subject() {
        return Source_Subject;
    }

    public void setSource_Subject(String source_Subject) {
        Source_Subject = source_Subject;
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

    public String getMarkedTo() {
        return MarkedTo;
    }

    public void setMarkedTo(String markedTo) {
        MarkedTo = markedTo;
    }

    public String getReadStatus() {
        return ReadStatus;
    }

    public void setReadStatus(String readStatus) {
        ReadStatus = readStatus;
    }
}
