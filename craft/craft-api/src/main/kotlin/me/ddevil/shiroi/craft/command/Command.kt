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

package me.ddevil.shiroi.craft.command

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Command(
        /**
         * The name of the command. If it is a sub command then its values would be
         * separated by period. ie. a command that would be a subcommand of test
         * would be 'test.subcommandname'

         * @return
         */
        val name: String,
        /**
         * Gets the required permission of the command

         * @return
         */
        val permission: String = "",
        /**
         * The message sent to the player when they do not have permission to
         * execute it

         * @return
         */
        val noPerm: String = "You do not have permission to perform that action",
        /**
         * A list of alternate names that the command is executed under. See
         * name() for details on how names work

         * @return
         */
        val aliases: Array<String> = arrayOf(),
        /**
         * The description that will appear in /help of the command

         * @return
         */
        val description: String = "",
        /**
         * The usage that will appear in /help (commandname)

         * @return
         */
        val usage: String = "",
        /**
         * Whether or not the command is available to player only

         * @return
         */
        val inGameOnly: Boolean = false, val inGameOnlyMessage: String = "You can only use this command while ingame!")
