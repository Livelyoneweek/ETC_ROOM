package kr.co.choi.etc.test;

public class Test3 {

    public void draw() {
        System.out.println('A');
        draw();
    }
    public void paint() {
        System.out.print('B');
        draw();
    }

}

class Test4 extends Test3 {
    public void paint() {
        super.paint();
        System.out.print('C');
        draw();
    }
    public void draw() {
        System.out.print('D');
    }
}

class Test3Impl {
    public static void main(String[] args) {
        Test3 a = new Test4();
        a.paint();
        a.draw();

    }
}