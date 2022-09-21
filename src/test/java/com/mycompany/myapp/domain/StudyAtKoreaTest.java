package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StudyAtKoreaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudyAtKorea.class);
        StudyAtKorea studyAtKorea1 = new StudyAtKorea();
        studyAtKorea1.setId(1L);
        StudyAtKorea studyAtKorea2 = new StudyAtKorea();
        studyAtKorea2.setId(studyAtKorea1.getId());
        assertThat(studyAtKorea1).isEqualTo(studyAtKorea2);
        studyAtKorea2.setId(2L);
        assertThat(studyAtKorea1).isNotEqualTo(studyAtKorea2);
        studyAtKorea1.setId(null);
        assertThat(studyAtKorea1).isNotEqualTo(studyAtKorea2);
    }
}
