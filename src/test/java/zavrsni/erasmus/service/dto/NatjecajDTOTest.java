package zavrsni.erasmus.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import zavrsni.erasmus.web.rest.TestUtil;

class NatjecajDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NatjecajDTO.class);
        NatjecajDTO natjecajDTO1 = new NatjecajDTO();
        natjecajDTO1.setId(1L);
        NatjecajDTO natjecajDTO2 = new NatjecajDTO();
        assertThat(natjecajDTO1).isNotEqualTo(natjecajDTO2);
        natjecajDTO2.setId(natjecajDTO1.getId());
        assertThat(natjecajDTO1).isEqualTo(natjecajDTO2);
        natjecajDTO2.setId(2L);
        assertThat(natjecajDTO1).isNotEqualTo(natjecajDTO2);
        natjecajDTO1.setId(null);
        assertThat(natjecajDTO1).isNotEqualTo(natjecajDTO2);
    }
}
