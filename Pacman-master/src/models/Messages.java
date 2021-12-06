<<<<<<< HEAD

package models;

=======
package models;
>>>>>>> 0cf91c0e8e0315fa2516fb15e07907f79a9fb731

import java.awt.*;

// Defines message types (update, collision test, reset), and sets an action for every one of them.
public class Messages {

    public static final int UPDATE = AWTEvent.RESERVED_ID_MAX + 1;
    public static final int COLTEST = AWTEvent.RESERVED_ID_MAX + 2;
    public static final int RESET = AWTEvent.RESERVED_ID_MAX + 3;


}
