/*
 *
 *  * Copyright 2015-2016 Bruno Silva Freire. All rights reserved.
 *  *
 *  *  Redistribution and use in source and binary forms, with or without modification, are
 *  *  permitted provided that the following conditions are met:
 *  *
 *  *     1. Redistributions of source code must retain the above copyright notice, this list of
 *  *        conditions and the following disclaimer.
 *  *
 *  *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *  *        of conditions and the following disclaimer in the documentation and/or other materials
 *  *        provided with the distribution.
 *  *
 *  *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 *  *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 *  *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  *
 *  *  The views and conclusions contained in the software and documentation are those of the
 *  *  authors and contributors and should not be interpreted as representing official policies,
 *  *  either expressed or implied, of anybody else.
 *
 */

package me.ddevil.shiroi.effect.reflection

import me.ddevil.shiroi.util.misc.MinecraftVersion
import org.bukkit.Bukkit
import java.lang.reflect.Method
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by BRUNO II on 04/08/2016.
 */
object ReflectionUtils {
    // Deduce the net.minecraft.server.v* package
    private val OBC_PREFIX = Bukkit.getServer().javaClass.`package`.name
    //private static String OBC_PREFIX = CraftServer.class.getPackage().getName();
    private val NMS_PREFIX = OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server")
    private val VERSION = OBC_PREFIX.replace("org.bukkit.craftbukkit.", "")
    // Variable replacement
    private val MATCH_VARIABLE = Pattern.compile("\\{([^}]+)}")

    val serverVersion: MinecraftVersion
        get() {
            val versionName = ReflectionUtils.version.toUpperCase()
            return MinecraftVersion.values().firstOrNull { versionName.contains(it.name) }
                    ?: MinecraftVersion.UNKNOWN
        }


    val version: String
        get() = VERSION + "."

