package com.ceodelhi.filesystemapp.utility;

/**
 * Created by win7 on 1/12/2017.
 */

public class MyConstants {

    public static final String NAMESPACE = "http://tempuri.org/";
    public static final String BASE_URL1 = "http://ceodelhi.gov.in/DelhiElection/Service.asmx";
    public static final String BASE_URL = "http://www.ceodelhinet.nic.in/DelhiElection/Service.asmx";

    public static final String urlImage = "http://ceodelhi.gov.in";

    public static String OTPSENDER = "CEODEL";
    //        public static String CEOMBNO = "9650215678";
//    public static String CEOMBNO = "9818041897";
//    public static String CEOMBNO = "9990022878";

    public static final String CHANDNICHOWK = "CHANDNI CHOWK";
    public static final String NORTHEAST = "NORTH EAST";
    public static final String EAST = "EAST";
    public static final String NEWDELHI = "NEW DELHI";
    public static final String NORTHWEST = "NORTH WEST";
    public static final String WEST = "WEST";
    public static final String NORTH = "NORTH";
    public static final String SOUTH = "SOUTH";

    public static final String CENTRAL = "CENTRAL";
    public static final String SOUTHWEST = "SOUTH WEST";
    public static final String SHAHDARA = "SHAHDARA";
    public static final String SOUTHEAST = "SOUTH EAST";

    public static final int FILE_DISPOSE = 101;
    public static final int LETTER_DISPOSE = 105;
    public static final int LETTER_MARKED = 106;

    public static int fileCounter = 0;
    public static int letterCounter = 0;

    public static final String SOAP_ACTION_GetFileDisposal = "http://tempuri.org/GetFileDisposal";
    public static final String METHOD_NAME_GetFileDisposal = "GetFileDisposal";

    public static final String SOAP_ACTION_UpdateFileDisposalStatus = "http://tempuri.org/UpdateFileDisposalStatus";
    public static final String METHOD_NAME_UpdateFileDisposalStatus = "UpdateFileDisposalStatus";

    public static final String SOAP_ACTION_SearchGetFileDisposal = "http://tempuri.org/SearchGetFileDisposal";
    public static final String METHOD_NAME_SearchGetFileDisposal = "SearchGetFileDisposal";

    // pending file letter
    public static final String SOAP_ACTION_PendingGetFileDisposal = "http://tempuri.org/GetLetterAndFilePending";
    public static final String METHOD_NAME_PendingGetFileDisposal = "GetLetterAndFilePending";



    public static final String SOAP_ACTION_GetLetterDisposal = "http://tempuri.org/GetLetterDisposal";
    // public static final String SOAP_ACTION_GetLetterDisposal = "http://localhost:9030/DelhiElection/Service.asmx";
    public static final String METHOD_NAME_GetLetterDisposal = "GetLetterDisposal";

    public static final String SOAP_ACTION_GetMarkedOfficerLetterDisposal = "http://tempuri.org/GetMarkedOfficerLetterDisposal";
    public static final String METHOD_NAME_GetMarkedOfficerLetterDisposal = "GetMarkedOfficerLetterDisposal";

    public static final String SOAP_ACTION_UpdateLetterDisposalStatus = "http://tempuri.org/UpdateLetterDisposalStatus";
    public static final String METHOD_NAME_UpdateLetterDisposalStatus = "UpdateLetterDisposalStatus";

    public static final String SOAP_ACTION_SearchGetLetterDisposal = "http://tempuri.org/SearchGetLetterDisposal";
    public static final String METHOD_NAME_SearchGetLetterDisposal = "SearchGetLetterDisposal";

    public static final String METHOD_SMS = "fncSMS_Multiple";
    public static final String SOAP_ACTION_SMS = "http://tempuri.org/fncSMS_Multiple";

    public static final String METHOD_SEND_TOKEN_DEVICE_ID = "InsertDeviceEntryForNotification";
    public static final String SOAP_ACTION_SEND_TOKEN_DEVICE_ID = "http://tempuri.org/InsertDeviceEntryForNotification";

    public static final String METHOD_PENDING_LETTER_FILE = "GetLetterAndFilePending";
    public static final String SOAP_ACTION_PENDING_LETTER_FILE = "http://tempuri.org/GetLetterAndFilePending";

}
