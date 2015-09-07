package com.zzy.secret.md;

/**
 * Describe 评论类
 * Created by zhengzy on 2015/8/27.
 * Github https://github.com/zheng-zy
 */
public class Comment {
    //评论内容
    private String content;
    //评论者
    private String phonr_md5;

    public Comment(String content, String phonr_md5) {
        this.content = content;
        this.phonr_md5 = phonr_md5;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getPhonr_md5() {
        return phonr_md5;
    }
    public void setPhonr_md5(String phonr_md5) {
        this.phonr_md5 = phonr_md5;
    }

}
