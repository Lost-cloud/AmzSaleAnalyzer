package com.vorspack.network;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface Html {
    Document getHtmlDocument(String url) throws IOException;
}
