/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2014, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.thymeleaf.itutorial;

import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    public static final String THYMELEAF_VERSION_PARAMETER = "thymeleafVersion";
    
    private final ServletContext servletContext;

    @Autowired
    public IndexController(final ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @RequestMapping("/")
    public String index(final Model model) {
        model.addAttribute("thymeleafVersion", servletContext.getInitParameter(THYMELEAF_VERSION_PARAMETER));
        model.addAttribute("basicExercises", Exercise.basicExercises());
        model.addAttribute("twoDotOneExercises", Exercise.twoDotOneExercises());
        return "index.html";
    }
}
