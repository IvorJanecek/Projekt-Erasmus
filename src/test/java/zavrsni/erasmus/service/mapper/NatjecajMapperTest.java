package zavrsni.erasmus.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

class NatjecajMapperTest {

    private NatjecajMapper natjecajMapper;

    @BeforeEach
    public void setUp() {
        natjecajMapper = new NatjecajMapperImplem();
    }
}
