package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FileTopic.
 */
@Entity
@Table(name = "file_topic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FileTopic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "file_orginal_name")
    private String fileOrginalName;

    @Column(name = "file_name_uz")
    private String fileNameUz;

    @Column(name = "file_name_ru")
    private String fileNameRu;

    @Column(name = "file_name_kr")
    private String fileNameKr;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne
    @JsonIgnoreProperties(value = { "fileTopics" }, allowSetters = true)
    private MaterialTopicLevel materialTopicLevel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FileTopic id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileOrginalName() {
        return this.fileOrginalName;
    }

    public FileTopic fileOrginalName(String fileOrginalName) {
        this.setFileOrginalName(fileOrginalName);
        return this;
    }

    public void setFileOrginalName(String fileOrginalName) {
        this.fileOrginalName = fileOrginalName;
    }

    public String getFileNameUz() {
        return this.fileNameUz;
    }

    public FileTopic fileNameUz(String fileNameUz) {
        this.setFileNameUz(fileNameUz);
        return this;
    }

    public void setFileNameUz(String fileNameUz) {
        this.fileNameUz = fileNameUz;
    }

    public String getFileNameRu() {
        return this.fileNameRu;
    }

    public FileTopic fileNameRu(String fileNameRu) {
        this.setFileNameRu(fileNameRu);
        return this;
    }

    public void setFileNameRu(String fileNameRu) {
        this.fileNameRu = fileNameRu;
    }

    public String getFileNameKr() {
        return this.fileNameKr;
    }

    public FileTopic fileNameKr(String fileNameKr) {
        this.setFileNameKr(fileNameKr);
        return this;
    }

    public void setFileNameKr(String fileNameKr) {
        this.fileNameKr = fileNameKr;
    }

    public String getFileType() {
        return this.fileType;
    }

    public FileTopic fileType(String fileType) {
        this.setFileType(fileType);
        return this;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public FileTopic fileSize(Long fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public FileTopic filePath(String filePath) {
        this.setFilePath(filePath);
        return this;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public MaterialTopicLevel getMaterialTopicLevel() {
        return this.materialTopicLevel;
    }

    public void setMaterialTopicLevel(MaterialTopicLevel materialTopicLevel) {
        this.materialTopicLevel = materialTopicLevel;
    }

    public FileTopic materialTopicLevel(MaterialTopicLevel materialTopicLevel) {
        this.setMaterialTopicLevel(materialTopicLevel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileTopic)) {
            return false;
        }
        return id != null && id.equals(((FileTopic) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileTopic{" +
            "id=" + getId() +
            ", fileOrginalName='" + getFileOrginalName() + "'" +
            ", fileNameUz='" + getFileNameUz() + "'" +
            ", fileNameRu='" + getFileNameRu() + "'" +
            ", fileNameKr='" + getFileNameKr() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", fileSize=" + getFileSize() +
            ", filePath='" + getFilePath() + "'" +
            "}";
    }
}
