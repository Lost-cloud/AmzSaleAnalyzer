package com.vorspack.ui;

import com.vorspack.util.ProductBasicInfo;
import com.vorspack.util.ProductFactory;

import javax.swing.*;
import java.awt.*;

public class ProductCellRenderer extends JLabel implements ListCellRenderer<Object> {

    private static int WIDTH = 100;
//    private Image[] image;
////
//    public ProductCellRenderer(Image[] image) {
////        super(image);
//        this.image = image;
//    }

    public ProductCellRenderer() {
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof  Image)addImage((Image) value);
        return this;
    }

    private void addImage(Image o) {
        Image smallImg;
        int height;
        height = o.getHeight(null) * WIDTH / o.getWidth(null);
        smallImg = o.getScaledInstance(WIDTH, height, Image.SCALE_SMOOTH);
        Icon icon = new ImageIcon(smallImg);
        this.setIcon(icon);
    }

}
