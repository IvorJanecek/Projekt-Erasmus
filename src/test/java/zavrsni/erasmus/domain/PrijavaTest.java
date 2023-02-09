package zavrsni.erasmus.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import zavrsni.erasmus.web.rest.TestUtil;

class PrijavaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prijava.class);
        Prijava prijava1 = new Prijava();
        prijava1.setId(1L);
        Prijava prijava2 = new Prijava();
        prijava2.setId(prijava1.getId());
        assertThat(prijava1).isEqualTo(prijava2);
        prijava2.setId(2L);
        assertThat(prijava1).isNotEqualTo(prijava2);
        prijava1.setId(null);
        assertThat(prijava1).isNotEqualTo(prijava2);
    }
}
