(function(a) {
    a.jBox = function(b, c) {
        c = a.extend({},
        a.jBox.defaults, c);
        c.showFade = c.opacity > 0x0;
//		c.showFade = false;
		c.opacity = 0.7;
        c.isTip = c.isTip || false;
        c.isG = c.isG || false;
        c.isMessager = c.isMessager || false;
        if (b == undefined) {
            b = ''
        };

        if (c.border < 0x0) {
            c.border = 0x0
        };
        if (c.id == undefined) {
            c.id = 'jBox_' + Math.floor(Math.random() * 0xf4240)
        };
		if(isIeBrowser || $("html").hasClass("ie11")) setIframeInOffice(c.id,'jbox');
		if(!c.isTip){closeIframeMenu();};
        var d = false;
        var e = a('#' + c.id);
        if (e.length > 0x0) {
            c.zIndex = a.jBox.defaults.zIndex++;
            e.css({
                zIndex: c.zIndex
            });
            e.find('#jbox').css({
                zIndex: c.zIndex + 0x1
            });
            return e
        };
        var f = {
            url: '',
            type: '',
            html: '',
            isObject: b.constructor == Object
        };
        if (!f.isObject) {
            b = b + '';
            var N = b.toLowerCase();
            if (N.indexOf('id:') == 0x0) f.type = 'ID';
            else if (N.indexOf('get:') == 0x0) f.type = 'GET';
            else if (N.indexOf('post:') == 0x0) f.type = 'POST';
            else if (N.indexOf('iframe:') == 0x0) f.type = 'IFRAME';
            else if (N.indexOf('html:') == 0x0) f.type = 'HTML';
            else {
                b = 'html:' + b;
                f.type = 'HTML'
            };
            b = b.substring(b.indexOf(":") + 0x1, b.length)
        };
        if (!c.isTip && !c.isMessager && !c.showScrolling) {
			var browserM = /msie/.test(navigator.userAgent.toLowerCase());
            a(browserM ? 'html': 'body').attr('style', 'overflow:hidden;padding-right:17px;')
        };
        var g = !c.isTip && !(c.title == undefined);
        var h = f.type == 'GET' || f.type == 'POST' || f.type == 'IFRAME';
        var i = typeof c.width == 'number' ? (c.width - 0x32) + 'px': "90%";
        var j = [];
        j.push('<div id="' + c.id + '" class="jbox-' + (c.isTip ? 'tip': (c.isMessager ? 'messager': 'body')) + '">');
        if (c.showFade) {
            j.push('<div id="jbox-fade" class="jbox-fade" style="position:absolute;background-color:#505050;"></div>')
        };
        j.push('<div id="jbox-temp" class="jbox-temp" style="width:0px;height:0px;background-color:#ff3300;position:absolute;z-index:1005;fdisplay:none;"></div>');
        if (c.draggable) {
            j.push('<div id="jbox-drag" class="jbox-drag" style="position:absolute;z-index:1005;display:none;"></div>')
        };

        j.push('<div id="jbox" class="jbox" style="position:absolute;width:auto;height:auto;background-color:#fff;border-radius: 12px;border:5px solid #5696cb;'+(c.isTip?'background-color:#fff;':'')+'">');
        j.push('<div class="jbox-help-title jbox-title-panel" style="height:30px;display:none;"></div>');
 //       j.push('<div class="jbox-help-button jbox-button-panel" style="height:25px;padding:5px 0 5px 0;display:none;"></div>');
        j.push('<div class="jbox-con-wrap" style="margin:0px;padding:0px;border:none;'+(c.icon == 'error'?'background-colorrgb(255, 249, 249)':'')+'">');
		
        j.push('<div class="jbox-container" style="width:auto; height:auto;">');
		
        j.push('<a class="fa jbox-close" title="' + a.jBox.languageDefaults.close + '" onmouseover="$(this).addClass(\'jbox-close-hover\');" onmouseout="$(this).removeClass(\'jbox-close-hover\');" style="position:absolute; display:block; color:#8d8d8d;cursor:pointer; top:' + (0x2 + c.border-(c.isTip?1:5)) + 'px; right:' + (0x2 + (c.isTip?c.border+5:c.border)) + 'px;font-size:20px; width:15px; height:15px;' + (c.showClose ? '': 'display:none;') + '" id="jbox-close-focus">×</a>');
        if (g) {
            j.push('<div class="jbox-title-panel" style="height:20px;">');
//            j.push('<div class="jbox-title' + (c.showIcon == true ? ' jbox-title-icon': (c.showIcon == false ? '': ' ' + c.showIcon)) + '" style="float:left; width:' + i + '; line-height:' + 0x18 + 'px;overflow:hidden;text-overflow:ellipsis;word-break:break-all;">' + (c.title == '' ? '&nbsp;': '') + '</div>');
            j.push('</div>')
        };
        j.push('<div id="jbox-states"></div>');
        j.push('</div></div></div></div>');
        var k = '<iframe name="jbox-iframe" id="jbox-iframe" width="100%" height="100%" marginheight="0" marginwidth="0" frameborder="0" scrolling="' + c.iframeScrolling + '"></iframe>';
        var l = a(window);
        var m = a(document.body);
        var n = a(j.join('')).appendTo(m);
        var o = n.children('#jbox');
        var p = n.children('#jbox-fade');
        var q = n.children('#jbox-temp');
        if (!f.isObject) {
            switch (f.type) {
            case "ID":
                f.html = a('#' + b).html();
                break;
            case "GET":
            case "POST":
                f.html = '';
                f.url = b;
                break;
            case "HTML":
                f.html = b;
                break;
            case "IFRAME":
                f.html = k;
                if (b.indexOf('#') == -0x1) {
                    f.url = b + (b.indexOf('?') == -0x1 ? '?___t': '&___t') + Math.random()
                } else {
                    var N = b.split('#');
                    f.url = N[0x0] + (N[0x0].indexOf('?') == -0x1 ? '?___t': '&___t') + Math.random() + '#' + N[0x1]
                };
                break;
            };
            b = {
                state0: {
                    content: f.html,
                    buttons: c.buttons,
                    buttonsFocus: c.buttonsFocus,
                    submit: c.submit
                }
            }
        };
        var r = [];
        var s = o.find('.jbox-help-title').outerHeight(true);
        var t = o.find('.jbox-help-button').outerHeight(true);
        var u =  'padding:0px 10px 0px 10px;';
        a.each(b,
        function(N, O) {
            if (f.isObject) {
                O = a.extend({},
                a.jBox.stateDefaults, O)
            };
            b[N] = O;
            if (O.buttons == undefined) {
                O.buttons = {}
            };
            var P = false;
            a.each(O.buttons,
            function(T, U) {
                P = true
            });
            var Q = 'auto';
            if (typeof c.height == 'number') {
                Q = c.height;
                if (g) {
                    Q = Q - s
                };
                if (P) {
                    Q = Q - t
                };
                Q = (Q - 0x1) + 'px'
            };
            var R = '';
            var S = '25px';
            if (!f.isObject && h) {
                var T = c.height;
                if (typeof c.height == 'number') {
                    if (g) {
                        T = T - s
                    };
                    if (P) {
                        T = T - t
                    };
                    S = ((T / 0x5) * 0x2) + 'px';
                    T = (T - 0x1) + 'px'
                };
                R = ['<div id="jbox-content-loading" class="jbox-content-loading" style="min-height:70px;height:' + T + '; text-align:center;">', '<div class="jbox-content-loading-image" style="display:block; margin:auto; width:220px; height:19px; padding-top: ' + S + ';"></div>', '</div>'].join('')
				
            };
            r.push('<div id="jbox-state-' + N + '" class="jbox-state" style="display:none;">');
            r.push('<div style="min-width:50px;height:' + Q + ';">' + R + '<div id="jbox-content" class="jbox-content" style="height:' + Q + ';max-height:270px;">' + O.content + '</div>');
			
			if(c.icon == 'success'){
				r.push('<div style="margin-top:10px;margin-left:20px;font-size:16px;">共<b>12</b>人<span style="margin-left:20px;color:#5696cb;cursor:pointer;" id="alert-content"><i class="fa fa-caret-right" style="margin-right:5px;"></i>查看全部</span></div>');
			}
			r.push('</div>');
			r.push('<div class="jbox-button-panel form-btn" style="height:auto;'+(c.icon=='success'?'padding:5px 0 10px 0;':c.icon=='info' || c.icon=='error'?'margin-bottom:15px;':'padding-right:10px;margin-bottom:10px;')+'text-align: right;' + (P ? '': 'display:none;') + '">');
			
            if (!c.isTip && c.icon === 'warning') {
                r.push('<button class="jbox-bottom-text btn"  type="button" ondragstart="return false" style="min-width:50px;margin-right:0;margin-left:10px;"></button><button class="jbox-bottom-text1 btn" type="button" style="min-width:50px;margin-right:0;margin-left:10px;"></button><button class="jbox-bottom-text2 btn" type="button" style="min-width:50px;margin-right:0;margin-left:10px;"></button>')

            };
			
			if(c.icon == 'success' || c.icon == 'error' || c.icon == 'info'){
				a.each(O.buttons,
				function(T, U) {
					r.push('<span value="' + U + '" ondragstart="return false" style="margin: 0px 10px 20px 0px;"><a href="#" ondragstart="return false" id="'+c.id+'_find" class="btn jbox-close" style="height:30px;min-width:60px;line-height:20px;">确定</a></span>')
				});
			}
            r.push('</div></div>')
        });
        o.find('#jbox-states').html(r.join('')).children('.jbox-state:first').css('display', 'block');
        if (h) {
            var N = o.find('#jbox-content').css({
                position: (d) ? "absolute": "fixed",
                left: -0x2710
            })
        };
		/*戈志刚添加 显示收起功能*/
		a(document).on('click','#alert-content',function(){
			a('#alert-conent-gang').css({"max-height":"200px","height":"auto","overflow-y":"auto"})
			a(this).hide();
		});
        a.each(b,
        function(N, O) {
            var P = o.find('#jbox-state-' + N);
            P.children('.jbox-button-panel').children('button').click(function() {
                var Q = P.find('#jbox-content');
                var R = O.buttons[a(this).text()];
                var S = {};
                a.each(o.find('#jbox-states :input').serializeArray(),
                function(U, V) {
                    if (S[V.name] === undefined) {
                        S[V.name] = V.value
                    } else if (typeof S[V.name] == Array) {
                        S[V.name].push(V.value)
                    } else {
                        S[V.name] = [S[V.name], V.value]
                    }
                });
                var T = O.submit(R, Q, S);
			
                if (T === undefined || T) {
					//点击是按钮不关闭窗口，点击否则关闭窗口if(!a(this).hasClass('jbox-bottom-text'))
					I()
                }
            }).bind('mousedown',
            function() {
                a(this).addClass('jbox-button-active')
            }).bind('mouseup',
            function() {
                a(this).removeClass('jbox-button-active')
            }).bind('mouseover',
            function() {
                a(this).addClass('jbox-button-hover')
            }).bind('mouseout',
            function() {
                a(this).removeClass('jbox-button-active').removeClass('jbox-button-hover')
            });
            
        });
        var v = function() {
            n.css({
                top: l.scrollTop()
            });
            if (c.isMessager) {
                o.css({
                    position: (d) ? "absolute": "fixed",
                    right: 0x1,
                    bottom: 0x1
                })
            }
        };
        var w = function() {
            var N = l.width();
            return document.body.clientWidth < N ? N: document.body.clientWidth
        };
        var x = function() {
            var N = l.height();
            return document.body.clientHeight < N ? N: document.body.clientHeight
        };
        var y = function() {
            if (!c.showFade) {
                return
            };
            if (c.persistent) {
                var N = 0x0;
                n.addClass('jbox-warning');
                var O = setInterval(function() {
                    n.toggleClass('jbox-warning');
                    if (N++>0x1) {
                        clearInterval(O);
                        n.removeClass('jbox-warning')
                    }
                },
                0x64)
            } else {
                I()
            }
        };
        var z = function(N) {
            if (c.isTip || c.isMessager) {
                return false
            };
            var O = (window.event) ? event.keyCode: N.keyCode;
            if (O == 0x1b) {
                I()
            };
            if (O == 0x9) {
                var P = a(':input:enabled:visible', n);
                var Q = !N.shiftKey && N.target == P[P.length - 0x1];
                var R = N.shiftKey && N.target == P[0x0];
                if (Q || R) {
                    setTimeout(function() {
                        if (!P) return;
                        var S = P[R === true ? P.length - 0x1: 0x0];
                        if (S) S.focus()
                    },
                    0xa);
                    return false
                }
            }
        };
        var A = function() {
            if (c.showFade) {
                p.css({
                    position: "absolute",
                    height: c.isTip ? x() : l.height(),
                    width: d ? l.width() : "100%",
                    top: 0x0,
                    left: 0x0,
                    right: 0x0,
                    bottom: 0x0
                })
            }
        };
        var B = function() {
            if (c.isMessager) {
                o.css({
                    position: (d) ? "absolute": "fixed",
                    right: 0x1,
                    bottom: 0x1
                })
            } else {
                q.css({
                    top: c.top
                });
				/* o就是弹出信息   -- 居中*/
                o.css({
                    position: "absolute",
                    top: ((l.height() - o.outerHeight()) / 2.5),
                    left: ((l.width() - o.outerWidth()) / 0x2)
                })
            };
            if ((c.showFade && !c.isTip) || (!c.showFade && !c.isTip && !c.isMessager)) {
                n.css({
                    position: (d) ? "absolute": "fixed",
                    height: c.showFade ? l.height() : 0x0,
                    width: "100%",
                    top: (d) ? l.scrollTop() : 0x0,
                    left: 0x0,
                    right: 0x0,
                    bottom: 0x0
                })
            };
            A()
        };
        var C = function() {
            c.zIndex = a.jBox.defaults.zIndex++;
            n.css({
                zIndex: c.zIndex
            });
            o.css({
                zIndex: c.zIndex + 0x1
            })
        };
        var D = function() {
            c.zIndex = a.jBox.defaults.zIndex++;
            n.css({
                zIndex: c.zIndex
            });
            o.css({
                display: "none",
                zIndex: c.zIndex + 0x1
            });
            if (c.showFade) {
                p.css({
                    display: "none",
                    zIndex: c.zIndex,
                    opacity: c.opacity
                })
            }
        };
        var E = function(N) {
            var O = N.data;
            O.target.find('iframe').hide();
            if (c.dragClone) {
                O.target.prev().css({
                    left: O.target.css('left'),
                    top: O.target.css('top'),
                    marginLeft: -0x2,
                    marginTop: -0x2,
                    width: O.target.width() + 0x2,
                    height: O.target.height() + 0x2
                }).show()
            };
            return false
        };
        var F = function(N) {
            var O = N.data;
            var P = O.startLeft + N.pageX - O.startX;
            var Q = O.startTop + N.pageY - O.startY;
            if (c.dragLimit) {
                var R = 0x1;
                var S = document.documentElement.clientHeight - N.data.target.height() - 0x1;
                var T = 0x1;
                var U = document.documentElement.clientWidth - N.data.target.width() - 0x1;
                if (Q < R) Q = R + (c.dragClone ? 0x2: 0x0);
                if (Q > S) Q = S - (c.dragClone ? 0x2: 0x0);
                if (P < T) P = T + (c.dragClone ? 0x2: 0x0);
                if (P > U) P = U - (c.dragClone ? 0x2: 0x0)
            };
			
            if (c.dragClone) {
                O.target.prev().css({
                    left: P,
                    top: Q
                })
            } else {
                O.target.css({
                    left: P,
                    top: Q
                })
            };
            return false
        };
        var G = function(N) {
            a(document).unbind('.draggable');
            if (c.dragClone) {
                var O = N.data.target.prev().hide();
                N.data.target.css({
                    left: O.css('left'),
                    top: O.css('top')
                }).find('iframe').show()
            } else {
                N.data.target.find('iframe').show()
            };
            return false
        };
        var H = function(N) {
            var O = N.data.target.position();
            var P = {
                target: N.data.target,
                startX: N.pageX,
                startY: N.pageY,
                startLeft: O.left,
                startTop: O.top
            };
            a(document).bind('mousedown.draggable', P, E).bind('mousemove.draggable', P, F).bind('mouseup.draggable', P, G)
        };
        var I = function() {
            if (!c.isTip && !c.isMessager) {
                if (a('.jbox-body').length == 0x1) {
                    a('body').removeAttr('style')
                };
                J()
            } else {
                if (c.isTip) {
                    var tip = a(document.body).data('tip');
                    if (tip && tip.next == true) {
                        q.css('top', tip.options.top);
                        var N = q.offset().top + l.scrollTop();
                        if (N == o.offset().top) {
                            J()
                        } else {
                            o.find('#jbox-content').html(tip.options.content.substr(0x5)).end().css({
                                left: ((l.width() - o.outerWidth()) / 0x2)
                            }).animate({
                                top: N,
                                opacity: 0.1
                            },
                            0x1f4, J)
                        }
                    } else {
                        o.animate({
                            top: '-=200',
                            opacity: 0x0
                        },
                        0x1f4, J)
                    }
                } else {
                    switch (c.showType) {
                    case 'slide':
                        o.slideUp(c.showSpeed, J);
                        break;
                    case 'fade':
                        o.fadeOut(c.showSpeed, J);
                        break;
                    case 'show':
                    default:
                        o.hide(c.showSpeed, J);
                        break
                    }
                }
            }
        };
        var J = function() {
            l.unbind('resize', A);
            if (c.draggable && !c.isTip && !c.isMessager) {
                o.find('.jbox-title-panel').unbind('mousedown', H)
            };
            if (f.type != 'IFRAME') {
                o.find('#jbox-iframe').attr({
                    'src': 'about:blank'
                })
            };
            o.html('').remove();
            if (d && !c.isTip) {
                m.unbind('scroll', v)
            };
            if (c.showFade) {
                p.fadeOut('fast',
                function() {
                    p.unbind('click', y).unbind('mousedown', C).html('').remove()
                })
            };
            n.unbind('keydown keypress', z).html('').remove();
            if (d && c.showFade) {
                a('select').css('visibility', 'visible')
            };
            if (typeof c.closed == 'function') {
                c.closed();
                if(!c.isTip){
                	openIframeMenu();
                	var  oth  = document.getElementById('topnav-other_modules') || window.parent.document.getElementById('topnav-other_modules');
                	setTimeout(function(){
                		if(!$(".modal").hasClass("in") && !$(".jbox-body").length)$(oth).show().other();
                	},300);
                };
                if(isIeBrowser || $("html").hasClass("ie11")) removeIframeInOffice(c.id);
            }
        };
        var K = function() {
            if (c.timeout > 0x0) {
                o.data('autoClosing', window.setTimeout(I, c.timeout));
                if (c.isMessager) {
                    o.hover(function() {
                        window.clearTimeout(o.data('autoClosing'))
                    },
                    function() {
                        o.data('autoClosing', window.setTimeout(I, c.timeout))
                    })
                }
            }
        };
        var L = function() {
            if (typeof c.loaded == 'function') {
                c.loaded(o.find('.jbox-state:visible').find('.jbox-content'))
            }
        };
        if (!f.isObject) {
            switch (f.type) {
            case "GET":
            case "POST":
                a.ajax({
                    type:
                    f.type,
                    url: f.url,
                    data: c.ajaxData == undefined ? {}: c.ajaxData,
                    dataType: 'html',
                    cache: false,
                    success: function(N, O) {
                        o.find('#jbox-content').css({
                            position: "static"
                        }).html(N).show().prev().hide();
                        L()
                    },
                    error: function() {
                        o.find('#jbox-content-loading').html('<div style="padding-top:50px;padding-bottom:50px;text-align:center;">Loading Error.</div>')
                    }
                });
                break;
            case "IFRAME":
                o.find('#jbox-iframe').attr({
                    'src':
                    f.url
                }).bind("load",
                function(N) {
                    a(this).parent().css({
                        position: "static"
                    }).show().prev().hide();
                    o.find('#jbox-states .jbox-state:first .jbox-button-focus').focus();
                    L()
                });
                break;
            default:
                o.find('#jbox-content').show();
                break
            }
        };
        B();
        D();
        if (d && !c.isTip) {
            l.scroll(v)
        };
        if (c.showFade) {
            p.click(y)
        };
        l.resize(A);
        n.bind('keydown keypress', z);
        o.find('.jbox-close').click(I);
        if (c.showFade) {
            p.fadeIn('fast')
        };
        var M = 'show';
        if (c.showType == 'slide') {
            M = 'slideDown'
        } else if (c.showType == 'fade') {
            M = 'fadeIn'
        };
        if (c.isMessager) {
            o[M](c.showSpeed, K)
        } else {
            var tip = a(document.body).data('tip');
            if (tip && tip.next == true) {
                a(document.body).data('tip', {
                    next: false,
                    options: {}
                });
                o.css('display', '')
            } else {
                if (!f.isObject && h) {
                    o[M](c.showSpeed)
                } else {
                    o[M](c.showSpeed, L);
                }
            }
        };
        if (!c.isTip) {
        	var btnName = {};
        	for(var i in c.buttons){
        		if(c.buttons[i] == "yes"){
        			btnName.yes = i;
        		}else if(c.buttons[i] == "no"){
        			btnName.no = i;
        		}else if(c.buttons[i] == "cancel"){
        			btnName.cancel = i;
        		}
    		}
        	if(btnName == null || btnName == undefined){
        		btnName.yes = "是";
        		btnName.cancel = "取消";
        		btnName.no = "否";
        	}
        	if(!btnName.cancel){
        		o.find('.jbox-bottom-text1').hide();
        	}
            o.find('.jbox-bottom-text').html(btnName.yes);
            o.find('.jbox-bottom-text1').html(btnName.cancel);
			o.find('.jbox-bottom-text2').html(btnName.no);
        } else {
            o.find('.jbox-container').addClass('jbox-tip-color jbox-tip-box')
        };
        a('#'+c.id+'_find').focus();
		o.find('.jbox-bottom-text2').focus();
		if(a('.modal').hasClass('in')){
			document.onkeydown = function(event_e){
				if( window.event )event_e = window.event;
				var int_keycode = event_e.charCode||event_e.keyCode;
				//去掉回车绑定关闭事件
//				if(int_keycode ==13){I();}
			}
		}

        c.isG&&o.find('.jbox-container').addClass('jbox-g-color');
        
        if (f.type != 'IFRAME') {
            o.find('#jbox-states .jbox-state:first .jbox-button-focus').focus()
        } else {
            o.focus()
        };
        if (!c.isMessager) {
            K()
        };
        n.bind('mousedown', C);
        if (c.draggable && !c.isTip && !c.isMessager) {
            o.find('.jbox-title-panel').bind('mousedown', {
                target: o
            },
            H).css('cursor', 'move')
        };
        return n
    };
    a.jBox.version = 2.3;
    a.jBox.defaults = {
        id: null,
        top: "12%",
        zIndex: '8888',
        border: 0x5,
        opacity: 0.3,
        timeout: 0x0,
        showType: 'fade',
        showSpeed: 'fast',
        showIcon: true,
        showClose: true,
        draggable: false,
        dragLimit: true,
        dragClone: false,
        persistent: true,
        showScrolling: true,
        ajaxData: {},
        iframeScrolling: 'auto',
        title: '',
        width: 0x15e,
        height: 'auto',
        bottomText: '',
        buttons: {},
        buttonsFocus: 0x0,
        loaded: function(b) {},
        submit: function(b, c, d) {
            return true
        },
        closed: function(b) {}
    };
    a.jBox.stateDefaults = {
        content: '',
        buttons: {
            '确定': 'ok'
        },
        buttonsFocus: 0x0,
        submit: function(b, c, d) {
            return true
        }
    };
	//timeout: 0xbb8,关闭
    a.jBox.tipDefaults = {
        content: '',
        icon: 'info',
        top: '40%',
        width: 'auto',
        height: 'auto',
        opacity: 0x0,
        timeout: 1000,
        closed: function() {}
    };
    a.jBox.messagerDefaults = {
        content: '',
        title: 'jBox',
        icon: 'none',
        width: 0x15e,
        height: 'auto',
        timeout: 0xbb8,
        showType: 'slide',
        showSpeed: 0x258,
        border: 0x0,
        buttons: {},
        buttonsFocus: 0x0,
        loaded: function() {},
        submit: function(b, c, d) {
            return true
        },
        closed: function() {}
    };
    a.jBox.languageDefaults = {
        close: '关闭',
        ok: '确定',
        yes: '是',
        no: '否',
        cancel: '取消'
    };
    a.jBox.setDefaults = function(b) {
        a.jBox.defaults = a.extend({},
        a.jBox.defaults, b.defaults);
        a.jBox.stateDefaults = a.extend({},
        a.jBox.stateDefaults, b.stateDefaults);
        a.jBox.tipDefaults = a.extend({},
        a.jBox.tipDefaults, b.tipDefaults);
        a.jBox.messagerDefaults = a.extend({},
        a.jBox.messagerDefaults, b.messagerDefaults);
        a.jBox.languageDefaults = a.extend({},
        a.jBox.languageDefaults, b.languageDefaults)
    };
    a.jBox.getBox = function() {
        return a('.jbox-body').eq(a('.jbox-body').length - 0x1)
    };
    a.jBox.getIframe = function(b) {
        var c = (typeof b == 'string') ? a('#' + b) : a.jBox.getBox();
        return c.find('#jbox-iframe').get(0x0)
    };
    a.jBox.getContent = function() {
        return a.jBox.getState().find('.jbox-content').html()
    };
    a.jBox.setContent = function(b) {
        return a.jBox.getState().find('.jbox-content').html(b)
    };
    a.jBox.getState = function(b) {
        if (b == undefined) {
            return a.jBox.getBox().find('.jbox-state:visible')
        } else {
            return a.jBox.getBox().find('#jbox-state-' + b)
        }
    };
    a.jBox.getStateName = function() {
        return a.jBox.getState().attr('id').replace('jbox-state-', '')
    };
    a.jBox.goToState = function(b, c) {
        var d = a.jBox.getBox();
        if (d != undefined && d != null) {
            var e;
            b = b || false;
            d.find('.jbox-state').slideUp('fast');
            if (typeof b == 'string') {
                e = d.find('#jbox-state-' + b)
            } else {
                e = b ? d.find('.jbox-state:visible').next() : d.find('.jbox-state:visible').prev()
            };
            e.slideDown(0x15e,
            function() {
                window.setTimeout(function() {
                    e.find('.jbox-button-focus').focus();
                    if (c != undefined) {
                        e.find('.jbox-content').html(c)
                    }
                },
                0x14)
            })
        }
    };
    a.jBox.nextState = function(b) {
        a.jBox.goToState(true, b)
    };
    a.jBox.prevState = function(b) {
        a.jBox.goToState(false, b)
    };
    a.jBox.close = function(b, c) {
        b = b || false;
        c = c || 'body';
        if (typeof b == 'string') {
            a('#' + b).find('.jbox-close').click()
        } else {
            var d = a('.jbox-' + c);
            if (b) {
                for (var e = 0x0,
                l = d.length; e < l; ++e) {
                    d.eq(e).find('.jbox-close').click()
                }
            } else {
                if (d.length > 0x0) {
                    d.eq(d.length - 0x1).find('.jbox-close').click()
                }
            }
        }
    };
    a.jBox.open = function(b, c, d, e, f) {
        var defaults = {
            content: b,
            title: c,
            width: d,
            height: e
        };
        f = a.extend({},
        defaults, f);
        f = a.extend({},
        a.jBox.defaults, f);
        a.jBox(f.content, f)
    };
    a.jBox.prompt = function(b, c, d, e) {
        var defaults = {
            content: b,
            title: c,
            icon: d,
            buttons: eval('({ "' + a.jBox.languageDefaults.ok + '": "ok" })')
        };
        e = a.extend({},
        defaults, e);
        e = a.extend({},
        a.jBox.defaults, e);
        if (e.border < 0x0) {
            e.border = 0x0
        };
        if (e.icon != 'info' && e.icon != 'warning' && e.icon != 'success' && e.icon != 'error' && e.icon != 'question') {
            padding = '';
            e.icon = 'none'
        };
		
        var f = e.title == undefined ? 0xa: 0x23;
        var g = e.icon == 'none' ? 'height:auto;': 'min-height:80px;' + 'height:auto;';
        var h = [];
        h.push('html:');
        h.push('<div style="width:380px;' + g + 'text-align:left;font-size:16px;line-height:35px;">');
		h.push('<div style="margin-bottom:15px;">');
        //h.push('<span class="fa jbox-icon-' + e.icon + '" style="position:absolute; top:' + (f + e.border) + 'px;left:' + (0xa + e.border) + 'px; width:32px; height:32px;"></span>');
		h.push('<span style="font-size: '+(e[0]=='S'?'18':'22')+'px;max-height:220px;overflow-y:auto;word-wrap: break-word;line-height:1.5;margin-top:10px;margin-left:10px;display:block;"><span class="fa jbox-icon-' + e.icon + '" style="float:left;width:45px; height:45px;margin-right:10px;background:url('+getRootPath()+'/images/demoimg/alert-'+(e.icon=='error'?'1':e.icon=='warning'?'3':'2')+'.png)"></span>'+e.title+'</span>');
		
		
		h.push('</div>');
		if(e.icon == 'success'){
			h.push('<div style="border:1px dashed #f3f3f3;"></div>');
		}
		h.push('<div style="overflow:hidden;' + (e.icon == 'success' ? 'height:100px;': '') + '" id="alert-conent-gang">');
		h.push('<div style="height:100%; margin-left:10px;margin-right:10px;line-height:26px;" class="jbox-span">');
		if(e.icon == 'success'){
			h.push('<p style="margin:0;color:#60c060;">已发送至</p>');
		}
        h.push(e.content);
		h.push('</div>');
		h.push('</div>');
        h.push('</div>');
        e.content = h.join('');
        a.jBox(e.content, e)
    };
    a.jBox.alert = function(b, c, d) {
        a.jBox.prompt(b, c, 'none', d)
    };
    a.jBox.info = function(b, c, d) {
        a.jBox.prompt(b, c, 'info', d)
    };
    a.jBox.success = function(b, c, d) {
        a.jBox.prompt(b, c, 'success', d)
    };
    a.jBox.error = function(b, c, d) {
        a.jBox.prompt(b, c, 'error', d)
    };
    a.jBox.confirm = function(b, c, d, e) {
        var defaults = {
            buttons: eval('({ "' + a.jBox.languageDefaults.ok + '": "ok", "' + a.jBox.languageDefaults.cancel + '": "cancel" })')
        };
        if (d != undefined && typeof d == 'function') {
            defaults.submit = d
        } else {
            defaults.submit = function(f, g, h) {
                return true
            }
        };
        e = a.extend({},
        defaults, e);
        a.jBox.prompt(b, c, 'question', e)
    };
    a.jBox.warning = function(b, c, d, e) {
        var defaults = {
            buttons: a.jBox.defaults.buttons
        };
        if (d != undefined && typeof d == 'function') {
            defaults.submit = d
        } else {
            defaults.submit = function(f, g, h) {
                return true
            }
        };
        e = a.extend({},
        defaults, e);
		
        a.jBox.prompt(b, c, 'warning', e)
    };
    a.jBox.tip = function(b, c, d) {
        var defaults = {
            content: b,
            icon: c,
			terror:c,
            opacity: 0x0,
            border: 0x0,
            showClose: true,
            buttons: {},
            isTip: true
        };
        
        if (defaults.icon == 'loading') {
            defaults.timeout = 0x0;
            defaults.opacity = 0.1
        };
        d = a.extend({},
        defaults, d);
        d = a.extend({},
        a.jBox.tipDefaults, d);
        d = a.extend({},
        a.jBox.defaults, d);
        if (d.timeout < 0x0) {
            d.timeout = 0x0
        };
        //d.timeout = 800;
        if (d.border < 0x0) {
            d.border = 0x0
        };
        if (d.icon != 'info' && d.icon != 'warning' && d.icon != 'success' && d.icon != 'error' && d.icon != 'loading') {
            d.icon = 'info'
        };
        var e = [];
        e.push('html:');
        e.push('<div style="width:310px;font-size:20px;"><div style="position:relative;margin:20px;vertical-align:middle;font-size: 22px;max-height:250px;overflow:auto;line-height:1.5;word-break:break-all;white-space:normal;"><span class="fa jbox-icon-' + e.icon + '" style="width:45px; height:45px;background:url('+getRootPath()+'/images/demoimg/alert-'+(defaults.terror=='error'?'1':'2')+'.png)"></span>&nbsp;<span style="position:relative;bottom:15px;line-height: 1.8;">');
        e.push(d.content);
        e.push('</span></div></div>');
        d.content = e.join('');
        if (a('.jbox-tip').length > 0x0) {
            a(document.body).data('tip', {
                next: true,
                options: d
            });
            a.jBox.closeTip()
        };
        if (d.focusId != undefined) {
            a('#' + d.focusId).focus();
            top.$('#' + d.focusId).focus()
        };
        a.jBox(d.content, d);
    };
    a.jBox.closeTip = function() {
        a.jBox.close(false, 'tip')
    };
    a.jBox.messager = function(b, c, d, e) {
        a.jBox.closeMessager();
        var defaults = {
            content: b,
            title: c,
            timeout: (d == undefined ? a.jBox.messagerDefaults.timeout: d),
            opacity: 0x0,
            showClose: true,
            draggable: false,
            isMessager: true
        };
        e = a.extend({},
        defaults, e);
        e = a.extend({},
        a.jBox.messagerDefaults, e);
        var f = a.extend({},
        a.jBox.defaults, {});
        f.title = null;
        e = a.extend({},
        f, e);
        if (e.border < 0x0) {
            e.border = 0x0
        };
        if (e.icon != 'info' && e.icon != 'warning' && e.icon != 'success' && e.icon != 'error' && e.icon != 'question') {
            padding = '';
            e.icon = 'none'
        };
        var g = e.title == undefined ? 0xa: 0x23;
        var h = e.icon == 'none' ? 'height:auto;': 'min-height:30px;' + ((browserM && parseInt(a.browser.version) < 0x7) ? 'height:auto !important;height:100%;_height:30px;': 'height:auto;');
        var i = [];
        i.push('html:');
        i.push('<div style="margin:10px;' + h + 'padding-left:' + (e.icon == 'none' ? 0x0: 0x28) + 'px;text-align:left;font-size:23px;">');
        i.push('<span class="fa jbox-icon-' + e.icon + '" style="position:absolute; top:' + (g + e.border) + 'px;left:' + (0xa + e.border) + 'px; width:35px; height:35px;"></span>');
		
        i.push(e.content);
        i.push('</div>');
        e.content = i.join('');
        a.jBox(e.content, e)
    };
    a.jBox.closeMessager = function() {
        a.jBox.close(false, 'messager')
    };
    window.jBox = a.jBox
})(jQuery);

