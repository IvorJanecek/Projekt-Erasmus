package zavrsni.erasmus.security;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long getUserIdByCurrentLogin() {
        String sql = "select id from jhi_user where login ='" + SecurityUtils.getCurrentUserLogin().get() + "'";
        return this.jdbcTemplate.queryForObject(sql, long.class);
    }
}
