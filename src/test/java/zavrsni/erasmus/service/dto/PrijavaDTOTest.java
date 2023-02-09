package zavrsni.erasmus.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import zavrsni.erasmus.web.rest.TestUtil;

class PrijavaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrijavaDTO.class);
        PrijavaDTO prijavaDTO1 = new PrijavaDTO();
        prijavaDTO1.setId(1L);
        PrijavaDTO prijavaDTO2 = new PrijavaDTO();
        assertThat(prijavaDTO1).isNotEqualTo(prijavaDTO2);
        prijavaDTO2.setId(prijavaDTO1.getId());
        assertThat(prijavaDTO1).isEqualTo(prijavaDTO2);
        prijavaDTO2.setId(2L);
        assertThat(prijavaDTO1).isNotEqualTo(prijavaDTO2);
        prijavaDTO1.setId(null);
        assertThat(prijavaDTO1).isNotEqualTo(prijavaDTO2);
    }
}
