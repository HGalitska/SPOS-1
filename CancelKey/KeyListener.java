package CancelKey;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyListener implements NativeKeyListener {
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_0) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e1) {
                e1.printStackTrace();
            }
            System.out.println("Computation was cancelled. It took too long to compute result. Sorry."); //which func
            System.exit(0);
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {}

    public void nativeKeyTyped(NativeKeyEvent e) {}

}