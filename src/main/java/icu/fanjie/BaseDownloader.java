package icu.fanjie;

public class BaseDownloader extends Downloader {
    public BaseDownloader() {
        System.out.println(acceptStatusCode.toString());
    }

    public static void main(String[] args) {
        new BaseDownloader();
    }
}
