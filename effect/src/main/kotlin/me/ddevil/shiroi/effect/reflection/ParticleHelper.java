package me.ddevil.shiroi.effect.reflection;

import org.jetbrains.annotations.NotNull;

/**
 * Created by bruno on 12/03/2017.
 */
public class ParticleHelper {
    @NotNull
    static Enum<?> getEnum(String enumFullName) {
        String[] x = enumFullName.split("\\.(?=[^.]+$)");
        if (x.length == 2) {
            String enumClassName = x[0];
            String enumName = x[1];
            try {
                Class<Enum> cl = (Class<Enum>) Class.forName(enumClassName);
                return Enum.valueOf(cl, enumName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalStateException("Unknown enum " + enumFullName);
    }
}

