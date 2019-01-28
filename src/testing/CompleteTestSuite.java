package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import levelLibrary.LevelTests;
import physicsLibrary.PhysicsTests;
import saveLoadLibrary.SaveLoadTests;
import entityLibrary.EntityTests;
import guiLibrary.tests.*;
import animationLibrary.tests.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LevelTests.class,
        PhysicsTests.class,
        SaveLoadTests.class,
        EntityTests.class,
        GUITests.class,
        AnimationTests.class
})

public class CompleteTestSuite {}