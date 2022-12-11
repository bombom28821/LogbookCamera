package fpt.grw.cameraapp;

public class Image {
    private int id;
    private String img_url;

    public Image(int id, String url) {
        this.id = id;
        this.img_url = url;
    }

    public Image(String url) {
        this.img_url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String url) {
        this.img_url = url;
    }
}
