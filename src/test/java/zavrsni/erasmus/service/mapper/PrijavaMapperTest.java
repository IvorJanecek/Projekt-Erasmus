package zavrsni.erasmus.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrijavaMapperTest {

    private PrijavaMapper prijavaMapper;

    @BeforeEach
    public void setUp() {
        prijavaMapper = new PrijavaMapperImpl();
    }
}
