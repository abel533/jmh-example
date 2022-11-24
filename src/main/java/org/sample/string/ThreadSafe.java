package org.sample.string;

public class ThreadSafe {
    public static void main(String[] args) throws InterruptedException {

        StringBuilder sbuilder = new StringBuilder();

        StringBuilderScaler builderThread1 = new StringBuilderScaler(sbuilder);
        StringBuilderScaler builderThread2 = new StringBuilderScaler(sbuilder);
        StringBuilderScaler builderThread3 = new StringBuilderScaler(sbuilder);

        builderThread1.start();
        builderThread2.start();
        builderThread3.start();
        builderThread1.join();
        builderThread2.join();
        builderThread3.join();

        System.out.println("StringBuilder: " + sbuilder.toString());

        StringBuffer sbuffer = new StringBuffer();

        StringBufferScaler bufferThread1 = new StringBufferScaler(sbuffer);
        StringBufferScaler bufferThread2 = new StringBufferScaler(sbuffer);
        StringBufferScaler bufferThread3 = new StringBufferScaler(sbuffer);

        bufferThread1.start();
        bufferThread2.start();
        bufferThread3.start();
        bufferThread1.join();
        bufferThread2.join();
        bufferThread3.join();

        System.out.println("StringBuffer : " + sbuffer.toString());
    }
}

class StringBuilderScaler extends Thread {

    StringBuilder sbuilder;

    public StringBuilderScaler(StringBuilder sb) {
        sbuilder = sb;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            sbuilder.append(i);
        }
    }
}

class StringBufferScaler extends Thread {

    StringBuffer sbuffer;

    public StringBufferScaler(StringBuffer sb) {
        sbuffer = sb;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            sbuffer.append(i);
        }
    }
}
