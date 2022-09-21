package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Image.
 */
@Entity
@Table(name = "image")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "orginal_name")
    private String orginalName;

    @Column(name = "name")
    private String name;

    @Column(name = "image_data")
    private String imageData;

    @Column(name = "mainly_photo")
    private Boolean mainlyPhoto;

    @ManyToOne
    @JsonIgnoreProperties(value = { "images" }, allowSetters = true)
    private Album image;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Image id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrginalName() {
        return this.orginalName;
    }

    public Image orginalName(String orginalName) {
        this.setOrginalName(orginalName);
        return this;
    }

    public void setOrginalName(String orginalName) {
        this.orginalName = orginalName;
    }

    public String getName() {
        return this.name;
    }

    public Image name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageData() {
        return this.imageData;
    }

    public Image imageData(String imageData) {
        this.setImageData(imageData);
        return this;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public Boolean getMainlyPhoto() {
        return this.mainlyPhoto;
    }

    public Image mainlyPhoto(Boolean mainlyPhoto) {
        this.setMainlyPhoto(mainlyPhoto);
        return this;
    }

    public void setMainlyPhoto(Boolean mainlyPhoto) {
        this.mainlyPhoto = mainlyPhoto;
    }

    public Album getImage() {
        return this.image;
    }

    public void setImage(Album album) {
        this.image = album;
    }

    public Image image(Album album) {
        this.setImage(album);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Image)) {
            return false;
        }
        return id != null && id.equals(((Image) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Image{" +
            "id=" + getId() +
            ", orginalName='" + getOrginalName() + "'" +
            ", name='" + getName() + "'" +
            ", imageData='" + getImageData() + "'" +
            ", mainlyPhoto='" + getMainlyPhoto() + "'" +
            "}";
    }
}
