package com.barryzhang.gankio.entities;

import java.util.List;

/**
 * Created by Barry on 16/5/16.
 *
 *  获取特定日期网站数据:
 * demo： http://gank.io/api/history/content/day/2016/05/11
 */
public class DaysContent extends BaseHttpResponseEntity<List<DaysContent.Content>> {

    public static class Content{
        private String _id;
        private String content;
        private String publishedAt;
        private String title;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
