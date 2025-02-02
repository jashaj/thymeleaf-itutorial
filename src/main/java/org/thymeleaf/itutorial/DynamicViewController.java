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

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.thymeleaf.itutorial.ModelPopulator.populateModelMap;
import static org.thymeleaf.tools.templateresolvers.DynamicTemplateResolver.PREFIX;

@Controller
public class DynamicViewController {

    @PostMapping(value = "/dynamicView")
    public String dynamicView(
            @RequestParam("code") final String code, ModelMap modelMap) {
        populateModelMap(modelMap);
        return PREFIX + code;
    }

}
