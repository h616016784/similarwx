package com.android.similarwx.beans;

import java.util.List;

/**
 * Created by hanhuailong on 2018/3/15.
 */

public class testbean {

    /**
     * subtype_id : 7
     * subtype_name : 户外
     * resources : [{"id":0,"title":"游泳","profile":"介绍幼儿游泳的基本知识，防止幼儿因基本知识的缺乏导致溺水的悲剧","url":"http://7xkskz.com1.z0.glb.clouddn.com/","img":"images/swim.jpg","is_show":"1"},{"id":10002,"title":"标示大家族","profile":"介绍户外标示，提高幼儿基本常识","url":"http://7xkskz.com1.z0.glb.clouddn.com/","img":"images/signs.jpg","is_show":"1"}]
     */

    private int subtype_id;
    private String subtype_name;
    private List<ResourcesBean> resources;

    public int getSubtype_id() {
        return subtype_id;
    }

    public void setSubtype_id(int subtype_id) {
        this.subtype_id = subtype_id;
    }

    public String getSubtype_name() {
        return subtype_name;
    }

    public void setSubtype_name(String subtype_name) {
        this.subtype_name = subtype_name;
    }

    public List<ResourcesBean> getResources() {
        return resources;
    }

    public void setResources(List<ResourcesBean> resources) {
        this.resources = resources;
    }

    public static class ResourcesBean {
        /**
         * id : 0
         * title : 游泳
         * profile : 介绍幼儿游泳的基本知识，防止幼儿因基本知识的缺乏导致溺水的悲剧
         * url : http://7xkskz.com1.z0.glb.clouddn.com/
         * img : images/swim.jpg
         * is_show : 1
         */

        private int id;
        private String title;
        private String profile;
        private String url;
        private String img;
        private String is_show;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }
    }
}
