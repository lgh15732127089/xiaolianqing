package com.duoxin.pojo;


public class Template {

    private int id;
    private String url;
    private String lujing;
    private String name;
    private String ischoose;


    public String getIschoose() {
        return ischoose;
    }

    public void setIschoose(String ischoose) {
        this.ischoose = ischoose;
    }

    public String getLujing() {
        return lujing;
    }

    public void setLujing(String lujing) {
        this.lujing = lujing;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Template{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", lujing='" + lujing + '\'' +
                ", name='" + name + '\'' +
                ", ischoose='" + ischoose + '\'' +
                '}';
    }
}
