package org.pragyaa.pragyaa2018;



public class InfoDept {

    private String title;
    private String main;
    private String joint;


    InfoDept(){

    }

    InfoDept(String title,String main,String joint){
        this.title = title;
        this.main = main;
        this.joint = joint;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getJoint() {
        return joint;
    }

    public void setJoint(String joint) {
        this.joint = joint;
    }
}
