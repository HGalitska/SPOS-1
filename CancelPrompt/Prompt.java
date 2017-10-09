package CancelPrompt;


import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Prompt extends TimerTask {
    public Timer myTimer = null;
    public static AtomicBoolean prompting = new AtomicBoolean(false);
    public static long lastPromptTime = 5000;

    public static int f_is;
    public static int g_is;

    public void run() {
        if(System.currentTimeMillis() - lastPromptTime > 2000 && prompting.compareAndSet(false, true)) {}
        else return;

        System.out.println("Press: 1 - continue, 2 - continue without prompt, 3 - cancel computation.");
        Scanner sc = new Scanner(System.in);
        if(sc.hasNextInt()){
            int i = sc.nextInt();

            switch (i) {
                case 1:
                    break;
                case 2:
                    myTimer.cancel();
                    break;
                case 3:
                    System.out.println("Computation was cancelled.");
                    if (f_is == -1)
                        System.out.println("We couldn't compute function F so fast. Sorry.");
                    else if (g_is == -1)
                        System.out.println("We couldn't compute function G so fast. Sorry.");
                    else System.out.println("But result was computed: " + (f_is | g_is));
                    myTimer.cancel();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong input.");
                    break;
            }
        }
        else{
            System.out.println("Wrong input.");
        }

        lastPromptTime = System.currentTimeMillis();
        prompting.set(false);
        return;
    }
}