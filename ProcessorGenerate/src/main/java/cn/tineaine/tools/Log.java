package cn.tineaine.tools;

public class Log {

    public void INFO(String info) {
        System.out.println("\033[32m" + info);
    }

    public void WARNING(String info) {
        System.out.println("\033[31m" + info);
    }

    public void ERROR(String info) {
        System.out.println("\033[43m" + info);
    }

}
