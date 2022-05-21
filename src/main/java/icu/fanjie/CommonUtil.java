package icu.fanjie;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;

public class CommonUtil implements Serializable {

    private TestDemo testDemo = null;

    public void setTestDemo(TestDemo testDemo) {
        this.testDemo = testDemo;
    }

    public TestDemo getTestDemo() {
        return testDemo;
    }

    public static String getMd5(String url) {
        return DigestUtils.md5Hex(url);
    }

    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;

        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);

            oos.writeObject(object);

            byte[] bytes = baos.toByteArray();

            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Object unserialize(byte[] bytes) {

        ByteArrayInputStream bais = null;

        try {
            bais = new ByteArrayInputStream(bytes);

            ObjectInputStream ois = new ObjectInputStream(bais);

            return ois.readObject();

        } catch (Exception e) {

        }

        return null;

    }
}
