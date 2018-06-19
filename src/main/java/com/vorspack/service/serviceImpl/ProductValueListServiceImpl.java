package com.vorspack.service.serviceImpl;

import com.vorspack.domain.Product;
import com.vorspack.domain.SellerType;
import com.vorspack.service.ProductValueListService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProductValueListServiceImpl implements ProductValueListService {
    @Override
    public List<Object[]> createProductIValueList(List<Product> products) {
        List<Object[]> valuesList=new ArrayList<>();
        List<Object> values;
        for (Product product : products) {
            values=new ArrayList<>();

            values.add(product.getImage());
            values.add(product.getAsin());
            values.add(product.getProductTitle());
            values.add(product.getLink());
            values.add(product.getBrand());
            values.add(product.getSeller());
            values.add(SellerType.getSellerTypeInfo(product.getSellerType().name()));

            if (product.isIfFBA()) {
                values.add( "是");
            } else {
                values.add( "否");
            }

            if (product.getSeller().equals("amazon.com")) {
                values.add("是");
            }else  values.add( "否");

            values.add(product.getRate());
            //上架时间
            values.add("手动查询");
            values.add(product.getReviewNum());
            values.add(product.getPrice());
            values.add(product.getRankInfo());
            //销量
            values.add("手动查询");
            values.add(product.getQaNum());
            values.add(product.getVariantNum());
            //净利润
            values.add("手动查询");

            //转换成String数组，并添加到单元格数据列表中
            valuesList.add(values.toArray());

        }
        return valuesList;
    }
}
