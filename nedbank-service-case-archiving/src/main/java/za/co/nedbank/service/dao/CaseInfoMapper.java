package za.co.nedbank.service.dao;

import java.io.IOException;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

public class CaseInfoMapper implements RowMapper<CaseInfo> {
    private static final Logger LOG = LoggerFactory.getLogger(CaseInfoMapper.class);

    @Override
    public CaseInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        String properties = "";

        try (Reader reader = rs.getClob("PROPERTIES").getCharacterStream()) {
            properties = IOUtils.toString(reader);
        } catch (IOException io) {
            LOG.error("Unable to retrieve properties", io);
        }

        return new CaseInfo(
                rs.getString("CASE_ID"),
                rs.getString("PROCESS_BPDID"),
                rs.getString("PROCESS_STATE"),
                rs.getString("PROCESS_TYPE"),
                properties,
                rs.getString("CREATE_DT"),
                rs.getString("COMPLETE_DT"));
    }
}
