package com.keycon.spring.scraping.model.xml;


import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Rss {
    public Channel channel;
    public String content;
    public double version;
    public String text;
}
