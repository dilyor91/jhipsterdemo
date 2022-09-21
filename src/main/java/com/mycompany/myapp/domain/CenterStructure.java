package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CenterStructure.
 */
@Entity
@Table(name = "center_structure")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CenterStructure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "content_uz")
    private String contentUz;

    @Column(name = "content_ru")
    private String contentRu;

    @Column(name = "content_kr")
    private String contentKr;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CenterStructure id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentUz() {
        return this.contentUz;
    }

    public CenterStructure contentUz(String contentUz) {
        this.setContentUz(contentUz);
        return this;
    }

    public void setContentUz(String contentUz) {
        this.contentUz = contentUz;
    }

    public String getContentRu() {
        return this.contentRu;
    }

    public CenterStructure contentRu(String contentRu) {
        this.setContentRu(contentRu);
        return this;
    }

    public void setContentRu(String contentRu) {
        this.contentRu = contentRu;
    }

    public String getContentKr() {
        return this.contentKr;
    }

    public CenterStructure contentKr(String contentKr) {
        this.setContentKr(contentKr);
        return this;
    }

    public void setContentKr(String contentKr) {
        this.contentKr = contentKr;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CenterStructure)) {
            return false;
        }
        return id != null && id.equals(((CenterStructure) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CenterStructure{" +
            "id=" + getId() +
            ", contentUz='" + getContentUz() + "'" +
            ", contentRu='" + getContentRu() + "'" +
            ", contentKr='" + getContentKr() + "'" +
            "}";
    }
}
