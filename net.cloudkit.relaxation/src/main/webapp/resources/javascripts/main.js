/*
 * Copyright (C) 2015. The CloudKit Open Source Project
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

$(function() {
    $(".nav-link li").hover(function() {
        $(this).children("a").addClass("hover");
        $(this).children(".popmenu").css("display", "block");
        // console.log($(this).children(".popmenu"));
    }, function() {
        $(this).children("a").removeClass("hover");
        $(this).children(".popmenu").css("display", "none");
    });
    $(".search-panel input").focusin(function() {
        $(this).css({"width": "400", "background": "#fff"});
        $(".header a.help-link").hide();
    }).focusout(function() {
        $(this).css({"width": "312", "background": "#91c6f9"});
        $(".header a.help-link").show();
    });

    // slider
    $('#slider').slider();

    // popbox
    $(window).scroll(function () {
        if ($(this).scrollTop() != 0) {
            $('#popbox-backtop').fadeIn();
        }
        else {
            $('#popbox-backtop').fadeOut();
        }
    });
    $('#popbox-backtop').click(function () {
        $('body, html').animate({scrollTop: 0}, 300);
    });
});
