package com.vorspack.comparator;

import com.vorspack.domain.Product;
import com.vorspack.exception.RegexNotMatchException;
import com.vorspack.util.RegexTool;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class ProductComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        Integer rank_1=getRankNum(o1.getRankInfo());
        Integer rank_2=getRankNum(o2.getRankInfo());
        return rank_1.compareTo(rank_2);
    }

    private Integer getRankNum(String rankInfo) {
        if (rankInfo != null) {
            try {
                return Integer.valueOf(RegexTool.getInfo("#(\\S+) in", rankInfo, 1).replaceAll(",",""));
            } catch (RegexNotMatchException e) {
                return -1;
            }
        }
        return -1;
    }

}
