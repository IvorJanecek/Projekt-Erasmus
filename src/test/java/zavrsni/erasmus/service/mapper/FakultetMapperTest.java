package zavrsni.erasmus.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FakultetMapperTest {

    private FakultetMapper fakultetMapper;

    @BeforeEach
    public void setUp() {
        fakultetMapper = new FakultetMapperImpl();
    }
}
