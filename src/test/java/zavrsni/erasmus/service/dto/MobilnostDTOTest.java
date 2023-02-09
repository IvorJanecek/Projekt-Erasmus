package zavrsni.erasmus.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import zavrsni.erasmus.web.rest.TestUtil;

class MobilnostDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MobilnostDTO.class);
        MobilnostDTO mobilnostDTO1 = new MobilnostDTO();
        mobilnostDTO1.setId(1L);
        MobilnostDTO mobilnostDTO2 = new MobilnostDTO();
        assertThat(mobilnostDTO1).isNotEqualTo(mobilnostDTO2);
        mobilnostDTO2.setId(mobilnostDTO1.getId());
        assertThat(mobilnostDTO1).isEqualTo(mobilnostDTO2);
        mobilnostDTO2.setId(2L);
        assertThat(mobilnostDTO1).isNotEqualTo(mobilnostDTO2);
        mobilnostDTO1.setId(null);
        assertThat(mobilnostDTO1).isNotEqualTo(mobilnostDTO2);
    }
}