/**
 * @license
 * =========================================================
 * bootstrap-datetimepicker.js
 * http://www.eyecon.ro/bootstrap-datepicker
 * =========================================================
 * Copyright 2012 Stefan Petre
 *
 * Contributions:
 *  - Andrew Rowls
 *  - Thiago de Arruda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================================================
 */

(function($) {

  // Picker object
  var smartPhone = (window.orientation != undefined);
  var DateTimePicker = function(element, options) {
    this.id = dpgId++;
    this.init(element, options);
  };

  var dateToDate = function(dt) {
    if (typeof dt === 'string') {
      return new Date(dt);
    }
    return dt;
  };

  DateTimePicker.prototype = {
    constructor: DateTimePicker,

    init: function(element, options) {
      var icon;
      if (!(options.pickTime || options.pickDate))
        throw new Error('Must choose at least one picker');
      this.options = options;
      this.$element = $(element);
      this.$element.attr("readonly", false);
      this.language = 'zh-CN';
      this.inputStor = {
          hours:"#dpTime input.tb",
          minutes:"#dpTime input.te",
          seconds:"#dpTime input.tl"
      };
      this.loadShow = options.loadShow;
      this.pickDate = options.pickDate;
      this.pickTime = options.pickTime;
      this.pickSeconds = options.pickSeconds;
      var isPickDate = this.$element.data('pick-date');
      if (isPickDate != undefined) {
        isPickDate ? this.pickDate = true : this.pickDate = false;
      }
      var isPickTime = this.$element.data('pick-time');


      if (isPickTime != undefined) {
        isPickTime ? this.pickTime = true : this.pickTime = false;
      }

      var isPickSecond = this.$element.data('pick-second');
      if (isPickSecond != undefined) {
        isPickSecond ? this.pickSeconds = true : this.pickSeconds = false;
      }


      //      alert(this.pickDate +" "+this.pickTime);
      /*      this.pickDate =  this.$element.data('date') || options.pickDate;
            alert(this.pickDate);
            this.pickTime =  this.$element.data('time') || options.pickTime;*/

      this.isInput = this.$element.is('input');
      this.component = false;
      /*
            if (this.$element.find('.input-append') || this.$element.find('.input-prepend'))
                this.component = this.$element.find('.add-on');
            if (this.component) {
              icon = this.component.find('i');
            }
      */
      if (this.pickTime) {
        //if (icon && icon.length) this.timeIcon = icon.data('time-icon');
        if (!this.timeIcon) this.timeIcon = 'fa fa-clock';
        //icon.addClass(this.timeIcon);
      }
      if (this.pickDate) {
        //if (icon && icon.length) this.dateIcon = icon.data('date-icon');
        if (!this.dateIcon) this.dateIcon = 'fa fa-calendar2';
        //icon.removeClass(this.timeIcon);
        //icon.addClass(this.dateIcon);
      }
      this.format = options.dateFormat;
      if (!this.format) {
        if (this.isInput) this.format = this.$element.data('date-format');
        else this.format = this.$element.find('input').data('date-format');
        if (!this.format) this.format = 'yyyy-MM-dd';
      }
      var isLoadShow = this.$element.hasClass("load-show") ? this.loadShow = 1 : this.loadShow = 0;
      //切记：不能与下面判断合并，此处在显示前禁用时间
      if (isLoadShow) {
        this.pickTime = false;
      }

      this._compileFormat();

      this.widget = $(getTemplate(this.timeIcon, this.pickDate, this.pickTime, options.pick12HourFormat, this.pickSeconds, options.collapse,isLoadShow)).appendTo(isLoadShow ? this.$element : $("#scrollable"));

      if (isLoadShow) {
    	  
      }

      this.minViewMode = options.minViewMode || this.$element.data('date-minviewmode') || 0;
      if (typeof this.minViewMode === 'string') {
        switch (this.minViewMode) {
          case 'months':
            this.minViewMode = 1;
            break;
          case 'years':
            this.minViewMode = 2;
            break;
          default:
            this.minViewMode = 0;
            break;
        }
      }
      this.viewMode = options.viewMode || this.$element.data('date-viewmode') || 0;
      if (typeof this.viewMode === 'string') {
        switch (this.viewMode) {
          case 'months':
            this.viewMode = 1;
            break;
          case 'years':
            this.viewMode = 2;
            break;
          default:
            this.viewMode = 0;
            break;
        }
      }
      this.startViewMode = this.viewMode;
      this.weekStart = options.weekStart || this.$element.data('date-weekstart') || 0;
      this.weekEnd = this.weekStart === 0 ? 6 : this.weekStart - 1;
      //      alert(this.$element.data('start-date')||options.startDate );

      this.setStartDate(this.$element.data('start-date') || options.startDate);
      this.setEndDate(this.$element.data('end-date') || options.endDate);

      //      alert(this.startDate);
      //      alert(this.endDate);
      this.fillDow();
      this.fillMonths();
      //this.fillHours();
      //this.fillMinutes();
      //this.fillSeconds();
      this.update();
      this.showMode();
      this._attachDatePickerEvents();
      //this.set();
    },

    show: function(e) {
      var refObjAndOperate = this.$element.data('ref-obj');
      if (refObjAndOperate) {
        tempString = refObjAndOperate.split(" ");
        if (tempString.length == 2) {
          refObjId = tempString[0];
          operate = tempString[1];
          refObj = $(refObjId);
          if (refObj) {
            var refObjValue = refObj.val();
            if (refObjValue) {
              refObjDate = Date.parse(refObjValue.replace(/-/g, "/"));
              if (operate == "lt") {
                this.setEndDate(new Date(refObjDate + 3600 * 24 * 1000));
              }
              if (operate == "gt") {
                this.setStartDate(new Date(refObjDate + 3600 * 8 * 1000));
              }
            } else {
              if (operate == "lt" && typeof this.endDate == Date) {
                this.setEndDate(Infinity);
              }
              if (operate == "gt" && typeof this.startDate == Date) {
                this.setStartDate(Infinity);
              }
            }
          }
        }
      }

      if (!this.$element.val()) {
        var tempCurrent = new Date();
        this._date = UTCDate(
          tempCurrent.getFullYear(),
          tempCurrent.getMonth(),
          tempCurrent.getDate(),
          tempCurrent.getHours(),
          tempCurrent.getMinutes(),
          tempCurrent.getSeconds(),
          tempCurrent.getMilliseconds()
        );
        this.viewDate = this._date;
        this.fillDate();
        //return;
      }
      //为弹出框的input注入初始值
      if(this.pickDate && this.pickTime){
          this.downMenuTime();
          this.setTime(this._date.getUTCHours(),this._date.getUTCMinutes(),this._date.getUTCSeconds());
      }

      if(!this.pickDate && this.pickTime && this.pickSeconds){
          this.fillTime();
      }
      this.widget.show();

      this.height = this.component ? this.component.outerHeight() : this.$element.outerHeight();
      if (!this.loadShow) {
        this.place();
      }

      this.$element.trigger({
        type: 'show',
        date: this._date
      });
      this._attachDatePickerGlobalEvents();
      if (e) {
        e.stopPropagation();
        e.preventDefault();
      }
    },

    disable: function() {
      this.$element.find('input').prop('disabled', true);
      this._detachDatePickerEvents();
    },
    enable: function() {
      this.$element.find('input').prop('disabled', false);
      this._attachDatePickerEvents();
    },

    hide: function() {
      // Ignore event if in the middle of a picker transition
      if (!this.widget.is(":visible")) {
        return;
      }
      if (this.loadShow) {
        return;
      }
      var collapse = this.widget.find('.collapse')
      for (var i = 0; i < collapse.length; i++) {
        var collapseData = collapse.eq(i).data('collapse');
        if (collapseData && collapseData.transitioning)
          return;
      }
      var str = this.$element.val();
      if (str != "" && str != null) {
          var edate = this.parseDate(str);
          if (!edate) {
            if (this.$element.hasClass("ferror")) return;
            if (confirm("不合法的日期格式或日期超出限定范围,需要撤销吗?")) {
              this.$element.removeClass("ferror").val("");
            } else {
              this.$element.addClass("ferror").val(str);
            }
          } else {
            this.$element.removeClass("ferror");
          }
      }
      this.widget.hide();
      this.viewMode = this.startViewMode;
      this.showMode();
      //this.set();
      this.$element.trigger({
        type: 'hide',
        date: this._date
      });
      //此处调用关联input失去焦点,方便input再次获取焦点时弹出选择框，由于input注册了事件onblur，调用该隐藏方法，形成嵌套，所以方法开始判断控件是否在显示状态
      //if(this.$element.focus){
      this.$element.blur();
      //}

      this._detachDatePickerGlobalEvents();
    },

    set: function() {
      var formatted = '';
      if (!this._unset) formatted = this.formatDate(this._date);
      var refObjAndOperate = this.$element.data('ref-obj');
      if (refObjAndOperate) {
        tempString = refObjAndOperate.split(" ");
        if (tempString.length == 2) {
          refObjId = tempString[0];
          operate = tempString[1];
          refObj = $(refObjId);
          if (refObj) {
            var refObjValue = refObj.val();
            if (refObjValue) {
              //refObjDate = Date.parse(refObjValue.replace(/-/g,   "/"));
              if (operate == "lt") {
                if (formatted > refObjValue) {
                  return;
                }
              }
              if (operate == "gt") {
                if (formatted < refObjValue) {
                  return;
                }
              }
            }
          }
        }
      }
      var ted = new Date(Date.parse(formatted.replace(/-/g, "/")) + 3600 * 24 * 1000);
      if(ted.valueOf() > this.endDate.valueOf() || ted.valueOf() < this.startDate.valueOf()){
         return;
      }
      if (!this.isInput) {
        if (this.component) {
          var input = this.$element.find('input');
          input.val(formatted);
          this._resetMaskPos(input);
        }
        this.$element.data('date', formatted);
      } else {
        this.$element.val(formatted);
        this._resetMaskPos(this.$element);
      }
    },
    //1002
    setValue: function(newDate) {
      if (!newDate) {
        this._unset = true;
      } else {
        this._unset = false;
      }
      if (typeof newDate === 'string') {
        this._date = this.parseDate(newDate);
      } else if (newDate) {
        this._date = new Date(newDate);
      }

      this.set();
      this.viewDate = UTCDate(this._date.getUTCFullYear(), this._date.getUTCMonth(), 1, 0, 0, 0, 0);
      this.fillDate();
      this.fillTime();
    },
    //设置时间 --> 时 分 秒
    setTime: function(hh,mm,ss){
        var inp = this.widget.find("input");
        var i = 0;
        while(i < inp.length){
            switch(inp[i].className){
                case "tb":
                    $(this.inputStor.hours).val(padLeft(hh.toString(),2,"0"));
                  break;
                case "te":
                    $(this.inputStor.minutes).val(padLeft(mm.toString(),2,"0"));
                  break;
                case "tl":
                    $(this.inputStor.seconds).val(padLeft(ss.toString(),2,"0"));
                  break;
            }
            i++;
        }
    },
    //关闭自动提示的时间信息
    downMenuTime: function(){
        $("#dpTime .menuSel").hide();
    },

    getDate: function() {
      if (this._unset) return null;
      return new Date(this._date.valueOf());
    },

    setDate: function(date) {
      if (!date) this.setValue(null);
      else this.setValue(date.valueOf());
    },

    setStartDate: function(date) {
      if (date instanceof Date) {
        this.startDate = date;
      } else if (typeof date === 'string') {
        if (date) {
          this.startDate = new Date(Date.parse(date.replace(/-/g, "/")) + 3600 * 24 * 1000);
          //alert( this.startDate);
          if (!this.startDate.getUTCFullYear()) {
            this.startDate = -Infinity;
          }
        }
      } else {
        this.startDate = -Infinity;
      }
      if (this.viewDate) {
        this.update();
      }
    },

    setEndDate: function(date) {
      if (date instanceof Date) {
        this.endDate = date;
      } else if (typeof date === 'string') {
        if (date) {
          this.endDate = new Date(Date.parse(date.replace(/-/g, "/")));
          if (!this.endDate.getUTCFullYear()) {
            this.endDate = Infinity;
          }
        }
      } else {
        this.endDate = Infinity;
      }
      if (this.viewDate) {
        this.update();
      }
    },

    getLocalDate: function() {
      if (this._unset) return null;
      var d = this._date;
      return new Date(d.getUTCFullYear(), d.getUTCMonth(), d.getUTCDate(),
        d.getUTCHours(), d.getUTCMinutes(), d.getUTCSeconds(), d.getUTCMilliseconds());
    },

    setLocalDate: function(localDate) {
      if (!localDate) this.setValue(null);
      else
        this.setValue(Date.UTC(
          localDate.getFullYear(),
          localDate.getMonth(),
          localDate.getDate(),
          localDate.getHours(),
          localDate.getMinutes(),
          localDate.getSeconds(),
          localDate.getMilliseconds()));
    },

    place: function() {
      var position = 'absolute';
      var offset = this.component ? this.component.offset() : this.$element.offset();
      this.width = this.component ? this.component.outerWidth() : this.$element.outerWidth(true);
      var dateHeight  = this.widget.outerHeight(true),		//当前date的高度
      	  $window	  = $("#scrollable"),
      	  inputHeight = this.component ? this.component.offset() : this.$element.offset();

      offset.top = offset.top + this.height + $("#scrollable").scrollTop();
      
      if (this.options.width != undefined) {
        this.widget.width(this.options.width);
      }
      
      if (this.options.orientation == 'left') {
        this.widget.addClass('left-oriented');
        offset.left = offset.left - this.width + 20;
      }
      //判断弹出层
      if (this._isInFixed()) {
        position = 'fixed';
        offset.top = inputHeight.top + this.height;
        offset.left = offset.left;
      }
      //判断右边距
      if ($window.width() < inputHeight.left + this.width) {
        offset.right = $window.width() - offset.left - this.width + ((position == 'fixed') ? 155 : 18);
        offset.left = 'auto';
        (position == 'fixed') ? offset.left = offset.left - ((this.width > 120) ? this.height : 105): '';
      } else {
        offset.right = 'auto';
      }
      //判断下边据
      if ($window.height() < inputHeight.top + dateHeight) {
        offset.top = offset.top - this.height - dateHeight;
      }
      
      this.widget.css({
        position: position,
        top: offset.top,
        left: offset.left,
        right: offset.right
      });
      this._modalPlace(this, offset)
    },

    notifyChange: function() {
      this.$element.trigger({
        type: 'changeDate',
        date: this.getDate(),
        localDate: this.getLocalDate()
      });
    },

    update: function(newDate) {
      var dateStr = newDate;
      if (!dateStr) {
        if (this.isInput) {
          dateStr = this.$element.val();
        } else {
          dateStr = this.$element.find('input').val();
        }
        if (dateStr) {
          this._date = this.parseDate(dateStr);
        }
        if (!this._date) {
          var tmp = new Date()
          this._date = UTCDate(tmp.getFullYear(),
            tmp.getMonth(),
            tmp.getDate(),
            tmp.getHours(),
            tmp.getMinutes(),
            tmp.getSeconds(),
            tmp.getMilliseconds())
        }
      }
      this.viewDate = UTCDate(this._date.getUTCFullYear(), this._date.getUTCMonth(), 1, 0, 0, 0, 0);
      this.fillDate();
      this.fillTime();
    },

    fillDow: function() {
      var dowCnt = this.weekStart;
      var html = $('<tr>');
      while (dowCnt < this.weekStart + 7) {
        html.append('<th class="dow">' + dates[this.language].daysMin[(dowCnt++) % 7] + '</th>');
      }
      this.widget.find('.datepicker-days thead').append(html);
    },

    fillMonths: function() {
      var html = '';
      var i = 0
      while (i < 12) {
        html += '<span class="month">' + dates[this.language].monthsShort[i++] + '</span>';
      }
      this.widget.find('.datepicker-months td').append(html);
    },

    fillDate: function() {
      var year = this.viewDate.getUTCFullYear();
      var month = this.viewDate.getUTCMonth();
      var currentDate = UTCDate(
        this._date.getUTCFullYear(),
        this._date.getUTCMonth(),
        this._date.getUTCDate(),
        0, 0, 0, 0
      );
      var startYear = typeof this.startDate === 'object' ? this.startDate.getUTCFullYear() : -Infinity;
      var startMonth = typeof this.startDate === 'object' ? this.startDate.getUTCMonth() : -1;
      var endYear = typeof this.endDate === 'object' ? this.endDate.getUTCFullYear() : Infinity;
      var endMonth = typeof this.endDate === 'object' ? this.endDate.getUTCMonth() : 12;

      this.widget.find('.datepicker-days').find('.disabled').removeClass('disabled');
      this.widget.find('.datepicker-months').find('.disabled').removeClass('disabled');
      this.widget.find('.datepicker-years').find('.disabled').removeClass('disabled');

      this.widget.find('.datepicker-days th:eq(1)').text(year + '年 ' + dates[this.language].months[month]);

      var prevMonth = UTCDate(year, month - 1, 28, 0, 0, 0, 0);
      var day = DPGlobal.getDaysInMonth(
        prevMonth.getUTCFullYear(), prevMonth.getUTCMonth());
      prevMonth.setUTCDate(day);
      prevMonth.setUTCDate(day - (prevMonth.getUTCDay() - this.weekStart + 7) % 7);
      if ((year == startYear && month <= startMonth) || year < startYear) {
        this.widget.find('.datepicker-days th:eq(0)').addClass('disabled');
      }
      if ((year == endYear && month >= endMonth) || year > endYear) {
        this.widget.find('.datepicker-days th:eq(2)').addClass('disabled');
      }

      var nextMonth = new Date(prevMonth.valueOf());
      nextMonth.setUTCDate(nextMonth.getUTCDate() + 42);
      nextMonth = nextMonth.valueOf();
      var html = [];
      var row;
      var clsName;
      while (prevMonth.valueOf() < nextMonth) {
        if (prevMonth.getUTCDay() === this.weekStart) {
          row = $('<tr>');
          html.push(row);
        }
        clsName = '';
        if (prevMonth.getUTCFullYear() < year ||
          (prevMonth.getUTCFullYear() == year &&
            prevMonth.getUTCMonth() < month)) {
          clsName += ' old';
        } else if (prevMonth.getUTCFullYear() > year ||
          (prevMonth.getUTCFullYear() == year &&
            prevMonth.getUTCMonth() > month)) {
          clsName += ' new';
        }
        if (prevMonth.valueOf() === currentDate.valueOf()) {
          clsName += ' active';
        }
        if ((prevMonth.valueOf() + 86400000) <= this.startDate) {
          clsName += ' disabled';
        }
        if (prevMonth.valueOf() > this.endDate) {
          clsName += ' disabled';
        }
        row.append('<td class="day' + clsName + '">' + prevMonth.getUTCDate() + '</td>');
        prevMonth.setUTCDate(prevMonth.getUTCDate() + 1);
      }
      this.widget.find('.datepicker-days tbody').empty().append(html);
      var currentYear = this._date.getUTCFullYear();

      var months = this.widget.find('.datepicker-months').find(
        'th:eq(1)').text(year + '年').end().find('span').removeClass('active');
      if (currentYear === year) {
        months.eq(this._date.getUTCMonth()).addClass('active');
      }
      if (currentYear - 1 < startYear) {
        this.widget.find('.datepicker-months th:eq(0)').addClass('disabled');
      }
      if (currentYear + 1 > endYear) {
        this.widget.find('.datepicker-months th:eq(2)').addClass('disabled');
      }
      for (var i = 0; i < 12; i++) {
        if ((year == startYear && startMonth > i) || (year < startYear)) {
          $(months[i]).addClass('disabled');
        } else if ((year == endYear && endMonth < i) || (year > endYear)) {
          $(months[i]).addClass('disabled');
        }
      }

      html = '';
      year = parseInt(year / 10, 10) * 10;
      var yearCont = this.widget.find('.datepicker-years').find(
        'th:eq(1)').text(year + '年 - ' + (year + 9) + '年').end().find('td');
      this.widget.find('.datepicker-years').find('th').removeClass('disabled');
      if (startYear > year) {
        this.widget.find('.datepicker-years').find('th:eq(0)').addClass('disabled');
      }
      if (endYear < year + 9) {
        this.widget.find('.datepicker-years').find('th:eq(2)').addClass('disabled');
      }
      year -= 1;
      for (var i = -1; i < 11; i++) {
        html += '<span class="year' + (i === -1 || i === 10 ? ' old' : '') + (currentYear === year ? ' active' : '') + ((year < startYear || year > endYear) ? ' disabled' : '') + '">' + year + '</span>';
        year += 1;
      }
      yearCont.html(html);
    },

    fillHours: function() {
      var table = this.widget.find(
        '.timepicker .timepicker-hours table');
      table.parent().hide();
      var html = '';
      if (this.options.pick12HourFormat) {
        var current = 1;
        for (var i = 0; i < 3; i += 1) {
          html += '<tr>';
          for (var j = 0; j < 4; j += 1) {
            var c = current.toString();
            html += '<td class="hour">' + padLeft(c, 2, '0') + '</td>';
            current++;
          }
          html += '</tr>'
        }
      } else {
        var current = 0;
        for (var i = 0; i < 6; i += 1) {
          html += '<tr>';
          for (var j = 0; j < 4; j += 1) {
            var c = current.toString();
            html += '<td class="hour">' + padLeft(c, 2, '0') + '</td>';
            current++;
          }
          html += '</tr>'
        }
      }
      table.html(html);
    },

    fillMinutes: function() {
      var table = this.widget.find(
        '.timepicker .timepicker-minutes table');
      table.parent().hide();
      var html = '';
      var current = 0;
      for (var i = 0; i < 5; i++) {
        html += '<tr>';
        for (var j = 0; j < 4; j += 1) {
          var c = current.toString();
          html += '<td class="minute">' + padLeft(c, 2, '0') + '</td>';
          current += 3;
        }
        html += '</tr>';
      }
      table.html(html);
    },

    fillSeconds: function() {
      var table = this.widget.find(
        '.timepicker .timepicker-seconds table');
      table.parent().hide();
      var html = '';
      var current = 0;
      for (var i = 0; i < 5; i++) {
        html += '<tr>';
        for (var j = 0; j < 4; j += 1) {
          var c = current.toString();
          html += '<td class="second">' + padLeft(c, 2, '0') + '</td>';
          current += 3;
        }
        html += '</tr>';
      }
      table.html(html);
    },
    //1003
    fillTime: function() {
      if (!this._date)
        return;
      var timeComponents = this.widget.find('.timepicker span[data-time-component]');
      var table = timeComponents.closest('table');
      var is12HourFormat = this.options.pick12HourFormat;
      var hour = this._date.getUTCHours();
      var minute = 0,second = 0;
      var period = 'AM';
      if (is12HourFormat) {
        if (hour >= 12) period = 'PM';
        if (hour === 0) hour = 12;
        else if (hour != 12) hour = hour % 12;
        this.widget.find(
          '.timepicker [data-action=togglePeriod]').text(period);
      }
    
      hour = hour.toString();
      minute = this._date.getUTCMinutes().toString();
      second = this._date.getUTCSeconds().toString();
      timeComponents.find("input").each(function(){
          switch($(this).data('action')){
              case "selectHour":
                  $(this).val(padLeft(hour,2,"0"));
                break;
              case "selectMinute":
                  $(this).val(padLeft(minute,2,"0"));
                break;
              case "selectSecond":
                  $(this).val(padLeft(second,2,"0"));
                break;
          }
      });
      // timeComponents.filter('[data-time-component=hours]').val(hour);
      // timeComponents.filter('[data-time-component=minutes] input').val(minute);
      // timeComponents.filter('[data-time-component=seconds] input').val(second);
    },

    click: function(e) {
      e.stopPropagation();
      e.preventDefault();
      this._unset = false;
      var target = $(e.target).closest('span, td, th, button, i, input');
      if (target.length === 1) {
        if (!target.is('.disabled')) {
          this.downMenuTime();
          switch (target[0].nodeName.toLowerCase()) {
            case 'th':
              switch (target[0].className) {
                case 'switch':
                  this.showMode(1);
                  break;
                case 'prev':
                case 'next':
                  var vd = this.viewDate;
                  var navFnc = DPGlobal.modes[this.viewMode].navFnc;
                  var step = DPGlobal.modes[this.viewMode].navStep;
                  if (target[0].className === 'prev') step = step * -1;
                  vd['set' + navFnc](vd['get' + navFnc]() + step);
                  this.fillDate();
                  this.set();
                  break;
              }
              break;
            case 'span':
              if(target.is('.menuSapn')){
                var tae = parseInt(target.text(), 10) || 0;
                if(target.parent().is(".hhMenu")){
                    $(this.inputStor.hours).val(padLeft(tae.toString(),2,"0"));      
                }
                if(target.parent().is(".mmMenu")){
                    $(this.inputStor.minutes).val(padLeft(tae.toString(),2,"0"));
                }
                if(target.parent().is(".ssMenu")){
                    $(this.inputStor.seconds).val(padLeft(tae.toString(),2,"0"));
                }
                this.downMenuTime();
                return;
              }
              if (target.is('.month')) {
                var month = target.parent().find('span').index(target);
                this.viewDate.setUTCMonth(month);
              } else {
                var year = parseInt(target.text(), 10) || 0;
                this.viewDate.setUTCFullYear(year);
              }
              if (this.viewMode !== 0) {
                this._date = UTCDate(
                  this.viewDate.getUTCFullYear(),
                  this.viewDate.getUTCMonth(),
                  this.viewDate.getUTCDate(),
                  this._date.getUTCHours(),
                  this._date.getUTCMinutes(),
                  this._date.getUTCSeconds(),
                  this._date.getUTCMilliseconds()
                );
                this.notifyChange();
              }
              this.showMode(-1);
              this.fillDate();
              this.set();
              if(this.viewMode == 2 && this.minViewMode == 2){
                  this.hide();
              }
              break;
            case 'td':
              if (target.is('.day')) {
                var day = parseInt(target.text(), 10) || 1;
                var month = this.viewDate.getUTCMonth();
                var year = this.viewDate.getUTCFullYear();
                if (target.is('.old')) {
                  if (month === 0) {
                    month = 11;
                    year -= 1;
                  } else {
                    month -= 1;
                  }
                } else if (target.is('.new')) {
                  if (month == 11) {
                    month = 0;
                    year += 1;
                  } else {
                    month += 1;
                  }
                }
                this._date = UTCDate(
                  year, month, day,
                  this._date.getUTCHours(),
                  this._date.getUTCMinutes(),
                  this._date.getUTCSeconds(),
                  this._date.getUTCMilliseconds()
                );

                this.viewDate = UTCDate(
                  year, month, Math.min(28, day), 0, 0, 0, 0);

                this.onCallback(e);
                this.fillDate();
                this.set();
                this.notifyChange();
                if(this.pickTime && this.pickDate){
                    if (target.is('.active')) {
                        this.hide();
                    }
                }else{
                    this.hide();
                }
              }
              break;
            case 'button':
              switch (target[0].className) {
                case 'clear':
                    this.$element.val("").removeClass("ferror");
                    this.hide();
                  break;
                case 'today':
                	var tmp = new Date();
                    if(this.pickDate && this.pickTime){
                        this._date = UTCDate(
                            tmp.getFullYear(),
                            tmp.getMonth(),
                            tmp.getDate(),
                            this.compareTime($(this.inputStor.hours).val(),"hour"),
                            this.compareTime($(this.inputStor.minutes).val(),"minutes"),
                            this.compareTime($(this.inputStor.seconds).val(),"seconds"),
                            this._date.getUTCMilliseconds()
                        );
                    }else{
                        this._date = UTCDate(
                            tmp.getFullYear(),
                            tmp.getMonth(),
                            tmp.getDate(),
                            tmp.getUTCHours(),
                            tmp.getUTCMinutes(),
                            tmp.getUTCSeconds(),
                            tmp.getUTCMilliseconds()
                        );
                    }
                    this.fillDate();
                    this.set();
                    this.notifyChange();
                    this.hide();
                    this.onCallback(e);
                  break;
                case 'confirm':
                    if(this.pickDate && this.pickTime){
                        this._date = UTCDate(
                        	this._date.getFullYear(),
                        	this._date.getMonth(),
                        	this._date.getDate(),
                            this.compareTime($(this.inputStor.hours).val(),"hour"),
                            this.compareTime($(this.inputStor.minutes).val(),"minutes"),
                            this.compareTime($(this.inputStor.seconds).val(),"seconds"),
                            this._date.getUTCMilliseconds()
                        );
                    }
                    this.set();
                    this.notifyChange();
                    this.hide();
                    this.onCallback(e);
                  break;
              }
              break;
            case 'i':
              alert("click for clock");
              break;
            case 'input':
              switch (target[0].className) {
                case 'tb':
                  $(target[0]).focus();
                  $(target[0]).blur(function(){
                     var s = parseInt($(this).val(),10) || 0;
                     if(s > 23) s = 23;
                     $(target[0]).val(padLeft(s.toString(),2,"0"));
                  });
                  $("#dpTime .hhMenu").show().siblings(".menuSel").hide();
                  break;
                case 'te':
                  $(target[0]).focus();
                  $(target[0]).blur(function(){
                     var s = parseInt($(this).val(),10) || 0;
                     if(s > 59) s = 59;
                     $(target[0]).val(padLeft(s.toString(),2,"0"));
                  });
                  $("#dpTime .mmMenu").show().siblings(".menuSel").hide();
                  break;
                case 'tl':
                  $(target[0]).focus();
                  $(target[0]).blur(function(){
                     var s = parseInt($(this).val(),10) || 0;
                     if(s > 59) s = 59;
                     $(target[0]).val(padLeft(s.toString(),2,"0"));
                  });
                  $("#dpTime .ssMenu").show().siblings(".menuSel").hide();
                  break;
              }
              break;
          }
        }
      }
    },
    onCallback : function(e){
        var functionName = this.$element.data('callback') || 0;
        if(functionName){
            var functionObj = eval(functionName);
            if (typeof(functionObj) == "function") {  
                e.pickerObject = this;
                functionObj.apply(this.$element, arguments);
            }
        }
    },
    actions: {
      incrementHours: function(e) {
    	  this.actions.blurTime.call(this);
          this._date.setUTCHours(this._date.getUTCHours() + 1);
      },

      incrementMinutes: function(e) {
    	  this.actions.blurTime.call(this);
          this._date.setUTCMinutes(this._date.getUTCMinutes() + 1);
      },

      incrementSeconds: function(e) {
    	  this.actions.blurTime.call(this);
    	  this._date.setUTCSeconds(this._date.getUTCSeconds() + 1);
      },

      decrementHours: function(e) {
    	  this.actions.blurTime.call(this);
          this._date.setUTCHours(this._date.getUTCHours() - 1);
      },

      decrementMinutes: function(e) {
    	  this.actions.blurTime.call(this);
          this._date.setUTCMinutes(this._date.getUTCMinutes() - 1);
      },

      decrementSeconds: function(e) {
    	  this.actions.blurTime.call(this);
          this._date.setUTCSeconds(this._date.getUTCSeconds() - 1);
      },

      togglePeriod: function(e) {
        var hour = this._date.getUTCHours();
        if (hour >= 12) hour -= 12;
        else hour += 12;
        this._date.setUTCHours(hour);
      },

      showPicker: function() {
        this.widget.find('.timepicker > div:not(.timepicker-picker)').hide();
        this.widget.find('.timepicker .timepicker-picker').show();
      },

      showHours: function() {
        // this.widget.find('.timepicker .timepicker-picker').hide();
        // this.widget.find('.timepicker .timepicker-hours').show();
      },
      
      sureTime: function(e){
          var timeComponents = this.widget.find('.timepicker span[data-time-component]'),that = this;
          timeComponents.find("input").each(function(){
              var date = $(this).val();
              switch($(this).data('action')){
                  case "selectHour":
                      that._date.setUTCHours(that.compareTime(date,"hour"));
                    break;
                  case "selectMinute":
                      that._date.setUTCMinutes(that.compareTime(date,"minutes"));
                    break;
                  case "selectSecond":
                      that._date.setUTCSeconds(that.compareTime(date,"seconds"));
                    break;
              }
          });
          that.hide();
          this.onCallback(e);
      },
      
      clearTime: function(){
    	  this.$element.val("");
          this.hide();
      },
      
      showMinutes: function() {
        // this.widget.find('.timepicker .timepicker-picker').hide();
        // this.widget.find('.timepicker .timepicker-minutes').show();
      },

      showSeconds: function() {
        // this.widget.find('.timepicker .timepicker-picker').hide();
        // this.widget.find('.timepicker .timepicker-seconds').show();
      },
      
      blurTime: function(){
    	  var timeComponents = this.widget.find('.timepicker span[data-time-component]'),that = this;
          timeComponents.find("input").each(function(){
              var date = $(this).val();
              switch($(this).data('action')){
                  case "selectHour":
                      that._date.setUTCHours(that.compareTime(date,"hour"));
                    break;
                  case "selectMinute":
                      that._date.setUTCMinutes(that.compareTime(date,"minutes"));
                    break;
                  case "selectSecond":
                      that._date.setUTCSeconds(that.compareTime(date,"seconds"));
                    break;
              }
          });
      },
      
      selectHour: function(e) {
    	 $(e.target).focus();
//         var tgt = $(e.target),that = this;
//         $(tgt).focus();
        // var value = parseInt(tgt.text(), 10);
        // if (this.options.pick12HourFormat) {
        //   var current = this._date.getUTCHours();
        //   if (current >= 12) {
        //     if (value != 12) value = (value + 12) % 24;
        //   } else {
        //     if (value === 12) value = 0;
        //     else value = value % 12;
        //   }
        // }
        // this._date.setUTCHours(value);

        // this.actions.showPicker.call(this);
      },

      selectMinute: function(e) {
    	  $(e.target).focus();
//        var tgt = $(e.target),that = this;
//        $(tgt).focus();
        // var value = parseInt(tgt.text(), 10);
        // this._date.setUTCMinutes(value);
        // this.actions.showPicker.call(this);
      },

      selectSecond: function(e) {
    	  $(e.target).focus();
//        var tgt = $(e.target),that = this;
//        $(tgt).focus();
        // var value = parseInt(tgt.text(), 10);
        // this._date.setUTCSeconds(value);
        // this.actions.showPicker.call(this);
      }
    },
    //1001
    doAction: function(e) {
      e.stopPropagation();
      e.preventDefault();
      if (!this._date) this._date = UTCDate(1970, 0, 0, 0, 0, 0, 0);
      var action = $(e.currentTarget).data('action');
      var rv = this.actions[action].apply(this, arguments);
      if(action == "clearTime" || action == "selectHour" || action == "selectMinute" || action == "selectSecond") return rv;
      this.set();
      this.fillTime();
      this.notifyChange();
      return rv;
    },
    setFillTime:function(date,type){
        if(date !== ""){
            if(type == "hour"){
                this._date.setUTCHours(this.compareTime(date,type));
            }else if(type == "minute"){
                this._date.setUTCMinutes(this.compareTime(date,type));
            }else if(type == "second"){
                this._date.setUTCSeconds(this.compareTime(date,type));
            }
            this.set();
            this.fillTime();
            this.notifyChange();
        }
    },
    stopEvent: function(e) {
      e.stopPropagation();
      e.preventDefault();
    },
    compareTime: function(date,type){
        var td = parseInt(date,10) || 0;
        if(type == "hour"){
            if(td > 23) td = 23;
        }else{
            if(td > 59) td = 59;
        }
        return td;
    },
    // part of the following code was taken from
    // http://cloud.github.com/downloads/digitalBush/jquery.maskedinput/jquery.maskedinput-1.3.js
    keydown: function(e) {
      // when hiting TAB, for accessibility
      var key = e.keyCode || e.which;
      if (key == 9) this.hide();
    },

    keypress: function(e) {
      var key = String.fromCharCode(e.keyCode).toLowerCase();
      if (e.ctrlKey && key == "v") {
        return;
      }
      var k = e.which;
      if (k == 8 || k == 46) {
        // For those browsers which will trigger
        // keypress on backspace/delete
        return;
      }
      var input = $(e.target);
      var c = String.fromCharCode(k);
      var val = input.val() || '';
      val += c;
      var mask = this._mask[this._maskPos];
      if (!mask) {
        return false;
      }
      if (mask.end != val.length) {
        return;
      }
      if (!mask.pattern.test(val.slice(mask.start))) {
        val = val.slice(0, val.length - 1);
        while ((mask = this._mask[this._maskPos]) && mask.character) {
          val += mask.character;
          // advance mask position past static
          // part
          this._maskPos++;
        }
        val += c;
        if (mask.end != val.length) {
          input.val(val);
          return false;
        } else {
          if (!mask.pattern.test(val.slice(mask.start))) {
            input.val(val.slice(0, mask.start));
            return false;
          } else {
            input.val(val);
            this._maskPos++;
            return false;
          }
        }
      } else {
        this._maskPos++;
      }
    },

    change: function(e) {
      var input = $(e.target);
      this._resetMaskPos(input);
    },

    showMode: function(dir) {
      if (dir) {
        this.viewMode = Math.max(this.minViewMode, Math.min(
          2, this.viewMode + dir));
      }
      this.widget.find('.datepicker > div').hide().filter(
        '.datepicker-' + DPGlobal.modes[this.viewMode].clsName).show();
    },

    destroy: function() {
      this._detachDatePickerEvents();
      this._detachDatePickerGlobalEvents();
      this.widget.remove();
      this.$element.removeData('datetimepicker');

      this.component.removeData('datetimepicker');
    },

    formatDate: function(d) {
      return this.format.replace(formatReplacer, function(match) {
        var methodName, property, rv, len = match.length;
        if (match === 'ms')
          len = 1;
        property = dateFormatComponents[match].property
        if (property === 'Hours12') {
          rv = d.getUTCHours();
          if (rv === 0) rv = 12;
          else if (rv !== 12) rv = rv % 12;
        } else if (property === 'Period12') {
          if (d.getUTCHours() >= 12) return 'PM';
          else return 'AM';
        } else if (property === 'UTCYear') {
          rv = d.getUTCFullYear();
          rv = rv.toString().substr(2);
        } else {
          methodName = 'get' + property;
          rv = d[methodName]();
        }
        if (methodName === 'getUTCMonth') rv = rv + 1;
        return padLeft(rv.toString(), len, '0');
      });
    },

    parseDate: function(str) {
      var match, i, property, methodName, value, parsed = {};
      if (!(match = this._formatPattern.exec(str)))
        return null;
      for (i = 1; i < match.length; i++) {
        property = this._propertiesByIndex[i];
        if (!property)
          continue;
        value = match[i];
        if (/^\d+$/.test(value))
          value = parseInt(value, 10);
        parsed[property] = value;
      }
      return this._finishParsingDate(parsed);
    },

    _resetMaskPos: function(input) {
      var val = input.val();
      for (var i = 0; i < this._mask.length; i++) {
        if (this._mask[i].end > val.length) {
          // If the mask has ended then jump to
          // the next
          this._maskPos = i;
          break;
        } else if (this._mask[i].end === val.length) {
          this._maskPos = i + 1;
          break;
        }
      }
    },

    _finishParsingDate: function(parsed) {
      var year, month, date, hours, minutes, seconds, milliseconds;
      year = parsed.UTCFullYear;
      if (parsed.UTCYear) year = 2000 + parsed.UTCYear;
      if (!year) year = 1970;
      if (parsed.UTCMonth) month = parsed.UTCMonth - 1;
      else month = 0;
      date = parsed.UTCDate || 1;
      hours = parsed.UTCHours || 0;
      minutes = parsed.UTCMinutes || 0;
      seconds = parsed.UTCSeconds || 0;
      milliseconds = parsed.UTCMilliseconds || 0;
      if (parsed.Hours12) {
        hours = parsed.Hours12;
      }
      if (parsed.Period12) {
        if (/pm/i.test(parsed.Period12)) {
          if (hours != 12) hours = (hours + 12) % 24;
        } else {
          hours = hours % 12;
        }
      }
      return UTCDate(year, month, date, hours, minutes, seconds, milliseconds);
    },

    _compileFormat: function() {
      var match, component, components = [],
        mask = [],
        str = this.format,
        propertiesByIndex = {},
        i = 0,
        pos = 0;
      while (match = formatComponent.exec(str)) {
        component = match[0];
        if (component in dateFormatComponents) {
          i++;
          propertiesByIndex[i] = dateFormatComponents[component].property;
          components.push('\\s*' + dateFormatComponents[component].getPattern(
            this) + '\\s*');
          mask.push({
            pattern: new RegExp(dateFormatComponents[component].getPattern(
              this)),
            property: dateFormatComponents[component].property,
            start: pos,
            end: pos += component.length
          });
        } else {
          components.push(escapeRegExp(component));
          mask.push({
            pattern: new RegExp(escapeRegExp(component)),
            character: component,
            start: pos,
            end: ++pos
          });
        }
        str = str.slice(component.length);
      }
      this._mask = mask;
      this._maskPos = 0;
      this._formatPattern = new RegExp(
        '^\\s*' + components.join('') + '\\s*$');
      this._propertiesByIndex = propertiesByIndex;
    },
    inputBlur: function(e) {
      if (!this.widget.is(":visible")) {
        return;
      }
    },
    _attachDatePickerEvents: function() {
      var self = this;
      // this handles date picker clicks
      this.widget.on('click', '.datepicker *', $.proxy(this.click, this));
      //this.widget.on('dblclick', '.datepicker *', $.proxy(this.test, this));
      // this handles time picker clicks
      this.widget.on('click', '[data-action]', $.proxy(this.doAction, this));
      if (this.isInput) {

        this.$element.on('blur', $.proxy(this.inputBlur, this));
      }

      this.widget.on('mousedown', $.proxy(this.stopEvent, this));
      if (this.pickDate && this.pickTime) {
        //日历折叠匿名函数
        this.widget.on('click.togglePicker', '.accordion-toggle', function(e) {
          e.stopPropagation();
          var $this = $(this);
          var $parent = $this.closest('ul');
          var expanded = $parent.find('.collapse.in');
          var closed = $parent.find('.collapse:not(.in)');

          if (expanded && expanded.length) {
            var collapseData = expanded.data('collapse');
            if (collapseData && collapseData.transitioning) return;
            expanded.collapse('hide');
            closed.collapse('show')
            $this.find('i').toggleClass(self.timeIcon + ' ' + self.dateIcon);
            self.$element.find('.add-on i').toggleClass(self.timeIcon + ' ' + self.dateIcon);
          }
        });
      }
      if (this.loadShow) {
        this.widget.show()
      }
      if (this.isInput) {
        this.$element.on({
          'focus': $.proxy(this.show, this),
          'keyup': $.proxy(this.change, this)
        });
        if (this.options.maskInput) {
          this.$element.on({
            'keydown': $.proxy(this.keydown, this),
            'keypress': $.proxy(this.keypress, this)
          });
        }
      } else {
        this.$element.on({
          'change': $.proxy(this.change, this)
        }, 'input');
        if (this.options.maskInput) {
          this.$element.on({
            'keydown': $.proxy(this.keydown, this),
            'keypress': $.proxy(this.keypress, this)
          }, 'input');
        }
        if (this.component) {
          this.component.on('click', $.proxy(this.show, this));
        } else {
          this.$element.on('click', $.proxy(this.show, this));
        }
      }
    },

    _attachDatePickerGlobalEvents: function() {
      //$(window).on('resize.datetimepicker' + this.id, $.proxy(this.place, this));
      //if (!this.isInput) {
      $(document).on(
        'mousedown.datetimepicker' + this.id, $.proxy(this.hide, this));
      //}
    },

    _detachDatePickerEvents: function() {
      this.widget.off('click', '.datepicker *', this.click);
      this.widget.off('click', '[data-action]');
      this.widget.off('mousedown', this.stopEvent);
      if (this.pickDate && this.pickTime) {
        this.widget.off('click.togglePicker');
      }
      if (this.isInput) {
        this.$element.off("blur", this.inputBlur);
        this.$element.off({
          'focus': this.show,
          'change': this.change
        });
        if (this.options.maskInput) {
          this.$element.off({
            'keydown': this.keydown,
            'keypress': this.keypress
          });
        }
      } else {
        this.$element.off({
          'change': this.change
        }, 'input');
        if (this.options.maskInput) {
          this.$element.off({
            'keydown': this.keydown,
            'keypress': this.keypress
          }, 'input');
        }
        if (this.component) {
          this.component.off('click', this.show);
        } else {
          this.$element.off('click', this.show);
        }
      }
    },

    _detachDatePickerGlobalEvents: function() {
      $(window).off('resize.datetimepicker' + this.id);
      //if (!this.isInput) {
      $(document).off('mousedown.datetimepicker' + this.id);
      //}
    },

    _isInFixed: function() {
      if (this.$element) {
        var parents = this.$element.parents();
        var inFixed = false;
        for (var i = 0; i < parents.length; i++) {
          if ($(parents[i]).css('position') == 'fixed') {
            inFixed = true;
            break;
          }
        };
        return inFixed;
      } else {
        return false;
      }
    },
    _modalPlace: function(obj, off) {
      // var tempthis = obj.widget
      var mBody = obj.$element.closest('.modal-body')
      mBody.scroll(function() {
        obj.hide()
          //   tempthis.css({
          //   position: 'fixed',
          //   top: off.top - mBody.scrollTop(),
          //   left: off.left,
          //   right: off.right
          // });
      })
    }
  };

  $.fn.datepicker = function(option, val) {
    return this.each(function() {
      var $this = $(this),
        data = $this.data('datetimepicker'),
        options = typeof option === 'object' && option;
      if (!data) {
        $this.data('datetimepicker', (data = new DateTimePicker(
          this, $.extend({}, $.fn.datepicker.defaults, options))));
      }
      if (typeof option === 'string') data[option](val);
    });
  };

  $.fn.datepicker.defaults = {
    maskInput: true,
    pickDate: true,
    pickTime: false,
    pick12HourFormat: false,
    pickSeconds: true,
    startDate: -Infinity,
    endDate: Infinity,
    collapse: true,
    loadShow: false
  };
  $.fn.datepicker.Constructor = DateTimePicker;
  var dpgId = 0;
  var dates = $.fn.datepicker.dates = {
    "zh-CN": {
      days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
      daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
      daysMin: ["日", "一", "二", "三", "四", "五", "六", "日"],
      months: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
      monthsShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
      today: "今日"
    }
  };

  var dateFormatComponents = {
    dd: {
      property: 'UTCDate',
      getPattern: function() {
        return '(0?[1-9]|[1-2][0-9]|3[0-1])\\b';
      }
    },
    MM: {
      property: 'UTCMonth',
      getPattern: function() {
        return '(0?[1-9]|1[0-2])\\b';
      }
    },
    yy: {
      property: 'UTCYear',
      getPattern: function() {
        return '(\\d{2})\\b'
      }
    },
    yyyy: {
      property: 'UTCFullYear',
      getPattern: function() {
        return '(\\d{4})\\b';
      }
    },
    hh: {
      property: 'Hours12',
      getPattern: function() {
        return '(0?[1-9]|1[0-2])\\b';
      }
    },
    mm: {
      property: 'UTCMinutes',
      getPattern: function() {
        return '(0?[0-9]|[1-5][0-9])\\b';
      }
    },
    ss: {
      property: 'UTCSeconds',
      getPattern: function() {
        return '(0?[0-9]|[1-5][0-9])\\b';
      }
    },
    ms: {
      property: 'UTCMilliseconds',
      getPattern: function() {
        return '([0-9]{1,3})\\b';
      }
    },
    HH: {
      property: 'UTCHours',
      getPattern: function() {
        return '(0?[0-9]|1[0-9]|2[0-3])\\b';
      }
    },
    PP: {
      property: 'Period12',
      getPattern: function() {
        return '(AM|PM|am|pm|Am|aM|Pm|pM)\\b';
      }
    }
  };

  var keys = [];
  for (var k in dateFormatComponents) keys.push(k);
  keys[keys.length - 1] += '\\b';
  keys.push('.');

  var formatComponent = new RegExp(keys.join('\\b|'));
  keys.pop();
  var formatReplacer = new RegExp(keys.join('\\b|'), 'g');

  function escapeRegExp(str) {
    // http://stackoverflow.com/questions/3446170/escape-string-for-use-in-javascript-regex
    return str.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");
  }

  function padLeft(s, l, c) {
    if (l < s.length) return s;
    else return Array(l - s.length + 1).join(c || ' ') + s;
  }

  function getTemplate(timeIcon, pickDate, pickTime, is12Hours, showSeconds, collapse, isShow) {
    if (pickDate && pickTime) {
      return (
        '<div class="bootstrap-datetimepicker-widget dropdown-menu 3" style="max-height:235px;">' +
        //'<ul>' +
        //'<li' + (collapse ? ' class="collapse in"' : '') + '>' +
        '<div class="datepicker">' +
        DPGlobal.tempButton(timeIcon,isShow) +
        '</div>' +
        //'</li>' +
        //'<li class="picker-switch accordion-toggle"><a><i class="' + timeIcon + '"></i></a></li>' +
        //'<li' + (collapse ? ' class="collapse"' : '') + '>' +
        //'<div class="timepicker">' +
        //TPGlobal.getTemplate(is12Hours, showSeconds) +
        //'</div>' +
        // '</li>' +
        // '</ul>' +
        '</div>'
      );
    } else if (pickTime) {
      return (
        '<div class="bootstrap-datetimepicker-widget dropdown-menu 2">' +
        '<div class="timepicker">' +
        TPGlobal.getTemplate(is12Hours, showSeconds) +
        '</div>' +
        '</div>'
      );
    } else {
      return (
        '<div class="bootstrap-datetimepicker-widget dropdown-menu 1" style="max-height:215px;">' +
        '<div class="datepicker">' +
        DPGlobal.tempButton(null,isShow) +
        '</div>' +
        '</div>'
      );
    }
  }

  function UTCDate() {

    return new Date(Date.UTC.apply(Date, arguments));
  }

  var DPGlobal = {
    modes: [{
      clsName: 'days',
      navFnc: 'UTCMonth',
      navStep: 1
    }, {
      clsName: 'months',
      navFnc: 'UTCFullYear',
      navStep: 1
    }, {
      clsName: 'years',
      navFnc: 'UTCFullYear',
      navStep: 10
    }],
    isLeapYear: function(year) {
      return (((year % 4 === 0) && (year % 100 !== 0)) || (year % 400 === 0))
    },
    getDaysInMonth: function(year, month) {
      return [31, (DPGlobal.isLeapYear(year) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][month]
    },
      headTemplate: '<thead>' +
          '<tr>' +
          '<th class="prev">&lsaquo;</th>' +
          '<th colspan="5" class="switch" ></th>' +
          '<th class="next">&rsaquo;</th>' +
          '</tr>' +
          '</thead>',
      contTemplate: '<tbody><tr><td colspan="7"></td></tr></tbody>'
  };
  //正常日历模版
  DPGlobal.template =
      '<div class="datepicker-days">' +
      '<table class="table-condensed">' +
      DPGlobal.headTemplate +
      '<tbody></tbody>' +
      '</table>' +
      '</div>' +
      '<div class="datepicker-months">' +
      '<table class="table-condensed">' +
      DPGlobal.headTemplate +
      DPGlobal.contTemplate +
      '</table>' +
      '</div>' +
      '<div class="datepicker-years">' +
      '<table class="table-condensed">' +
      DPGlobal.headTemplate +
      DPGlobal.contTemplate +
      '</table>' +
      '</div>';
  DPGlobal.timeModelate = 
      '<div class="menuSel hhMenu" >'+
          '<span class="menuSapn">0</span><span class="menuSapn">1</span><span class="menuSapn">2</span><span class="menuSapn">3</span>'+
          '<span class="menuSapn">4</span><span class="menuSapn">5</span><span class="menuSapn">6</span>'+
          '<span class="menuSapn">7</span><span class="menuSapn">8</span><span class="menuSapn">9</span>'+
          '<span class="menuSapn">10</span><span class="menuSapn">11</span><span class="menuSapn">12</span>'+
          '<span class="menuSapn">13</span><span class="menuSapn">14</span><span class="menuSapn">15</span>'+
          '<span class="menuSapn">16</span><span class="menuSapn">17</span><span class="menuSapn">18</span>'+
          '<span class="menuSapn">19</span><span class="menuSapn">20</span><span class="menuSapn">21</span>'+
          '<span class="menuSapn">22</span><span class="menuSapn">23</span>'+
      '</div>'+
      '<div class="menuSel mmMenu" >'+
          '<span class="menuSapn">0</span><span class="menuSapn">5</span><span class="menuSapn">10</span>'+
          '<span class="menuSapn">15</span><span class="menuSapn">20</span><span class="menuSapn">25</span>'+
          '<span class="menuSapn">30</span><span class="menuSapn">35</span><span class="menuSapn">40</span>'+
          '<span class="menuSapn">45</span><span class="menuSapn">50</span><span class="menuSapn">55</span>'+
      '</div>'+
      '<div class="menuSel ssMenu" >'+
          '<span class="menuSapn">0</span><span class="menuSapn">10</span><span class="menuSapn">20</span>'+
          '<span class="menuSapn">30</span><span class="menuSapn">40</span><span class="menuSapn">50</span>'+
      '</div>';
  DPGlobal.timeiInputMode = 
      '<div class="dpconter">'+
      '<font class="bold m-r-sm">时间</font>' +
      '</div>'+
      '<div class="dpconter b">'+
      '<input class="tb" maxlength="2"/>' +
      '<b class="tm">:</b>' +
      '<input class="te" maxlength="2"/>' +
      '<b class="tm">:</b>' +
      '<input class="tl" maxlength="2"/>' +
      '</div>';
      //预留添加小时钟
      // '<div class="dpconter">'+
      // '<a class="accordion-toggle" >' +
      // '<i class="' + icon + '"></i>' +
      // '</a>' +
      // '</div>'+
  //日历模版
  DPGlobal.tempButton = function(icon,isShow) {
    if(icon == null){
        return ('<div class="datepicker-days">' +
            '<table class="table-condensed">' +
            DPGlobal.headTemplate +
            '<tbody></tbody>' +
            '</table>' +
            (!isShow?
            		'<div class="fr">' +
                    '<button class="clear">清空</button>' +
                    '<button class="today" style="margin-left: 5px;">今天</button>' +
                    '<button class="confirm" style="margin-left: 5px;">确定</button></div>' : '')+
            
            '</div>' +
            '<div class="datepicker-months">' +
            '<table class="table-condensed">' +
            DPGlobal.headTemplate +
            DPGlobal.contTemplate +
            '</table>' +
            '</div>' +
            '<div class="datepicker-years">' +
            '<table class="table-condensed">' +
            DPGlobal.headTemplate +
            DPGlobal.contTemplate +
            '</table>' +
        '</div>');
    }
    return ('<div class="datepicker-days">' +
        '<table class="table-condensed">' +
        DPGlobal.headTemplate +
        '<tbody></tbody>' +
        '</table>' +
        '<div class="divconter" id="dpTime">' +
        DPGlobal.timeModelate+
        DPGlobal.timeiInputMode+
        '</div>' +
        (!isShow?
        		'<div class="fr">' +
                '<button class="clear">清空</button>' +
                '<button class="today" style="margin-left: 5px;">今天</button>' +
                '<button class="confirm" style="margin-left: 5px;">确定</button></div>' : '')+
        '</div>' +
        '<div class="datepicker-months">' +
        '<table class="table-condensed">' +
        DPGlobal.headTemplate +
        DPGlobal.contTemplate +
        '</table>' +
        '</div>' +
        '<div class="datepicker-years">' +
        '<table class="table-condensed">' +
        DPGlobal.headTemplate +
        DPGlobal.contTemplate +
        '</table>' +
    '</div>');
    
  }

  var TPGlobal = {
    hourTemplate: '<span data-action="showHours" data-time-component="hours" class="timepicker-hour"><input type="text" maxlength="2" data-action="selectHour" style="text-align:center; vertical-align:middle"/></span>',
    minuteTemplate: '<span data-action="showMinutes" data-time-component="minutes" class="timepicker-minute"><input type="text" maxlength="2" data-action="selectMinute" style="text-align:center; vertical-align:middle"/></span>',
    secondTemplate: '<span data-action="showSeconds" data-time-component="seconds" class="timepicker-second"><input type="text" maxlength="2" data-action="selectSecond" style="text-align:center; vertical-align:middle"/></span>'
  };
  //时间模版
  TPGlobal.getTemplate = function(is12Hours, showSeconds) {
    return (
      '<div class="timepicker-picker">' +
      '<table class="table-condensed"' +
      (is12Hours ? ' data-hour-format="12"' : '') +
      '>' +
      '<tr>' +
      '<td><a href="#" class="btn" data-action="incrementHours"><i class="fa fa-chevron-up"></i></a></td>' +
      '<td class="separator"></td>' +
      '<td><a href="#" class="btn" data-action="incrementMinutes"><i class="fa fa-chevron-up"></i></a></td>' +
      (showSeconds ?
        '<td class="separator"></td>' +
        '<td><a href="#" class="btn" data-action="incrementSeconds"><i class="fa fa-chevron-up"></i></a></td>' : '') +
      (is12Hours ? '<td class="separator"></td>' : '') +
      '</tr>' +
      '<tr>' +
      '<td>' + TPGlobal.hourTemplate + '</td> ' +
      '<td class="separator">:</td>' +
      '<td>' + TPGlobal.minuteTemplate + '</td> ' +
      (showSeconds ?
        '<td class="separator">:</td>' +
        '<td>' + TPGlobal.secondTemplate + '</td>' : '') +
      (is12Hours ?
        '<td class="separator"></td>' +
        '<td>' +
        '<button type="button" class="btn btn-primary" data-action="togglePeriod"></button>' +
        '</td>' : '') +
      '</tr>' +
      '<tr>' +
      '<td><a href="#" class="btn" data-action="decrementHours"><i class="fa fa-chevron-down"></i></a></td>' +
      '<td class="separator"></td>' +
      '<td><a href="#" class="btn" data-action="decrementMinutes"><i class="fa fa-chevron-down"></i></a></td>' +
      (showSeconds ?
        '<td class="separator"></td>' +
        '<td><a href="#" class="btn" data-action="decrementSeconds"><i class="fa fa-chevron-down"></i></a></td>' : '') +
      (is12Hours ? '<td class="separator"></td>' : '') +
      '</tr>' +
      '</table>' +
      '<div class="fr m-t-xs">' +
		      '<button data-action="clearTime">清空</button>' +
		      '<button data-action="sureTime" style="margin-left:10px;">确定</button>' +
		'</div>' +
      '</div>' //+
      // '<div class="timepicker-hours" >' +
      // '<table class="table-condensed">' +
      // '</table>' +
      // '</div>' +
      // '<div class="timepicker-minutes" >' +
      // '<table class="table-condensed">' +
      // '</table>' +
      // '</div>' +
      // (showSeconds ?
      //   '<div class="timepicker-seconds" >' +
      //   '<table class="table-condensed">' +
      //   '</table>' +
      //   '</div>' : '')
    );
  }


})(window.jQuery);
