package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnswerAndQuestion.
 */
@Entity
@Table(name = "answer_and_question")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnswerAndQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "question_uz")
    private String questionUz;

    @Column(name = "question_ru")
    private String questionRu;

    @Column(name = "question_kr")
    private String questionKr;

    @Column(name = "answer_uz")
    private String answerUz;

    @Column(name = "answer_ru")
    private String answerRu;

    @Column(name = "answer_kr")
    private String answerKr;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AnswerAndQuestion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionUz() {
        return this.questionUz;
    }

    public AnswerAndQuestion questionUz(String questionUz) {
        this.setQuestionUz(questionUz);
        return this;
    }

    public void setQuestionUz(String questionUz) {
        this.questionUz = questionUz;
    }

    public String getQuestionRu() {
        return this.questionRu;
    }

    public AnswerAndQuestion questionRu(String questionRu) {
        this.setQuestionRu(questionRu);
        return this;
    }

    public void setQuestionRu(String questionRu) {
        this.questionRu = questionRu;
    }

    public String getQuestionKr() {
        return this.questionKr;
    }

    public AnswerAndQuestion questionKr(String questionKr) {
        this.setQuestionKr(questionKr);
        return this;
    }

    public void setQuestionKr(String questionKr) {
        this.questionKr = questionKr;
    }

    public String getAnswerUz() {
        return this.answerUz;
    }

    public AnswerAndQuestion answerUz(String answerUz) {
        this.setAnswerUz(answerUz);
        return this;
    }

    public void setAnswerUz(String answerUz) {
        this.answerUz = answerUz;
    }

    public String getAnswerRu() {
        return this.answerRu;
    }

    public AnswerAndQuestion answerRu(String answerRu) {
        this.setAnswerRu(answerRu);
        return this;
    }

    public void setAnswerRu(String answerRu) {
        this.answerRu = answerRu;
    }

    public String getAnswerKr() {
        return this.answerKr;
    }

    public AnswerAndQuestion answerKr(String answerKr) {
        this.setAnswerKr(answerKr);
        return this;
    }

    public void setAnswerKr(String answerKr) {
        this.answerKr = answerKr;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnswerAndQuestion)) {
            return false;
        }
        return id != null && id.equals(((AnswerAndQuestion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnswerAndQuestion{" +
            "id=" + getId() +
            ", questionUz='" + getQuestionUz() + "'" +
            ", questionRu='" + getQuestionRu() + "'" +
            ", questionKr='" + getQuestionKr() + "'" +
            ", answerUz='" + getAnswerUz() + "'" +
            ", answerRu='" + getAnswerRu() + "'" +
            ", answerKr='" + getAnswerKr() + "'" +
            "}";
    }
}
