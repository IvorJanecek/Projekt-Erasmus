package zavrsni.erasmus.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import zavrsni.erasmus.web.rest.TestUtil;

class MobilnostTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mobilnost.class);
        Mobilnost mobilnost1 = new Mobilnost();
        mobilnost1.setId(1L);
        Mobilnost mobilnost2 = new Mobilnost();
        mobilnost2.setId(mobilnost1.getId());
        assertThat(mobilnost1).isEqualTo(mobilnost2);
        mobilnost2.setId(2L);
        assertThat(mobilnost1).isNotEqualTo(mobilnost2);
        mobilnost1.setId(null);
        assertThat(mobilnost1).isNotEqualTo(mobilnost2);
    }
}
