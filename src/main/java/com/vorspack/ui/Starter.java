package com.vorspack.ui;

import com.vorspack.config.RootConfig;
import com.vorspack.network.Html;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;

public class Starter {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            ApplicationContext  context = new AnnotationConfigApplicationContext(RootConfig.class);
            AsaWindow asaWindow = context.getBean(AsaWindow.class);
            @Override
            public void run() {
                try {
                    Show.inFrame(asaWindow, "AmzSaleAnalyser", 800, 600);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
