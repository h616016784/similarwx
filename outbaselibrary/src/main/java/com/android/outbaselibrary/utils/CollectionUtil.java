package com.android.outbaselibrary.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by hanhuailong on 2017/3/10.
 * @desc 集合工具类，支持集合的交集，并集，差集，判重，查重，去重，拼接，分组等
 */

public class CollectionUtil {

    //******************判空******************
    /**
     * @param <T>
     * @param datas
     * @return true表示为空
     * @author puyf
     * @Description:判断所给集合是否为空
     */
    public static <T> boolean isEmpty(Collection<T> datas) {
        return datas == null || datas.isEmpty();
    }

    //******************2.分组******************
    /**
     * @param datas
     * @param c     是否为同一组的判断标准
     * @return
     * @author puyf
     * @Description:按条件分组
     */
    public static <T> List<List<T>> divider(Collection<T> datas, Comparator<? super T> c) {
        List<List<T>> result = new ArrayList<List<T>>();
        for (T t : datas) {
            boolean isSameGroup = false;
            for (int j = 0; j < result.size(); j++) {
                if (c.compare(t, result.get(j).get(0)) == 0) {
                    isSameGroup = true;
                    result.get(j).add(t);
                    break;
                }
            }
            if (!isSameGroup) {
                // 创建
                List<T> innerList = new ArrayList<T>();
                result.add(innerList);
                innerList.add(t);
            }
        }
        return result;
    }

    /**
     * @param datas
     * @param unitCount 代表每组的个数
     * @return 返回所有的组
     * @author puyf
     * @Description:按个数分组
     */
    public static <T> List<List<T>> divider(Collection<T> datas, int unitCount) {
        List<List<T>> returnDatas = new ArrayList<List<T>>();
        List<T> unit = null;
        int counter = 0;
        for (T t : datas) {
            if (counter % unitCount == 0) {
                unit = new ArrayList<T>();
                returnDatas.add(unit);
            }
            unit.add(t);
            counter++;
        }
        return returnDatas;
    }


    //******************3.拼接操作******************

    /**
     * @param datas 泛型为K的集合（K是一个Collection的子集就行了，这样datas中的元素K可以是List，可以是Set）
     * @return 以List<T>的形式拼接所有集合
     * @author puyf
     * @Description:拼接所有需要拼接的集合,输入->输出：Collection<K extends Collection<T>> ->> List<T>
     */
    public static <T, K extends Collection<T>> List<T> contact(Collection<K> datas) {
        List<T> result = new ArrayList<>();
        for (K k : datas) {
            result.addAll(k);
        }
        return result;
    }

    /**
     * @param datas 可变参数datas，datas的泛型为K（K是一个Collection的子集就行了，这样datas中的元素K可以是List，可以是Set）
     * @return 以List<T>的形式拼接所有集合
     * @author puyf
     * @Description:拼接所有需要拼接的集合,输入->输出：n个K（K满足K extends Collection<T>） ->> List<T>
     */
    public static <T, K extends Collection<T>> List<T> contact(K... datas) {
        return contact(Arrays.asList(datas));
    }


    //******************4.求交集******************
    /**
     * @param datas
     * @return
     * @author puyf
     * @Description:集合类求交集
     */
    public static <T, K extends Collection<? extends T>> List<T> intersection(Collection<K> datas) {
        List<T> result = new ArrayList<>();
        Iterator<K> it = datas.iterator();
        if (it.hasNext()) {
            K copy = it.next();
            if (copy != null) {
                result = new ArrayList<>(copy);
                for (K k : datas) {
                    result.retainAll(k);
                }
            }
        }
        return result;
    }

    /**
     * @param datas
     * @return
     * @author puyf
     * @Description:集合类求交集
     */
    public static <T, K extends Collection<? extends T>> List<T> intersection(K... datas) {
        return intersection(Arrays.asList(datas));
    }


    //******************5.求差集******************

    /**
     * @param a
     * @param b
     * @return 返回A-B的结果
     * @author puyf
     * @Description:求集合a-b
     */
    public static <T, K extends Collection<? extends T>> List<T> sub(K a, K b) {
        List<T> result = new ArrayList<>();
        if (a != null) {
            result = new ArrayList<>(a);
            result.removeAll(b);
        }
        return result;
    }


    //******************6.重复相关******************

    /**
     * @param datas 需要判断的目标集合
     * @return
     * @author puyf
     * @Description:判断Collection中的元素是否有重复元素
     */
    public static boolean isRepeatInCollection(Collection<?> datas) {
        if (datas == null) {// 为空则认为不含重复元素
            return false;
        }
        if (datas instanceof Set) {//如果是set则不含有重复元素
            return false;
        }
        Set<?> noRepeatSet = new HashSet<>(datas);
        return !(datas.size() == noRepeatSet.size());
    }

    /**
     * @param datas
     * @return
     * @author puyf
     * @Description:Collection中去重并将集合转为List
     */
    public static <T> List<T> distinct(Collection<T> datas) {
        if (datas == null) {
            return new ArrayList<>();
        }
        Set<T> set = new HashSet<>(datas);//使用HashSet构造方法去重
        return new ArrayList<>(set);
    }

    /**
     * @param datas
     * @return
     * @author puyf
     * @Description:找出list中的重复数据
     */
    public static <T> List<T> findRepeat(Collection<T> datas) {
        if (datas instanceof Set) {
            return new ArrayList<>();
        }
        HashSet<T> set = new HashSet<T>();
        List<T> repeatEles = new ArrayList<T>();
        for (T t : datas) {
            if (set.contains(t)) {
                repeatEles.add(t);
            } else {
                set.add(t);
            }
        }
        return repeatEles;
    }

}
