(function($, window, document, undefined) {

	$.fn.quicksearch = function (target, opt) {
		
		var timeout, cache, rowcache, jq_results, val = '',  arrayVal= {},e = this, options = $.extend({ 
			delay: 100,
			selector: null,
			stripeRows: null,
			loader: null,
			noResults: '',
			matchedResultsCount: 0,
			bind: 'keyup change',
			attrOrHtml:'html',//是通过属性筛选过滤还是html内容筛选过滤
			screenattribute:{},
			onBefore: function () { 
				return;
			},
			onAfter: function () { 
				return;
			},
			show: function () {
				this.style.display = "";
			},
			hide: function () {
				this.style.display = "none";
			},
			prepareQuery: function (val) {
				return val.toLowerCase().split(' ');
			},
			toggleOption:function(show){
				$(this).toggle(show);
			    if(show) {
			        if( $(this).parent('span.toggleOption').length || $(this).is('span.toggleOption'))
			            $(this).children().unwrap();
			    } else {
			        if($(this).parent('span.toggleOption').length == 0 && !$(this).is('span.toggleOption'))
			            $(this).wrap('<span class="toggleOption" style="display: none;" />');
			    }
			},
			testQuery: function (query, txt, _row) {
				for (var i = 0; i < query.length; i += 1) {
					if (txt.indexOf(query[i]) === -1) {
						return false;
					}
				}
				return true;
			}
		}, opt);
		/*
		* 判断val数组搜索集合是否为空
		*/
		this.searchIfempty = function(){
			var sig = false;//默认不为空
			var count = 0;
			$.each(arrayVal, function(key_, val_) {
				if(val_==""){
					count++;
				}
			});
			if(count == arrayVal.length){
				sig = true;
			};
			return sig;
		}
		this.go = function () {
		
			var i = 0,
				numMatchedRows = 0,
				noresults = true, 
				query = options.prepareQuery(val),
				val_empty_attr = e.searchIfempty();
				val_empty = (val.replace(' ', '').length === 0);
				
			for (var i = 0, len = rowcache.length; i < len; i++) {
				if (val_empty_attr || options.testQuery(arrayVal,jQuery.parseJSON(cache[i]), rowcache[i])) {
					//if(rowcache[i].tagName != undefined && rowcache[i].tagName.toLowerCase() =='option'){
						options.toggleOption.call(rowcache[i],1);
					//}else{
						//options.show.apply(rowcache[i]);
				//	}
					noresults = false;
					numMatchedRows++;
				} else {
					//if(rowcache[i].tagName != undefined && rowcache[i].tagName.toLowerCase() =='option'){
						options.toggleOption.call(rowcache[i],0);
				//	}else{
						//options.hide.apply(rowcache[i]);
				//	}
					
				}
			}
			
			if (noresults) {
				this.results(false);
			} else {
				this.results(true);
				this.stripe();
			}
			
			this.matchedResultsCount = numMatchedRows;
			this.loader(false);
			options.onAfter();
			
			return this;
		};

		/*
		 * External API so that users can perform search programatically. 
		 * */
		this.search = function (submittedVal) {
			val = submittedVal;
			e.trigger();
		};
		
		/*
		 * External API to get the number of matched results as seen in 
		 * https://github.com/ruiz107/quicksearch/commit/f78dc440b42d95ce9caed1d087174dd4359982d6
		 * */
		this.currentMatchedResults = function() {
			return this.matchedResultsCount;
		};
		
		this.stripe = function () {
			
			if (typeof options.stripeRows === "object" && options.stripeRows !== null)
			{
				var joined = options.stripeRows.join(' ');
				var stripeRows_length = options.stripeRows.length;
				
				jq_results.not(':hidden').each(function (i) {
					$(this).removeClass(joined).addClass(options.stripeRows[i % stripeRows_length]);
				});
			}
			
			return this;
		};
		
		this.strip_html = function (input) {
			var output = input.toString().replace(new RegExp('<[^<]+\>', 'g'), "");
			output = $.trim(output.toLowerCase());
			return output;
		};
		
		this.results = function (bool) {
			if (typeof options.noResults === "string" && options.noResults !== "") {
				if (bool) {
					$(options.noResults).hide();
				} else {
					$(options.noResults).show();
				}
			}
			return this;
		};
		
		this.loader = function (bool) {
			if (typeof options.loader === "string" && options.loader !== "") {
				 (bool) ? $(options.loader).show() : $(options.loader).hide();
			}
			return this;
		};
		
		this.cache = function () {
			
			jq_results = $(target);
			
			if (typeof options.noResults === "string" && options.noResults !== "") {
				jq_results = jq_results.not(options.noResults);
			}
			
			var t = (typeof options.selector === "string") ? jq_results.find(options.selector) : $(target).not(options.noResults);
			cache = t.map(function () {
				var that = this;
				var temp = "";
				if (options.attrOrHtml =='html') {
					temp=e.strip_html(this.innerHTML);
				}else if(options.attrOrHtml=='attr'){

					$.each(options.screenattribute, function(index, val) {
						var temp_attr = $(that).attr(val);
						if($(that).children('option').length>0){
							temp_attr = $(that).children('option').attr(val);
						}

						temp+= '"'+val+'":"'+e.strip_html(temp_attr)+'" ';
						
					});
					//temp = $(this).attr(options.screenattribute[0]);
				}
				
				return "{"+$.trim(temp).replace(' ',',')+"}";
			});
			
			rowcache = jq_results.map(function () {
				return this;
			});

			/*
			 * Modified fix for sync-ing "val". 
			 * Original fix https://github.com/michaellwest/quicksearch/commit/4ace4008d079298a01f97f885ba8fa956a9703d1
			 * */
			arrayVal = arrayVal || {};
			val = val || this.val() || "";
			
			return this.go();
		};
		
		this.trigger = function () {
			this.loader(true);
			options.onBefore();
			
			window.clearTimeout(timeout);
			timeout = window.setTimeout(function () {
				e.go();
			}, options.delay);
			
			return this;
		};
		
		this.cache();
		this.results(true);
		this.stripe();
		this.loader(false);
		
		return this.each(function () {
			
			/*
			 * Changed from .bind to .on.
			 * */
			$(this).on(options.bind, function () {
				var tempObj = {};
				var dataAll = $(this).attr('data-type');
				$('select[data-type="'+dataAll+'"]').each(function(index,val){
					var tempkey = $(val).attr('id');
				
					tempObj[tempkey] = $(val).val();
					//tempArr.push(tempObj);
				});
				arrayVal = tempObj;
				val = $(this).val();
				e.cache();
				//e.trigger();
			});
		});
		
	};

}(jQuery, this, document));