    fun getNMSClass(className: String): Class<*> {
        val fullName = "net.minecraft.server." + version + className
        var clazz: Class<*>? = null
        try {
            clazz = Class.forName(fullName)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return clazz ?: throw IllegalStateException("Unknown class $className")
    }

    fun getOBCClass(className: String): Class<*> {
        val fullName = "org.bukkit.craftbukkit." + version + className
        var clazz: Class<*>? = null
        try {
            clazz = Class.forName(fullName)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return clazz ?: throw IllegalStateException("Unknown class $className")
    }

    /**
     * Expand variables such as "{nms}" and "{obc}" to their corresponding packages.

     * @param name the full name of the class
     * *
     * @return the expanded string
     */
    private fun expandVariables(name: String): String {
        val output = StringBuffer()
        val matcher = MATCH_VARIABLE.matcher(name)

        while (matcher.find()) {
            val variable = matcher.group(1)
            var replacement: String

            // Expand all detected variables
            if ("nms".equals(variable, ignoreCase = true))
                replacement = NMS_PREFIX
            else if ("obc".equals(variable, ignoreCase = true))
                replacement = OBC_PREFIX
            else if ("version".equals(variable, ignoreCase = true))
                replacement = VERSION
            else
                throw IllegalArgumentException("Unknown variable: " + variable)

            // Assume the expanded variables are all packages, and append a dot
            if (replacement.isNotEmpty() && matcher.end() < name.length && name[matcher.end()] != '.')
                replacement += "."
            matcher.appendReplacement(output, Matcher.quoteReplacement(replacement))
        }
        matcher.appendTail(output)
        return output.toString()
    }

    /**
     * Retrieve a class by its canonical name.

     * @param canonicalName the canonical name
     * *
     * @return the class
     */
    private fun getCanonicalClass(canonicalName: String): Class<*> {
        try {
            return Class.forName(canonicalName)
        } catch (e: ClassNotFoundException) {
            throw IllegalArgumentException("Cannot find " + canonicalName, e)
        }

    }

    /**
     * Retrieve a class from its full name.
     *
     *
     * Strings enclosed with curly brackets such as {TEXT} will be replaced according
     * to the following table:
     *
     *
     * <table border="1">
     * <tr>
     * <th>Variable</th>
     * <th>Content</th>
    </tr> *
     * <tr>
     * <td>{nms}</td>
     * <td>Actual package name of net.minecraft.server.VERSION</td>
    </tr> *
     * <tr>
     * <td>{obc}</td>
     * <td>Actual pacakge name of org.bukkit.craftbukkit.VERSION</td>
    </tr> *
     * <tr>
     * <td>{version}</td>
     * <td>The current Minecraft package VERSION, if any.</td>
    </tr> *
    </table> *

     * @param lookupName the class name with variables
     * *
     * @return the looked up class
     * *
     * @throws IllegalArgumentException If a variable or class could not be found
     */
    fun getClass(lookupName: String): Class<*> {
        return getCanonicalClass(expandVariables(lookupName))
    }

    /**
     * Search for the first publicly and privately defined constructor of the given name and parameter count.

     * @param className lookup name of the class, see [.getClass]
     * *
     * @param params    the expected parameters
     * *
     * @return an internal that invokes this constructor
     * *
     * @throws IllegalStateException If we cannot find this method
     */
    fun getConstructor(className: String, vararg params: Class<*>): ConstructorInvoker {
        return getConstructor(getClass(className), *params)
    }

    /**
     * Search for the first publicly and privately defined constructor of the given name and parameter count.

     * @param clazz  a class to start with
     * *
     * @param params the expected parameters
     * *
     * @return an internal that invokes this constructor
     * *
     * @throws IllegalStateException If we cannot find this method
     */
    fun getConstructor(clazz: Class<*>, vararg params: Class<*>): ConstructorInvoker {
        for (constructor in clazz.declaredConstructors) {
            if (Arrays.equals(constructor.parameterTypes, params)) {

                constructor.isAccessible = true
                return object : ConstructorInvoker {
                    override operator fun invoke(vararg arguments: Any): Any {
                        try {
                            return constructor.newInstance(*arguments)
                        } catch (e: Exception) {
                            throw RuntimeException("Cannot invoke constructor " + constructor, e)
                        }

                    }
                }
            }
        }
        throw IllegalStateException(String.format(
                "Unable to find constructor for %s (%s).", clazz, Arrays.asList(*params)))
    }

    /**
     * Retrieve a class in the org.bukkit.craftbukkit.VERSION.* package.

     * @param name the name of the class, excluding the package
     * *
     * @throws IllegalArgumentException If the class doesn't exist
     */
    fun getCraftBukkitClass(name: String): Class<*> {
        return getCanonicalClass(OBC_PREFIX + "." + name)
    }

    /**
     * Retrieve a field accessor for a specific field type and name.

     * @param target    the target type
     * *
     * @param name      the name of the field, or NULL to ignore
     * *
     * @param fieldType a compatible field type
     * *
     * @return the field accessor
     */
    fun <T> getField(target: Class<*>, name: String, fieldType: Class<T>): FieldAccessor<T> {
        return getField(target, name, fieldType, 0)
    }

    /**
     * Retrieve a field accessor for a specific field type and name.

     * @param className lookup name of the class, see [.getClass]
     * *
     * @param name      the name of the field, or NULL to ignore
     * *
     * @param fieldType a compatible field type
     * *
     * @return the field accessor
     */
    fun <T> getField(className: String, name: String, fieldType: Class<T>): FieldAccessor<T> {
        return getField(getClass(className), name, fieldType, 0)
    }

    /**
     * Retrieve a field accessor for a specific field type and name.

     * @param target    the target type
     * *
     * @param fieldType a compatible field type
     * *
     * @param index     the number of compatible fields to skip
     * *
     * @return the field accessor
     */
    fun <T> getField(target: Class<*>, fieldType: Class<T>, index: Int): FieldAccessor<T> {
        return getField(target, null, fieldType, index)
    }

    /**
     * Retrieve a field accessor for a specific field type and name.

     * @param className lookup name of the class, see [.getClass]
     * *
     * @param fieldType a compatible field type
     * *
     * @param index     the number of compatible fields to skip
     * *
     * @return the field accessor
     */
    fun <T> getField(className: String, fieldType: Class<T>, index: Int): FieldAccessor<T> {
        return getField(getClass(className), fieldType, index)
    }

    // Common method
    private fun <T> getField(target: Class<*>, name: String?, fieldType: Class<T>, index: Int): FieldAccessor<T> {
        var index = index
        for (field in target.declaredFields) {
            if ((name == null || field.name == name) && fieldType.isAssignableFrom(field.type) && index-- <= 0) {
                field.isAccessible = true

                // A function for retrieving a specific field value
                return object : FieldAccessor<T> {
                    override operator fun get(target: Any): T {
                        try {
                            return field.get(target) as T
                        } catch (e: IllegalAccessException) {
                            throw RuntimeException("Cannot access reflection.", e)
                        }

                    }

                    override operator fun set(target: Any, value: Any) {
                        try {
                            field.set(target, value)
                        } catch (e: IllegalAccessException) {
                            throw RuntimeException("Cannot access reflection.", e)
                        }

                    }

                    override fun hasField(target: Any): Boolean {
                        // target instanceof DeclaringClass
                        return field.declaringClass.isAssignableFrom(target.javaClass)
                    }
                }
            }
        }

        // Search in parent classes
        if (target.superclass != null)
            return getField(target.superclass, name, fieldType, index)
        throw IllegalArgumentException("Cannot find field with type " + fieldType)
    }

    /**
     * Search for the first publicly and privately defined method of the given name and parameter count.

     * @param className  lookup name of the class, see [.getClass]
     * *
     * @param methodName the method name, or NULL to skip
     * *
     * @param params     the expected parameters
     * *
     * @return an internal that invokes this specific method
     * *
     * @throws IllegalStateException If we cannot find this method
     */
    fun getMethod(className: String, methodName: String, vararg params: Class<*>): MethodInvoker {
        return getTypedMethod(getClass(className), methodName, null, *params)
    }

    /**
     * Search for the first publicly and privately defined method of the given name and parameter count.

     * @param clazz      a class to start with
     * *
     * @param methodName the method name, or NULL to skip
     * *
     * @param params     the expected parameters
     * *
     * @return an internal that invokes this specific method
     * *
     * @throws IllegalStateException If we cannot find this method
     */
    fun getMethod(clazz: Class<*>, methodName: String, vararg params: Class<*>): MethodInvoker {
        return getTypedMethod(clazz, methodName, null, *params)
    }

    inline fun <reified T : Enum<T>> getEnum(name: String): T {
        val result = T::class.java.enumConstants.firstOrNull { it.name == name }
        if (result != null) {
            return result
        }
        throw java.lang.IllegalArgumentException("No enum constant ${T::class.java.canonicalName}.$name")
    }

    /**
     * Search for the first publicly and privately defined method of the given name and parameter count.

     * @param clazz  target class
     * *
     * @param method the method name
     * *
     * @return the method found
     */
    fun getMethodSimply(clazz: Class<*>, method: String): Method? {
        return clazz.methods.firstOrNull { it.name == method }
    }

    /**
     * Retrieve a class in the net.minecraft.server.VERSION.* package.

     * @param name the name of the class, excluding the package
     * *
     * @throws IllegalArgumentException If the class doesn't exist
     */
    fun getMinecraftClass(name: String): Class<*> {
        return getCanonicalClass(NMS_PREFIX + "." + name)
    }

    /**
     * Search for the first publicly and privately defined method of the given name and parameter count.

     * @param clazz      a class to start with
     * *
     * @param methodName the method name, or NULL to skip
     * *
     * @param returnType the expected return type, or NULL to ignore
     * *
     * @param params     the expected parameters
     * *
     * @return an internal that invokes this specific method
     * *
     * @throws IllegalStateException If we cannot find this method
     */
    fun getTypedMethod(clazz: Class<*>,
                       methodName: String,
                       returnType: Class<*>?,
                       vararg params: Class<*>): MethodInvoker {
        for (method in clazz.declaredMethods) {
            if (method.name == methodName && returnType == null || method.returnType == returnType && Arrays.equals(
                    method.parameterTypes,
                    params)) {
                method.isAccessible = true
                return object : MethodInvoker {
                    override operator fun invoke(target: Any, vararg arguments: Any): Any {
                        try {
                            return method.invoke(target, *arguments)
                        } catch (e: Exception) {
                            throw RuntimeException("Cannot invoke method " + method, e)
                        }

                    }
                }
            }
        }
        // Search in every superclass
        if (clazz.superclass != null)
            return getMethod(clazz.superclass, methodName, *params)
        throw IllegalStateException(String.format(
                "Unable to find method %s (%s).", methodName, Arrays.asList(*params)))
    }

    /**
     * Retrieve a class from its full name, without knowing its type on compile time.
     *
     *
     * This is useful when looking up fields by a NMS or OBC type.
     *
     *

     * @param lookupName the class name with variables
     * *
     * @return the class
     * *
     * @see {@link .getClass
     */
    fun getUntypedClass(lookupName: String): Class<Any> {
        val clazz = getClass(lookupName) as Class<Any>
        return clazz
    }

    fun <T> newInstance(type: Class<T>): T? {
        try {
            return type.newInstance()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * An interface for invoking a specific constructor.
     */
    interface ConstructorInvoker {
        /**
         * Invoke a constructor for a specific class.

         * @param arguments the arguments to pass to the constructor.
         * *
         * @return the constructed internal.
         */
        operator fun invoke(vararg arguments: Any): Any
    }

    /**
     * An interface for invoking a specific method.
     */
    interface MethodInvoker {
        /**
         * Invoke a method on a specific target internal.

         * @param target    the target internal, or NULL for a static method.
         * *
         * @param arguments the arguments to pass to the method.
         * *
         * @return the return value, or NULL if is void.
         */
        operator fun invoke(target: Any, vararg arguments: Any): Any
    }

    /**
     * An interface for retrieving the field content.

     * @param <T> field type
    </T> */
    interface FieldAccessor<out T> {
        /**
         * Retrieve the content of a field.

         * @param target the target internal, or NULL for a static field
         * *
         * @return the value of the field
         */
        operator fun get(target: Any): T

        /**
         * Set the content of a field.

         * @param target the target internal, or NULL for a static field
         * *
         * @param value  the new value of the field
         */
        operator fun set(target: Any, value: Any)

        /**
         * Determine if the given internal has this field.

         * @param target the internal to test
         * *
         * @return TRUE if it does, FALSE otherwise
         */
        fun hasField(target: Any): Boolean
    }

}
