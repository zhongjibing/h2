package com.icezhg.h2.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class OriginArchive implements Serializable{
    private static final long serialVersionUID = 3523444389208317632L;

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;
    private String archiveName;
    private String expandedName;
    @Lob
    private String context;

    public OriginArchive() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArchiveName() {
        return archiveName;
    }

    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName;
    }

    public String getExpandedName() {
        return expandedName;
    }

    public void setExpandedName(String expandedName) {
        this.expandedName = expandedName;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
