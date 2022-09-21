package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.FileEntity;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A File.
 */
@Entity
@Table(name = "file")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "orginal_name")
    private String orginalName;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_format")
    private String fileFormat;

    @Column(name = "file_path")
    private String filePath;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_entity")
    private FileEntity fileEntity;

    @ManyToOne
    @JsonIgnoreProperties(value = { "files" }, allowSetters = true)
    private Institution institution;

    @ManyToOne
    @JsonIgnoreProperties(value = { "files" }, allowSetters = true)
    private StudyAtKorea studyAtKorea;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public File id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrginalName() {
        return this.orginalName;
    }

    public File orginalName(String orginalName) {
        this.setOrginalName(orginalName);
        return this;
    }

    public void setOrginalName(String orginalName) {
        this.orginalName = orginalName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public File fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public File fileSize(Long fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileFormat() {
        return this.fileFormat;
    }

    public File fileFormat(String fileFormat) {
        this.setFileFormat(fileFormat);
        return this;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public File filePath(String filePath) {
        this.setFilePath(filePath);
        return this;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public FileEntity getFileEntity() {
        return this.fileEntity;
    }

    public File fileEntity(FileEntity fileEntity) {
        this.setFileEntity(fileEntity);
        return this;
    }

    public void setFileEntity(FileEntity fileEntity) {
        this.fileEntity = fileEntity;
    }

    public Institution getInstitution() {
        return this.institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public File institution(Institution institution) {
        this.setInstitution(institution);
        return this;
    }

    public StudyAtKorea getStudyAtKorea() {
        return this.studyAtKorea;
    }

    public void setStudyAtKorea(StudyAtKorea studyAtKorea) {
        this.studyAtKorea = studyAtKorea;
    }

    public File studyAtKorea(StudyAtKorea studyAtKorea) {
        this.setStudyAtKorea(studyAtKorea);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof File)) {
            return false;
        }
        return id != null && id.equals(((File) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "File{" +
            "id=" + getId() +
            ", orginalName='" + getOrginalName() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", fileSize=" + getFileSize() +
            ", fileFormat='" + getFileFormat() + "'" +
            ", filePath='" + getFilePath() + "'" +
            ", fileEntity='" + getFileEntity() + "'" +
            "}";
    }
}
