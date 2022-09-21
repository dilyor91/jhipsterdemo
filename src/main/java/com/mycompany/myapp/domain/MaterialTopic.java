package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MaterialTopic.
 */
@Entity
@Table(name = "material_topic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MaterialTopic implements Serializable {

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

    @JsonIgnoreProperties(value = { "fileTopics" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private MaterialTopicLevel materialTopicLevel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MaterialTopic id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleUz() {
        return this.titleUz;
    }

    public MaterialTopic titleUz(String titleUz) {
        this.setTitleUz(titleUz);
        return this;
    }

    public void setTitleUz(String titleUz) {
        this.titleUz = titleUz;
    }

    public String getTitleRu() {
        return this.titleRu;
    }

    public MaterialTopic titleRu(String titleRu) {
        this.setTitleRu(titleRu);
        return this;
    }

    public void setTitleRu(String titleRu) {
        this.titleRu = titleRu;
    }

    public String getTitleKr() {
        return this.titleKr;
    }

    public MaterialTopic titleKr(String titleKr) {
        this.setTitleKr(titleKr);
        return this;
    }

    public void setTitleKr(String titleKr) {
        this.titleKr = titleKr;
    }

    public MaterialTopicLevel getMaterialTopicLevel() {
        return this.materialTopicLevel;
    }

    public void setMaterialTopicLevel(MaterialTopicLevel materialTopicLevel) {
        this.materialTopicLevel = materialTopicLevel;
    }

    public MaterialTopic materialTopicLevel(MaterialTopicLevel materialTopicLevel) {
        this.setMaterialTopicLevel(materialTopicLevel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaterialTopic)) {
            return false;
        }
        return id != null && id.equals(((MaterialTopic) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MaterialTopic{" +
            "id=" + getId() +
            ", titleUz='" + getTitleUz() + "'" +
            ", titleRu='" + getTitleRu() + "'" +
            ", titleKr='" + getTitleKr() + "'" +
            "}";
    }
}
