/*
 * Copyright (C) 2016. The CloudKit Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.cloudkit.example.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

//@Component, @Service, @Repository, @Controller
@Controller
@RestController
//@RequestMapping("/user")
@EnableAutoConfiguration
public class SampleController {

    /*
    @Autowired
    private CityService cityService;
    */

    @RequestMapping("/hello")
    @ResponseBody
    @Transactional(readOnly = true)
    public String helloWorld(@RequestParam(defaultValue = "world") String name) {
        // return this.cityService.getCity("Bath", "UK").getName();
        return "hello " + name + "!";
    }

    // , produces = "application/json;charset=utf-8"
    @RequestMapping(value = "/hello2")
    @ResponseBody
    public Model hellojsp(@RequestParam(defaultValue = "world") String name, Model m) {
        m.addAttribute("text", "hello " + name);
        return m;
    }

    @RequestMapping(value = "/hello3", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "hello world";
    }

}
