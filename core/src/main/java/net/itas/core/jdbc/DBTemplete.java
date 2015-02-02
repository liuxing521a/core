package net.itas.core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.itas.util.ItasException;
import net.itas.util.Logger;
import net.itas.util.Utils.SQLUtils;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

public final class DBTemplete {

    public static int execute(String sql) {
        return execute(sql, new Object[] {});
    }

    public static int execute(String sql, Object[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBPool.getConnection();
            ps = buildStatement(conn, sql, args);
            
            return ps.executeUpdate();
        }  catch (SQLException e) {
            Logger.error("jdbc excute exception", e);
            throw new ItasException(e);
        } finally {
        	SQLUtils.close(conn, ps);
        }
    }
    
    public static boolean isExist(String sql, Object[] args) {
    	 ResultSet rs = null;
         PreparedStatement ps = null;
         Connection conn = null;
         try {
             conn = DBPool.getConnection();
             ps = buildStatement(conn, sql, args);
             rs = ps.executeQuery();

             return rs.next() ? rs.getInt(1) > 0 : false;
         } catch (SQLException e) {
             throw new ItasException(e);
         } finally {
        	 SQLUtils.close(conn, ps, rs);
         }
    }

    public static <T> List<T> queryForList(RowMapper<T> mapper, String sql, Object...args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<T>();
        try {
            conn = DBPool.getConnection();
            ps = buildStatement(conn, sql, args);
            rs = ps.executeQuery();
            while (rs.next()) {
                T entry = mapper.mapRow(rs);
                list.add(entry);
            }
            return list;
        } catch (SQLException e) {
            throw new ItasException(e);
        } finally {
        	SQLUtils.close(conn, ps, rs);
        }
    }

    public static <T> Set<T> queryForSet(RowMapper<T> mapper, String sql, Object...args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Set<T> set = new HashSet<T>();
        try {
            conn = DBPool.getConnection();
            ps = buildStatement(conn, sql, args);
            rs = ps.executeQuery();
            while (rs.next()) {
                T entry = mapper.mapRow(rs);
                set.add(entry);
            }
        } catch (SQLException e) {
            throw new ItasException(e);
        } finally {
        	SQLUtils.close(conn, ps, rs);
        }
        return set;
    }


    public  static <T> T queryForObject(RowMapper<T> mapper, String sql, Object...args) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = DBPool.getConnection();
            ps = buildStatement(conn, sql, args);
            rs = ps.executeQuery();

            return rs.next() ? mapper.mapRow(rs) : null;
        } catch (SQLException e) {
            throw new ItasException(e);
        } finally {
        	SQLUtils.close(conn, ps, rs);
        }
    }



    private static PreparedStatement buildStatement(Connection conn, String sql, Object...args) throws SQLException {
        sql = expandArrayParams(sql, args);
        PreparedStatement ps = conn.prepareStatement(sql);
        if (args.length > 0) {
            setBindVariable(ps, 1, args);
        }
        return ps;
    }


    private static int setBindVariable(PreparedStatement statement, int startIndex, Object param) {
        int index = startIndex;
        if (param instanceof Iterable) {
            for (Object o : (Iterable<?>) param) {
                index = setBindVariable(statement, index, o);
            }
            index -= 1;
        } else if (param instanceof Object[]) {
            for (Object o : (Object[]) param) {
                index = setBindVariable(statement, index, o);
            }
            index -= 1;
        } else {
            try {
                statement.setObject(index, param);
            } catch (SQLException ex) {
                throw new ItasException(ex);
            }
        }
        return index + 1;
    }

    private static String expandArrayParams(String query, Object...params) {
        if (params.length < 1) {
            return query;
        }

        Pattern p = Pattern.compile("\\?");
        Matcher m = p.matcher(query);
        StringBuffer result = new StringBuffer();
        int i = 0;

        while (m.find()) {
            try {
                m.appendReplacement(result, marks(params[i]));
            } catch (Exception ex) {
                throw new ItasException(ex); // too few query parameter
            }
            i += 1;
        }
        m.appendTail(result);
        return result.toString();
    }

    private static String marks(Object param) {
        if (param instanceof Iterable) {
            Iterable<?> iter = ((Iterable<?>) param);
            Iterable<String> markIter = Iterables.transform(iter,
                    new Function<Object, String>() {
                        @Override
                        public String apply(Object input) {
                            return "?";
                        }
                    });
            return Joiner.on(',').join(markIter);
        }
        return "?";
    }

    private DBTemplete() {
    }
}