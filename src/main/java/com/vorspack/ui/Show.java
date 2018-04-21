package com.vorspack.ui;

import javax.swing.*;
import java.awt.*;

public class Show {
	public static void inFrame(Component jp, int width, int height) {
		String title = jp.getClass().toString(); // Remove the word "class":
		if (title.contains("class"))
			title = title.substring(6);
		inFrame(jp,title,width,height);
	}

	public static void inFrame(Component jp,String title,int width, int height) {
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().add(jp);
		frame.setSize(width, height);
		frame.setVisible(true);
	}
} /// :~
