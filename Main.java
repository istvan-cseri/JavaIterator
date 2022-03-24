import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Main {
    static Node root = new Node(null);
    static LinkedBlockingQueue<Integer> send1 = new LinkedBlockingQueue<Integer>();
    static LinkedBlockingQueue<Node> response1 = new LinkedBlockingQueue<Node>();
    static LinkedBlockingQueue<Integer> send2 = new LinkedBlockingQueue<Integer>();
    static LinkedBlockingQueue<Node> response2 = new LinkedBlockingQueue<Node>();

    public static void main(String[] args) {
        try {
            Node n1 = new Node(root);
            Node n2 = new Node(root);
            Node n21 = new Node(n2);
            Node n22 = new Node(n2);
            Node n23 = new Node(n2);
            Node n24 = new Node(n2);
            Node n3 = new Node(root);
            Runnable run1 = new Runner1();
            Thread t1 = new Thread(run1);
            t1.start();
            Runnable run2 = new Runner2();
            Thread t2 = new Thread(run2);
            t2.start();

            for (int i = 0; i < 4; i++) {
                send1.add(1);
                System.out.println(response1.take().toString());
            }

            System.out.println("do the delete!");
            send2.add(1);
            System.out.println(response2.take().toString());

            send1.add(0);

            t1.join();
            t2.join();
            
            System.out.println("After delete");
            Iterator iter = new Iterator(root);
            while (iter.hasMoreElements()) {
                Node n = (Node)iter.nextElement();
                System.out.println(String.format("MAIN: %d", n.id));
            }

        }
        catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    static class Runner1 implements Runnable {
        public void run() {
            try {
                boolean take = true;
                System.out.println("Starting thread1");
                Iterator iter = new Iterator(root);
                while (iter.hasMoreElements()) {
                    if (take) {
                        Integer i = send1.take();
                        if (i == 0) {
                            take = false;
                        }
                    }
                    Node n = (Node)iter.nextElement();
                    System.out.println(String.format("THREAD1: %d", n.id));
                    response1.add(n);
                }
                System.out.println("End thread1");
            }
            catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    static class Runner2 implements Runnable {
        public void run() {
            try {
                System.out.println("Starting thread2");
                Iterator iter = new Iterator(root);
                while (iter.hasMoreElements()) {
                    Node n = (Node)iter.nextElement();
                    if (n.id == 2) {
                        Integer i = send2.take();
                        iter.delete(n);
                        response2.add(n);
                        System.out.println(String.format("THREAD2: %d", n.id));
                    }
                }
                System.out.println("End thread2");
            }
            catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
