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
(function ($, window, undefined) {
    var Slider = function (element, options) {
        // Defaults are below
        var settings = $.extend({}, $.fn.slider.defaults, options);

        // Useful variables. Play carefully.
        var vars = {
            currentSlide: 0,
            currentImage: '',
            totalSlides: 0,
            running: false,
            paused: false,
            stop: false,
            paginationEl: false
        };

        // Get this slider
        var slider = $(element);
        slider.data('slider:vars', vars).addClass('slider-list');

        // Find our slider children
        var kids = slider.children();
        kids.each(function () {
            var child = $(this);
            var link = '';
            if (!child.is('img')) {
                if (child.is('a')) {
                    child.addClass('image-link');
                    link = child;
                }
                child = child.find('img:first');
            }
            // Get img width & height
            var childWidth = (childWidth === 0) ? child.attr('width') : child.width(),
                childHeight = (childHeight === 0) ? child.attr('height') : child.height();

            if (link !== '') {
                link.css('display', 'none');
            }
            child.css('display', 'none');
            vars.totalSlides++;
        });

        // If randomStart
        if (settings.randomStart) {
            settings.startSlide = Math.floor(Math.random() * vars.totalSlides);
        }

        // Set startSlide
        if (settings.startSlide > 0) {
            if (settings.startSlide >= vars.totalSlides) {
                settings.startSlide = vars.totalSlides - 1;
            }
            vars.currentSlide = settings.startSlide;
        }

        // Get initial image
        if ($(kids[vars.currentSlide]).is('img')) {
            vars.currentImage = $(kids[vars.currentSlide]);
        } else {
            vars.currentImage = $(kids[vars.currentSlide]).find('img:first');
        }

        // Show initial link
        if ($(kids[vars.currentSlide]).is('a')) {
            $(kids[vars.currentSlide]).css('display', 'block');
        }

        // Set first background
        var sliderImg = $('<img/>').addClass('slider-main-image');
        sliderImg.attr('src', vars.currentImage.attr('src')).show();
        slider.append(sliderImg);

        // Detect Window Resize
        $(window).resize(function () {
            slider.children('img').width(slider.width());
            sliderImg.attr('src', vars.currentImage.attr('src'));
            sliderImg.stop().height('auto');
            $('.slider-slice').remove();
            $('.slider-box').remove();
        });

        // Create caption
        slider.append($('<div class="summary"><a class="summary-title"></a><div class="summary-text"></div></div>'));

        // Process caption function
        var processCaption = function (settings) {
            var sliderSummary = $('.summary', slider);
            var sliderSummaryTitle = sliderSummary.children(".summary-title");
            var sliderSummaryText = sliderSummary.children(".summary-text");
            if (vars.currentImage.attr('title') != '' && vars.currentImage.attr('data-title') != undefined) {
                var title = vars.currentImage.attr('data-title');
                var text = vars.currentImage.attr('data-text');
                title = (title && (title.substr(0, 1) == '#')) ? $(title).html() : title;
                text = (text && (text.substr(0, 1) == '#')) ? $(text).html() : text;

                if (sliderSummary.css('display') == 'block') {
                    setTimeout(function () {
                        sliderSummaryTitle.html(title);
                        sliderSummaryText.html(text);
                    }, settings.animSpeed);
                } else {
                    sliderSummaryTitle.html(title);
                    sliderSummaryText.html(text);
                    sliderSummary.stop().fadeIn(settings.animSpeed);
                }
            } else {
                sliderSummary.stop().fadeOut(settings.animSpeed);
            }
        }

        // Process initial  caption
        processCaption(settings);

        // In the words of Super Mario "let's a go!"
        var timer = 0;
        if (!settings.manualAdvance && kids.length > 1) {
            timer = setInterval(function () {
                sliderRun(slider, kids, settings, false);
            }, settings.pauseTime);
        }

        // Add Direction nav
        if (settings.direction) {
            slider.append('<div class="direction"><a class="arrow-prev">' + settings.prevText + '</a><a class="arrow-next">' + settings.nextText + '</a></div>');

            $(slider).on('click', 'a.arrow-prev', function () {
                if (vars.running) {
                    return false;
                }
                clearInterval(timer);
                timer = '';
                vars.currentSlide -= 2;
                sliderRun(slider, kids, settings, 'prev');
            });

            $(slider).on('click', 'a.arrow-next', function () {
                if (vars.running) {
                    return false;
                }
                clearInterval(timer);
                timer = '';
                sliderRun(slider, kids, settings, 'next');
            });
        }

        // Add Control nav
        if (settings.pagination) {
            vars.paginationEl = $('<div class="pagination"></div>');
            slider.after(vars.paginationEl);
            for (var i = 0; i < kids.length; i++) {
                if (settings.paginationThumbs) {
                    vars.paginationEl.addClass('slider-thumbs-enabled');
                    var child = kids.eq(i);
                    if (!child.is('img')) {
                        child = child.find('img:first');
                    }
                    if (child.attr('data-thumb')) vars.paginationEl.append('<a class="indicator" rel="' + i + '"><img src="' + child.attr('data-thumb') + '" alt="" /></a>');
                } else {
                    vars.paginationEl.append('<a class="indicator" rel="' + i + '">' + (i + 1) + '</a>');
                }
            }

            // Set initial active link
            $('a:eq(' + vars.currentSlide + ')', vars.paginationEl).addClass('active');

            $('a', vars.paginationEl).bind('click', function () {
                if (vars.running) return false;
                if ($(this).hasClass('active')) return false;
                clearInterval(timer);
                timer = '';
                sliderImg.attr('src', vars.currentImage.attr('src'));
                vars.currentSlide = $(this).attr('rel') - 1;
                sliderRun(slider, kids, settings, 'control');
            });
        }

        // For pauseOnHover setting
        if (settings.pauseOnHover) {
            slider.hover(function () {
                vars.paused = true;
                clearInterval(timer);
                timer = '';
            }, function () {
                vars.paused = false;
                // Restart the timer
                if (timer === '' && !settings.manualAdvance) {
                    timer = setInterval(function () {
                        sliderRun(slider, kids, settings, false);
                    }, settings.pauseTime);
                }
            });
        }

        // Event when Animation finishes
        slider.bind('slider:animFinished', function () {
            sliderImg.attr('src', vars.currentImage.attr('src'));
            vars.running = false;
            // Hide child links
            $(kids).each(function () {
                if ($(this).is('a')) {
                    $(this).css('display', 'none');
                }
            });
            // Show current link
            if ($(kids[vars.currentSlide]).is('a')) {
                $(kids[vars.currentSlide]).css('display', 'block');
            }
            // Restart the timer
            if (timer === '' && !vars.paused && !settings.manualAdvance) {
                timer = setInterval(function () {
                    sliderRun(slider, kids, settings, false);
                }, settings.pauseTime);
            }
            // Trigger the afterChange callback
            settings.afterChange.call(this);
        });

        // Add slices for slice animations
        var createSlices = function (slider, settings, vars) {
            if ($(vars.currentImage).parent().is('a')) $(vars.currentImage).parent().css('display', 'block');
            $('img[src="' + vars.currentImage.attr('src') + '"]', slider).not('.slider-main-image,.indicator img').width(slider.width()).css('visibility', 'hidden').show();
            var sliceHeight = ($('img[src="' + vars.currentImage.attr('src') + '"]', slider).not('.slider-main-image,.indicator img').parent().is('a')) ? $('img[src="' + vars.currentImage.attr('src') + '"]', slider).not('.slider-main-image,.indicator img').parent().height() : $('img[src="' + vars.currentImage.attr('src') + '"]', slider).not('.slider-main-image,.indicator img').height();

            for (var i = 0; i < settings.slices; i++) {
                var sliceWidth = Math.round(slider.width() / settings.slices);

                if (i === settings.slices - 1) {
                    slider.append(
                        $('<div class="slider-slice" name="' + i + '"><img src="' + vars.currentImage.attr('src') + '" style="position:absolute; width:' + slider.width() + 'px; height:auto; display:block !important; top:0; left:-' + ((sliceWidth + (i * sliceWidth)) - sliceWidth) + 'px;" /></div>').css({
                            left: (sliceWidth * i) + 'px',
                            width: (slider.width() - (sliceWidth * i)) + 'px',
                            height: sliceHeight + 'px',
                            opacity: '0',
                            overflow: 'hidden'
                        })
                    );
                } else {
                    slider.append(
                        $('<div class="slider-slice" name="' + i + '"><img src="' + vars.currentImage.attr('src') + '" style="position:absolute; width:' + slider.width() + 'px; height:auto; display:block !important; top:0; left:-' + ((sliceWidth + (i * sliceWidth)) - sliceWidth) + 'px;" /></div>').css({
                            left: (sliceWidth * i) + 'px',
                            width: sliceWidth + 'px',
                            height: sliceHeight + 'px',
                            opacity: '0',
                            overflow: 'hidden'
                        })
                    );
                }
            }

            $('.slider-slice', slider).height(sliceHeight);
            sliderImg.stop().animate({
                height: $(vars.currentImage).height()
            }, settings.animSpeed);
        };

        // Add boxes for box animations
        var createBoxes = function (slider, settings, vars) {
            if ($(vars.currentImage).parent().is('a')) $(vars.currentImage).parent().css('display', 'block');
            $('img[src="' + vars.currentImage.attr('src') + '"]', slider).not('.slider-main-image, .indicator img').width(slider.width()).css('visibility', 'hidden').show();
            var boxWidth = Math.round(slider.width() / settings.boxCols),
                boxHeight = Math.round($('img[src="' + vars.currentImage.attr('src') + '"]', slider).not('.slider-main-image, .indicator img').height() / settings.boxRows);


            for (var rows = 0; rows < settings.boxRows; rows++) {
                for (var cols = 0; cols < settings.boxCols; cols++) {
                    if (cols === settings.boxCols - 1) {
                        slider.append(
                            $('<div class="slider-box" name="' + cols + '" rel="' + rows + '"><img src="' + vars.currentImage.attr('src') + '" style="position:absolute; width:' + slider.width() + 'px; height:auto; display:block; top:-' + (boxHeight * rows) + 'px; left:-' + (boxWidth * cols) + 'px;" /></div>').css({
                                opacity: 0,
                                left: (boxWidth * cols) + 'px',
                                top: (boxHeight * rows) + 'px',
                                width: (slider.width() - (boxWidth * cols)) + 'px'

                            })
                        );
                        $('.slider-box[name="' + cols + '"]', slider).height($('.slider-box[name="' + cols + '"] img', slider).height() + 'px');
                    } else {
                        slider.append(
                            $('<div class="slider-box" name="' + cols + '" rel="' + rows + '"><img src="' + vars.currentImage.attr('src') + '" style="position:absolute; width:' + slider.width() + 'px; height:auto; display:block; top:-' + (boxHeight * rows) + 'px; left:-' + (boxWidth * cols) + 'px;" /></div>').css({
                                opacity: 0,
                                left: (boxWidth * cols) + 'px',
                                top: (boxHeight * rows) + 'px',
                                width: boxWidth + 'px'
                            })
                        );
                        $('.slider-box[name="' + cols + '"]', slider).height($('.slider-box[name="' + cols + '"] img', slider).height() + 'px');
                    }
                }
            }

            sliderImg.stop().animate({
                height: $(vars.currentImage).height()
            }, settings.animSpeed);
        };

        // Private run method
        var sliderRun = function (slider, kids, settings, nudge) {
            // Get our vars
            var vars = slider.data('slider:vars');

            // Trigger the lastSlide callback
            if (vars && (vars.currentSlide === vars.totalSlides - 1)) {
                settings.lastSlide.call(this);
            }

            // Stop
            if ((!vars || vars.stop) && !nudge) {
                return false;
            }

            // Trigger the beforeChange callback
            settings.beforeChange.call(this);

            // Set current background before change
            if (!nudge) {
                sliderImg.attr('src', vars.currentImage.attr('src'));
            } else {
                if (nudge === 'prev') {
                    sliderImg.attr('src', vars.currentImage.attr('src'));
                }
                if (nudge === 'next') {
                    sliderImg.attr('src', vars.currentImage.attr('src'));
                }
            }

            vars.currentSlide++;
            // Trigger the slideshowEnd callback
            if (vars.currentSlide === vars.totalSlides) {
                vars.currentSlide = 0;
                settings.slideshowEnd.call(this);
            }
            if (vars.currentSlide < 0) {
                vars.currentSlide = (vars.totalSlides - 1);
            }
            // Set vars.currentImage
            if ($(kids[vars.currentSlide]).is('img')) {
                vars.currentImage = $(kids[vars.currentSlide]);
            } else {
                vars.currentImage = $(kids[vars.currentSlide]).find('img:first');
            }

            // Set active links
            if (settings.pagination) {
                $('a', vars.paginationEl).removeClass('active');
                $('a:eq(' + vars.currentSlide + ')', vars.paginationEl).addClass('active');
            }

            // Process caption
            processCaption(settings);

            // Remove any slices from last transition
            $('.slider-slice', slider).remove();

            // Remove any boxes from last transition
            $('.slider-box', slider).remove();

            var currentEffect = settings.effect,
                anims = '';

            // Generate random effect
            if (settings.effect === 'random') {
                anims = new Array('sliceDownRight', 'sliceDownLeft', 'sliceUpRight', 'sliceUpLeft', 'sliceUpDown', 'sliceUpDownLeft', 'fold', 'fade', 'boxRandom', 'boxRain', 'boxRainReverse', 'boxRainGrow', 'boxRainGrowReverse');
                currentEffect = anims[Math.floor(Math.random() * (anims.length + 1))];
                if (currentEffect === undefined) {
                    currentEffect = 'fade';
                }
            }

            // Run random effect from specified set (eg: effect:'fold,fade')
            if (settings.effect.indexOf(',') !== -1) {
                anims = settings.effect.split(',');
                currentEffect = anims[Math.floor(Math.random() * (anims.length))];
                if (currentEffect === undefined) {
                    currentEffect = 'fade';
                }
            }

            // Custom transition as defined by "data-transition" attribute
            if (vars.currentImage.attr('data-transition')) {
                currentEffect = vars.currentImage.attr('data-transition');
            }

            // Run effects
            vars.running = true;
            var timeBuff = 0,
                i = 0,
                slices = '',
                firstSlice = '',
                totalBoxes = '',
                boxes = '';

            if (currentEffect === 'sliceDown' || currentEffect === 'sliceDownRight' || currentEffect === 'sliceDownLeft') {
                createSlices(slider, settings, vars);
                timeBuff = 0;
                i = 0;
                slices = $('.slider-slice', slider);
                if (currentEffect === 'sliceDownLeft') {
                    slices = $('.slider-slice', slider)._reverse();
                }

                slices.each(function () {
                    var slice = $(this);
                    slice.css({'top': '0px'});
                    if (i === settings.slices - 1) {
                        setTimeout(function () {
                            slice.animate({opacity: '1.0'}, settings.animSpeed, '', function () {
                                slider.trigger('slider:animFinished');
                            });
                        }, (100 + timeBuff));
                    } else {
                        setTimeout(function () {
                            slice.animate({opacity: '1.0'}, settings.animSpeed);
                        }, (100 + timeBuff));
                    }
                    timeBuff += 50;
                    i++;
                });
            } else if (currentEffect === 'sliceUp' || currentEffect === 'sliceUpRight' || currentEffect === 'sliceUpLeft') {
                createSlices(slider, settings, vars);
                timeBuff = 0;
                i = 0;
                slices = $('.slider-slice', slider);
                if (currentEffect === 'sliceUpLeft') {
                    slices = $('.slider-slice', slider)._reverse();
                }

                slices.each(function () {
                    var slice = $(this);
                    slice.css({'bottom': '0px'});
                    if (i === settings.slices - 1) {
                        setTimeout(function () {
                            slice.animate({opacity: '1.0'}, settings.animSpeed, '', function () {
                                slider.trigger('slider:animFinished');
                            });
                        }, (100 + timeBuff));
                    } else {
                        setTimeout(function () {
                            slice.animate({opacity: '1.0'}, settings.animSpeed);
                        }, (100 + timeBuff));
                    }
                    timeBuff += 50;
                    i++;
                });
            } else if (currentEffect === 'sliceUpDown' || currentEffect === 'sliceUpDownRight' || currentEffect === 'sliceUpDownLeft') {
                createSlices(slider, settings, vars);
                timeBuff = 0;
                i = 0;
                var v = 0;
                slices = $('.slider-slice', slider);
                if (currentEffect === 'sliceUpDownLeft') {
                    slices = $('.slider-slice', slider)._reverse();
                }

                slices.each(function () {
                    var slice = $(this);
                    if (i === 0) {
                        slice.css('top', '0px');
                        i++;
                    } else {
                        slice.css('bottom', '0px');
                        i = 0;
                    }

                    if (v === settings.slices - 1) {
                        setTimeout(function () {
                            slice.animate({opacity: '1.0'}, settings.animSpeed, '', function () {
                                slider.trigger('slider:animFinished');
                            });
                        }, (100 + timeBuff));
                    } else {
                        setTimeout(function () {
                            slice.animate({opacity: '1.0'}, settings.animSpeed);
                        }, (100 + timeBuff));
                    }
                    timeBuff += 50;
                    v++;
                });
            } else if (currentEffect === 'fold') {
                createSlices(slider, settings, vars);
                timeBuff = 0;
                i = 0;

                $('.slider-slice', slider).each(function () {
                    var slice = $(this);
                    var origWidth = slice.width();
                    slice.css({top: '0px', width: '0px'});
                    if (i === settings.slices - 1) {
                        setTimeout(function () {
                            slice.animate({width: origWidth, opacity: '1.0'}, settings.animSpeed, '', function () {
                                slider.trigger('slider:animFinished');
                            });
                        }, (100 + timeBuff));
                    } else {
                        setTimeout(function () {
                            slice.animate({width: origWidth, opacity: '1.0'}, settings.animSpeed);
                        }, (100 + timeBuff));
                    }
                    timeBuff += 50;
                    i++;
                });
            } else if (currentEffect === 'fade') {
                createSlices(slider, settings, vars);

                firstSlice = $('.slider-slice:first', slider);
                firstSlice.css({
                    'width': slider.width() + 'px'
                });

                firstSlice.animate({opacity: '1.0'}, (settings.animSpeed * 2), '', function () {
                    slider.trigger('slider:animFinished');
                });
            } else if (currentEffect === 'slideInRight') {
                createSlices(slider, settings, vars);

                firstSlice = $('.slider-slice:first', slider);
                firstSlice.css({
                    'width': '0px',
                    'opacity': '1'
                });

                firstSlice.animate({width: slider.width() + 'px'}, (settings.animSpeed * 2), '', function () {
                    slider.trigger('slider:animFinished');
                });
            } else if (currentEffect === 'slideInLeft') {
                createSlices(slider, settings, vars);

                firstSlice = $('.slider-slice:first', slider);
                firstSlice.css({
                    'width': '0px',
                    'opacity': '1',
                    'left': '',
                    'right': '0px'
                });

                firstSlice.animate({width: slider.width() + 'px'}, (settings.animSpeed * 2), '', function () {
                    // Reset positioning
                    firstSlice.css({
                        'left': '0px',
                        'right': ''
                    });
                    slider.trigger('slider:animFinished');
                });
            } else if (currentEffect === 'boxRandom') {
                createBoxes(slider, settings, vars);

                totalBoxes = settings.boxCols * settings.boxRows;
                i = 0;
                timeBuff = 0;

                boxes = shuffle($('.slider-box', slider));
                boxes.each(function () {
                    var box = $(this);
                    if (i === totalBoxes - 1) {
                        setTimeout(function () {
                            box.animate({opacity: '1'}, settings.animSpeed, '', function () {
                                slider.trigger('slider:animFinished');
                            });
                        }, (100 + timeBuff));
                    } else {
                        setTimeout(function () {
                            box.animate({opacity: '1'}, settings.animSpeed);
                        }, (100 + timeBuff));
                    }
                    timeBuff += 20;
                    i++;
                });
            } else if (currentEffect === 'boxRain' || currentEffect === 'boxRainReverse' || currentEffect === 'boxRainGrow' || currentEffect === 'boxRainGrowReverse') {
                createBoxes(slider, settings, vars);

                totalBoxes = settings.boxCols * settings.boxRows;
                i = 0;
                timeBuff = 0;

                // Split boxes into 2D array
                var rowIndex = 0;
                var colIndex = 0;
                var box2Darr = [];
                box2Darr[rowIndex] = [];
                boxes = $('.slider-box', slider);
                if (currentEffect === 'boxRainReverse' || currentEffect === 'boxRainGrowReverse') {
                    boxes = $('.slider-box', slider)._reverse();
                }
                boxes.each(function () {
                    box2Darr[rowIndex][colIndex] = $(this);
                    colIndex++;
                    if (colIndex === settings.boxCols) {
                        rowIndex++;
                        colIndex = 0;
                        box2Darr[rowIndex] = [];
                    }
                });

                // Run animation
                for (var cols = 0; cols < (settings.boxCols * 2); cols++) {
                    var prevCol = cols;
                    for (var rows = 0; rows < settings.boxRows; rows++) {
                        if (prevCol >= 0 && prevCol < settings.boxCols) {
                            /* Due to some weird JS bug with loop vars
                             being used in setTimeout, this is wrapped
                             with an anonymous function call */
                            (function (row, col, time, i, totalBoxes) {
                                var box = $(box2Darr[row][col]);
                                var w = box.width();
                                var h = box.height();
                                if (currentEffect === 'boxRainGrow' || currentEffect === 'boxRainGrowReverse') {
                                    box.width(0).height(0);
                                }
                                if (i === totalBoxes - 1) {
                                    setTimeout(function () {
                                        box.animate({
                                            opacity: '1',
                                            width: w,
                                            height: h
                                        }, settings.animSpeed / 1.3, '', function () {
                                            slider.trigger('slider:animFinished');
                                        });
                                    }, (100 + time));
                                } else {
                                    setTimeout(function () {
                                        box.animate({opacity: '1', width: w, height: h}, settings.animSpeed / 1.3);
                                    }, (100 + time));
                                }
                            })(rows, prevCol, timeBuff, i, totalBoxes);
                            i++;
                        }
                        prevCol--;
                    }
                    timeBuff += 100;
                }
            }
        };

        // Shuffle an array
        var shuffle = function (arr) {
            for (var j, x, i = arr.length; i; j = parseInt(Math.random() * i, 10), x = arr[--i], arr[i] = arr[j], arr[j] = x);
            return arr;
        };

        // For debugging
        var trace = function (msg) {
            if (this.console && typeof console.log !== 'undefined') {
                console.log(msg);
            }
        };

        // Start / Stop
        this.stop = function () {
            if (!$(element).data('slider:vars').stop) {
                $(element).data('slider:vars').stop = true;
                trace('Stop Slider');
            }
        };

        this.start = function () {
            if ($(element).data('slider:vars').stop) {
                $(element).data('slider:vars').stop = false;
                trace('Start Slider');
            }
        };

        // Trigger the afterLoad callback
        settings.afterLoad.call(this);

        return this;
    };

    $.fn.slider = function (options) {
        return this.each(function (key, value) {
            var element = $(this);
            // Return early if this element already has a plugin instance
            if (element.data('slider')) {
                return element.data('slider');
            }
            // Pass options to plugin constructor
            var slider = new Slider(this, options);
            // Store plugin object in this element's data
            element.data('slider', slider);
        });
    };

    //Default settings
    $.fn.slider.defaults = {
        effect: 'random',
        slices: 15,
        boxCols: 8,
        boxRows: 4,
        animSpeed: 500,
        pauseTime: 3000,
        startSlide: 0,
        direction: true,
        pagination: true,
        paginationThumbs: false,
        pauseOnHover: true,
        manualAdvance: false,
        prevText: 'Prev',
        nextText: 'Next',
        randomStart: false,

        beforeChange: function () {
            //
        },
        afterChange: function () {
            //
        },
        slideshowEnd: function () {
            //
        },
        lastSlide: function () {
            //
        },
        afterLoad: function () {
            //
        }
    };

    $.fn._reverse = [].reverse;

})(jQuery, window);
