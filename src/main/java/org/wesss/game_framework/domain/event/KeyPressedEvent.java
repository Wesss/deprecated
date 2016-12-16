package org.wesss.game_framework.domain.event;

import org.wesss.game_framework.domain.GameEventListener;

public class KeyPressedEvent extends RawGameEvent {

    private int keyCode;

    public KeyPressedEvent(int keyCode) {
        this.keyCode = keyCode;
    }

    public void acceptEventListener(GameEventListener listener) {
        listener.processGameEvent(this);
    }

    /**
     * @return the unicode character of the key being pressed.
     * <p>
     * Specific Keys can be found using KeyEvent.getKeyText(keyCode) and can
     * be more efficiently compared with KeyEvent.(key constant)
     * ie. KeyEvent.VK_A == key would return true if the pressed key was the 'a' key
     */
    public int getKeyCode() {
        return keyCode;
    }
}
