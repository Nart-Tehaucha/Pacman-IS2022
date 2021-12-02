<<<<<<< HEAD:Pacman-master/src/model/Messages.java
package model;
=======
package models;
>>>>>>> ea6a1897000173c7d22a8c43166c097d24125e39:Pacman-master/src/models/Messages.java

import java.awt.*;

// Defines message types (update, collision test, reset), and sets an action for every one of them.
public class Messages {

    public static final int UPDATE = AWTEvent.RESERVED_ID_MAX + 1;
    public static final int COLTEST = AWTEvent.RESERVED_ID_MAX + 2;
    public static final int RESET = AWTEvent.RESERVED_ID_MAX + 3;


}
