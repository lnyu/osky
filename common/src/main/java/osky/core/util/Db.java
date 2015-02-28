package osky.core.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import osky.core.context.SpringContextHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;

public class Db {

    private static final Logger logger = LoggerFactory.getLogger(Db.class);

    private static DataSource dataSource = null;

    /**
     * 获取数据库时间
     *
     * @return
     */
    public static Date now() {
        Date date = null;
        Connection conn = null;
        try {
            if (dataSource == null) {
                dataSource = SpringContextHolder.getBean(DataSource.class);
            }

            conn = dataSource.getConnection();
            String sql = "select now()";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            rs.next();
            date = rs.getTimestamp(1);

        } catch (Exception e) {
            logger.warn("", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    logger.warn("", e);
                }
            }
            return date == null ? new Date() : date;
        }
    }
}
