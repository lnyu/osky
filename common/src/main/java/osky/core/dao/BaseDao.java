package osky.core.dao;

import java.util.List;
import java.util.Map;

public interface BaseDao<T> {

    int queryCnt();

    int queryCnt(Map<String, Object> param);

    List<Map<String, Object>> queryAll();

    List<Map<String, Object>> queryAll(Map<String, Object> param);

    T queryById(Long id);

    T queryTop(Map<String, Object> param);

    int insert(T object);

    int update(T object);

    int delete(Long id);
}
