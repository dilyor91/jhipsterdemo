package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FileTopicTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileTopic.class);
        FileTopic fileTopic1 = new FileTopic();
        fileTopic1.setId(1L);
        FileTopic fileTopic2 = new FileTopic();
        fileTopic2.setId(fileTopic1.getId());
        assertThat(fileTopic1).isEqualTo(fileTopic2);
        fileTopic2.setId(2L);
        assertThat(fileTopic1).isNotEqualTo(fileTopic2);
        fileTopic1.setId(null);
        assertThat(fileTopic1).isNotEqualTo(fileTopic2);
    }
}
