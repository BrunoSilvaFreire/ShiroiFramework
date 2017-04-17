package me.ddevil.shiroi.ui.api.component

/**
 * The base class for every single object in an UI hierarchy, every UI element extends this.
 */
interface Component {

    /**
     * <b>Not functional yet, just set this to null</b><br>
     * The unique identifier for the object, used to find specific objects in a hierarchy.
     * There must NOT have two object with the same id in the same hierarchy.
     */
    var id: String?

    /**
     * Called when the component state changes. Usually on clicks, or before the component or parent is re-drawn, "rendered", etc.
     */
    fun update()

}