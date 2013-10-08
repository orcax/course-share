$(function(){
  
  /***** Global variables. *****/

  var P_TYPE_CLS = {
    '概念题': 'cs-frame-grey',
    '填空题': 'cs-frame-blue',
    '选择题': 'cs-frame-green',
    '问答题': 'cs-frame-yellow',
    '综合题': 'cs-frame-red'
  };
    
  var P_DIFF_CLS = {
    '1': 'btn-primary',
    '2': 'btn-info',
    '3': 'btn-success',
    '4': 'btn-warning',
    '5': 'btn-danger'
  };

  var URL = ROOT + 'problemset/list';
  
  var _getOffset = function() {
    var offset = parseInt($('#ps-offset').val());
    if(isNaN(offset) || offset < 0)
      offset = 0;
    return offset;
  };
  
  var _setOffset = function(offset) {
    var offset = parseInt(offset);
    if(isNaN(offset) || offset < 0)
      offset = 0;
    var old = _getOffset();
    if (offset >= old){
      $('#ps-offset').val(offset + 1);
    }
  };
 
  /**
   * Get filter parameters from page.
   */
  var _getParams = function() {
    var content = $('#ps-search-content').val();
    var know = $('#ps-search-know').val();
    var types = [], diffs = [];
    $('input[name="ps-type"][id!="type-all"]:checked').each(function() {
      types.push($(this).attr('id'));
    });
    $('input[name="ps-diff"][id!="diff-all"]:checked').each(function() {
      diffs.push($(this).attr('id'));
    }); 
    return {
      problem_type: types.join(','),
      difficulty: diffs.join(','),
      problem_content: content,
      knowledge: know,
      offset: _getOffset()
    };
  };
   
  /**
   * Visualize problems.
   */
  var _makeProblems = function(problems) {
    var list = [], max = -1;
    $.each(problems, function(k, v){
      var p = _.template($('#problem-tpl').html(), {
        'id': v.id,
        'type': v.problemType,
        'typeCls': P_TYPE_CLS[v.problemType],
        'diff': v.difficulty,
        'diffCls': P_DIFF_CLS[v.difficulty],
        'content': v.problemContent,
        'know': v.knowledge,
        'key': v.keyContent
      });
      list.push($.trim(p));
      max = parseInt(v.id) > max ? parseInt(v.id) : max;
    });
    if(list.length <= 0)
      return false;
    _setOffset(max);
    list = $(list.join(''));
    list.find('button.key').click(function(){
      var pid = $(this).attr('id');
      var key = $('div.ps-key[id="'+pid+'"]');
      if(key.is(':visible')){
        key.slideUp();
        $(this).text('答案');      
      }
      else{
        key.slideDown();
        $(this).text('隐藏');
      }
    });
    list.find('button.paper').click(function(){
      var pid = $(this).attr('id');
      if($(this).hasClass('btn-success')) {
        $(this).removeClass('btn-success').addClass('btn-danger').text('取消');
        $('div.ps-row[id="'+pid+'"]').addClass('cs-border-red');
      }
      else if($(this).hasClass('btn-danger')) {
        $(this).removeClass('btn-danger').addClass('btn-success').text('出题');
        $('div.ps-row[id="'+pid+'"]').removeClass('cs-border-red');
      }
    });
    $('#problemset-list').append(list);
  };
  
  /**
   * Enable scroll pagination.
   */
  var _enableScroll = function() {
    $('#problemset-list').scrollPagination({
      url: URL,
      param: _getParams(),
      method: 'GET',
      scrollTarget: $(window),
      heightOffset: 10,
      beforeLoad: function() {
        $('#problemset-load').fadeIn();
        this.param = _getParams();
      },
      afterLoad: function(data) {
        $('#problemset-load').fadeOut();
        if (data == null || data.length <= 0){
          _disableScroll();
          return false;
        }
        _makeProblems(data);
      }  
    });
  };
  
  /**
   * Disable scroll pagination.
   */
  var _disableScroll = function() {
    $('#problemset-list').stopScrollPagination();
  };
  
  /**
   * Refresh problems.
   */
  var _refresh = function() {
    $('#problemset-list').html('');
    $('#ps-offset').val(0);
    _enableScroll();
//    $.getJSON(URL, _getParams(), function(data) {
//      if (data == null || data.length <= 0){
//        _disableScroll();
//        return false;
//      }
//      _makeProblems(data);
//      _enableScroll();
//    });
  };
  
  /***** Global Event *****/
  
  _enableScroll();
  
  $('button#ps-search').click(function() {
    _refresh();
  });
  
  $('button#ps-paper').click(function() {
    var list = [];
    $('button.paper.btn-danger').each(function() {
      list.push($(this).attr('id'));
    });
    if(list.length <= 0) {
      alert('请选择题目');
      return false;
    }
    window.location.href = ROOT + 'problemset/paper?pids=' + list.join(',');
  });
  
  $('input[name="ps-type"]').click(function() {
    var tid = $(this).attr('id');
    if($(this).is(':checked')) {
      if(tid == 'type-all') {
        $('input[name="ps-type"][id!="type-all"]').prop('checked', false);
      }
      else{
        $('input[name="ps-type"][id="type-all"]').prop('checked', false);
      }
    }
    else {
      if($('input[name="ps-type"]:checked').size() <= 0){
        $('input[name="ps-type"][id="type-all"]').prop('checked', true);        
      }
    }
  });
  
  $('input[name="ps-diff"]').click(function() {
    var did = $(this).attr('id');
    if($(this).is(':checked')) {
      if(did == 'diff-all') {
        $('input[name="ps-diff"][id!="diff-all"]').prop('checked', false);
      }
      else{
        $('input[name="ps-diff"][id="diff-all"]').prop('checked', false);
      }
    }
    else {
      if($('input[name="ps-diff"]:checked').size() <= 0){
        $('input[name="ps-diff"][id="diff-all"]').prop('checked', true);        
      }
    }
  });
  
});