package org.mskcc.cbio.bigmech.util;

import org.openbel.bel.model.BELDocumentHeader;

public class BELHeaderWrapper {
    private String name;
    private String description;
    private String version;
    private String copyright;
    private String authors;
    private String licenses;
    private String disclaimer;
    private String contactInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getLicenses() {
        return licenses;
    }

    public void setLicenses(String licenses) {
        this.licenses = licenses;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public BELHeaderWrapper(String name) {
        this.name = name;
    }

    public BELHeaderWrapper(String name, String description, String version) {
        this.name = name;
        this.description = description;
        this.version = version;
    }

    public BELHeaderWrapper(String name, String description, String version, String copyright, String authors, String licenses, String disclaimer, String contactInfo) {
        this.name = name;
        this.description = description;
        this.version = version;
        this.copyright = copyright;
        this.authors = authors;
        this.licenses = licenses;
        this.disclaimer = disclaimer;
        this.contactInfo = contactInfo;
    }

    public BELDocumentHeader getAsBELDocumentHeader() {
        return new BELDocumentHeader(
                getName(),
                getDescription(),
                getVersion(),
                getAuthors(),
                getCopyright(),
                getContactInfo(),
                getDisclaimer(),
                getLicenses()
        );
    }
}
