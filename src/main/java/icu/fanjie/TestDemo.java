package icu.fanjie;

public class TestDemo implements Runnable {
    private String url;

    public TestDemo(String url) {
        this.url = url;
    }


    @Override
    public void run() {
        System.out.println(url);
    }
}
