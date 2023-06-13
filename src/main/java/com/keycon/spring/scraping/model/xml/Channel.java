package com.keycon.spring.scraping.model.xml;

import java.util.Date;
import java.util.List;

public class Channel {

    public String title;
    public String link;
    public String description;
    public Date pubDate;
    public Date lastBuildDate;
    public String copyright;
    public Image image;
    public String generator;
    public Object language;
    public String docs;
    public List<Item> item;
}
