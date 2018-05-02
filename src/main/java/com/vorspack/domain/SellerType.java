package com.vorspack.domain;

public enum SellerType{
        AMAZON("亚马逊自营"),
        BRAND_SELLER("品牌卖家"),
        OTHER_SELLER("第三方跟卖"),
        UNKNOWN("未知售卖类型");

   private String sellerTypeInfo;
   SellerType(String sellerTypeInfo){
           this.sellerTypeInfo = sellerTypeInfo;
   }
   public static String getSellerTypeInfo(String name) {
           return SellerType.valueOf(name).sellerTypeInfo;
   }
}
