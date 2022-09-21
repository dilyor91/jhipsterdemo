package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MaterialTopicLevel.
 */
@Entity
@Table(name = "material_topic_level")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MaterialTopicLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title_uz")
    private String titleUz;

    @Column(name = "title_ru")
    private String titleRu;

    @Column(name = "title_kr")
    private String titleKr;

    @OneToMany(mappedBy = "materialTopicLevel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "materialTopicLevel" }, allowSetters = true)
    private Set<FileTopic> fileTopics = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MaterialTopicLevel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleUz() {
        return this.titleUz;
    }

    public MaterialTopicLevel titleUz(String titleUz) {
        this.setTitleUz(titleUz);
        return this;
    }

    public void setTitleUz(String titleUz) {
        this.titleUz = titleUz;
    }

    public String getTitleRu() {
        return this.titleRu;
    }

    public MaterialTopicLevel titleRu(String titleRu) {
        this.setTitleRu(titleRu);
        return this;
    }

    public void setTitleRu(String titleRu) {
        this.titleRu = titleRu;
    }

    public String getTitleKr() {
        return this.titleKr;
    }

    public MaterialTopicLevel titleKr(String titleKr) {
        this.setTitleKr(titleKr);
        return this;
    }

    public void setTitleKr(String titleKr) {
        this.titleKr = titleKr;
    }

    public Set<FileTopic> getFileTopics() {
        return this.fileTopics;
    }

    public void setFileTopics(Set<FileTopic> fileTopics) {
        if (this.fileTopics != null) {
            this.fileTopics.forEach(i -> i.setMaterialTopicLevel(null));
        }
        if (fileTopics != null) {
            fileTopics.forEach(i -> i.setMaterialTopicLevel(this));
        }
        this.fileTopics = fileTopics;
    }

    public MaterialTopicLevel fileTopics(Set<FileTopic> fileTopics) {
        this.setFileTopics(fileTopics);
        return this;
    }

    public MaterialTopicLevel addFileTopic(FileTopic fileTopic) {
        this.fileTopics.add(fileTopic);
        fileTopic.setMaterialTopicLevel(this);
        return this;
    }

    public MaterialTopicLevel removeFileTopic(FileTopic fileTopic) {
        this.fileTopics.remove(fileTopic);
        fileTopic.setMaterialTopicLevel(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaterialTopicLevel)) {
            return false;
        }
        return id != null && id.equals(((MaterialTopicLevel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaterialTopicLevel{" +
            "id=" + getId() +
            ", titleUz='" + getTitleUz() + "'" +
            ", titleRu='" + getTitleRu() + "'" +
            ", titleKr='" + getTitleKr() + "'" +
            "}";
    }
}
