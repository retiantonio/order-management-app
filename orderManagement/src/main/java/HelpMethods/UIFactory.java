package HelpMethods;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Utility factory class for UI-related helper methods.
 */
public class UIFactory {

    /**
     * Calls the method named {@code restoreUI} reflectively on the given controller object,
     * passing the provided list as an argument.
     * <p>
     * This method looks for a method with the signature
     * {@code void restoreUI(List<?>)} in the controller's class, makes it accessible,
     * and invokes it with the given list.
     * </p>
     *
     * @param controller the controller instance on which to invoke the method
     * @param list       the list to pass as a parameter to the {@code restoreUI} method
     */
    public static void callRestoreUIReflectively(Object controller, List<?> list) {
        try {
            if (list == null || list.isEmpty()) return;

            Method restoreMethod = controller.getClass().getDeclaredMethod("restoreUI", List.class);
            restoreMethod.setAccessible(true);

            restoreMethod.invoke(controller, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
