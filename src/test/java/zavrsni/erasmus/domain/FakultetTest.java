package zavrsni.erasmus.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import zavrsni.erasmus.web.rest.TestUtil;

class FakultetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fakultet.class);
        Fakultet fakultet1 = new Fakultet();
        fakultet1.setId(1L);
        Fakultet fakultet2 = new Fakultet();
        fakultet2.setId(fakultet1.getId());
        assertThat(fakultet1).isEqualTo(fakultet2);
        fakultet2.setId(2L);
        assertThat(fakultet1).isNotEqualTo(fakultet2);
        fakultet1.setId(null);
        assertThat(fakultet1).isNotEqualTo(fakultet2);
    }
}
