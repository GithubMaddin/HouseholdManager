package de.was_wichtiges.householdmanager.attachement;

/**
 * Created by M.Friedrich on 15.02.2017.
 */
public class Attachement {
    public enum Filetype {
        JPG,
        PDF;
    }
    private long attachementID;
    private long fileID;
    private String fileName;
    private Filetype filetype;

    /**
     * Constructor
     * @param attachementID
     * @param fileID
     * @param fileName
     * @param filetype
     */
    public Attachement(long attachementID, long fileID, String fileName, Filetype filetype) {
        this.attachementID = attachementID;
        this.fileID = fileID;
        this.fileName = fileName;
        this.filetype = filetype;
    }

    // ==========================================================================
    // === GET and SET methods===================================================
    // ==========================================================================

    /**
     * Returns attachementID
     * @return attachementID
     */
    public long getAttachementID() {
        return attachementID;
    }

    /**
     * Sets attachementID
     * @param attachementID
     */
    public void setAttachementID(long attachementID) {
        this.attachementID = attachementID;
    }

    /**
     * Returns fileID
     * @return fileID
     */
    public long getFileID() {
        return fileID;
    }

    /**
     * Sets fileID
     * @param fileID
     */
    public void setFileID(long fileID) {
        this.fileID = fileID;
    }

    /**
     * Returns fileName
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets fileName
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Returns fileType
     * @return fileType
     */
    public Filetype getFiletype() {
        return filetype;
    }

    /**
     * Sets filetype
     * @param filetype: based on enum list from this class
     */
    public void setFiletype(Filetype filetype) {
        this.filetype = filetype;
    }
}
