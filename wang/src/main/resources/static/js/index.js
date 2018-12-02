(function(e){function t(e,t){this.$ele=t,this.set=e,this.WH=this.set.vertical?t.height():t.width(),this.lis=t.find("ul li"),this.idx=0,this.timer=null}e.fn.myPic=function(n){return e.fn.myPic.defaults={vertical:!1,button:!0,auto:!0,effect:"scroll",onMouse:"mouseover"},this.each(function(){var r=n?e.extend(e.fn.myPic.defaults,n):e.fn.myPic.defaults,i=new t(r,e(this));r.button&&i.generate(e(this)),r.auto&&i.auto()}),this},t.prototype={generate:function(t){var n=e("<ol></ol>").appendTo(t),r=this;e.each(this.lis,function(t,r){e("<li>"+(t+1)+"</li>").appendTo(n)}),this.olis=t.find("ol li"),this.olis.eq(0).attr("class","on");switch(this.set.effect){case"scroll":t.find("ul").css({position:"absolute",left:0,top:0}),this.set.vertical&&this.lis.css({"float":"none"});break;case"flip":case"fade":this.lis.css({position:"absolute",left:0,top:0,"float":"none"}).eq(0).css("zIndex","2");break;case"in":this.lis.css({display:"none"}).eq(0).css("display","block");break;default:}t.delegate("ol li",this.set.onMouse,function(){var $this=e(this);setTimeout(function(){r.start($this.index())},300),r.stop()}).delegate("ol li","mouseout",function(){r.auto()})},start:function(e){this.idx=e,this.idx!=this.from&&(this.effect(this),this.reset())},effect:function(t){var n=t.idx,r={};switch(t.set.effect){case"scroll":r[t.set.vertical?"top":"left"]=-(n*this.WH),this.$ele.find("ul").stop(!0,!0).animate(r),r=null;break;case"flip":this.lis.eq(n).css("zIndex",1),this.lis.eq(this.from||0).stop(!0,!0).css("opacity",.8).animate({left:-100,opacity:0},600,function(){e(this).css({zIndex:0,opacity:1,left:0}),t.lis.eq(n).css("zIndex","2")});break;case"fade":this.lis.eq(n).css("zIndex","1"),this.lis.eq(this.from||0).stop(!0,!0).fadeOut(500,function(){e(this).css({zIndex:0,display:"block"}),t.lis.eq(n).css("zIndex","2")});break;case"in":this.lis.eq(this.from||0).stop(!0,!0).fadeOut(400,function(){t.lis.eq(n).stop(!0,!0).fadeIn(700)});default:}},reset:function(){this.olis.eq(this.from||0).attr("class",""),this.olis.eq(this.idx).attr("class","on"),this.from=this.idx||0},stop:function(){var e=this;clearInterval(e.timer)},auto:function(){var e=this,t=this.lis.length;this.timer=setInterval(function(){e.idx=e.idx>=t-1?0:++e.idx,e.start(e.idx)},3e3)}}})(jQuery);
$('#play-list').myPic(); 

/*---------------------------------- 光荣榜 ：点击切换 -------------------------------------*/
var Effect = (function() {
    
    var Slider = function(o) {
        this.setting      = typeof o === 'object' ? o : {};
        this.target       = this.setting.target || 'slider';
        this.showMarkers  = this.setting.showMarkers || false;
        this.showControls = this.setting.showControls || false;
        this.timer        = null;
        this.currentTime  = null;
        this.ms           = 35;
        this.autoMs       = 3000;
        this.iTarget      = 0;
        this.nextTarget   = 0;
        this.speed        = 0;
        
        this.init();
        this.handleEvent();
    };
    
    Slider.prototype = {
        init: function() {
            this.obj      = document.getElementById(this.target);
            this.oUl      = this.obj.getElementsByTagName('ul')[0];
            this.aUlLis   = this.oUl.getElementsByTagName('li');
            this.width    = this.aUlLis[0].offsetWidth;
            this.number   = this.aUlLis.length;
            
            this.oUl.style.width = this.width * this.number + 'px';
            
            if(this.showMarkers) {
                var oDiv = document.createElement('div');
                var aLis = [];
                for(var i = 0; i < this.number; i++) {
                    aLis.push('<li>'+ (i+1) +'<\/li>');
                };
                oDiv.innerHTML = '<ol>'+ aLis.join('') +'<\/ol>';
                this.obj.appendChild(oDiv.firstChild);
                this.aLis = this.obj.getElementsByTagName('ol')[0].getElementsByTagName('li');
                this.aLis[0].className = 'active';
                oDiv = null;
            };
            
            if(this.showControls) {
                this.oPrev = document.createElement('span');
                this.oNext = document.createElement('span');
                this.oPrev.className = 'prev';
                this.oPrev.innerHTML = '&laquo;';
                this.oNext.className = 'next';
                this.oNext.innerHTML = '&raquo;';
                this.obj.appendChild(this.oPrev);
                this.obj.appendChild(this.oNext);
                
            };
            
        },
        
        handleEvent: function() {
            var that = this;
            
            this.currentTime = setInterval(function() {
                that.autoPlay();
            }, this.autoMs);
            
            this.addEvent(this.obj, 'mouseover', function() {
                clearInterval(that.currentTime);
            });
            
            this.addEvent(this.obj, 'mouseout', function() {
                that.currentTime = setInterval(function() {
                    that.autoPlay();
                }, that.autoMs);
            });
            
            if(this.showMarkers) {
                for(var i = 0; i < this.number; i++) {
                    var el = this.aLis[i];
                    (function(index) {
                        that.addEvent(el, 'mouseover', function() {
                            that.goTime(index);
                        });
                    })(i);
                };
            };
            
            if(this.showControls) {
                this.addEvent(this.oPrev, 'click', function() {
                    that.fnPrev();
                });
                this.addEvent(this.oNext, 'click', function() {
                    that.autoPlay();
                });
            };
            
        },
        
        addEvent: function(el, type, fn) {
            if(window.addEventListener) {
                el.addEventListener(type, fn, false);
            }
            else if(window.attachEvent) {
                el.attachEvent('on' + type, fn);
            };
        },
        
        fnPrev: function() {
            this.nextTarget--;
            if(this.nextTarget < 0) {
                this.nextTarget = this.number - 1;
            };
            this.goTime(this.nextTarget);
        },
        
        autoPlay: function() {
            this.nextTarget++;
            if(this.nextTarget >= this.number) {
                this.nextTarget = 0;
            };
            this.goTime(this.nextTarget);
        },
        
        goTime: function(index) {
            var that = this;
            
            if(this.showMarkers) {
                for(var i = 0; i < this.number; i++) {
                    i == index ? this.aLis[i].className = 'active' : this.aLis[i].className = '';
                };
            };
            
            this.iTarget = -index * this.width;
            if(this.timer) {
                clearInterval(this.timer);
            };
            this.timer = setInterval(function() {
                that.doMove(that.iTarget);
            }, this.ms);
        },
        
        doMove: function(target) {
            this.oUl.style.left = this.speed + 'px';
            this.speed += (target - this.oUl.offsetLeft) / 3;
            if(Math.abs(target - this.oUl.offsetLeft) === 0) {
                this.oUl.style.left = target + 'px';
                clearInterval(this.timer);
                this.timer = null;
            };
        }

    };
    
    return {
        
        slider: function(o) {
            var tt = new Slider(o);
        }
    };
})();

// 调用语句
Effect.slider({
    'targetElement': 'slider',
    'showMarkers': true,
    'showControls': true
});