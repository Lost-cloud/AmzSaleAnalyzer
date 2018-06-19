package com.vorspack.ui;

import javax.swing.*;

public class ProductListModel extends AbstractListModel<Object> {

    private Object[] productValues;

    public ProductListModel(Object[] productValues) {
        this.productValues = productValues;
    }

    @Override
    public int getSize() {
        return productValues.length;
    }

    @Override
    public Object getElementAt(int index) {
        return productValues[index];
    }
}
