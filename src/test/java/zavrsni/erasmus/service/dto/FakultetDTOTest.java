package zavrsni.erasmus.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import zavrsni.erasmus.web.rest.TestUtil;

class FakultetDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FakultetDTO.class);
        FakultetDTO fakultetDTO1 = new FakultetDTO();
        fakultetDTO1.setId(1L);
        FakultetDTO fakultetDTO2 = new FakultetDTO();
        assertThat(fakultetDTO1).isNotEqualTo(fakultetDTO2);
        fakultetDTO2.setId(fakultetDTO1.getId());
        assertThat(fakultetDTO1).isEqualTo(fakultetDTO2);
        fakultetDTO2.setId(2L);
        assertThat(fakultetDTO1).isNotEqualTo(fakultetDTO2);
        fakultetDTO1.setId(null);
        assertThat(fakultetDTO1).isNotEqualTo(fakultetDTO2);
    }
}
