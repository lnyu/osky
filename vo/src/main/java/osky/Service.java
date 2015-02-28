package osky;

import osky.vo.Page;

import java.util.List;
import java.util.Map;

public interface Service<T> {

    int queryCnt();

    int queryCnt(Map<String, Object> param);

    List<Map<String, Object>> queryAll();

    List<Map<String, Object>> queryAll(Map<String, Object> param);

    Page<Map<String, Object>> page(int pageNumber, int pageSize, Map<String, Object>... args);

    T queryTop(Long id);

    T queryTop(Map<String, Object> param);

    int add(T object);

    int modify(T object);

    int remove(Long id);
}
