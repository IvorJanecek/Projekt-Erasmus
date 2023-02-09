package zavrsni.erasmus.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import zavrsni.erasmus.web.rest.TestUtil;

class NatjecajTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Natjecaj.class);
        Natjecaj natjecaj1 = new Natjecaj();
        natjecaj1.setId(1L);
        Natjecaj natjecaj2 = new Natjecaj();
        natjecaj2.setId(natjecaj1.getId());
        assertThat(natjecaj1).isEqualTo(natjecaj2);
        natjecaj2.setId(2L);
        assertThat(natjecaj1).isNotEqualTo(natjecaj2);
        natjecaj1.setId(null);
        assertThat(natjecaj1).isNotEqualTo(natjecaj2);
    }
}
