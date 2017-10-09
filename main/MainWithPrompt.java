package main;

import CancelPrompt.Prompt;

import java.io.*;
import java.net.*;
import java.util.Timer;

public class MainWithPrompt {
    public static Process processF;
    public static Process processG;

    public static void terminate() {
        processF.destroy();
        processG.destroy();
    }

    public static void resultsInZero(String funcName) {
        System.out.println(funcName + " finished with zero value. Process is terminated.");
        terminate();
        System.exit(0);
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int test = Integer.parseInt(args[0]);

        Prompt prompt = new Prompt();
        prompt.myTimer = new Timer();
        prompt.myTimer.scheduleAtFixedRate(prompt, 2000, 4000);
        prompt.promptGap = 4000;

        try {
            ServerSocket manager = new ServerSocket(2323);

            ProcessBuilder pbF = new ProcessBuilder("java", "functionF.FuncF");
            pbF.directory(new File("/Volumes/STUDY/University/5thTerm/SPOS/labs/Lab1/out/production/Lab1"));
            processF = pbF.start();
            ProcessBuilder pbG = new ProcessBuilder("java", "functionG.FuncG");
            pbG.directory(new File("/Volumes/STUDY/University/5thTerm/SPOS/labs/Lab1/out/production/Lab1"));
            processG = pbG.start();

            Socket socketF = manager.accept();
            Socket socketG = manager.accept();

            DataOutputStream outputF = new DataOutputStream(socketF.getOutputStream());
            outputF.writeUTF(Integer.toString(test));
            DataOutputStream outputG = new DataOutputStream(socketG.getOutputStream());
            outputG.writeUTF(Integer.toString(test));

            DataInputStream inputF = new DataInputStream(socketF.getInputStream());
            DataInputStream inputG = new DataInputStream(socketG.getInputStream());

            int resultOfF = -1;
            int resultOfG = -1;

            while (true) {
                if (inputF.available() > 0 && resultOfF == -1 && !prompt.prompting) {
                    long timeSpent = System.currentTimeMillis() - startTime;
                    resultOfF = inputF.readByte();
                    if (resultOfF == 0) {
                        resultsInZero("F");
                        break;
                    }
                }

                if (inputG.available() > 0 && resultOfG == -1 && !prompt.prompting) {
                    resultOfG = inputG.readByte();
                    if (resultOfG == 0) {
                        resultsInZero("G");
                        break;
                    }
                }
                prompt.f_is = resultOfF;
                prompt.g_is = resultOfG;
                if (resultOfF > 0 && resultOfG > 0) break;
            }
            System.out.print("\nResult: " + (resultOfF | resultOfG) + "\n");

            inputF.close();
            inputG.close();

            prompt.myTimer.cancel();

            terminate();

            socketF.close();
            socketG.close();
            manager.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
