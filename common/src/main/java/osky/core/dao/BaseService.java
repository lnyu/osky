package osky.core.dao;

import osky.vo.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseService<T, E extends BaseDao<T>> {

    protected abstract E getDao();

    public int queryCnt() {
        return getDao().queryCnt();
    }

    public List<Map<String, Object>> queryAll() {
        return getDao().queryAll();
    }

    public int queryCnt(Map<String, Object> param) {
        return getDao().queryCnt(param);
    }

    public List<Map<String, Object>> queryAll(Map<String, Object> param) {
        return getDao().queryAll(param);
    }

    public Page<Map<String, Object>> page(int pageNumber, int pageSize, Map<String, Object>... args) {
        Map<String, Object> param = args == null || args.length < 1 ? new HashMap<String, Object>() : args[0];
        int totalRow = this.queryCnt(param);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (totalRow > 0) {
            param.put("offset", pageNumber * pageSize - pageSize);
            param.put("pageSize", pageSize);
            list = this.queryAll(param);
        }
        return new Page<Map<String, Object>>(list, pageNumber, pageSize, totalRow);
    }

    public T queryTop(Long id) {
        return getDao().queryById(id);
    }

    public T queryTop(Map<String, Object> param) {
        return getDao().queryTop(param);
    }

    public int add(T object) {
        return getDao().insert(object);
    }

    public int modify(T object) {
        return getDao().update(object);
    }

    public int remove(Long id) {
        return getDao().delete(id);
    }
}
