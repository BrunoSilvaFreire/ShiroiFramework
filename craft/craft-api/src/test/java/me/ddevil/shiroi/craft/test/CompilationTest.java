package me.ddevil.shiroi.craft.test;

import me.ddevil.shiroi.craft.plugin.ShiroiPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Created by bruno on 06/01/2017.
 */
public class CompilationTest {
    @NotNull
    public static final ShiroiPlugin<?, ?> plugin = new TestShiroiPlugin();

    public static void main(String[] args) {
    }
}
