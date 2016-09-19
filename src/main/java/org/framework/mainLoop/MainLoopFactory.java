package org.framework.mainLoop;

public class MainLoopFactory {

    public static MainLoop getMainLoop(int fps) {
        if (fps <= 0)
            throw new IllegalArgumentException("fps must be positive");

        MainLoopModel model = new MainLoopModel();
        MainLoopInterfaceFactory interfaceFactory = new MainLoopInterfaceFactory(model);
        MainLoopController controller = new MainLoopController(
                interfaceFactory,
                interfaceFactory.getAdvancedInterface(),
                new MainLoopGroupFactory(interfaceFactory.getAdvancedInterface(), MainLoopController.GAMEOBJ_GROUP_PRIORITY)
        );

        return new MainLoop(
                model,
                controller,
                new MainLoopRunnableTarget(model, fps)
        );
    }

    // TODO are destructors necessary on the factory?
    public static void destroyMainLoop(MainLoop mainLoop) {
        mainLoop.exit();
    }
}
