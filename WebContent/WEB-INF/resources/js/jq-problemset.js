$(function(){
    
  var pTypeCls = {
    '概念题': 'cs-frame-grey',
    '填空题': 'cs-frame-blue',
    '选择题': 'cs-frame-green',
    '问答题': 'cs-frame-yellow',
    '综合题': 'cs-frame-red'
  };
  
  var pDiffCls = {
    '1': 'btn-primary',
    '2': 'btn-info',
    '3': 'btn-success',
    '4': 'btn-warning',
    '5': 'btn-danger'
  };
  
  var format = function(p) {
    return {
      'id': p.id,
      'type': p.problemType,
      'typeCls': pTypeCls[p.problemType],
      'diff': p.difficulty,
      'diffCls': pDiffCls[p.difficulty],
      'content': p.problemContent,
      'know': p.knowledge,
      'key': p.keyContent
    };
  };
    
  var makeProblems = function(problems) {
    var list = [];
    $.each(problems, function(k, v){
      var p = _.template($('#problem-tpl').html(), format(v));
      list.push(p.trim());
    });
    if(list.length <= 0)
      return false;
    list = $(list.join(''));
    list.find('button.key').click(function(){
      var pid = $(this).attr('id');
      var key = $('div.ps-key[id="'+pid+'"]');
      if(key.is(':visible')){
        key.slideUp();
      }
      else{
        key.slideDown();
      }
    });
    list.find('button.paper').click(function(){
      
    });
    $('#problemset-list').append(list);
  };
  
  $('#problemset-list').scrollPagination({
    url: ROOT + 'problemset/list',
    method: 'GET',
    param: {},
    scrollTarget: $(window),
    heightOffset: 10,
    beforeLoad: function() {
      $('#problemset-load').fadeIn();
    },
    afterLoad: function(data) {
      $('#problemset-load').fadeOut();
      makeProblems(data);
    }  
  });
  
});