package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OurHistory.
 */
@Entity
@Table(name = "our_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OurHistory implements Serializable {

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

    @Column(name = "posted_date")
    private LocalDate postedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OurHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentUz() {
        return this.contentUz;
    }

    public OurHistory contentUz(String contentUz) {
        this.setContentUz(contentUz);
        return this;
    }

    public void setContentUz(String contentUz) {
        this.contentUz = contentUz;
    }

    public String getContentRu() {
        return this.contentRu;
    }

    public OurHistory contentRu(String contentRu) {
        this.setContentRu(contentRu);
        return this;
    }

    public void setContentRu(String contentRu) {
        this.contentRu = contentRu;
    }

    public String getContentKr() {
        return this.contentKr;
    }

    public OurHistory contentKr(String contentKr) {
        this.setContentKr(contentKr);
        return this;
    }

    public void setContentKr(String contentKr) {
        this.contentKr = contentKr;
    }

    public LocalDate getPostedDate() {
        return this.postedDate;
    }

    public OurHistory postedDate(LocalDate postedDate) {
        this.setPostedDate(postedDate);
        return this;
    }

    public void setPostedDate(LocalDate postedDate) {
        this.postedDate = postedDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OurHistory)) {
            return false;
        }
        return id != null && id.equals(((OurHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OurHistory{" +
            "id=" + getId() +
            ", contentUz='" + getContentUz() + "'" +
            ", contentRu='" + getContentRu() + "'" +
            ", contentKr='" + getContentKr() + "'" +
            ", postedDate='" + getPostedDate() + "'" +
            "}";
    }
}
