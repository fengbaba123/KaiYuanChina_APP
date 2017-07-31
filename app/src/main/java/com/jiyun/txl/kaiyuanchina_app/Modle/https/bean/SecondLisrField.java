package com.jiyun.txl.kaiyuanchina_app.Modle.https.bean;

import java.util.List;

/**
 * 分类 三级列表
 */

public class SecondLisrField {


    private String pagesize;
    private String softwarecount;
    private List<SoftwareBean> softwares;

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getSoftwarecount() {
        return softwarecount;
    }

    public void setSoftwarecount(String softwarecount) {
        this.softwarecount = softwarecount;
    }

    public List<SoftwareBean> getSoftwares() {
        return softwares;
    }

    public void setSoftwares(List<SoftwareBean> softwares) {
        this.softwares = softwares;
    }

    public static class SoftwareBean {
        private String id;
        private String name;
        private String description;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
