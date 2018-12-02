/*
 * multiselect2side jQuery plugin
 *
 * Copyright (c) 2010 Giovanni Casassa (senamion.com - senamion.it)
 *
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 * http://www.senamion.com
 *
 */

(function($)
{
	// SORT INTERNAL
	function internalSort(a, b) {
		var compA = $(a).text().toUpperCase();
		var compB = $(b).text().toUpperCase();
		return (compA < compB) ? -1 : (compA > compB) ? 1 : 0;
	};
	

	var methods = {
		init : function(options) {
			var o = {
				selectedPosition: 'right',
				moveOptions: false,
				labelTop: 'Top',
				labelBottom: 'Bottom',
				labelUp: 'Up',
				labelDown: 'Down',
				labelSort: 'Sort',
				labelsx: '人员选择框',
				labeldx: '已选人员框',
				maxSelected: -1,
				autoSort: false,
				autoSortAvailable: false,
				search: false,
				caseSensitive: false,
				delay: 200,
				optGroupSearch: false,
				minSize: 6
				

			};

			return this.each(function () {
				var	el = $(this);
				var data = el.data('multiselect2side');

				if (options)
					$.extend(o, options);

				if (!data)
					el.data('multiselect2side', o);

				var	originalName = $(this).attr("name");
				if (originalName.indexOf('[') != -1)
					originalName = originalName.substring(0, originalName.indexOf('['));

				var	nameDx = originalName + "ms2side__dx";
				var	nameSx = originalName + "ms2side__sx";
				var size = $(this).attr("size");
				// SIZE MIN
				if (size < o.minSize) {
					$(this).attr("size", "" + o.minSize);
					size = o.minSize;
				}

				// UP AND DOWN
				var divUpDown =
						"<div class='ms2side__updown'>" +
							"<p class='SelSort' title='Sort'>" + o.labelSort + "</p>" +
							"<p class='MoveTop' title='Move on top selected option'>" + o.labelTop + "</p>" +
							"<p class='MoveUp' title='Move up selected option'>" + o.labelUp + "</p>" +
							"<p class='MoveDown' title='Move down selected option'>" + o.labelDown + "</p>" +
							"<p class='MoveBottom' title='Move on bottom selected option'>" + o.labelBottom + "</p>" +
						"</div>";

				// INPUT TEXT FOR SEARCH OPTION
				var	leftSearch = false, rightSearch = false;
				// BOTH SEARCH AND OPTGROUP SEARCH
				if (o.search != false && o.optGroupSearch != false) {
					var ss = 
						o.optGroupSearch + "<select class='small' ><option value=__null__> </option></select> " +
						o.search + "<input class='small' type='text' /><a href='#'> </a>";

					if (o.selectedPosition == 'right')
						leftSearch = ss;
					else
						rightSearch = ss;
				}
				else if (o.search != false) {
					var	ss = o.search + "<input type='text' /><a href='#'> </a>";

					if (o.selectedPosition == 'right')
						leftSearch = ss;
					else
						rightSearch = ss;
				}
				else if (o.optGroupSearch != false) {
					var	ss = o.optGroupSearch + "<select><option value=__null__> </option></select>";

					if (o.selectedPosition == 'right')
						leftSearch = ss;
					else
						rightSearch = ss;
				}
				//自定义 左右选择箭头
				var indicate = ((o.selectedPosition == 'right')
							?
							("<p class='AddOne' title='Add Selected'><span style='padding:2px 17px;'>&rsaquo;</span></p>" +
							"<p class='AddAll' title='Add All'><span style='padding:2px 17px;'>&raquo;</span></p>" +
							"<p class='RemoveOne' title='Remove Selected'><span style='padding:2px 17px;'>&lsaquo;</span></p>" +
							"<p class='RemoveAll' title='Remove All'><span style='padding:2px 17px;'>&laquo;</span></p>")
							:
							("<p class='AddOne' title='Add Selected'><span style='padding:2px 17px;'>&lsaquo;</span></p>" +
							"<p class='AddAll' title='Add All'><span style='padding:2px 17px;'>&laquo;</span></p>" +
							"<p class='RemoveOne' title='Remove Selected'><span style='padding:2px 17px;'>&rsaquo;</span></p>" +
							"<p class='RemoveAll' title='Remove All'><span style='padding:2px 17px;'>&raquo;</span></p>")
							)
				// CREATE NEW ELEMENT (AND HIDE IT) AFTER THE HIDDED ORGINAL SELECT
				var htmlToAdd = 
					"<div class='ms2side__div'>" +
							((o.selectedPosition != 'right' && o.moveOptions) ? divUpDown : "") +
						"<div class='ms2side__select'>" +
							((o.labelsx || leftSearch != false) ? ("<div class='ms2side__header'>" + (leftSearch != false ? leftSearch : o.labelsx) + "</div>") : "") +
							"<select title='" + o.labelsx + "' name='" + nameSx + "' id='" + nameSx + "' size='" + size + "' multiple='multiple' ></select>" +
						"</div>" +
						"<div  class='ms2side__options'>" + indicate
							 +
						"</div>" +
						"<div class='ms2side__select'>" +
							((o.labeldx || rightSearch != false) ? ("<div class='ms2side__header'>" + (rightSearch != false ? rightSearch : o.labeldx) + "</div>") : "") +
							"<select title='" + o.labeldx + "' name='" + nameDx + "' id='" + nameDx + "' size='" + size + "' multiple='multiple' ></select>" +
						"</div>" +
						((o.selectedPosition == 'right' && o.moveOptions) ? divUpDown : "") +
					"</div>";
				el.after(htmlToAdd).hide();

				// ELEMENTS
				var allSel = el.next().children(".ms2side__select").children("select");
				var	leftSel = (o.selectedPosition == 'right') ? allSel.eq(0) : allSel.eq(1);
				var	rightSel = (o.selectedPosition == 'right') ? allSel.eq(1) : allSel.eq(0);
				// HEIGHT DIV
				var	heightDiv = $(".ms2side__select").eq(0).height();

				// SELECT optgroup
				var searchSelect = $();

				// SEARCH INPUT
				var searchInput = $(this).next().find("input:text");
				var	removeFilter = searchInput.next().hide();
				var	toid = false;
				var searchV = false;


				// SELECT optgroup - ADD ALL OPTGROUP AS OPTION
				if (o.optGroupSearch != false) {
					var	lastOptGroupSearch = false;

					searchSelect = $(this).next().find("select").eq(0);

					el.children("optgroup").each(function() {
						if (searchSelect.find("[value='" + $(this).attr("label") + "']").size() == 0)
							searchSelect.append("<option value='" + $(this).attr("label") + "'>" + $(this).attr("label") + "</option>");
					});
					searchSelect.change(function() {
						var	sEl = $(this);

						if (sEl.val() != lastOptGroupSearch) {

							// IF EXIST SET SEARCH TEXT TO VOID
							if (searchInput.val() != "") {
								clearTimeout(toid);
								removeFilter.hide();
								searchInput.val("");//.trigger('keyup');
								searchV = "";
								// fto();
							}

							setTimeout(function() {
								if (sEl.val() == "__null__") {
									els = el.find("option:not(:selected)");
								}
								else
									els = el.find("optgroup[label='" + sEl.val() + "']").children("option:not(:selected)");

								// REMOVE ORIGINAL ELEMENTS AND ADD OPTION OF OPTGROUP SELECTED
								leftSel.find("option").remove();
								els.each(function() {
									leftSel.append($(this).clone());
								});
								lastOptGroupSearch = sEl.val();
								leftSel.trigger('change');
							}, 100);
						}
					});
				}


				// SEARCH FUNCTION
				var	fto = function() {
					var	els = leftSel.children();
					var	toSearch = el.find("option:not(:selected)");

					// RESET OptGroupSearch
					lastOptGroupSearch = "__null__";
					searchSelect.val("__null__");

					if (searchV == searchInput.val())
						return;

					searchInput
						.addClass("wait")
						.removeAttr("style");

					searchV = searchInput.val();

					// A LITTLE TIMEOUT TO VIEW WAIT CLASS ON INPUT ON IE
					setTimeout(function() {
						leftSel.children().remove();
						if (searchV == "") {
							toSearch.clone().appendTo(leftSel).removeAttr("selected");
							removeFilter.hide();
						}
						else {
							toSearch.each(function() {
								var	myText = $(this).text();

								if (o.caseSensitive)
									find = myText.indexOf(searchV);
								else
									find = myText.toUpperCase().indexOf(searchV.toUpperCase());

								if (find != -1)
									$(this).clone().appendTo(leftSel).removeAttr("selected");
							});

							if (leftSel.children().length == 0)
								searchInput.css({'border': '1px red solid'});

							removeFilter.show();
							leftSel.trigger('change');
						}
						leftSel.trigger('change');
						searchInput.removeClass("wait");
					}, 5);
				};


				// REMOVE FILTER ON SEARCH FUNCTION
				removeFilter.click(function() {
					clearTimeout(toid);
					searchInput.val("");
					fto();
					return false;
				});

				// ON CHANGE TEXT INPUT
				searchInput.keyup(function() {
					clearTimeout(toid);
					toid = setTimeout(fto, o.delay);
				});


				// CENTER MOVE OPTIONS AND UPDOWN OPTIONS
				$(this).next().find('.ms2side__options, .ms2side__updown').each(function(){
					var	top = ((heightDiv/2) - ($(this).height()/2));
					if (top > 0)
						$(this).css('padding-top',  top + 'px' );
				})

				// MOVE SELECTED OPTION TO RIGHT, NOT SELECTED TO LEFT
				$(this).find("option:selected").clone().appendTo(rightSel); // .removeAttr("selected");
				$(this).find("option:not(:selected)").clone().appendTo(leftSel);

				// SELECT FIRST LEFT ITEM AND DESELECT IN RIGHT (NOT IN IE6)
				if ('undefined' == typeof(document.body.style.maxHeight)) {
					leftSel.find("option").eq(0).attr("selected", true);
					rightSel.children().removeAttr("selected");
				}

				// ON CHANGE SORT SELECTED OPTIONS
				var	nLastAutosort = 0;
				if (o.autoSort)
					allSel.change(function() {
						var	selectDx = rightSel.find("option");

						if (selectDx.length != nLastAutosort) {
							// SORT SELECTED ELEMENT
							selectDx.sort(internalSort);
							// FIRST REMOVE FROM ORIGINAL SELECT
							el.find("option:selected").remove();
							// AFTER ADD ON ORIGINAL AND RIGHT SELECT
							selectDx.each(function() {
								rightSel.append($(this).clone());
								$(this).appendTo(el).attr("selected", true);
								//el.append($(this).attr("selected", true));		HACK IE6
							});
							nLastAutosort = selectDx.length;
						}
					});

				// ON CHANGE SORT AVAILABLE OPTIONS (NOT NECESSARY IN ORIGINAL SELECT)
				var	nLastAutosortAvailable = 0;
				if (o.autoSortAvailable)
					allSel.change(function() {
						var	selectSx = leftSel.find("option");

						if (selectSx.length != nLastAutosortAvailable) {
							// SORT SELECTED ELEMENT
							selectSx.sort(internalSort);
							// REMOVE ORIGINAL ELEMENTS AND ADD SORTED
							leftSel.find("option").remove();
							selectSx.each(function() {
								leftSel.append($(this).clone());
							});
							nLastAutosortAvailable = selectSx.length;
						}
					});

				// ON CHANGE REFRESH ALL BUTTON STATUS
				allSel.change(function() {
					// HACK FOR IE6 (SHOW AND HIDE ORIGINAL SELECT)
					if ('undefined' == typeof(document.body.style.maxHeight))
						el.show().hide();
					var	div = $(this).parent().parent();
					var	selectSx = leftSel.children(':not(span.toggleOption)'); // 除了隐藏掉的
					var	selectDx = rightSel.children();
					//var	selectedSx = leftSel.find("option:selected");
					var selectedSx = leftSel.find('>:not("span.toggleOption")option:selected')
					var	selectedDx = rightSel.find("option:selected");
					if (o.maxSelected==1) { // 为的是 在只选择一个的情况下能够 直接点击替换. 
						if (selectedSx.size() == 0 || selectedSx.size()>=2 || ((selectedSx.size() + selectDx.size())>2)) {
							div.find(".AddOne").addClass('ms2side__hide');
						}else{
							div.find(".AddOne").removeClass('ms2side__hide');
						}
					
					}else{
						if (selectedSx.size() == 0 ||
							(o.maxSelected >= 0 && (selectedSx.size() + selectDx.size()) > o.maxSelected))
							div.find(".AddOne").addClass('ms2side__hide');
						else
							div.find(".AddOne").removeClass('ms2side__hide');
					}

					// FIRST HIDE ALL
					div.find(".RemoveOne, .MoveUp, .MoveDown, .MoveTop, .MoveBottom, .SelSort").addClass('ms2side__hide');
					if (selectDx.size() > 1)
						div.find(".SelSort").removeClass('ms2side__hide');
					if (selectedDx.size() > 0) {
						div.find(".RemoveOne").removeClass('ms2side__hide');
						// ALL SELECTED - NO MOVE
						if (selectedDx.size() < selectDx.size()) {	// FOR NOW (JOE) && selectedDx.size() == 1
							if (selectedDx.val() != selectDx.val())	// FIRST OPTION, NO UP AND TOP BUTTON
								div.find(".MoveUp, .MoveTop").removeClass('ms2side__hide');
							if (selectedDx.last().val() != selectDx.last().val())	// LAST OPTION, NO DOWN AND BOTTOM BUTTON
								div.find(".MoveDown, .MoveBottom").removeClass('ms2side__hide');
						}
					}

					if (selectSx.size() == 0 ||
							(o.maxSelected >= 0 && selectSx.size() >= o.maxSelected))
						div.find(".AddAll").addClass('ms2side__hide');
					else
						div.find(".AddAll").removeClass('ms2side__hide');

					if (selectDx.size() == 0)
						div.find(".RemoveAll").addClass('ms2side__hide');
					else
						div.find(".RemoveAll").removeClass('ms2side__hide');
				});

				// DOUBLE CLICK ON LEFT SELECT OPTION
				leftSel.dblclick(function () {
						
					$(this).find("option:selected").each(function(i, selected){

						if (o.maxSelected == 1) {
							$($('.ms2side__options').find('.RemoveAll').get(0)).trigger('click');
							leftSel.find('option[id="'+this.id+'"]').attr('selected', false).remove().appendTo(rightSel);
							el.find("[value='" + $(selected).val() + "']").remove().appendTo(el).attr("selected", true);
						}else if (o.maxSelected < 0 || rightSel.children().size() < o.maxSelected) {
							$(this).remove().appendTo(rightSel);
							el.find("[value='" + $(selected).val() + "']").remove().appendTo(el).attr("selected", true);
						} 
					});
					$(this).trigger('change');
					/**这里增加了一个回调函数 方便在执行备选项双击事件的时候去执行自定义方法*/
					if (typeof o.afterSelect === 'function') {
		            	o.afterSelect.call(this);
			        };
			        sortFun('left');
				});

				// DOUBLE CLICK ON RIGHT SELECT OPTION
				rightSel.dblclick(function () {
					/**这里增加了一个回调函数 方便在执行已选项双击事件的时候去执行自定义方法*/
					if (typeof o.deselectBefore === 'function') {
		            	o.deselectBefore.call(this,$(this).find("option:selected"));
			        };
					$(this).find("option:selected").each(function(i, selected){
						$(this).remove().appendTo(leftSel);
						el.find("[value='" + $(selected).val() + "']").removeAttr("selected").remove().appendTo(el);
					});
					$(this).trigger('change');

					// TRIGGER CHANGE AND VALUE NULL FORM OPTGROUP SEARCH (IF EXIST)
					searchSelect.val("__null__").trigger("change");
					// TRIGGER CLICK ON REMOVE FILTER (IF EXIST)
					removeFilter.click();
					
					if (typeof o.removeOneafter === 'function') {
					    o.removeOneafter.call(this);
					};
					sortFun('right');
				});
				//allSel
				allSel.on('sortselect2side',function(event) {
					event.preventDefault();
					sortFun('right');
					/* Act on the event */
				}); 
				// CLICK ON OPTION
				$(this).next().find('.ms2side__options').children().click(function () {
					if (!$(this).hasClass("ms2side__hide")) {
						if ($(this).hasClass("AddOne")) {
							$leftselected =  leftSel.find("option:selected");
							if (o.maxSelected == 1) {// 这里的目的是 当 maxSelected 为1 时 让其 点击右单箭头时直接替换已选项
								rightSel.children().each(function(index, el) { //让已选栏中所有项均为非选中状态
									$(el).attr('selected', false)
								});

								$($('.ms2side__options').find('.RemoveAll').get(0)).trigger('click');//然已选内容全部进入备选状态
								$leftselected = leftSel.find('option[id="'+$leftselected.get(0).id+'"]');//把之前选中的option重新选中
							};
							$leftselected.each(function(i, selected){
								$(this).remove().appendTo(rightSel);

								el.find("[value='" + $(selected).val() + "']").remove().appendTo(el).attr("selected", true);
								/**这里增加了一个回调函数 方便在执行备选项双击事件的时候去执行自定义方法*/
								if (typeof o.afterSelect === 'function') {
					            	o.afterSelect.call(this);
						        };

							});
							sortFun('left');
						}
						else if ($(this).hasClass("AddAll")) {	// ALL SELECTED
							// TEST IF HAVE A FILTER OR A SELECT OPTGROUP
							if (removeFilter.is(":visible") || (searchSelect.length > 0 && searchSelect.val() != "__null__"))
								leftSel.children().each(function(i, selected){
									$(this).remove().appendTo(rightSel);
									el.find("[value='" + $(selected).val() + "']").remove().appendTo(el).attr("selected", true);
								});
							else {
								leftSel.children(':not(span.toggleOption)').remove().appendTo(rightSel);
								el.find('option').attr("selected", true);
								// el.children().attr("selected", true); -- PROBLEM WITH OPTGROUP
							}
							sortFun('left');
						}
						else if ($(this).hasClass("RemoveOne")) {
							
							rightSel.find("option:selected").each(function(i, selected){
								var sig = true;
								/**这里增加了一个回调函数 方便在执行已选项双击事件的时候去执行自定义方法*/
								if (typeof o.deselectBefore === 'function') {
					            	sig = o.deselectBefore.call(this,$(this));
						        };
						        if(sig){	
									$(this).remove().appendTo(leftSel);
									el.find("[value='" + $(selected).val() + "']").remove().appendTo(el).removeAttr("selected");
						        }
							});

							// TRIGGER CLICK ON REMOVE FILTER (IF EXIST)
							removeFilter.click();
							// TRIGGER CHANGE AND VALUE NULL FORM OPTGROUP SEARCH (IF EXIST)
							searchSelect.val("__null__").trigger("change");
							
							if (typeof o.removeOneafter === 'function') {
					            	o.removeOneafter.call(this);
						    };
							sortFun('right');
						}
						else if ($(this).hasClass("RemoveAll")) {	// ALL REMOVED
							rightSel.children().each(function(i, selected){
								var sig = true;
								/**这里增加了一个回调函数 方便在执行已选项双击事件的时候去执行自定义方法*/
								if (typeof o.deselectBefore === 'function') {
					            	sig = o.deselectBefore.call(this,$(this));
						        };
						        if(sig){	
									$(this).remove().appendTo(leftSel);
									el.find("[value='" + $(selected).val() + "']").remove().appendTo(el).removeAttr("selected");
						        }
							});

							// TRIGGER CLICK ON REMOVE FILTER (IF EXIST)
							removeFilter.click();
							// TRIGGER CHANGE AND VALUE NULL FORM OPTGROUP SEARCH (IF EXIST)
							searchSelect.val("__null__").trigger("change");
							sortFun('right');
							//已选到备选回调函数
							if (typeof o.removeAllafter === 'function') {
					            	o.removeAllafter.call(this,leftSel);
						    };
							
						//	rightSel.children().appendTo(leftSel);
						//	rightSel.children().remove();
						//	el.find('option').removeAttr("selected");
							//el.children().removeAttr("selected"); -- PROBLEM WITH OPTGROUP
							// TRIGGER CLICK ON REMOVE FILTER (IF EXIST)
						//	removeFilter.click();
							// TRIGGER CHANGE AND VALUE NULL FORM OPTGROUP SEARCH (IF EXIST)
						//	searchSelect.val("__null__").trigger("change");
						}

					}

					leftSel.trigger('change');
				
				});
				var internalSort_interior = function(a, b) {
						var compA = $(a).attr(o.order);
						var compB = $(b).attr(o.order);
						return (compA < compB) ? -1 : (compA > compB) ? 1 : 0;
					};
				/**排序方法*/
				var sortFun = function(sig){
					if(o.order != undefined && o.order != null && o.order != ''){
						var	selectDx;
						if(sig=="left"){
							selectDx = rightSel.find(">option");
						}else if (sig=="right") {
							selectDx = leftSel.find(">option");
						}
						
						selectDx.sort(internalSort_interior);
						el.find("option:selected").remove();
						selectDx.each(function() {
							if(sig=="left"){
									rightSel.append($(this).clone());
							}else if (sig=="right") {
									leftSel.append($(this).clone());
							}
						
							el.append($(this).attr("selected", true));
						});
					}
				}
				// CLICK ON UP - DOWN
				$(this).next().find('.ms2side__updown').children().click(function () {
					var	selectedDx = rightSel.find("option:selected");
					var	selectDx = rightSel.find("option");

					if (!$(this).hasClass("ms2side__hide")) {
						if ($(this).hasClass("SelSort")) {
							// SORT SELECTED ELEMENT
							selectDx.sort(internalSort);
							// FIRST REMOVE FROM ORIGINAL SELECT
							el.find("option:selected").remove();
							// AFTER ADD ON ORIGINAL AND RIGHT SELECT
							selectDx.each(function() {
								rightSel.append($(this).clone().attr("selected", true));
								el.append($(this).attr("selected", true));
							});
						}
						else if ($(this).hasClass("MoveUp")) {
							var	prev = selectedDx.first().prev();
							var	hPrev = el.find("[value='" + prev.val() + "']");

							selectedDx.each(function() {
								$(this).insertBefore(prev);
								el.find("[value='" + $(this).val() + "']").insertBefore(hPrev);	// HIDDEN SELECT
							});
						}
						else if ($(this).hasClass("MoveDown")) {
							var	next = selectedDx.last().next();
							var	hNext = el.find("[value='" + next.val() + "']");

							selectedDx.each(function() {
								$(this).insertAfter(next);
								el.find("[value='" + $(this).val() + "']").insertAfter(hNext);	// HIDDEN SELECT
							});
						}
						else if ($(this).hasClass("MoveTop")) {
							var	first = selectDx.first();
							var	hFirst = el.find("[value='" + first.val() + "']");

							selectedDx.each(function() {
								$(this).insertBefore(first);
								el.find("[value='" + $(this).val() + "']").insertBefore(hFirst);	// HIDDEN SELECT
							});
						}
						else if ($(this).hasClass("MoveBottom")) {
							var	last = selectDx.last();
							var	hLast = el.find("[value='" + last.val() + "']");

							selectedDx.each(function() {
								last = $(this).insertAfter(last);	// WITH last = SAME POSITION OF SELECTED OPTION AFTER MOVE
								hLast = el.find("[value='" + $(this).val() + "']").insertAfter(hLast);	// HIDDEN SELECT
							});
						}
					}

					leftSel.trigger('change');
				});

				// HOVER ON OPTION
				$(this).next().find('.ms2side__options, .ms2side__updown').children().hover(
					function () {
						$(this).addClass('ms2side_hover');
					},
					function () {
						$(this).removeClass('ms2side_hover');
					}
				);

				// UPDATE BUTTON ON START
				leftSel.trigger('change');
				// SHOW WHEN ALL READY
				$(this).next().show();
			});
		},
		destroy : function( ) {
			return this.each(function () {
				var	el = $(this);
				var data = el.data('multiselect2side');

				if (!data)
					return;

				el.show().next().remove();
			});
		},
		/**
		* 用于option添加的数据排序功能
		* @param{Object} 备选框jquery对象
		* @param{Object} 已选框jquery对象
		* @param{Number} 排序的位置
		* @param{String} 排序的属性
		* @param{Object} 需要进行排序的对象
		*/
		orderAppend : function(leftSel,rightSel,orderNum,orderName,strEl,oAddOption){
			var indexResult = -1;
			if(oAddOption.selected){
				rightSel.children().each(function(index, el) {//判断当前添加option的位置 已选框
					try{
						var orderResult = Number(orderNum) < Number($(el).attr(orderName));
						if(orderResult){
							indexResult=index;
						    return false;
						};
					}catch(e){
						alert("error!in orderAppend !"+e);
					}
				});
			}else{
				leftSel.children().each(function(index, el) {//判断当前添加option的位置 备选框
					try{
						var orderResult = Number(orderNum) < Number($(el).attr(orderName));
						if(orderResult){
							indexResult=index;
						    return false;
						};
					}catch(e){
						alert("error!in orderAppend !"+e);
					}
				});
			}
			if (indexResult == -1 ) { //最开始的位置正常添加
				if (oAddOption.selected)
					rightSel.append(strEl).trigger('change');
				else
					leftSel.append(strEl).trigger('change');
			}else{
				if (oAddOption.selected){
					var obj = rightSel.children().eq(indexResult);
					obj.before(strEl).trigger('change');
				}
				else{
					var obj = leftSel.children().eq(indexResult);
						obj.before(strEl).trigger('change');
				}
				
			
			}
		},
		addOption : function(options,order) {
			var oAddOption = {
				name: false,
				value: false,
				selected: false
			};
			return this.each(function () {
			
				var	el = $(this);
				var data = el.data('multiselect2side');

				if (!data)
					return;

				if (options)
					$.extend(oAddOption, options);
				//这里改造一下
				var strEl = $('<option>');
					strEl.attr(oAddOption);
					strEl.text(oAddOption.value);
				//var	strEl = "<option value='" + oAddOption.value + "' " + (oAddOption.selected ? "selected" : "") + " >" + oAddOption.name + "</option>";

				el.append(strEl);

				// ELEMENTS
				var allSel = el.next().children(".ms2side__select").children("select");
				var	leftSel = (data.selectedPosition == 'right') ? allSel.eq(0) : allSel.eq(1);
				var	rightSel = (data.selectedPosition == 'right') ? allSel.eq(1) : allSel.eq(0);
				//判断是否需要排序 这里的order 是传递过来的.因为这里不能拿到初始化属性 o
				if (order != undefined && order != null && order != '') {
					methods.orderAppend(leftSel,rightSel,oAddOption[order],order,strEl,oAddOption);
				}else{
					if (oAddOption.selected)
						rightSel.append(strEl).trigger('change');
					else
						leftSel.append(strEl).trigger('change');
				}
			});
		}
	};

  $.fn.multiselect2side = function( method ) {
    if ( methods[method] ) {
      return methods[method].apply( this, Array.prototype.slice.call( arguments, 1 ));
    } else if ( typeof method === 'object' || ! method ) {
      return methods.init.apply( this, arguments );
    } else {
      $.error( 'Method ' +  method + ' does not exist on jQuery.multiselect2side' );
    }    
  };

})(jQuery);