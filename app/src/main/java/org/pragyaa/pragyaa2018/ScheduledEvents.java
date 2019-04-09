package org.pragyaa.pragyaa2018;



public class ScheduledEvents {

    private String title;
    private String url;
    private String desc;
    private String image;

    ScheduledEvents(){

    }

    ScheduledEvents(String image, String desc, String title,String url){

        this.image = image;
        this.desc = desc;
        this.title = title;
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
