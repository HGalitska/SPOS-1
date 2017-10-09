package CancelPrompt;

import java.util.*;

public class Prompt extends TimerTask {
    public Timer myTimer = null;
    public boolean prompting = false;
    public long promptGap;
    public long lastPromptTime;

    public void run() {
        prompting = true;
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
                    System.out.println("Computation was cancelled. It took too long to compute result. Sorry.");
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
        prompting = false;
        return;
    }
}