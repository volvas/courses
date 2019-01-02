/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Vladimir
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.devproserv.courses.command;

import com.devproserv.courses.model.Response;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import javax.servlet.http.HttpServletRequest;

/**
 * Defines application commands.
 *
 * @since 0.5.0
 */
public class Commands implements Serializable {
    /**
     * Serial number.
     */
    private static final long serialVersionUID = 7663609960402177509L;

    /**
     * Login command name.
     */
    private static final String COMMAND_LOGIN = "login";

    /**
     * Sign up command name.
     */
    private static final String COMMAND_SIGNUP = "signup";

    /**
     * Logout command name.
     */
    private static final String COMMAND_LOGOUT = "logout";

    /**
     * Enroll command name.
     */
    private static final String COMMAND_ENROLL = "enroll";

    /**
     * Unroll command name.
     */
    private static final String COMMAND_UNROLL = "unroll";

    /**
     * Commands.
     */
    private transient Map<String, Supplier<Command>> map = new HashMap<>();

    /**
     * Builds commands. Provides lazy initialization.
     * @return This instance
     */
    public Commands build() {
        this.map.put(Commands.COMMAND_SIGNUP, SignUp::new);
        this.map.put(Commands.COMMAND_LOGIN,  Login::new);
        this.map.put(Commands.COMMAND_LOGOUT, Logout::new);
        this.map.put(Commands.COMMAND_ENROLL, Enroll::new);
        this.map.put(Commands.COMMAND_UNROLL, Unroll::new);
        return this;
    }

    /**
     * Delivers a path of a page defined by given request.
     * In addition uploads the given request with payload be presented
     * on web page later.
     *
     * @param request HTTP request
     * @return String with a path
     */
    public String path(final HttpServletRequest request) {
        final Response response = this.command(request).response(request);
        response.getPayload().forEach(request::setAttribute);
        return response.getPath();
    }

    /**
     * Instantiates a command by HTTP request parameter.
     * @param request HTTP request
     * @return Command instance
     */
    private Command command(final HttpServletRequest request) {
        final String par = request.getParameter("command");
        final Optional<Supplier<Command>> cmds = Optional.ofNullable(this.map.get(par));
        return cmds.map(Supplier::get).orElse(new NotFound());
    }

    /**
     * Overrides deserialization method to meet findbugs requirements.
     *
     * @param ois ObjectInputStream
     * @throws IOException IO exception
     * @throws ClassNotFoundException Class not found exception
     */
    private void readObject(
        final ObjectInputStream ois
    ) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.map = new HashMap<>();
        this.build();
    }
}
